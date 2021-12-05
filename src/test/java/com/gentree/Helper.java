package com.gentree;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

public class Helper
{
    static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    @Contract("_ -> new")
    public static @NotNull File getResourceFile(String path)
        throws UnsupportedEncodingException
    {
        return new File(URLDecoder.decode(
            Objects.requireNonNull(classLoader.getResource(path)).getFile(), "UTF-8"));
    }
}
