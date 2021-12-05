package com.gentree.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RelationTest
{
    private final Person person1 = new Person("John Doe", "man");
    private final Person person2 = new Person("Jane Doe", "woman");
    private final Relation relation = new Relation(person1, person2, "husband");

    @Test
    @DisplayName("Test get person1")
    public void testGetPerson1()
    {
        assertEquals(person1, relation.getPerson1());
    }

    @Test
    @DisplayName("Test get person2")
    public void testGetPerson2()
    {
        assertEquals(person2, relation.getPerson2());
    }

    @Test
    @DisplayName("Test relation bond")
    public void testRelationBond()
    {
        assertEquals(Relation.Bond.HUSBAND, relation.getBond());
    }
}
