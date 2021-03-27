package com.ac101m.seedgrind.util;

public class HumanReadable {
    private static final String[] s_SIUnits = {"", "k", "M", "G", "T", "P", "E", "Z", "Y"};

    public static String SI(double value) {
        String units = "";

        for (int i = 0; i < s_SIUnits.length; i++) {
            if (value < 1000) {
                units = s_SIUnits[i];
                break;
            }
            value /= 1000;
        }

        return value + units;
    }
}
