package com.gentree.model;

import com.gentree.common.Util;

import java.util.ArrayList;
import java.util.Arrays;
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

    public Person(String fullName, Gender gender, Person parent1, Person parent2)
    {
        this(fullName, gender, parent1);
        setParent(parent2);
    }

    public Person(String fullName, Gender gender, Person parent)
    {
        this(fullName, gender);
        setParent(parent);
    }

    public Person(String fullName, Gender gender)
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
        this.fullName = fullName;
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

    public enum Gender
    {
        WOMAN("woman"),
        MAN("man");

        private final String value;

        Gender(final String value)
        {
            this.value = Util.normalise(value);
        }

        /**
         * The value of enum
         * @return enum value
         */
        public String getValue()
        {
            return value;
        }

        /**
         * Returns the list of supported genders
         * @return the list of supported genders
         */
        public static List<String> getSupportedGenders()
        {
            return Stream.of(Gender.values())
                .map(Gender::getValue)
                .collect(Collectors.toList());
        }

        /**
         * Returns true if the gender is supported, false otherwise
         * @param gender String value of gender
         * @return true when gender is support, false when not
         */
        public static boolean isValid(String gender)
        {
            return findFirstByValue(gender.toLowerCase(Locale.getDefault())).isPresent();
        }

        /**
         * Returns the Gender based on string value
         * @param gender the String Gender value
         * @return Gender enum
         */
        private static Gender resolveGender(String gender)
        {
            // Resolve the gender string to Gender enum
            Optional<Gender> g = findFirstByValue(Util.normalise(gender));

            // Check if the optional variable is present
            if (!g.isPresent())
                throw new IllegalArgumentException(
                    String.format(Locale.getDefault(), "Gender '%s' is not supported by the system.", gender));

            // Return the found Enum property
            return g.get();
        }

        /**
         * Find the first element in the list that matches the given value if it exists
         * @param value The value to match
         * @return The first element in the list that matches the given value if it exists
         */
        private static Optional<Gender> findFirstByValue(String value)
        {
            return Arrays.stream(values())
                .filter(gender -> gender.value.equals(value))
                .findFirst();
        }

        /**
         * Returns the string value of current enum
         * @return The value of current enum
         */
        @Override
        public String toString()
        {
            return getValue();
        }
    }
}
