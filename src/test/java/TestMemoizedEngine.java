import com.ac101m.seedgrind.biome.sampler.BiomeSampler;
import com.ac101m.seedgrind.biome.search.MemoizedEngine;
import com.ac101m.seedgrind.biome.search.SimpleEngine;
import com.ac101m.seedgrind.util.Area;
import com.ac101m.seedgrind.util.SeedGrindException;
import com.ac101m.seedgrind.util.Vec2;

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

        for (int resolution = 0; resolution <= 10; resolution++) {
            for (long seed = 0; seed < 64; seed++) {
                for (int radius = 64; radius <= 32768; radius <<= 1) {

                    // Skip anything that will take too long to process
                    if ((radius * 2) / (1 << resolution) > 64) {
                        continue;
                    }

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
    }
}
