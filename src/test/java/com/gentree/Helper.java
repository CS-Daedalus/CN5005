package com.gentree;

import java.io.File;
import java.util.Objects;

public class Helper
{
    static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public static File getResourceFile(String path)
    {
        return new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
    }
}
