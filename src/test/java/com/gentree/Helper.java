package com.gentree;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;

public class Helper
{
    static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public static File getResourceFile(String path)
        throws UnsupportedEncodingException
    {
        return new File(URLDecoder.decode(
            Objects.requireNonNull(classLoader.getResource(path)).getFile(), "UTF-8"));
    }
}
