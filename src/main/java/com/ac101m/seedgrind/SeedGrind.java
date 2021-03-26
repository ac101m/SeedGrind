package com.ac101m.seedgrind;

import org.docopt.Docopt;

import java.util.Map;

public class SeedGrind {
    private static final String s_doc =
            "[SeedGrind]\n" +
            "A command line tool for finding seeds with given characteristics.\n" +
            "\n" +
            "Usage:\n" +
            "  seedgrind (-h | --help)\n" +
            "\n" +
            "Options:\n" +
            "  -h --help    Display this help message.\n";

    public static void main(String[] args) {
        Map<String, Object> opts = new Docopt(s_doc).parse(args);
        System.out.println(opts);
    }
}
