package com.gentree.service;

import com.gentree.Helper;
import com.gentree.model.Person;
import com.gentree.model.Relation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.io.UnsupportedEncodingException;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class CsvServiceTest
{
    private String valid = null;
    private String invalid = null;
    private String empty = null;
    private String incomplete = null;
    private CsvService stub = null;

    @BeforeAll
    public void setUp()
        throws UnsupportedEncodingException
    {
        valid = Helper.getResourceFile("csv/valid.csv").getAbsolutePath();
        invalid = Helper.getResourceFile("csv/invalid.csv").getAbsolutePath();
        empty = Helper.getResourceFile("csv/empty.csv").getAbsolutePath();
        incomplete = Helper.getResourceFile("csv/incomplete.csv").getAbsolutePath();

        // Since RepositoriesService is a singleton, we need to create a new instance
        // with the help of reflection by accessing directly its private constructor.
        stub = Helper.createSingletonInstance(CsvService.class);
    }

    @AfterEach
    public void afterEach()
    {
        // Reset stub CsvService cache
        stub.reset();
    }

    @Test
    @DisplayName("Test Singleton behaviour")
    public void testSingleton()
    {
        CsvService csvService = CsvService.getInstance();
        assertSame(CsvService.getInstance(), csvService);
    }

    @Test
    @DisplayName("Testing a valid csv file")
    public void testValidCsv()
    {
        try
        {
            ImmutablePair<Deque<Person.Tuple>, Deque<Relation.Tuple>> result = stub.readFile(valid);
            assertNotNull(result);
            assertEquals(13, result.getLeft().size());
            assertEquals(16, result.getRight().size());
        }
        catch (Exception e)
        {
            fail();
        }
    }

    @Test
    @DisplayName("Testing an empty csv file")
    public void testEmptyCsv()
    {
        assertThrows(IllegalArgumentException.class, () -> stub.readFile(empty));
    }

    @Test
    @DisplayName("Testing an incomplete csv file")
    public void testIncompleteCsv()
    {
        assertThrows(IllegalArgumentException.class, () -> stub.readFile(incomplete));
    }

    @Test
    @DisplayName("Testing an invalid csv file")
    public void testInValidCsv()
    {
        assertThrows(IllegalArgumentException.class, () -> stub.readFile(invalid));
    }
}
