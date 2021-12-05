package com.gentree.model;

import com.gentree.common.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Person
{
    private String fullName;
    private final List<Person> children = new ArrayList<>();
    private Gender gender;
    private Person mother;
    private Person father;

    public Person(String fullName, String gender, Person parent1, Person parent2)
    {
        this(fullName, gender, parent1);
        setParent(parent2);
    }

    public Person(String fullName, String gender, Person parent)
    {
        this(fullName, gender);
        setParent(parent);
    }

    public Person(String fullName, String gender)
    {
        setFullName(fullName);
        setGender(gender);
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = Util.capitalise(fullName);
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

    public void setGender(String gender)
    {
        this.gender = Gender.resolveGender(gender);
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

    private void setParent(Person parent)
    {
        switch (parent.gender)
        {
            case WOMAN:
                setMother(parent);
                break;

            case MAN:
                setFather(parent);
                break;
        }
    }

    /**
     * Tuple of Person object
     */
    public static class Tuple
    {
        public String fullName;
        public String gender;
    }

    public enum Gender
    {
        WOMAN,
        MAN;

        /**
         * Returns the list of supported genders
         * @return the list of supported genders
         */
        public static List<String> getSupportedGenders()
        {
            return Stream.of(values())
                         .map(Enum::name)
                         .collect(Collectors.toList());
        }

        /**
         * Returns true if the gender is supported, false otherwise
         * @param gender String value of gender
         * @return true when gender is supported, false when not
         */
        public static boolean isSupported(String gender)
        {
            return getSupportedGenders().contains(Util.normaliseUpper(gender));
        }

        /**
         * Returns the Gender based on string value
         * @param gender the String Gender value
         * @return Gender enum
         */
        private static Gender resolveGender(String gender)
        {
            // Resolve the gender string to Gender enum
            Optional<Gender> g = Util.getEnumByValue(Gender.class, Util.normaliseUpper(gender));

            // Check if the optional variable is present
            if (!g.isPresent())
                throw new IllegalArgumentException(
                    String.format(
                        Locale.getDefault(), "Gender '%s' is not supported by the system.", gender));

            // Return the found Enum property
            return g.get();
        }

        /**
         * Returns the string value of current enum
         * @return The value of current enum
         */
        @Override
        public String toString()
        {
            return Util.normaliseLower(name());
        }
    }
}
