package com.gentree.service;

import com.gentree.model.Person;
import com.gentree.model.Relation;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Locale;

public class CsvService
{
    // Making safe the use of same instance via multiple threads at the same time
    private static volatile CsvService instance;
    public final String CSV_SEPARATOR = ",";
    private Deque<String> persons       = new ArrayDeque<>();
    private Deque<String> relationships = new ArrayDeque<>();

    private CsvService()
    {

    }

    public static CsvService getInstance()
    {
        // Thread-safe singleton
        if (null == instance)
            synchronized (CsvService.class)
            {
                if (null == instance)
                    instance = new CsvService();
            }

        return instance;
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
            while (null != (line = bufferedReader.readLine()))
            {
                if(line.isEmpty()) continue;
                String[] split = sanityCheck(line);
                if(split.length == 2)
                {
                    getPersons().add(split[0] + "," + split[1]);
                }
                else if(split.length == 3)
                {
                    getRelationships().add(split[0] + "," + split[1] + "," + split[2]);
                }
            }
        }
        catch (IOException e)
        {
            // normally we log the exception and then we propagate it
            throw e;
        }

        if (persons.isEmpty() || relationships.isEmpty())
            throw new IllegalArgumentException("The file is either empty or missing people or relations.");

        //while (!persons.isEmpty()) {
        //    String person = persons.pollFirst();
        //    System.out.println(person);
        //}
        //
        //System.out.println("####################################################");
        //
        //while (!relationships.isEmpty()) {
        //    String relationship = relationships.pollFirst();
        //    System.out.println(relationship);
        //}

        return new ImmutablePair<>(persons, relationships);
    }

    public Deque<String> getPersons()
    {
        return persons;
    }

    public Deque<String> getRelationships()
    {
        return relationships;
    }

    private String[] sanityCheck(final String line)
        throws IllegalArgumentException
    {
        final String[] split = Arrays.stream(line.split(CSV_SEPARATOR)).map(String::trim).toArray(String[]::new);

        if (2 > split.length || 3 < split.length)
            throw new IllegalArgumentException(
                String.format(Locale.getDefault(), "Line [%s] is not valid", line)
            );

        if (2 == split.length && !Person.Gender.isValid(split[1]))
            throw new IllegalArgumentException(
                String.format(
                    Locale.getDefault(),
                    "Unsupported gender in line: [%s], accepting: %s, \"%s\" is given.",
                    line, Person.Gender.getSupportedGenders(), split[1]
                )
            );

        if (3 == split.length && !Relation.Bond.isValid(split[1]))
            throw new IllegalArgumentException(
                String.format(
                    Locale.getDefault(),
                    "Unsupported relation in line: [%s], accepting: %s, \"%s\" is given.",
                    line, Relation.Bond.getSupportedRelations(), split[1]
                )
            );

        return split;
    }
}
