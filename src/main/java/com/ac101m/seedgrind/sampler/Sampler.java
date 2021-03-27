package com.ac101m.seedgrind.sampler;

import kaptainwutax.seedutils.mc.Dimension;
import kaptainwutax.seedutils.mc.MCVersion;

public class Sampler {
    protected final MCVersion m_mcVersion;
    protected final Dimension m_dimension;
    protected final long m_seed;
    protected long m_sampleCount;

    public Sampler(MCVersion mcVersion, Dimension dimension, long seed) {
        m_mcVersion = mcVersion;
        m_dimension = dimension;
        m_seed = seed;
        m_sampleCount = 0;
    }

    public long getSeed() { return m_seed; }
    public MCVersion getMcVersion() { return m_mcVersion; }
    public long getSampleCount() { return m_sampleCount; }
}
