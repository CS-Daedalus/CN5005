package com.gentree.common;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Locale;

/**
 * Utility class for common methods and constants.
 */
public final class Utils
{
    public static final int APP_WIDTH = 640;
    public static final int APP_HEIGHT = 400;
    public static final String TITLE = "CN5005 - Genealogy Tree";
    public static final String OS = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
    public static final String USER_HOME_DIR = System.getProperty("user.home");
    public static final String USER_DOWNLOAD_DIR = USER_HOME_DIR + File.separator + "Downloads";

    /**
     * Utility classes, which are collections of static members, are not meant to be instantiated.
     */
    private Utils()
    {

    }

    /**
     * Checks if the OS is Windows.
     * @return true if the OS is Windows, false otherwise.
     */
    public static boolean isWindows()
    {
        return Utils.OS.contains("win");
    }

    /**
     * Checks if the OS is Mac.
     * @return true if the OS is Mac, false otherwise.
     */
    public static boolean isMac()
    {
        return Utils.OS.contains("mac") || Utils.OS.contains("darwin");
    }

    /**
     * Capitalise each word in a string.
     * @param s the string to be capitalised.
     * @return the capitalised string.
     */
    public static @NotNull String capitalise(@NotNull String s)
    {
        if (s.length() == 0)
            return s;
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String word : words)
        {
            word = normaliseLower(word);

            sb.append(Character.toUpperCase(word.charAt(0)))
              .append(word.substring(1)).append(" ");
        }

        return sb.toString().trim();
    }

    /**
     * Normalise a string to lower case.
     * @param s the string to be normalised.
     * @return the normalised string.
     */
    public static @NotNull String normaliseLower(@NotNull String s)
    {
        return s.trim().toLowerCase(Locale.getDefault());
    }

    /**
     * Normalise a string to upper case.
     * @param s the string to be normalised.
     * @return the normalised string.
     */
    public static @NotNull String normaliseUpper(String s)
    {
        return normaliseLower(s).toUpperCase();
    }
}
