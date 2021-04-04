package com.ac101m.seedgrind.biome.search;

import com.ac101m.seedgrind.biome.sampler.BiomeSampler;
import com.ac101m.seedgrind.util.Area;
import com.ac101m.seedgrind.util.SeedGrindException;
import com.ac101m.seedgrind.util.Vec2;

import kaptainwutax.biomeutils.Biome;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Quadtree based memoized algorithm
 */
public class MemoizedEngine extends Engine {
    protected SearchTile root;

    public MemoizedEngine(BiomeSampler biomeSampler, int resolution, Area boundary) throws SeedGrindException {
        super(biomeSampler, resolution, boundary);

        Area expandedBoundary = new Area(boundary);
        expandedBoundary.expandPower2(resolution);

        int maxXMagnitude = Math.max(Math.abs(expandedBoundary.min.x), Math.abs(expandedBoundary.max.x));
        int maxZMagnitude = Math.max(Math.abs(expandedBoundary.max.z), Math.abs(expandedBoundary.max.z));

        int radius = 1;

        while (radius < Math.max(maxXMagnitude, maxZMagnitude)) {
            radius <<= 1;
        }

        int rootSearchTileRadius = radius >> resolution;

        Area area = new Area(new Vec2(0, 0), rootSearchTileRadius);
        this.root = new SearchTile(biomeSampler, area, resolution);
    }

    @Override
    public Set<Biome> findAllBiomes(Area searchArea) throws SeedGrindException {
        Set<Biome> biomes = new HashSet<>();

        for (Map.Entry<Integer, Biome> entry : Biome.REGISTRY.entrySet()) {
            Biome biome = entry.getValue();
            if (this.biomePresent(searchArea, biome)) {
                biomes.add(biome);
            }
        }

        return biomes;
    }

    @Override
    public boolean biomePresent(Area searchArea, Biome biome) throws SeedGrindException {
        this.assertWithinBoundary(searchArea);

        Area scaledArea = new Area(searchArea);
        scaledArea.expandPower2(this.resolution);
        scaledArea.scale(1.0 / (1 << this.resolution));

        return this.root.isBiomePresent(scaledArea, biome);
    }

    @Override
    public boolean allBiomesPresent(Area searchArea, Set<Biome> biomes) throws SeedGrindException {
        for (Biome biome : biomes) {
            return this.biomePresent(searchArea, biome);
        }
        return true;
    }
}
