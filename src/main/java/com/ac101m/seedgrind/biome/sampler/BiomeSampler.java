package com.ac101m.seedgrind.sampler;

import com.ac101m.seedgrind.util.SeedGrindException;
import com.ac101m.seedgrind.util.Vec3;
import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.biomeutils.source.EndBiomeSource;
import kaptainwutax.biomeutils.source.NetherBiomeSource;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.seedutils.mc.Dimension;
import kaptainwutax.seedutils.mc.MCVersion;

public class BiomeSampler extends Sampler {
    protected final BiomeSource m_biomeSource;

    public BiomeSampler(MCVersion mcVersion, Dimension dimension, long seed)
            throws SeedGrindException {

        super(mcVersion, dimension, seed);

        switch (dimension) {
            case OVERWORLD:
                m_biomeSource = new OverworldBiomeSource(mcVersion, seed);
                break;
            case NETHER:
                m_biomeSource = new NetherBiomeSource(mcVersion, seed);
                break;
            case END:
                m_biomeSource = new EndBiomeSource(mcVersion, seed);
                break;
            default:
                throw new SeedGrindException("Unrecognised dimension '" + dimension + "'");
        }
    }

    public Biome getBiome(Vec3 position) {
        return getBiome(position.x, position.y, position.z);
    }

    public Biome getBiome(int x, int y, int z) {
        m_sampleCount++;
        return m_biomeSource.getBiome(x, y, z);
    }
}
