package com.ac101m.seedgrind.util;

import kaptainwutax.seedutils.mc.Dimension;
import kaptainwutax.seedutils.mc.MCVersion;

public class WorldSampler {
    protected final MCVersion mcVersion;
    protected final long seed;
    protected final Dimension dimension;

    public WorldSampler(MCVersion mcVersion, long seed, Dimension dimension) {
        this.mcVersion = mcVersion;
        this.seed = seed;
        this.dimension = dimension;
    }

    public MCVersion getMcVersion() { return this.mcVersion; }
    public long getSeed() { return this.seed; }
    public Dimension getDimension() { return this.dimension; }
}
