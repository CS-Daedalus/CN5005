package com.gentree.service;

import com.gentree.model.Person;
import com.gentree.model.Relation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class RepositoriesService
{
    // Making safe the use of same instance via multiple threads at the same time
    private static volatile RepositoriesService instance;
    private final PersonRepository personRepository;
    private final RelationRepository relationRepository;

    private RepositoriesService()
    {
        // Prevent instantiation
        personRepository = new PersonRepository();
        relationRepository = new RelationRepository();
    }

    /**
     * Thread-safe singleton implementation
     * @return the instance
     */
    public static RepositoriesService getInstance()
    {
        RepositoriesService localInstance = instance;
        // Thread-safe singleton
        if (null == localInstance)
            synchronized (RepositoriesService.class)
            {
                localInstance = instance;
                if (null == instance)
                    instance = localInstance = new RepositoriesService();
            }

        return localInstance;
    }

    public PersonRepository getPersonRepository()
    {
        return personRepository;
    }

    public RelationRepository getRelationRepository()
    {
        return relationRepository;
    }

    public void feed(@NotNull Deque<Person.Tuple> personTuples, @NotNull Deque<Relation.Tuple> relationTuples)
    {
        // First process the persons
        do
        {
            Person.Tuple tuple = personTuples.poll();
            if (null != tuple)
                personRepository.insert(tuple);
        }
        while (!personTuples.isEmpty());

        // Then process the relations
        do
        {
            Relation.Tuple tuple = relationTuples.poll();
            if (null != tuple)
                relationRepository.insert(tuple);
        }
        while (!relationTuples.isEmpty());
    }

    /**
     * Person Repository
     */
    private static class PersonRepository
    {
        private final Map<String, Person> store = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        public void insert(Person.@NotNull Tuple entity)
        {
            store.put(entity.fullName, new Person(entity.fullName, entity.gender));
        }

        public Person findOne(String key)
        {
            return store.get(key);
        }

        @Contract(pure = true)
        public @NotNull Iterable<Person> findAll()
        {
            return store.values();
        }
    }

    /**
     * Relation Repository
     */
    private class RelationRepository
    {
        private final Set<Relation> store = new HashSet<>();

        public void insert(Relation.@NotNull Tuple entity)
        {
            store.add(new Relation(
                RepositoriesService.this.personRepository.findOne(entity.person1FullName),
                RepositoriesService.this.personRepository.findOne(entity.person2FullName),
                entity.bond
            ));
        }

        @Contract(pure = true)
        public @NotNull Iterable<Relation> findAll()
        {
            return store;
        }
    }
}
