package com.gentree.model;

import com.gentree.common.Util;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Describes the relation bond that person 1 has with person 2.
 */
public class Relation
{
    private Person person1;
    private Person person2;
    private Bond bond;

    public Relation(Person person1, Person person2, String bond)
    {
        setPerson1(person1);
        setPerson2(person2);
        setBond(bond);
    }

    /**
     * Returns the person1 of the relation
     * @return the person1 of the relation
     */
    public Person getPerson1()
    {
        return person1;
    }

    /**
     * Returns the person2 of the relation
     * @return the person2 of the relation
     */
    public Person getPerson2()
    {
        return person2;
    }

    /**
     * Returns the bond of the relation between person1 and person2
     * @return the bond of the relation between person1 and person2
     */
    public Bond getBond()
    {
        return bond;
    }

    /**
     * Sets the person1 of the relation
     * @param person1 the person1 to set
     */
    private void setPerson1(Person person1)
    {
        this.person1 = person1;
    }

    /**
     * Sets the person2 of the relation
     * @param person2 the person2 to set
     */
    private void setPerson2(Person person2)
    {
        this.person2 = person2;
    }

    /**
     * Sets the bond of the relation between person1 and person2
     * @param bond the bond to set
     */
    private void setBond(String bond)
    {
        this.bond = Bond.resolveBond(bond);
    }

    /**
     * Tuple of Relation object
     */
    public static class Tuple
    {
        public String person1FullName;
        public String person2FullName;
        public String bond;
    }

    public enum Bond
    {
        //TODO: Add proper enum values

        PARENT(1),
        MOTHER(PARENT.value + + Person.Gender.WOMAN.getValue()),
        FATHER(PARENT.value + + Person.Gender.MAN.getValue()),

        CHILD(PARENT.value + 1),
        DAUGHTER(CHILD.value + Person.Gender.WOMAN.getValue()),
        SON(CHILD.value + Person.Gender.MAN.getValue()),

        SIBLING(PARENT.value + CHILD.value),
        SISTER(SIBLING.value + Person.Gender.WOMAN.getValue()),
        BROTHER(SIBLING.value + Person.Gender.MAN.getValue()),

        SPOUSE(4),
        WIFE(SPOUSE.value + Person.Gender.WOMAN.getValue()),
        HUSBAND(SPOUSE.value + Person.Gender.MAN.getValue());




        //UNCLE,
        //AUNT,
        //COUSIN,
        //GRANDMOTHER,
        //GRANDFATHER,
        //GRANDSISTER,
        //GRANDBROTHER,
        //GRANDDAUGHTER,
        //GRANDPARENT,
        //GRANDCHILD,
        //GRANDUNCLE,
        //GRANDDAUNT,
        //GRANDCOUSIN,
        //NEPHEW,
        //NIECE,
        //DAUGHTER_IN_LAW,
        //HUSBAND_IN_LAW,
        //GRANDSON,
        //GRANDDAUGHTER_IN_LAW,
        //GRANDHUSBAND_IN_LAW,
        //NONE;

        private final double value;

        Bond(final double value)
        {
            this.value = value;
        }

        /**
         * Returns the value of current enum
         * @return The value of current enum
         */
        public double getValue()
        {
            return value;
        }

        /**
         * Returns the list of user-defined supported bonds
         * @return the list of supported bonds
         */
        public static @NotNull List<String> getSupportedBonds()
        {
            return Arrays.asList(MOTHER.name(), FATHER.name(), HUSBAND.name());
        }

        /**
         * Returns true if the bond is supported, false otherwise
         * @param bond String value of gender
         * @return true when bond is supported, false when not
         */
        public static boolean isSupported(String bond)
        {
            return getSupportedBonds().contains(Util.normaliseUpper(bond));
        }

        /**
         * Returns the bond that corresponds to the given value
         * @param value The value of the bond
         * @return The bond that corresponds to the given value
         */
        public static @NotNull Bond getBondByValue(double value)
        {
            Optional<Bond> b = Arrays.stream(Bond.values()).filter(bond -> bond.getValue() == value).findFirst();

            if (b.isPresent())
                return b.get();

            throw new IllegalArgumentException(
                String.format(
                    "Bond with value %s is not supported", value));
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

        /**
         * Returns the Bond based on string value
         * @param bond the String Bond value
         * @return Bond enum
         */
        private static @NotNull Bond resolveBond(String bond)
        {
            try
            {
                // Resolve the bond string to Bond enum
                return valueOf(Bond.class, Util.normaliseUpper(bond));
            }
            catch (IllegalArgumentException e)
            {
                throw new IllegalArgumentException(
                    String.format(
                        Locale.getDefault(), "Bond '%s' is not supported by the system.", bond));
            }
        }
    }
}
