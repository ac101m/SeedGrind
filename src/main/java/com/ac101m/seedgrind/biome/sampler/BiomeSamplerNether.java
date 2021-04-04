package com.ac101m.seedgrind.biome.sampler;

import kaptainwutax.biomeutils.source.NetherBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;

public class BiomeSamplerNether extends BiomeSampler {
    public BiomeSamplerNether(MCVersion mcVersion, long seed) {
        super(new NetherBiomeSource(mcVersion, seed));
    }
}
