package com.gentree.service;

import com.gentree.Helper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.*;

public class CsvServiceTest
{
    private final String valid = Helper.getResourceFile("csv/valid.csv").getAbsolutePath();
    private final String invalid = Helper.getResourceFile("csv/invalid.csv").getAbsolutePath();
    private final String empty = Helper.getResourceFile("csv/empty.csv").getAbsolutePath();
    private final String incomplete = Helper.getResourceFile("csv/incomplete.csv").getAbsolutePath();
    private final CsvService csvService = CsvService.getInstance();

    public CsvServiceTest()
        throws UnsupportedEncodingException
    {}

    @BeforeEach
    private void resetCache()
    {
        // Reset CsvService cache
        csvService.getPersons().clear();
        csvService.getRelationships().clear();
    }

    @Test
    @DisplayName("Testing a valid csv file")
    public void testValidCsv()
    {
        try
        {
            ImmutablePair<Deque<String>, Deque<String>> result = csvService.readFile(valid);
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
        assertThrows(IllegalArgumentException.class, () -> csvService.readFile(empty));
    }

    @Test
    @DisplayName("Testing an incomplete csv file")
    public void testIncompleteCsv()
    {
        assertThrows(IllegalArgumentException.class, () -> csvService.readFile(incomplete));
    }

    @Test
    @DisplayName("Testing an invalid csv file")
    public void testInValidCsv()
    {
        assertThrows(IllegalArgumentException.class, () -> csvService.readFile(invalid));
    }
}
