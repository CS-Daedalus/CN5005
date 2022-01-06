package com.gentree.service;

import com.gentree.model.Person;
import com.gentree.model.Relation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

public class RepositoriesService
{
    // Making safe the use of same instance via multiple threads at the same time
    private static volatile RepositoriesService instance;
    private final PersonRepository personRepository;
    private final RelationRepository relationRepository;
    private boolean isInitialised = false;
    private int version = 0;

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

    public void feed(@NotNull Deque<Person.Tuple> personTuples, @NotNull Deque<Relation.Tuple> relationTuples)
    {
        isInitialised = true;
        version++;

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

    public boolean isInitialised()
    {
        return isInitialised;
    }

    public int getVersion()
    {
        return version;
    }

    public void reset()
    {
        personRepository.reset();
        relationRepository.reset();
        isInitialised = false;
    }

    public PersonRepository getPersonRepository()
    {
        return personRepository;
    }

    public RelationRepository getRelationRepository()
    {
        return relationRepository;
    }

    /**
     * Person Repository
     */
    public static class PersonRepository
    {
        private final Map<String, Person> store = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        private PersonRepository()
        {
            // Prevent instantiation
        }

        public void insert(Person.@NotNull Tuple entity)
        {
            store.put(entity.fullName, new Person(entity.fullName, entity.gender));
        }

        public int count()
        {
            return store.size();
        }

        public List<String> getPersonsName()
        {
            return new ArrayList<>(store.keySet());
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

        public void reset()
        {
            store.clear();
        }
    }

    /**
     * Relation Repository
     */
    public class RelationRepository
    {
        private final Set<Relation> store = new HashSet<>();

        private RelationRepository()
        {
            // Prevent instantiation
        }

        public void insert(Relation.@NotNull Tuple entity)
        {
            store.add(new Relation(
                RepositoriesService.this.personRepository.findOne(entity.person1FullName),
                RepositoriesService.this.personRepository.findOne(entity.person2FullName),
                entity.bond
            ));
        }

        public int count()
        {
            return store.size();
        }

        public Optional<Relation> findOne(String person1FullName, String person2FullName)
        {
            Optional<Relation> result = Optional.empty();

            for (Relation relation : store)
                if (relation.getPerson1().getFullName().equals(person1FullName)
                &&  relation.getPerson2().getFullName().equals(person2FullName))
                    result = Optional.of(relation);

            return result;
        }

        @Contract(pure = true)
        public @NotNull Iterable<Relation> findAll()
        {
            return store;
        }

        public void reset()
        {
            store.clear();
        }
    }
}
