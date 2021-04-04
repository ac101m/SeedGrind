import com.ac101m.seedgrind.biome.sampler.BiomeSampler;
import com.ac101m.seedgrind.biome.search.Engine;
import com.ac101m.seedgrind.biome.search.MemoizedEngine;
import com.ac101m.seedgrind.biome.search.SimpleEngine;
import com.ac101m.seedgrind.util.*;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.seedutils.mc.Dimension;
import kaptainwutax.seedutils.mc.MCVersion;

import java.util.*;

import org.junit.Test;

public class TestMemoizedEngine {

    @Test
    public void testFindBiomes1_16_4() throws SeedGrindException {
        MCVersion gameVersion = MCVersion.v1_16_4;
        Dimension dimension = Dimension.OVERWORLD;

        for (long seed = 0; seed < 4; seed++) {
            for (int resolution = 0; resolution < 10; resolution++) {
                int radius = (1 << resolution) * 32;

                System.out.println(
                        "Testing: resolution=" + resolution +
                        " seed=" + seed +
                        " radius=" + radius);

                BiomeSampler simpleEngineSampler = BiomeSampler.newSampler(gameVersion, seed, dimension);
                BiomeSampler memoizedEngineSampler = BiomeSampler.newSampler(gameVersion, seed, dimension);

                Area area = new Area(new Vec2(0, 0), radius);

                Set<Biome> expectedBiomes = new SimpleEngine(simpleEngineSampler, resolution, area).findAllBiomes(area);
                Set<Biome> foundBiomes = new MemoizedEngine(memoizedEngineSampler, resolution, area).findAllBiomes(area);

                assert (expectedBiomes.containsAll(foundBiomes));
                assert (foundBiomes.containsAll(expectedBiomes));
            }
        }
    }

    @Test
    public void testSpeed() throws SeedGrindException {
        System.out.println("Resolution  Samples     duration    Samples/s   sqkm/s");

        long seed = 0;

        for (int resolution = 0; resolution < 10; resolution++) {
            Area area = new Area(new Vec2(0, 0), 256 << resolution);
            BiomeSampler biomeSampler = BiomeSampler.newSampler(MCVersion.v1_16_4, seed, Dimension.OVERWORLD);
            Engine engine = new MemoizedEngine(biomeSampler, resolution, area);

            long tStart = System.nanoTime();
            engine.findAllBiomes(area);
            long tEnd = System.nanoTime();

            double durationSeconds = (double) (tEnd - tStart) / 1000000000.0;
            double sampleRate = biomeSampler.getSampleCount() / durationSeconds;
            double sqkmPerSample = (1 << resolution) * (1 << resolution) / 1000000.0;

            System.out.println(
                    StringUtil.padRight(String.valueOf(resolution), 12) +
                    StringUtil.padRight(String.valueOf(biomeSampler.getSampleCount()), 12) +
                    StringUtil.padRight(HumanReadable.time(durationSeconds), 12) +
                    StringUtil.padRight(HumanReadable.si(sampleRate), 12) +
                    StringUtil.padRight(HumanReadable.si(sqkmPerSample * sampleRate), 12)
            );
        }
    }
}
