package com.ac101m.seedgrind.biome.sampler;

import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.seedutils.mc.Dimension;
import kaptainwutax.seedutils.mc.MCVersion;

public class BiomeSamplerOverworld extends BiomeSampler {
    public BiomeSamplerOverworld(MCVersion mcVersion, long seed) {
        super(new OverworldBiomeSource(mcVersion, seed));
    }
}
