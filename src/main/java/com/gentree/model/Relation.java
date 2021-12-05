package com.gentree.model;

import com.gentree.common.Util;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
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
        MOTHER,
        FATHER,
        //SISTER,
        //BROTHER,
        //DAUGHTER,
        //WIFE,
        HUSBAND;
        //SIBLING,
        //PARENT,
        //CHILD,
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
        //SON,
        //DAUGHTER_IN_LAW,
        //HUSBAND_IN_LAW,
        //GRANDSON,
        //GRANDDAUGHTER_IN_LAW,
        //GRANDHUSBAND_IN_LAW,
        //NONE;

        /**
         * Returns the list of supported bonds
         * @return the list of supported bonds
         */
        public static List<String> getSupportedBonds()
        {
            return Stream.of(values())
                         .map(Enum::name)
                         .collect(Collectors.toList());
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
