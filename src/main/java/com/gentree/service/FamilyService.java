package com.gentree.service;

import com.gentree.common.Graph;
import com.gentree.common.IEdge;
import com.gentree.common.IVertex;
import com.gentree.model.Person;
import com.gentree.model.Relation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FamilyService
{
    // Directed and weighted graph object
    private final Graph<String, Person> family = new Graph<>();
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
        addFamilyMembers(RepositoriesService.getInstance().getPersonRepository().findAll());
        addFamilyRelations(RepositoriesService.getInstance().getRelationRepository().findAll());
    }

    public Relation.Bond findBond(String person1, String person2)
    {
        return findBond(
            family.getVertex(person1).getPayload(),
            family.getVertex(person2).getPayload()
        );
    }

    public Relation.Bond findBond(Person person1, Person person2)
    {
        //TODO: Incomplete implementation
        List<ImmutablePair<Person, Double>> path = family.getPath(person1, person2);
        Relation.Bond bond = Relation.Bond.getBondByValue(
            path.size() + path.get(path.size() - 1).right + person1.getGender().getValue()
        );

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
            family.insertEdge(
                family.getVertex(relation.getPerson1().getFullName()),
                family.getVertex(relation.getPerson2().getFullName()),
                relation.getBond().getValue()
            );
        }
    }
}
