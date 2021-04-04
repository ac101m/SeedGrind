package com.ac101m.seedgrind.util;

public class StringUtil {

    /**
     * Pads the end of a string with spaces.
     * @param str Input string to pad.
     * @param count Desired number of characters after padding.
     * @return Padded input string.
     */
    public static String padRight(String str, int count) {
        return padRight(str, ' ', count);
    }

    /**
     * Pads the end of a string with the specified character.
     * @param str Input string to pad.
     * @param padChar Character to pad string with.
     * @param count Desired number of characters after padding.
     * @return Padded input string.
     */
    public static String padRight(String str, char padChar, int count) {
        if (str.length() >= count) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < count) {
            sb.append(padChar);
        }
        return sb.toString();
    }

    /**
     * Pads the beginning of a string with spaces.
     * @param str Input string to pad.
     * @param count Desired number of characters after padding.
     * @return Padded input string.
     */
    public static String padLeft(String str, int count) {
        return padLeft(str, ' ', count);
    }

    /**
     * Pads the beginning of a string with the specified character.
     * @param str Input string to pad.
     * @param padChar Character to pad string with.
     * @param count Desired number of characters after padding.
     * @return Padded input string.
     */
    public static String padLeft(String str, char padChar, int count) {
        if (str.length() >= count) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < count - str.length()) {
            sb.append(padChar);
        }
        sb.append(str);
        return sb.toString();
    }
}
