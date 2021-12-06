package com.gentree.service;

import com.gentree.Helper;
import com.gentree.model.Person;
import com.gentree.model.Relation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class RepositoriesServiceTest
{
    private RepositoriesService stub = null;

    @BeforeAll
    public void setUp()
    {
        // Since RepositoriesService is a singleton, we need to create a new instance
        // with the help of reflection by accessing directly its private constructor.
        stub = Helper.createSingletonInstance(RepositoriesService.class);
    }

    @Test
    @Order(1)
    @DisplayName("Test Singleton behaviour")
    public void testSingleton()
    {
        RepositoriesService repositoriesService = RepositoriesService.getInstance();
        assertSame(RepositoriesService.getInstance(), repositoriesService);
    }

    @Test
    @Order(2)
    @DisplayName("Feed repositories")
    public void testFeedRepositories()
    {
        // Persons stub data
        Deque<Person.Tuple> persons = new ArrayDeque<Person.Tuple>(){{
            add(new Person.Tuple(){{
                fullName = "Female Doe";
                gender = "woman";
            }});

            add(new Person.Tuple(){{
                fullName = "Male Doe";
                gender = "man";
            }});

            add(new Person.Tuple(){{
                fullName = "John Doe";
                gender = "man";
            }});

            add(new Person.Tuple(){{
                fullName = "Jane Doe";
                gender = "woman";
            }});
        }};

        // Relations stub data
        Deque<Relation.Tuple> relations = new ArrayDeque<Relation.Tuple>(){{
           add(new Relation.Tuple(){{
               person1FullName = "Female Doe";
               person2FullName = "John Doe";
               bond = "mother";
           }});

            add(new Relation.Tuple(){{
                person1FullName = "Male Doe";
                person2FullName = "John Doe";
                bond = "father";
            }});

            add(new Relation.Tuple(){{
                person1FullName = "John Doe";
                person2FullName = "Jane Doe";
                bond = "husband";
            }});
        }};

        stub.feed(persons, relations);

        assertEquals(4, stub.getPersonRepository().count());
        assertEquals(3, stub.getRelationRepository().count());
    }

    @Test
    @Order(3)
    @DisplayName("Test get persons names sorted")
    public void testGetPersonsNamesSorted()
    {
        List<String> expected = new ArrayList<String>(){{
            add("Female Doe");
            add("Jane Doe");
            add("John Doe");
            add("Male Doe");
        }};

        assertEquals(expected, stub.getPersonRepository().getPersonsName());
    }

    @Test
    @Order(4)
    @DisplayName("Test get Person from Person Repository")
    public void testGetPersonFromPersonRepository()
    {
        Person person = stub.getPersonRepository().findOne("John Doe");

        assertNotNull(person);
        assertEquals("John Doe", person.getFullName());
    }

    @Test
    @Order(5)
    @DisplayName("Test get all Persons from Person Repository")
    public void testGetAllPersonsFromPersonRepository()
    {
        Iterable<Person> persons = stub.getPersonRepository().findAll();

        assertEquals(4, ((Collection<?>) persons).size());
    }

    @Test
    @Order(6)
    @DisplayName("Test get Relation from Relation Repository")
    public void testGetRelationFromRelationRepository()
    {
        Optional<Relation> relation = stub.getRelationRepository()
                                          .findOne("John Doe", "Jane Doe");

        assertTrue(relation.isPresent());
        assertEquals(Relation.Bond.HUSBAND, relation.get().getBond());
    }

    @Test
    @Order(7)
    @DisplayName("Test get all Relations from Relation Repository")
    public void testGetAllRelationsFromRelationRepository()
    {
        Iterable<Relation> relations = stub.getRelationRepository().findAll();

        assertEquals(3, ((Collection<?>) relations).size());
    }
}
