package com.gentree.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonGenderTest
{
    @Test
    @DisplayName("Test supported genders")
    public void testSupportedGenders()
    {
        List<String> actual = Person.Gender.getSupportedGenders();
        List<String> expected = new ArrayList<>(Arrays.asList(
            "WOMAN","MAN"
        ));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test supported gender")
    public void testSupportedGender()
    {
        assertTrue(Person.Gender.isSupported("man"));
    }

    @Test
    @DisplayName("Test unsupported gender")
    public void testUnsupportedGender()
    {
        assertFalse(Person.Gender.isSupported("unsupported"));
    }

    @Test
    @DisplayName("Test gender resolver for supported gender")
    public void testGenderSupportedResolver()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        Method privateGenderResolver = Person.Gender.class.getDeclaredMethod("resolveGender", String.class);
        privateGenderResolver.setAccessible(true);

        assertEquals(Person.Gender.WOMAN, privateGenderResolver.invoke(null, "woman"));
    }

    @Test
    @DisplayName("Test gender resolver for unsupported gender")
    public void testGenderUnsupportedResolver()
        throws NoSuchMethodException
    {
        Method privateResolveGender = Person.Gender.class.getDeclaredMethod("resolveGender", String.class);
        privateResolveGender.setAccessible(true);

        assertThrows(IllegalArgumentException.class, () -> {
            try
            {
                privateResolveGender.invoke(null, "unsupported");
            }
            catch (InvocationTargetException e)
            {
                throw e.getCause();
            }
        });
    }

    @Test
    @DisplayName("Test toString method")
    public void testToString()
    {
        assertEquals("woman", Person.Gender.WOMAN.toString());
    }
}
