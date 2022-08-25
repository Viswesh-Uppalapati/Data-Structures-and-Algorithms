/*
 * NAME: Viswesh Uppalapati
 * PID: A15600068
 */


/**
 * An implementation of a bloom filter prototype.
 * @author Viswesh Uppalapati
 * @since 05/27/20
 */
public class BloomFilterJunior
{

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 50;
    private static final int HASH_THREE_LEFT_SHIFT = 8;
    private static final int ALPH_BASE = 27; // use in Horner's and CRC func
    private static final int LEFT_SHIFT = 5; // use in CRC

    /* Instance variables */
    private boolean[] table;

    /**
     * Constructor, initializes a boolean bloom filter based on the
     * passed in capacity.
     * @param capacity  The capacity of the bloom filter.
     */
    public BloomFilterJunior(int capacity)
    {
        if (capacity < MIN_INIT_CAPACITY)
            throw new IllegalArgumentException();

        table = new boolean[capacity];
    }

    /**
     * Insert method, takes a value and sets the three possible locations
     * for it to true.
     * @param value The value to be inserted.
     */
    public void insert(String value)
    {
        // check input
        if (value == null)
            throw new NullPointerException();

        // set all possible locations to true
        table[hashOne(value)] = true;
        table[hashTwo(value)] = true;
        table[hashThree(value)] = true;

    }

    /**
     * Lookup function, takes in a value and returns whether or
     * not it may exist in the bloom filter.
     * @param value The value to be found in the filter.
     * @return      Whether or not it may exist.
     */
    public boolean lookup(String value)
    {
        // check input
        if (value == null)
            throw new IllegalArgumentException();

        // check if all three corresponding indices are true
        // can result in a false positive
        return (table[hashOne(value)] &&
                table[hashTwo(value)] &&
                table[hashThree(value)]);
    }

    /**
     * Hash function 1.
     *
     * @param value string to hash
     * @return hash value
     */
    private int hashOne(String value)
    {
        int hashVal = 0;
        for (int i = 0; i < value.length(); i++)
        {
            int letter = value.charAt(i);
            hashVal = (hashVal * ALPH_BASE + letter) % table.length;
        }
        return hashVal;
    }

    /**
     * Hash function 2.
     *
     * @param value string to hash
     * @return hash value
     */
    private int hashTwo(String value)
    {
        int hashVal = 0;
        for (int i = 0; i < value.length(); i++)
        {
            int leftShifted = hashVal << LEFT_SHIFT;
            int rightShifted = hashVal >>> ALPH_BASE;
            hashVal = (leftShifted | rightShifted) ^ value.charAt(i);
        }
        return Math.abs(hashVal % table.length);
    }

    /**
     * Hash function 3.
     *
     * @param value string to hash
     * @return hash value
     */
    private int hashThree(String value) {
        // Base-256 String
        int hash = 0;
        char[] chars = value.toCharArray();
        for (char c : chars) {
            hash = ((hash << HASH_THREE_LEFT_SHIFT) + c) % table.length;
        }
        return Math.abs(hash % table.length);
    }
}
