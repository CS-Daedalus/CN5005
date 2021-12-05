package com.gentree.common;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility class for common methods and constants.
 */
public final class Util
{
    public static final int APP_WIDTH = 640;
    public static final int APP_HEIGHT = 400;
    public static final String TITLE = "CN5005 - Genealogy Tree";
    public static final String OS = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String USER_HOME_DIR = System.getProperty("user.home");

    /**
     * Utility classes, which are collections of static members, are not meant to be instantiated.
     */
    private Util()
    {

    }

    /**
     * Checks if the OS is Windows.
     * @return true if the OS is Windows, false otherwise.
     */
    public static boolean isWindows()
    {
        return Util.OS.contains("win");
    }

    /**
     * Checks if the OS is Mac.
     * @return true if the OS is Mac, false otherwise.
     */
    public static boolean isMac()
    {
        return Util.OS.contains("mac") || Util.OS.contains("darwin");
    }

    /**
     * Converts a collection of File objects to a collection of Path objects.
     * @param fileList the collection of File objects.
     * @return the collection of Path objects.
     */
    public static Collection<Path> convertListFileToListPath(Collection<File> fileList)
    {
        return fileList.stream().map(File::toPath).collect(Collectors.toList());
    }

    /**
     * Capitalise each word in a string.
     * @param s the string to be capitalised.
     * @return the capitalised string.
     */
    public static String capitalise(String s)
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
    public static String normaliseLower(String s)
    {
        return s.trim().toLowerCase(Locale.getDefault());
    }

    /**
     * Normalise a string to upper case.
     * @param s the string to be normalised.
     * @return the normalised string.
     */
    public static String normaliseUpper(String s)
    {
        return normaliseLower(s).toUpperCase();
    }
}
