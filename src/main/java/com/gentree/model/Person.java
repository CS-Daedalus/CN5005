package com.gentree.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Person
{
    String name;
    List<Person> children = new ArrayList<>();
    Gender gender;
    Person mother;
    Person father;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Person> getChildren()
    {
        return children;
    }

    public void addChild(Person child)
    {
        child.setFather(this);
        this.children.add(child);
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public Person getMother()
    {
        return mother;
    }

    public void setMother(Person mother)
    {
        this.mother = mother;
    }

    public Person getFather()
    {
        return father;
    }

    public void setFather(Person father)
    {
        this.father = father;
    }

    public Person(String name, Gender gender, Person mother, Person father)
    {
        this(name, gender, mother);
        setFather(father);
    }

    public Person(String name, Gender gender, Person mother)
    {
        this(name, gender);
        setMother(mother);
    }

    public Person(String name, Gender gender)
    {
        setName(name);
        setGender(gender);
    }

    public enum Gender
    {
        WOMAN(0),
        MAN(1);

        private static List<String> supportedGenders;
        private final String value;

        Gender(final int index)
        {
            this.value = getSupportedGenders().get(index);
        }

        public String getValue()
        {
            return value;
        }

        public static List<String> getSupportedGenders()
        {
            if (null == supportedGenders)
                supportedGenders = new ArrayList<>(Arrays.asList("woman","man"));

            return supportedGenders;
        }

        public static boolean isValid(String gender)
        {
            return getSupportedGenders().contains(gender.toLowerCase(Locale.getDefault()));
        }

        @Override
        public String toString()
        {
            return getValue();
        }
    }
}
