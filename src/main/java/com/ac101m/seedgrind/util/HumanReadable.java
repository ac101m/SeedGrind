package com.ac101m.seedgrind.util;

public class HumanReadable {
    private static final String[] s_suUnits = {"", "k", "M", "G", "T", "P", "E", "Z", "Y"};
    private static final String[] s_tUnits = {"ns", "us", "ms", "s"};

    /**
     * @param value Double containing dimensionless quantity.
     * @return Human readable string representation.
     */
    public static String si(double value) {
        return si(value, 3);
    }

    /**
     * @param value Double containing dimensionless quantity.
     * @param dp Number of decimal places to use.
     * @return Human readable string representation.
     */
    public static String si(double value, int dp) {
        String units = "";

        for (String s_siUnit : s_suUnits) {
            if (value < 1000) {
                units = s_siUnit;
                break;
            }
            value /= 1000;
        }

        return String.format("%." + dp + "f", value) + units;
    }

    /**
     * @param time Double containing time in seconds.
     * @return Human readable string representation of time (3 decimal places).
     */
    public static String time(double time) {
        return time(time, 3);
    }

    /**
     * @param time Double containing time in seconds.
     * @param dp Decimal places to use.
     * @return Human readable string representation of time.
     */
    public static String time(double time, int dp) {
        String units = "";

        time *= 1000000000.0;

        for (String s_TUnits : s_tUnits) {
            if (time < 1000) {
                units = s_TUnits;
                break;
            }
            time /= 1000;
        }

        return String.format("%." + dp + "f", time) + units;
    }
}
