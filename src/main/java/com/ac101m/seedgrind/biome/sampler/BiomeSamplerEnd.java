package com.ac101m.seedgrind.biome.sampler;

import kaptainwutax.biomeutils.source.EndBiomeSource;
import kaptainwutax.seedutils.mc.Dimension;
import kaptainwutax.seedutils.mc.MCVersion;

public class BiomeSamplerEnd extends BiomeSampler {
    public BiomeSamplerEnd(MCVersion mcVersion, long seed) {
        super(new EndBiomeSource(mcVersion, seed));
    }
}
