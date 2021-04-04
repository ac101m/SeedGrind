package com.ac101m.seedgrind.biome.sampler;

import com.ac101m.seedgrind.util.WorldSampler;
import com.ac101m.seedgrind.util.SeedGrindException;
import com.ac101m.seedgrind.util.Vec3;
import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.seedutils.mc.Dimension;
import kaptainwutax.seedutils.mc.MCVersion;

public class BiomeSampler extends WorldSampler {
    protected BiomeSource biomeSource;
    protected long sampleCount = 0;

    public static BiomeSampler newSampler(MCVersion mcVersion, long seed, Dimension dimension)
            throws SeedGrindException {

        switch (dimension) {
            case OVERWORLD:
                return new BiomeSamplerOverworld(mcVersion, seed);
            case NETHER:
                return new BiomeSamplerNether(mcVersion, seed);
            case END:
                return new BiomeSamplerEnd(mcVersion, seed);
            default:
                throw new SeedGrindException("Unrecognised dimension '" + dimension + "'");
        }
    }

    protected BiomeSampler(BiomeSource biomeSource) {
        super(biomeSource.getVersion(), biomeSource.getWorldSeed(), biomeSource.getDimension());
        this.biomeSource = biomeSource;
    }

    public Biome getBiome(Vec3 position) {
        return getBiome(position.x, position.y, position.z);
    }

    public Biome getBiome(int x, int y, int z) {
        this.sampleCount++;
        return this.biomeSource.getBiome(x, y, z);
    }

    public long getSampleCount() { return this.sampleCount; }
}
