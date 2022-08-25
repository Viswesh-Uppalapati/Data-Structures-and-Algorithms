/*
 * Name: Viswesh Uppalapati
 * PID: A15600068
 */

/**
 * This class contains the functionality of the Cuckoo-Hash
 * table.
 * 
 * @author Viswesh Uppalapati
 * @since 05/22/20
 */
public class HashTable implements IHashTable
{

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 10;
    private static final int DEFAULT_CAPACITY = 20;
    private static final double MAX_LOAD_FACTOR = 0.5;
    private static final int ALPH_BASE = 27; // use in Horner's and CRC func
    private static final int LEFT_SHIFT = 5; // use in CRC
    private static final int SPLIT = 2;

    /* Instance variables */
    private String[] table1, table2; // sub-tables
    private int nElems; // size
    private int nRehashes; // number of rehashes
    private int nEvictions; // number of "kick outs"
    private String log = ""; // log of the statistics

    /**
     * Constructor, initializes a cuckoo hash with the default capacity.
     */
    public HashTable() { this(DEFAULT_CAPACITY); }

    /**
     * Constructor, initializes table with the given capacity.
     * @param capacity  The capacity of table to be initialized
     */
    public HashTable(int capacity)
    {
        if (capacity < MIN_INIT_CAPACITY)
            throw new IllegalArgumentException();

        table1 = new String[capacity / SPLIT];
        table2 = new String[capacity / SPLIT];
        nElems = 0;
    }

    /**
     * Insert method that inserts a value into the table and returns
     * whether or not it was inserted.
     * @param value value to insert
     * @return      Whether or not the value was inserted
     */
    @Override
    public boolean insert(String value)
    {
        // check input
        if (value == null)
            throw new NullPointerException();

        // avoid duplicates
        if (lookup(value))
            return false;

        // resize if loadfactor meets condition
        double loadFactor = (double) size() / (double)capacity();
        if (loadFactor > MAX_LOAD_FACTOR)
            rehash();

        // insert and loop till max iterations using the pigeon hole
        // principle
        int maxIterations = 1 + nElems;
        String current = value;

        for (int i = 0; i < maxIterations; i++)
        {
            // check table1
            int hash = hashOne(current);
            if (table1[hash] == null)
            {
                table1[hash] = current;
                nElems++;
                return true; // insert complete
            }

            // kick out if table1 already has a value
            String temp = table1[hash];
            table1[hash] = current;
            current = temp;
            nEvictions++;

            // check table2
            hash = hashTwo(current);
            if (table2[hash] == null)
            {
                table2[hash] = current;
                nElems++;
                return true; // insert complete
            }

            // kick out if table2 already has a value
            temp = table2[hash];
            table2[hash] = current;
            current = temp;
            nEvictions++;
        }

        // if method reaches here, infinite loop due to pigeon hole principle
        // rehash and reinsert the last kick out element
        rehash();
        return insert(current);
    }

    /**
     * Delete method, finds a certain value in the hash table and removes it.
     * @param value value to delete
     * @return      Whether or not it was deleted.
     */
    @Override
    public boolean delete(String value)
    {
        // check input
        if (value == null)
            throw new NullPointerException();

        // if it doesn't exist in table, nothing to delete
        if (!lookup(value))
            return false;

        // get both hash keys for the value
        int key1 = hashOne(value);
        int key2 = hashTwo(value);

        // look in each table and set the corresponding value to null if found
        if (table1[key1] != null && table1[key1].equals(value))
            table1[key1] = null;

        if (table2[key2] != null && table2[key2].equals(value))
            table2[key2] = null;

        nElems--;
        return true;
    }

    /**
     * Lookup method, finds a certain value in the table and tells whether
     * it exists in the table.
     * @param value value to look up
     * @return      whether the value exists in the table
     */
    @Override
    public boolean lookup(String value)
    {
        // check inputs
        if (value == null)
            throw new NullPointerException();

        // get both the hash keys of the values
        int key1 = hashOne(value);
        int key2 = hashTwo(value);


        // if it exists in either table, return true
        if (table1[key1] != null && table1[key1].equals(value))
            return true;

        if (table2[key2] != null && table2[key2].equals(value))
            return true;

        // doesn't exist
        return false;
    }

    /**
     * Size method, returns the number of values stores in the table.
     * @return  The number of elements stored
     */
    @Override
    public int size() { return nElems; }

    /**
     * Capacity method, returns the number of values that can be stored in
     * the current table.
     * @return  The max number of values that can be stored.
     */
    @Override
    public int capacity() { return table1.length + table2.length; }

    /**
     * Get the string representation of the hash table.
     *
     * Format Example:
     * | index | table 1 | table 2 |
     * | 0 | Marina | [NULL] |
     * | 1 | [NULL] | DSC30 |
     * | 2 | [NULL] | [NULL] |
     * | 3 | [NULL] | [NULL] |
     * | 4 | [NULL] | [NULL] |
     *
     * @return string representation
     */
    @Override
    public String toString()
    {
        String result = "| index | table 1 | table 2 |";
        for (int i = 0; i < table1.length; i++)
        {
            result += "\n| " + i + " | ";
            if (table1[i] == null)
                result += "[NULL]";
            else
                result += table1[i];

            result += " | ";
            if (table2[i] == null)
                result += "[NULL]";
            else
                result += table2[i];

            result += " |";
        }

        return result + "\n";
    }

    /**
     * Get the rehash stats log.
     *
     * Format Example: 
     * Before rehash # 1: load factor 0.80, 3 evictions.
     * Before rehash # 2: load factor 0.75, 5 evictions.
     *
     * @return rehash stats log
     */
    public String getStatsLog() { return log; }

    /**
     * Rehash method that first logs the progress, then doubles the
     * capacity and reinserts all the old elements into the new
     * hash table.
     */
    private void rehash()
    {
        // increment the number of rehashes and add to statsLog
        nRehashes++;
        log += formatStr();

        // initialize the tables to be double the size
        int newCapacity = capacity() * SPLIT;
        String [] temp1 = table1;
        String [] temp2 = table2;
        table1 = new String[newCapacity / SPLIT];
        table2 = new String[newCapacity / SPLIT];
        nElems = 0;
        nEvictions = 0;

        // reinsert all the elements from each table
        for (String s : temp1)
            if (s != null)
                insert(s);

        for (String s : temp2)
            if (s != null)
                insert(s);
    }

    /**
     * Formatter function that returns each line of the statslog
     * properly formatted.
     * @return  The formatted line of stats.
     */
    private String formatStr()
    {
        String str = "Before rehash # " + nRehashes + ": load factor %.2f, " +
                nEvictions + " evictions.\n";
        return String.format(str, ((double) size() / (double) capacity()));
    }


    /**
     * Horners hash function, creates a hash value that can be mapped
     * onto the table.
     * @param value The string for which the key has to be found
     * @return      The key
     */
    private int hashOne(String value)
    {
        int hashVal = 0;
        for (int i = 0; i < value.length(); i++)
        {
            int letter = value.charAt(i);
            hashVal = (hashVal * ALPH_BASE + letter) % table1.length;
        }
        return hashVal;
    }

    /**
     * CRC hash function, creates a hash value corresponding to table2
     * for a String that needs to be inserted.
     * @param value The string for which the key has to be found
     * @return      The key
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
        return Math.abs(hashVal % table2.length);
    }
}
