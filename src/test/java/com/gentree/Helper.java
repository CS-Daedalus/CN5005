package com.gentree;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
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

    /**
     * Allowing to create a new instance of a singleton class for testing purposes.
     * @param c Class of the singleton class.
     * @param <T> Type of the singleton class.
     * @return New instance of the singleton class.
     */
    public static <T> @NotNull T createSingletonInstance(Class<T> c)
    {
        try
        {
            Constructor<T> instance = c.getDeclaredConstructor();
            instance.setAccessible(true);
            return instance.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
