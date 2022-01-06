package com.gentree.model;

import com.gentree.common.Util;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

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
        // Grand Parents
        // Starting point: Size: 3, Weight: 4 | Size step = 1
        // |ε| = 2 > 1 -> elastic ∴ when size increase by 1, the weight increase by 2
        GRANDPARENT1(new Key(3, 4)),
        GRANDMOTHER1(Key.factory(GRANDPARENT1.key).setGender(Person.Gender.WOMAN)),
        GRANDFATHER1(Key.factory(GRANDPARENT1.key).setGender(Person.Gender.MAN)),

        GRANDPARENT2(new Key(4, 6)),
        GRANDMOTHER2(Key.factory(GRANDPARENT2.key).setGender(Person.Gender.WOMAN)),
        GRANDFATHER2(Key.factory(GRANDPARENT2.key).setGender(Person.Gender.MAN)),

        GRANDPARENT3(new Key(5, 8)),
        GRANDMOTHER3(Key.factory(GRANDPARENT3.key).setGender(Person.Gender.WOMAN)),
        GRANDFATHER3(Key.factory(GRANDPARENT3.key).setGender(Person.Gender.MAN)),

        // Parents
        PARENT(new Key(2, 2)),
        MOTHER(Key.factory(PARENT.key).setGender(Person.Gender.WOMAN)),
        FATHER(Key.factory(PARENT.key).setGender(Person.Gender.MAN)),

        // Child
        CHILD(new Key(2, 3)),
        DAUGHTER(Key.factory(CHILD.key).setGender(Person.Gender.WOMAN)),
        SON(Key.factory(CHILD.key).setGender(Person.Gender.MAN)),

        // Sibling
        SIBLING(new Key(3, 5)),
        SISTER(Key.factory(SIBLING.key).setGender(Person.Gender.WOMAN)),
        BROTHER(Key.factory(SIBLING.key).setGender(Person.Gender.MAN)),

        // Cousin
        // Cousins are gender-neutral
        // Starting point: Size: 5, Weight: 10 | Size step = 2
        // |ε| = 2.5 > 1 -> elastic ∴ when size increase by 1, the weight increase by 2.5,
        //                            but, since the size has a step of 2, it means that
        //                            for each step (size + 2), the weight increase by 5
        COUSIN1(new Key(5, 10)),
        COUSIN2(new Key(7, 15)),
        COUSIN3(new Key(9, 20)),

        // Aunt
        // Starting point: Size: 4, Weight: 7 | Size step = 1
        // |ε| = 2 > 1 -> elastic ∴ when size increase by 1, the weight increase by 2
        AUNT1(new Key(4, 7, Person.Gender.WOMAN)),
        AUNT2(new Key(5, 9, Person.Gender.WOMAN)),
        AUNT3(new Key(6, 11, Person.Gender.WOMAN)),

        // Uncle
        // Same as Aunt
        UNCLE1(Key.factory(AUNT1.key).setGender(Person.Gender.MAN)),
        UNCLE2(Key.factory(AUNT2.key).setGender(Person.Gender.MAN)),
        UNCLE3(Key.factory(AUNT3.key).setGender(Person.Gender.MAN)),

        // Niece
        // Starting point: Size: 4, Weight: 8 | Size step = 1
        // |ε| = 3 > 1 -> elastic ∴ when size increase by 1, the weight increase by 3
        NIECE1(new Key(4, 8, Person.Gender.WOMAN)),
        NIECE2(new Key(5, 11, Person.Gender.WOMAN)),
        NIECE3(new Key(6, 14, Person.Gender.WOMAN)),

        // Nephew
        // Same as Niece
        NEPHEW1(Key.factory(NIECE1.key).setGender(Person.Gender.MAN)),
        NEPHEW2(Key.factory(NIECE2.key).setGender(Person.Gender.MAN)),
        NEPHEW3(Key.factory(NIECE3.key).setGender(Person.Gender.MAN)),

        // Grand child
        // Starting point: Size: 3, Weight: 6 | Size step = 1
        // |ε| = 3 > 1 -> elastic ∴ when size increase by 1, the weight increase by 3
        GRANDCHILD1(new Key(3, 6)),
        GRANDDAUGHTER1(Key.factory(GRANDCHILD1.key).setGender(Person.Gender.WOMAN)),
        GRANDSON1(Key.factory(GRANDCHILD1.key).setGender(Person.Gender.MAN)),

        GRANDCHILD2(new Key(4, 9)),
        GRANDDAUGHTER2(Key.factory(GRANDCHILD2.key).setGender(Person.Gender.WOMAN)),
        GRANDSON2(Key.factory(GRANDCHILD2.key).setGender(Person.Gender.MAN)),

        GRANDCHILD3(new Key(5, 12)),
        GRANDDAUGHTER3(Key.factory(GRANDCHILD3.key).setGender(Person.Gender.WOMAN)),
        GRANDSON3(Key.factory(GRANDCHILD3.key).setGender(Person.Gender.MAN)),

        // EO BLOOD RELATIONS

        // Law Relations
        // The theory behind the decimal weight is that a bond by blood is a complete bond
        // therefore a division of such bond should not produce a remainder, that concept
        // allowing to distinguish the relations by law.

        // Grand Parents by Law
        // Starting point: Size: 4, Weight: 4.5 | Size step = 1
        // |ε| = 2 > 1 -> elastic ∴ when size increase by 1, the weight increase by 2
        LAW_GRANDPARENT1(new Key(4, 4.5)),
        LAW_GRANDMOTHER1(Key.factory(LAW_GRANDPARENT1.key).setGender(Person.Gender.WOMAN)),
        LAW_GRANDFATHER1(Key.factory(LAW_GRANDPARENT1.key).setGender(Person.Gender.MAN)),

        LAW_GRANDPARENT2(new Key(5, 6.5)),
        LAW_GRANDMOTHER2(Key.factory(LAW_GRANDPARENT2.key).setGender(Person.Gender.WOMAN)),
        LAW_GRANDFATHER2(Key.factory(LAW_GRANDPARENT2.key).setGender(Person.Gender.MAN)),

        LAW_GRANDPARENT3(new Key(6, 8.8)),
        LAW_GRANDMOTHER3(Key.factory(LAW_GRANDPARENT3.key).setGender(Person.Gender.WOMAN)),
        LAW_GRANDFATHER3(Key.factory(LAW_GRANDPARENT3.key).setGender(Person.Gender.MAN)),

        // Parents by Law
        LAW_PARENT(new Key(3, 2.5)),
        LAW_MOTHER(Key.factory(LAW_PARENT.key).setGender(Person.Gender.WOMAN)),
        LAW_FATHER(Key.factory(LAW_PARENT.key).setGender(Person.Gender.MAN)),

        // Child by Law
        LAW_CHILD(new Key(3, 3.5)),
        LAW_DAUGHTER(Key.factory(LAW_CHILD.key).setGender(Person.Gender.WOMAN)),
        LAW_SON(Key.factory(LAW_CHILD.key).setGender(Person.Gender.MAN)),

        // Sibling by Law
        LAW_SIBLING(new Key(4, 5.5)),
        LAW_SISTER(Key.factory(LAW_SIBLING.key).setGender(Person.Gender.WOMAN)),
        LAW_BROTHER(Key.factory(LAW_SIBLING.key).setGender(Person.Gender.MAN)),

        // Spouse
        SPOUSE(new Key(2, 0.5)),
        WIFE(Key.factory(SPOUSE.key).setGender(Person.Gender.WOMAN)),
        HUSBAND(Key.factory(SPOUSE.key).setGender(Person.Gender.MAN)),

        // Cousin by Law
        // Cousins are gender-neutral
        // Starting point: Size: 6, Weight: 5.5 | Size step = 2
        // |ε| = 2.5 > 1 -> elastic ∴ when size increase by 1, the weight increase by 2.5,
        //                            but, since the size has a step of 2, it means that
        //                            for each step (size + 2), the weight increase by 5
        LAW_COUSIN1(new Key(6, 10.5)),
        LAW_COUSIN2(new Key(8, 15.5)),
        LAW_COUSIN3(new Key(10, 20.5)),

        // Aunt by Law
        // Starting point: Size: 5, Weight: 7.5 | Size step = 1
        // |ε| = 2 > 1 -> elastic ∴ when size increase by 1, the weight increase by 2
        LAW_AUNT1(new Key(5, 7.5, Person.Gender.WOMAN)),
        LAW_AUNT2(new Key(6, 9.5, Person.Gender.WOMAN)),
        LAW_AUNT3(new Key(7, 11.5, Person.Gender.WOMAN)),

        // Uncle by Law
        // Same as Aunt by Law
        LAW_UNCLE1(Key.factory(LAW_AUNT1.key).setGender(Person.Gender.MAN)),
        LAW_UNCLE2(Key.factory(LAW_AUNT2.key).setGender(Person.Gender.MAN)),
        LAW_UNCLE3(Key.factory(LAW_AUNT3.key).setGender(Person.Gender.MAN)),

        // Niece by Law
        // Starting point: Size: 5, Weight: 8.5 | Size step = 1
        // |ε| = 3 > 1 -> elastic ∴ when size increase by 1, the weight increase by 3
        LAW_NIECE1(new Key(5, 8.5, Person.Gender.WOMAN)),
        LAW_NIECE2(new Key(6, 11.5, Person.Gender.WOMAN)),
        LAW_NIECE3(new Key(7, 14.5, Person.Gender.WOMAN)),

        // Nephew by Law
        // Same as Niece by Law
        LAW_NEPHEW1(Key.factory(LAW_NIECE1.key).setGender(Person.Gender.MAN)),
        LAW_NEPHEW2(Key.factory(LAW_NIECE2.key).setGender(Person.Gender.MAN)),
        LAW_NEPHEW3(Key.factory(LAW_NIECE3.key).setGender(Person.Gender.MAN)),

        // Grand child by Law
        // Starting point: Size: 4, Weight: 6.5 | Size step = 1
        // |ε| = 3 > 1 -> elastic ∴ when size increase by 1, the weight increase by 3
        LAW_GRANDCHILD1(new Key(4, 6.5)),
        LAW_GRANDDAUGHTER1(Key.factory(LAW_GRANDCHILD1.key).setGender(Person.Gender.WOMAN)),
        LAW_GRANDSON1(Key.factory(LAW_GRANDCHILD1.key).setGender(Person.Gender.MAN)),

        LAW_GRANDCHILD2(new Key(5, 9.5)),
        LAW_GRANDDAUGHTER2(Key.factory(LAW_GRANDCHILD2.key).setGender(Person.Gender.WOMAN)),
        LAW_GRANDSON2(Key.factory(LAW_GRANDCHILD2.key).setGender(Person.Gender.MAN)),

        LAW_GRANDCHILD3(new Key(6, 12.5)),
        LAW_GRANDDAUGHTER3(Key.factory(LAW_GRANDCHILD3.key).setGender(Person.Gender.WOMAN)),
        LAW_GRANDSON3(Key.factory(LAW_GRANDCHILD3.key).setGender(Person.Gender.MAN)),


        // No relation
        NONE(new Key(0, 0));

        private final Key key;
        private static int findByKeyAttempts = 0;

        Bond(final Key key)
        {
            this.key = key;
        }

        /**
         * Returns the value of current enum
         * @return The value of current enum
         */
        public Key getKey()
        {
            return key;
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
         * Returns the bond that corresponds to the given key
         * @param key The key of the bond
         * @return The bond that corresponds to the given key
         */
        public static @NotNull Bond getBondByKey(Key key)
        {
            findByKeyAttempts++;

            Optional<Bond> b = Arrays.stream(
                Bond.values()).filter(bond -> bond.getKey().hashCode() == key.hashCode()).findFirst();

            if (b.isPresent())
            {
                findByKeyAttempts = 0;
                return b.get();
            }

            if (1 == findByKeyAttempts)
                // try second time but with gender-neutral key
                return getBondByKey(key.setGender(Person.Gender.UNKNOWN));

            return NONE;
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

    public static class Key
    {
        private final int size;
        private final double weight;
        private Person.Gender gender = Person.Gender.UNKNOWN;

        public Key(int size, double weight)
        {
            this.size = size;
            this.weight = weight;
        }

        public Key(int size, double weight, Person.Gender gender)
        {
            this(size, weight);
            setGender(gender);
        }

        private Key(Key key)
        {
            this(key.getSize(), key.getWeight(), key.getGender());
        }

        public int getSize()
        {
            return size;
        }

        public double getWeight()
        {
            return weight;
        }

        public Person.Gender getGender()
        {
            return gender;
        }

        public Key setGender(Person.Gender gender)
        {
            this.gender = gender;

            return this;
        }

        public static Key factory(Key key)
        {
            return new Key(key);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(size, weight, gender.getValue());
        }

        @Override
        public boolean equals(Object obj)
        {
            if(!(obj instanceof Key))
                return false;

            final Key other = (Key) obj;
            return this.hashCode() == other.hashCode();
        }
    }
}
