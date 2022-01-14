package com.gentree.service;

import com.gentree.model.Person;
import com.gentree.model.Relation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    private final Deque<Person.Tuple> personTuples = new ArrayDeque<>();
    private final Deque<Relation.Tuple> relationTuples = new ArrayDeque<>();

    private CsvService()
    {
        // Prevent instantiation
    }

    /**
     * Thread-safe singleton implementation
     * @return the instance
     */
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

    /**
     * Reads the CSV file and populates the Deque objects of persons and relations
     * @param fileName the file name
     * @return the number of lines read
     * @throws IOException if the file cannot be read
     * @throws IllegalArgumentException if the file is not valid
     */
    @Contract("_ -> new")
    public @NotNull ImmutablePair<Deque<Person.Tuple>, Deque<Relation.Tuple>> readFile(final String fileName)
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

        if (getPersonTuples().isEmpty() || getRelationTuples().isEmpty())
            throw new IllegalArgumentException("The file is either empty or missing either people or relations.");

        return new ImmutablePair<>(getPersonTuples(), getRelationTuples());
    }

    /**
     * Returns the persons
     * @return the persons
     */
    public Deque<Person.Tuple> getPersonTuples()
    {
        return personTuples;
    }

    /**
     * Returns the relations
     * @return the relations
     */
    public Deque<Relation.Tuple> getRelationTuples()
    {
        return relationTuples;
    }

    /**
     * Resets the Deque objects of persons and relations
     */
    public void reset()
    {
        getPersonTuples().clear();
        getRelationTuples().clear();
    }

    /**
     * Process the given line
     * @param line the line
     * @param lineNumber the line number
     * @throws IllegalArgumentException if the line is invalid
     */
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
                addRelations(split);
                break;

            default:
                throw new IllegalArgumentException(
                    String.format(
                        Locale.getDefault(),
                        "Line %d is not valid. It has %d fields",
                        lineNumber, split.length));
        }
    }

    /**
     * Adds a person to the Deque
     * @param split the split line
     */
    private void addPerson(String @NotNull [] split)
    {
        // split[0] = full name
        // split[1] = gender
        getPersonTuples().add(new Person.Tuple(){{
            fullName = split[0];
            gender = split[1];
        }});
    }

    /**
     * Adds a relation to the Deque
     * @param split the split line
     */
    private void addRelations(String @NotNull [] split)
    {
        // split[0] = full name (person1)
        // split[1] = bond
        // split[2] = full name (person2)
        getRelationTuples().add(new Relation.Tuple(){{
            person1FullName = split[0];
            bond = split[1];
            person2FullName = split[2];
        }});
    }

    /**
     * Sanity checks the given line
     * @param line the line
     * @return the split line
     * @throws IllegalArgumentException if the line is invalid
     */
    private @NotNull String @NotNull [] sanityCheck(final @NotNull String line)
        throws IllegalArgumentException
    {
        final String[] split = Arrays.stream(line.split(CSV_SEPARATOR)).map(String::trim).toArray(String[]::new);

        if (2 > split.length || 3 < split.length)
            throw new IllegalArgumentException(
                String.format(Locale.getDefault(), "Line [%s] is not valid", line));

        if (2 == split.length && !Person.Gender.isSupported(split[1]))
            throw new IllegalArgumentException(
                String.format(
                    Locale.getDefault(),
                    "Unsupported gender in line: [%s], accepting: %s, \"%s\" is given.",
                    line, Person.Gender.getSupportedGenders(), split[1]));

        if (3 == split.length && !Relation.Bond.isSupported(split[1]))
            throw new IllegalArgumentException(
                String.format(
                    Locale.getDefault(),
                    "Unsupported relation in line: [%s], accepting: %s, \"%s\" is given.",
                    line, Relation.Bond.getSupportedBonds(), split[1]));

        return split;
    }
}
