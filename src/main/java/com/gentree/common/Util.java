package com.gentree.common;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for common methods and constants.
 */
public class Util
{
    /**
     * Utility classes, which are collections of static members, are not meant to be instantiated.
     */
    private Util() {}

    public static final int APP_WIDTH = 640;
    public static final int APP_HEIGHT = 400;
    public static final String TITLE = "CN5005 - Genealogy Tree";
    public static final String OS = System.getProperty("os.name").toLowerCase();
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

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
     * Converts a list of File objects to a list of Path objects.
     * @param fileList the list of File objects.
     * @return the list of Path objects.
     */
    public static List<Path> convertListFileToListPath(List<File> fileList)
    {
        return fileList.stream().map(File::toPath).collect(Collectors.toList());
    }
}
