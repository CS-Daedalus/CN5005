package com.gentree.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Relation
{

    public enum Bond
    {
        MOTHER(0),
        FATHER(1),
        //SISTER,
        //BROTHER,
        //DAUGHTER,
        //WIFE,
        HUSBAND(2);
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
        //NONE

        private static List<String> supportedRelations;
        private final String value;

        Bond(final int index)
        {
            this.value = getSupportedRelations().get(index);
        }

        public String getValue()
        {
            return value;
        }

        public static List<String> getSupportedRelations()
        {
            if (null == supportedRelations)
                supportedRelations = new ArrayList<>(Arrays.asList(
                    "mother", "father", "husband"
                ));

            return supportedRelations;
        }

        public static boolean isValid(String relation)
        {
            return getSupportedRelations().contains(relation.toLowerCase(Locale.getDefault()));
        }

        @Override
        public String toString()
        {
            return getValue();
        }
    }
}
