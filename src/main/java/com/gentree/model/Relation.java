package com.gentree.model;

import com.gentree.common.Util;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Relation
{

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
         * Returns the list of supported relations
         * @return the list of supported relations
         */
        public static List<String> getSupportedRelations()
        {
            return Stream.of(values())
                         .map(Enum::name)
                         .map(Util::capitalise)
                         .collect(Collectors.toList());
        }

        /**
         * Returns true if the relation is supported, false otherwise
         * @param relation String value of gender
         * @return true when relation is supported, false when not
         */
        public static boolean isSupported(String relation)
        {
            return getSupportedRelations().contains(Util.normalise(relation));
        }

        /**
         * Returns the Bond based on string value
         * @param relation the String Bond value
         * @return Bond enum
         */
        private static Bond resolveRelation(String relation)
        {
            // Resolve the relation string to Bond enum
            Optional<Bond> b = Util.getEnumByValue(Bond.class, Util.capitalise(relation));

            // Check if the optional variable is present
            if (!b.isPresent())
                throw new IllegalArgumentException(
                    String.format(Locale.getDefault(), "Relation '%s' is not supported by the system.", relation));

            // Return the found Enum property
            return b.get();
        }

        /**
         * Returns the string value of current enum
         * @return The value of current enum
         */
        @Override
        public String toString()
        {
            return Util.capitalise(name());
        }
    }
}
