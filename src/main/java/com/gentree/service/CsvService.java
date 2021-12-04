package com.gentree.service;

import com.gentree.model.Person;
import com.gentree.model.Relation;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Locale;

public final class CsvService
{
    // Making safe the use of same instance via multiple threads at the same time
    private static volatile CsvService instance;
    public static final String CSV_SEPARATOR = ",";
    private final Deque<String> persons       = new ArrayDeque<>();
    private final Deque<String> relationships = new ArrayDeque<>();

    private CsvService()
    {

    }

    public static CsvService getInstance()
    {
        CsvService localInstance = instance;
        // Thread-safe singleton
        if (null == localInstance)
            synchronized (CsvService.class)
            {
                localInstance = instance;
                if (null == instance)
                    instance = localInstance = new CsvService();
            }

        return localInstance;
    }

    public ImmutablePair<Deque<String>, Deque<String>> readFile(final String fileName)
        throws IOException, IllegalArgumentException
    {
        try (
            FileInputStream inputStream = new FileInputStream(Paths.get(fileName).toAbsolutePath().toString());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(bufferedInputStream, StandardCharsets.UTF_8))
        ) {
            String line;
            long lineNumber = 0;
            while (null != (line = bufferedReader.readLine()))
            {
                lineNumber++;
                if(line.isEmpty()) continue;
                processLine(line, lineNumber);
            }
        }

        if (persons.isEmpty() || relationships.isEmpty())
            throw new IllegalArgumentException("The file is either empty or missing people or relations.");

        return new ImmutablePair<>(persons, relationships);
    }

    public void reset()
    {
        persons.clear();
        relationships.clear();
    }

    private void processLine(String line, long lineNumber)
        throws IllegalArgumentException
    {
        String[] split = sanityCheck(line);

        switch (split.length)
        {
            case 2:
                addPerson(split);
                break;

            case 3:
                addRelationships(split);
                break;

            default:
                throw new IllegalArgumentException(
                    String.format(
                        Locale.getDefault(),
                        "Line %d is not valid. It has %d fields",
                        lineNumber, split.length));
        }
    }

    private void addPerson(String[] split)
    {
        persons.add(split[0] + "," + split[1]);
    }

    private void addRelationships(String[] split)
    {
        relationships.add(split[0] + "," + split[1] + "," + split[2]);
    }

    private String[] sanityCheck(final String line)
        throws IllegalArgumentException
    {
        final String[] split = Arrays.stream(line.split(CSV_SEPARATOR)).map(String::trim).toArray(String[]::new);

        if (2 > split.length || 3 < split.length)
            throw new IllegalArgumentException(
                String.format(Locale.getDefault(), "Line [%s] is not valid", line));

        if (2 == split.length && !Person.Gender.isValid(split[1]))
            throw new IllegalArgumentException(
                String.format(
                    Locale.getDefault(),
                    "Unsupported gender in line: [%s], accepting: %s, \"%s\" is given.",
                    line, Person.Gender.getSupportedGenders(), split[1]));

        if (3 == split.length && !Relation.Bond.isValid(split[1]))
            throw new IllegalArgumentException(
                String.format(
                    Locale.getDefault(),
                    "Unsupported relation in line: [%s], accepting: %s, \"%s\" is given.",
                    line, Relation.Bond.getSupportedRelations(), split[1]));

        return split;
    }
}
