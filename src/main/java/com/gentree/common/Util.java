package com.gentree.common;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Locale;
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
}
