package com.ac101m.seedgrind.util;

public class HumanReadable {
    private static final String[] s_SIUnits = {"", "k", "M", "G", "T", "P", "E", "Z", "Y"};

    public static String SI(double value) {
        String units = "";

        for (String s_siUnit : s_SIUnits) {
            if (value < 1000) {
                units = s_siUnit;
                break;
            }
            value /= 1000;
        }

        return value + units;
    }
}
