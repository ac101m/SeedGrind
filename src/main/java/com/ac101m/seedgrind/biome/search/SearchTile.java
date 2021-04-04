package com.ac101m.seedgrind.biome.search;

import com.ac101m.seedgrind.biome.sampler.BiomeSampler;
import com.ac101m.seedgrind.util.Area;
import com.ac101m.seedgrind.util.SeedGrindException;
import com.ac101m.seedgrind.util.Vec2;

import kaptainwutax.biomeutils.Biome;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A component of the spatially memoized search algorithm
 */
public class SearchTile extends Area {
    private final BiomeSampler biomeSampler;
    private final int resolution;

    private final HashSet<Biome> biomes = new HashSet<>();

    private SearchTile[] subTiles = null;

    private boolean searchedLocally = false;
    private boolean searchedExhaustively = false;

    /**
     * Check if tile dimensions are valid
     * @throws SeedGrindException if they aren't
     */
    private void checkTileDimensions() throws SeedGrindException {
        if ((this.width() & (this.width() - 1)) != 0) {
            throw new SeedGrindException("Tile width must be a power of two, got '" + this.width() + "'.");
        }
        if ((this.height() & (this.height() - 1)) != 0) {
            throw new SeedGrindException("Tile height must be a power of two, got '" + this.height() + "'.");
        }
    }

    /**
     * Initialize from area
     * @param biomeSampler sampler to use for sampling the worldspace.
     * @param area Area to limit sampling to.
     * @throws SeedGrindException If anything goes wonky.
     */
    public SearchTile(BiomeSampler biomeSampler, Area area, int resolution) throws SeedGrindException {
        super(area.min, area.max);
        this.checkTileDimensions();
        this.biomeSampler = biomeSampler;
        this.resolution = resolution;
    }

    /**
     * Initialize local sub-tiles
     */
    private void initSubTiles() throws SeedGrindException {
        this.subTiles = new SearchTile[4];
        Vec2 center = this.center();

        Area topLeft = new Area(center, new Vec2(min.x, max.z));
        Area topRight = new Area(center, max);
        Area bottomLeft = new Area(center, min);
        Area bottomRight = new Area(center, new Vec2(max.x, min.z));

        this.subTiles[0] = new SearchTile(this.biomeSampler, topLeft, this.resolution);
        this.subTiles[1] = new SearchTile(this.biomeSampler, topRight, this.resolution);
        this.subTiles[2] = new SearchTile(this.biomeSampler, bottomLeft, this.resolution);
        this.subTiles[3] = new SearchTile(this.biomeSampler, bottomRight, this.resolution);
    }

    /**
     * Get a set of all biomes within this search tile.
     * This call may be expensive as it requires an exhaustive search.
     * @return Hashset containing all biomes within this tile
     */
    public Set<Biome> getBiomes() {
        return new HashSet<>();
    }

    /**
     * Search the entire tile first locally, then recursively to a specified depth
     * @return true as soon as the biome is found, or false otherwise
     */
    private boolean search(Biome biome, int maxDepth) throws SeedGrindException {

        // Run local search if one hasn't been done here already
        if (!this.searchedLocally) {
            Vec2 center = this.center();
            Biome searchBiome = this.biomeSampler.getBiome(
                    this.min.x << this.resolution,
                    0,
                    this.min.z << this.resolution);
            this.biomes.add(searchBiome);
            this.searchedLocally = true;
            this.searchedExhaustively = this.size() == 1;
            if (searchBiome == biome) {
                return true;
            }
        }

        // Run a recursive search if the depth is greater than zero and the tile
        // hasn't already been exhaustively searched
        if (!this.searchedExhaustively && maxDepth > 0) {
            if (this.subTiles == null) {
                this.initSubTiles();
            }

            for (SearchTile tile : this.subTiles) {
                boolean biomeFound = tile.search(biome, maxDepth - 1);
                this.biomes.addAll(tile.biomes);
                if (biomeFound) {
                    return true;
                }
            }

            this.searchedExhaustively =
                    subTiles[0].searchedExhaustively && subTiles[1].searchedExhaustively &&
                    subTiles[2].searchedExhaustively && subTiles[3].searchedExhaustively;
        }

        return this.biomes.contains(biome);
    }

    /**
     * Used to search within a given area for a given biome
     * @param searchArea area to search in
     * @param biome biome to search for
     * @return true if biome is present within search area, false otherwise
     */
    public boolean isBiomePresent(Area searchArea, Biome biome) throws SeedGrindException {
        Optional<Area> overlap = Area.overlap(searchArea, this);

        // If the search area does not align with this tile, return false
        if (!overlap.isPresent()) {
            return false;
        }

        // If the overlap matches this tile completely, search the tile
        if (Area.equals(overlap.get(), this)) {
            int maxDepth = 0;
            while (!this.searchedExhaustively) {
                if (this.search(biome, maxDepth++)) {
                    return true;
                }
            }

        // Otherwise, pass the search to sub-tiles
        } else {
            if (this.subTiles == null) {
                this.initSubTiles();
            }
            for (SearchTile tile : this.subTiles) {
                if (tile.isBiomePresent(searchArea, biome)) {
                    return true;
                }
            }
        }

        return this.biomes.contains(biome);
    }
}
