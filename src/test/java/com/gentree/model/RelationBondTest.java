package com.gentree.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RelationBondTest
{
    @Test
    @DisplayName("Test supported bonds")
    public void testSupportedGenders()
    {
        List<String> actual = Relation.Bond.getSupportedBonds();
        List<String> expected = new ArrayList<>(Arrays.asList(
            "MOTHER","FATHER","HUSBAND"
        ));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test supported bond")
    public void testSupportedGender()
    {
        assertTrue(Relation.Bond.isSupported("Mother"));
    }

    @Test
    @DisplayName("Test unsupported bond")
    public void testUnsupportedGender()
    {
        assertFalse(Relation.Bond.isSupported("unsupported"));
    }

    @Test
    @DisplayName("Test gender resolver for supported gender")
    public void testGenderSupportedResolver()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        Method privateResolveGender = Relation.Bond.class.getDeclaredMethod("resolveBond", String.class);
        privateResolveGender.setAccessible(true);

        assertEquals(Relation.Bond.MOTHER, privateResolveGender.invoke(null, "mother"));
    }

    @Test
    @DisplayName("Test bond resolver for unsupported bond")
    public void testGenderUnsupportedResolver()
        throws NoSuchMethodException
    {
        Method privateBondResolver = Relation.Bond.class.getDeclaredMethod("resolveBond", String.class);
        privateBondResolver.setAccessible(true);

        assertThrows(IllegalArgumentException.class, () -> {
            try
            {
                privateBondResolver.invoke(null, "unsupported");
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
        assertEquals("mother", Relation.Bond.MOTHER.toString());
    }
}
