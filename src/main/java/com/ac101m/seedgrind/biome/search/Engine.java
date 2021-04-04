package com.ac101m.seedgrind.biome.search;

import com.ac101m.seedgrind.biome.sampler.BiomeSampler;
import com.ac101m.seedgrind.util.Area;
import com.ac101m.seedgrind.util.SeedGrindException;

import kaptainwutax.biomeutils.Biome;

import java.util.Optional;
import java.util.Set;

public abstract class Engine {
    protected final BiomeSampler biomeSampler;
    protected final Area boundary;
    protected final int resolution;

    /**
     * @param biomeSampler Sampler to use for biome lookups.
     * @param resolution Resolution of search. Specifies power of two grid:
     *                   0 = every block,
     *                   1 = every other block,
     *                   2 = every fourth block, etc...
     * @param boundary Area defining edge of the sampling area. This will be
     *                 expanded to the nearest multiple power of two.
     */
    public Engine(BiomeSampler biomeSampler, int resolution, Area boundary) {
        this.biomeSampler = biomeSampler;
        this.resolution = resolution;
        this.boundary = new Area(boundary);
        this.boundary.expandPower2(resolution);
    }

    /**
     * @param area Area to check against boundary.
     * @throws SeedGrindException if the area is not entirely contained by boundary.
     */
    protected void assertWithinBoundary(Area area) throws SeedGrindException {
        Optional<Area> overlapArea = Area.overlap(area, this.boundary);

        if (!overlapArea.isPresent() || overlapArea.get().size() < area.size()) {
            throw new SeedGrindException("Search area out of bounds");
        }
    }

    /**
     * @param searchArea Area to search.
     * @return Set of Biome objects within the search area.
     */
    public abstract Set<Biome> findAllBiomes(Area searchArea) throws SeedGrindException;

    /**
     * @param searchArea Area to search.
     * @param biome Type of biome to search for
     * @return True if the biome is present within the search area, false otherwise.
     */
    public abstract boolean biomePresent(Area searchArea, Biome biome) throws SeedGrindException;

    /**
     * @param searchArea Area to search.
     * @param biomes Set of biomes to search for.
     * @return True if all biomes are present within the search area, false otherwise.
     */
    public abstract boolean allBiomesPresent(Area searchArea, Set<Biome> biomes) throws SeedGrindException;

    /**
     * Get for internal biome sampler.
     * @return The internal biome sampler object.
     */
    public BiomeSampler getBiomeSampler() { return this.biomeSampler; }
}
