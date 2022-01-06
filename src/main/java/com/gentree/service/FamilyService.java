package com.gentree.service;

import com.gentree.common.Graph;
import com.gentree.model.Person;
import com.gentree.model.Relation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FamilyService
{
    // Directed and weighted graph object
    private final Graph<String, Person> family = new Graph<>();
    private int repositoryVersion = 0;
    // Making safe the use of same instance via multiple threads at the same time
    private static volatile FamilyService instance;

    private FamilyService()
    {
        // Prevent instantiation
    }

    /**
     * Thread-safe singleton implementation
     * @return the instance
     */
    public static FamilyService getInstance()
    {
        FamilyService localInstance = instance;
        // Thread-safe singleton
        if (null == localInstance)
            synchronized (FamilyService.class)
            {
                localInstance = instance;
                if (null == instance)
                    instance = localInstance = new FamilyService();
            }

        return localInstance;
    }

    public void populateFamilyTree()
    {
        RepositoriesService repositoriesService = RepositoriesService.getInstance();

        if (0 == repositoryVersion)
            repositoryVersion = repositoriesService.getVersion();
        else if (repositoriesService.getVersion() != repositoryVersion)
            family.resetGraph();

        addFamilyMembers(repositoriesService.getPersonRepository().findAll());
        addFamilyRelations(repositoriesService.getRelationRepository().findAll());
    }

    public void findBond(String person1, String person2)
    {
        findBond(
            family.getVertex(person1).getPayload(),
            family.getVertex(person2).getPayload()
        );
    }

    public Relation.Bond findBond(Person person1, Person person2)
    {
        //TODO: Incomplete implementation
        List<ImmutablePair<Person, Double>> path = family.getPath(person1, person2);

        Relation.Key current_key = new Relation.Key(path.size(), path.get(path.size() - 1).right, person1.getGender());

        Relation.Bond bond = Relation.Bond.getBondByKey(current_key);

        System.out.println("path size: " + path.size());
        System.out.println("weight: " + path.get(path.size() - 1).right);
        System.out.println("gender value: " + person1.getGender().getValue());
        System.out.println("Current bond key hash: " + current_key.hashCode());
        System.out.println("Current bond name: " + Relation.Bond.getBondByKey(current_key));
        System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
        System.out.println();

        return bond;
    }

    private void addFamilyMembers(@NotNull Iterable<Person> familyMembers)
    {
        for (Person member : familyMembers)
        {
            family.insertVertex(member.getFullName(), member);
        }
    }

    private void addFamilyRelations(@NotNull Iterable<Relation> relations)
    {
        for (Relation relation : relations)
        {
            switch (relation.getBond())
            {
                case MOTHER:
                case FATHER:
                    family.insertEdge(
                        family.getVertex(relation.getPerson2().getFullName()),
                        family.getVertex(relation.getPerson1().getFullName()),
                        Relation.Bond.CHILD.getKey().getWeight()
                    );
                    break;

                case HUSBAND:
                    family.insertEdge(
                        family.getVertex(relation.getPerson2().getFullName()),
                        family.getVertex(relation.getPerson1().getFullName()),
                        Relation.Bond.HUSBAND.getKey().getWeight()
                    );
                    break;
            }

            family.insertEdge(
                family.getVertex(relation.getPerson1().getFullName()),
                family.getVertex(relation.getPerson2().getFullName()),
                relation.getBond().getKey().getWeight()
            );
        }
    }
}
