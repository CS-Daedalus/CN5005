package com.gentree.model;

import com.gentree.common.Util;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Person
{
    private String fullName;
    private final List<Person> children = new ArrayList<>();
    private Gender gender;
    private Person mother;
    private Person father;

    public Person(String fullName, String gender, Person mother, Person father)
    {
        this(fullName, gender, mother);
        setFather(father);
    }

    public Person(String fullName, String gender, Person mother)
    {
        this(fullName, gender);
        setMother(mother);
    }

    public Person(String fullName, String gender)
    {
        setFullName(fullName);
        setGender(gender);
    }

    /**
     * Returns the full name of this person
     * @return the full name of this person
     */
    public String getFullName()
    {
        return fullName;
    }

    /**
     * Sets the full name for this person
     * @param fullName the full name of this person
     */
    public void setFullName(String fullName)
    {
        this.fullName = Util.capitalise(fullName);
    }

    /**
     * Returns the gender of this person
     * @return the gender os this person
     */
    public Gender getGender()
    {
        return gender;
    }

    /**
     * Sets the gender of this person
     * @param gender the gender of this person
     */
    public void setGender(String gender)
    {
        this.gender = Gender.resolveGender(gender);
    }

    /**
     * Returns the mother of this person
     * @return the mother of this person
     */
    public Person getMother()
    {
        return mother;
    }

    /**
     * Sets the mother of this person
     * @param mother the mother to set
     */
    public void setMother(Person mother)
    {
        // Prevent adding self as mother
        preventSelf(mother);

        this.mother = mother;
    }

    /**
     * Returns the father of this person
     * @return the father of this person
     */
    public Person getFather()
    {
        return father;
    }

    /**
     * Sets the father of this person
     * @param father the father to set
     */
    public void setFather(Person father)
    {
        // Prevent adding self as father
        preventSelf(father);

        this.father = father;
    }

    /**
     * Returns the children of this person
     * @return the children of this person
     */
    public List<Person> getChildren()
    {
        return children;
    }

    /**
     * Adds a child to this person
     * @param child the child to add
     */
    public void addChild(@NotNull Person child)
    {
        // Prevent adding self as child
        preventSelf(child);

        // Prevent adding same child twice
        if (children.contains(child))
            throw new IllegalArgumentException(
                    String.format(
                        Locale.getDefault(), "Person %s already has child %s", this.fullName, child.fullName));

        child.setFather(this);
        this.children.add(child);
    }

    /**
     * Prevents adding self as parent of self
     * @param person the person to check against
     */
    private void preventSelf(Person person)
    {
        if (this.equals(person))
            throw new IllegalArgumentException(
                    String.format(
                        Locale.getDefault(), "Person %s cannot be a parent of itself", this.fullName));
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
        private static @NotNull Gender resolveGender(String gender)
        {
            try
            {
                // Resolve the gender string to Gender enum
                return valueOf(Gender.class, Util.normaliseUpper(gender));
            }
            catch (IllegalArgumentException e)
            {
                throw new IllegalArgumentException(
                    String.format(
                        Locale.getDefault(), "Gender '%s' is not supported by the system.", gender));
            }
        }

        /**
         * Returns the string value of current enum
         * @return The value of current enum
         */
        @Override
        public @NotNull String toString()
        {
            return Util.normaliseLower(name());
        }
    }
}
