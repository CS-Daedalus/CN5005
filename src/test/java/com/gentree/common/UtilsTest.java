package com.gentree.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest
{
    @Test
    @DisplayName("Test word capitalisation")
    public void testCapitalisation()
    {
        String test = "FOo bAR";
        String expected = "Foo Bar";
        String actual = Utils.capitalise(test);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test word normalisation to lower case")
    public void testNormaliseLower()
    {
        String test = "FOo bAR";
        String expected = "foo bar";
        String actual = Utils.normaliseLower(test);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test word normalisation to upper case")
    public void testNormaliseUpper()
    {
        String test = "FOo bAR";
        String expected = "FOO BAR";
        String actual = Utils.normaliseUpper(test);

        assertEquals(expected, actual);
    }
}
