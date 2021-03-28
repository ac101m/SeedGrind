package com.ac101m.seedgrind;

import com.ac101m.seedgrind.biome.sampler.BiomeSampler;
import com.ac101m.seedgrind.util.SeedGrindException;
import com.ac101m.seedgrind.util.Vec3;
import kaptainwutax.biomeutils.Biome;
import kaptainwutax.seedutils.mc.Dimension;
import kaptainwutax.seedutils.mc.MCVersion;
import org.docopt.Docopt;

import java.util.Map;

public class SeedGrind {
    private static final String s_help =
            "[SeedGrind]\n" +
            "A command line tool for finding minecraft seeds with specified characteristics.\n" +
            "\n" +
            "Usage:\n" +
            "  seedgrind (-h | --help)\n" +
            "  seedgrind biome <seed> <x> <y> <z> [options]\n"+
            "\n" +
            "Options:\n" +
            "  -h --help                Display this help message.\n" +
            "  -v --version=<version>   Minecraft version. [default: 1.16.4]\n" +
            "  -w --dimension=<name>    World to search. [default: overworld]\n";

    public static void biomeCommand(MCVersion gameVersion, Dimension dimension, Map<String, Object> opts)
            throws SeedGrindException {

        final Vec3 position = new Vec3();
        long seed = 0;

        try {
            position.x = Integer.parseInt((String)opts.get("<x>"));
            position.y = Integer.parseInt((String)opts.get("<y>"));
            position.z = Integer.parseInt((String)opts.get("<z>"));
        } catch (NumberFormatException e) {
            System.out.println("Coordinates must be integers!");
            System.exit(1);
        }

        try {
            seed = Integer.parseInt((String)opts.get("<seed>"));
        } catch (NumberFormatException e) {
            System.out.println("Seed must be an integer!");
            System.exit(1);
        }

        BiomeSampler biomeSampler = BiomeSampler.newSampler(gameVersion, seed, dimension);
        Biome biome = biomeSampler.getBiome(position);

        System.out.println("game version: " + gameVersion.name());
        System.out.println("dimension: " + dimension);
        System.out.println("Seed: " + seed);
        System.out.println("coordinates: " + position);
        System.out.println("biome: " + biome.getName());
    }

    public static void command(Map<String, Object> opts) throws SeedGrindException {

        final String versionString = (String)opts.get("--version");
        final MCVersion gameVersion = MCVersion.fromString(versionString);

        if (gameVersion == null) {
            System.out.println("'" + versionString + "' is not a valid game version!");
            System.exit(1);
        }

        final String dimensionString = (String)opts.get("--dimension");
        final Dimension dimension = Dimension.fromString(dimensionString);

        if (dimension == null) {
            System.out.println("'" + dimensionString + "' is not a valid dimension!");
            System.exit(1);
        }

        if ((Boolean) opts.get("biome")) {
            biomeCommand(gameVersion, dimension, opts);
        } else {
            System.out.println("Nothing to be done! See -h or --help for usage instructions.");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        final Map<String, Object> opts = new Docopt(s_help).parse(args);

        try {
            command(opts);
        } catch (SeedGrindException e) {
            System.out.println("Error: " + e);
            System.exit(1);
        }
    }
}
