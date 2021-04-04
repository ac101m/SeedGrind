package com.ac101m.seedgrind.biome.search;

import com.ac101m.seedgrind.biome.sampler.BiomeSampler;
import com.ac101m.seedgrind.util.Area;
import com.ac101m.seedgrind.util.SeedGrindException;
import com.ac101m.seedgrind.util.Vec2;
import kaptainwutax.biomeutils.Biome;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements a simple brute force search on a power-of-two aligned grid
 * Very simple brute-force implementation with no memoization.
 */
public class SimpleEngine extends Engine {

    public SimpleEngine(BiomeSampler biomeSampler, int resolution, Area boundary) {
        super(biomeSampler, resolution, boundary);
    }

    public Set<Biome> findAllBiomes(Area searchArea) throws SeedGrindException {
        this.assertWithinBoundary(searchArea);

        // Scale the search area to match the engine resolution
        Area scaledArea = new Area(searchArea);
        scaledArea.expandPower2(this.resolution);
        scaledArea.scale(1.0 / (1 << this.resolution));

        HashSet<Biome> biomes = new HashSet<>();

        for (int z = scaledArea.min.z; z < scaledArea.max.z; z++) {
            for (int x = scaledArea.min.x; x < scaledArea.max.x; x++) {
                biomes.add(biomeSampler.getBiome(x << this.resolution, 0, z << this.resolution));
            }
        }

        return biomes;
    }

    @Override
    public boolean biomePresent(Area searchArea, Biome biome) throws SeedGrindException {
        return this.findAllBiomes(searchArea).contains(biome);
    }

    @Override
    public boolean allBiomesPresent(Area searchArea, Set<Biome> biomes) throws SeedGrindException {
        Set<Biome> presentBiomes = this.findAllBiomes(searchArea);

        for (Biome biome : biomes) {
            if(!presentBiomes.contains(biome)) {
                return false;
            }
        }

        return true;
    }
}
