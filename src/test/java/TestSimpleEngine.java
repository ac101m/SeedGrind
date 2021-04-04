import com.ac101m.seedgrind.biome.sampler.BiomeSampler;
import com.ac101m.seedgrind.biome.search.Engine;
import com.ac101m.seedgrind.biome.search.SimpleEngine;
import com.ac101m.seedgrind.util.*;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.seedutils.mc.Dimension;
import kaptainwutax.seedutils.mc.MCVersion;

import java.util.*;

import org.junit.Test;

public class TestSimpleEngine {

    @Test
    public void testFindBiomes1_16_4() throws SeedGrindException {
        Area area = new Area(new Vec2(0, 0), 256);

        Map<Long, Set<String>> expectedResults = new HashMap<>();

        expectedResults.put(1234L, new HashSet<>(Arrays.asList(
                "river",
                "frozen_river",
                "snowy_tundra",
                "snowy_mountains",
                "snowy_taiga"
        )));

        expectedResults.put(4321L, new HashSet<>(Arrays.asList(
                "savanna_plateau",
                "forest",
                "sunflower_plains",
                "desert_hills",
                "savanna",
                "plains",
                "river",
                "desert"
        )));

        expectedResults.put(2345L, new HashSet<>(Arrays.asList(
                "forest",
                "mountains",
                "taiga_mountains",
                "taiga",
                "flower_forest",
                "birch_forest",
                "wooded_mountains",
                "cold_ocean",
                "gravelly_mountains",
                "deep_cold_ocean",
                "beach",
                "river",
                "stone_shore"
        )));

        expectedResults.put(5432L, new HashSet<>(Arrays.asList(
                "forest",
                "ocean",
                "lukewarm_ocean",
                "beach",
                "plains",
                "deep_lukewarm_ocean",
                "river",
                "deep_ocean"
        )));

        for (Long seed : expectedResults.keySet()) {
            Set<String> expectedBiomeNames = expectedResults.get(seed);

            BiomeSampler biomeSampler = BiomeSampler.newSampler(MCVersion.v1_16_4, seed, Dimension.OVERWORLD);
            Engine engine = new SimpleEngine(biomeSampler, 4, area);

            Set<Biome> biomes = engine.findAllBiomes(area);
            Set<String> biomeNames = new HashSet<>();

            for (Biome biome : biomes) {
                biomeNames.add(biome.getName());
            }

            assert (expectedBiomeNames.containsAll(biomeNames));
            assert (biomeNames.containsAll(expectedBiomeNames));
        }
    }

    @Test
    public void testSpeed() throws SeedGrindException {
        System.out.println("Resolution  Samples     duration    Samples/s   sqkm/s");

        long seed = 0;

        for (int resolution = 0; resolution < 10; resolution++) {
            Area area = new Area(new Vec2(0, 0), 256 << resolution);
            BiomeSampler biomeSampler = BiomeSampler.newSampler(MCVersion.v1_16_4, seed, Dimension.OVERWORLD);
            Engine engine = new SimpleEngine(biomeSampler, resolution, area);

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
