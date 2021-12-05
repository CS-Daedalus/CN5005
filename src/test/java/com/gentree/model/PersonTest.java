package com.gentree.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonTest
{
    private final Person stub = new Person("uNkNoWn dOe", "woman");

    @Test
    @DisplayName("Test 4 params constructor")
    public void testFourParamsConstructor()
    {
        Person mother = new Person("Female Doe", "woman");
        Person father = new Person("Father Doe", "man");
        Person person = new Person("John Doe", "man", mother, father);

        assertEquals(mother, person.getMother());
        assertEquals(father, person.getFather());
        assertEquals("John Doe", person.getFullName());
        assertEquals(Person.Gender.MAN, person.getGender());
    }

    @Test
    @DisplayName("Test 3 params constructor")
    public void testThreeParamsConstructor()
    {
        Person mother = new Person("Female Doe", "woman");
        Person person = new Person("John Doe", "man", mother);

        assertEquals(mother, person.getMother());
        assertNull(person.getFather());
        assertEquals("John Doe", person.getFullName());
        assertEquals(Person.Gender.MAN, person.getGender());
    }

    @Test
    @DisplayName("Test full name")
    public void testGetFullName()
    {
        assertEquals("Unknown Doe", stub.getFullName());
    }

    @Test
    @DisplayName("Test setting full name")
    public void testSetFullName()
    {
        stub.setFullName("john doe");

        assertEquals("John Doe", stub.getFullName());
    }

    @Test
    @DisplayName("Test get gender")
    public void testGetGender()
    {
        assertEquals(Person.Gender.WOMAN, stub.getGender());
    }

    @Test
    @DisplayName("Test setting gender")
    public void testSetGender()
    {
        stub.setGender("man");

        assertEquals(Person.Gender.MAN, stub.getGender());
    }

    @Test
    @DisplayName("Test getting mother when not set")
    public void testGetMotherWhenNotSet()
    {
        assertNull(stub.getMother());
    }

    @Test
    @DisplayName("Test setting mother")
    public void testSetMother()
    {
        Person mother = new Person("Female Doe", "woman");
        stub.setMother(mother);

        assertEquals(mother, stub.getMother());
    }

    @Test
    @DisplayName("Test setting self as mother")
    public void testSetSelfAsMother()
    {
        assertThrows(IllegalArgumentException.class, () -> stub.setMother(stub));
    }

    @Test
    @DisplayName("Test getting father when not set")
    public void testGetFatherWhenNotSet()
    {
        assertNull(stub.getFather());
    }

    @Test
    @DisplayName("Test setting father")
    public void testSetFather()
    {
        Person father = new Person("Male Doe", "man");
        stub.setFather(father);

        assertEquals(father, stub.getFather());
    }

    @Test
    @DisplayName("Test setting self as father")
    public void testSetSelfAsFather()
    {
        assertThrows(IllegalArgumentException.class, () -> stub.setFather(stub));
    }

    @Test
    @DisplayName("Test get children when empty")
    public void testGetChildrenWhenEmpty()
    {
        assertEquals(0, stub.getChildren().size());
    }

    @Test
    @DisplayName("Test adding child")
    public void testAddChild()
    {
        Person child = new Person("Baby Doe", "man");
        stub.addChild(child);

        assertEquals(1, stub.getChildren().size());
        assertTrue(stub.getChildren().contains(child));
    }

    @Test
    @DisplayName("Test adding same child")
    public void testAddSameChild()
    {
        Person child = new Person("Baby Doe", "man");
        stub.addChild(child);

        assertThrows(IllegalArgumentException.class, () -> stub.addChild(child));
    }
}
