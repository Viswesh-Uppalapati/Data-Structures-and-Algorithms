import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class HashTableTest
{
    HashTable ht;

    @Before
    public void setup()
    {
        ht = new HashTable();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorExcept()
    {
        ht = new HashTable(5);
    }

    @Test
    public void insert()
    {
        assertTrue(ht.insert("a"));
        assertFalse(ht.insert("a"));
        assertTrue(ht.insert("b"));

        for (int i = 0; i < 50; i++)
            assertTrue(ht.insert(Integer.toString(i)));

        assertEquals(160, ht.capacity());
        assertEquals(52, ht.size());

    }

    @Test (expected = NullPointerException.class)
    public void testInsertExcept()
    {
        ht.insert(null);
    }

    @Test
    public void delete()
    {
        ht.insert("a");
        ht.insert("b");
        ht.insert("c");
        assertTrue(ht.delete("a"));
        assertFalse(ht.lookup("a"));
        assertEquals(2, ht.size());
        assertFalse(ht.delete("a"));
        assertTrue(ht.delete("b"));
        assertFalse(ht.lookup("b"));
    }

    @Test (expected = NullPointerException.class)
    public void testDeleteExcept()
    {
        ht.delete(null);
    }

    @Test
    public void lookup()
    {
        assertFalse(ht.lookup("a"));
        ht.insert("a");
        assertTrue(ht.lookup("a"));
        ht.delete("a");
        assertFalse(ht.lookup("a"));
    }

    @Test (expected = NullPointerException.class)
    public void testLookUpExcept()
    {
        ht.lookup(null);
    }


    @Test
    public void size()
    {
        assertEquals(0, ht.size());
        ht.insert("a");
        ht.insert("b");
        assertEquals(2, ht.size());
        ht.delete("b");
        assertEquals(1, ht.size());
    }

    @Test
    public void capacity()
    {
        assertEquals(20, ht.capacity());
        ht = new HashTable(500);
        assertEquals(500, ht.capacity());
        ht = new HashTable(17);
        assertEquals(16, ht.capacity());
    }

    @Test
    public void testToString()
    {
        assertEquals("| index | table 1 | table 2 |\n" +
                "| 0 | [NULL] | [NULL] |\n" +
                "| 1 | [NULL] | [NULL] |\n" +
                "| 2 | [NULL] | [NULL] |\n" +
                "| 3 | [NULL] | [NULL] |\n" +
                "| 4 | [NULL] | [NULL] |\n" +
                "| 5 | [NULL] | [NULL] |\n" +
                "| 6 | [NULL] | [NULL] |\n" +
                "| 7 | [NULL] | [NULL] |\n" +
                "| 8 | [NULL] | [NULL] |\n" +
                "| 9 | [NULL] | [NULL] |\n", ht.toString());

        ht.insert("a");
        ht.insert("b");
        assertEquals("| index | table 1 | table 2 |\n" +
                "| 0 | [NULL] | [NULL] |\n" +
                "| 1 | [NULL] | [NULL] |\n" +
                "| 2 | [NULL] | [NULL] |\n" +
                "| 3 | [NULL] | [NULL] |\n" +
                "| 4 | [NULL] | [NULL] |\n" +
                "| 5 | [NULL] | [NULL] |\n" +
                "| 6 | [NULL] | [NULL] |\n" +
                "| 7 | a | [NULL] |\n" +
                "| 8 | b | [NULL] |\n" +
                "| 9 | [NULL] | [NULL] |\n", ht.toString());

        ht.insert("c");
        assertEquals("| index | table 1 | table 2 |\n" +
                "| 0 | [NULL] | [NULL] |\n" +
                "| 1 | [NULL] | [NULL] |\n" +
                "| 2 | [NULL] | [NULL] |\n" +
                "| 3 | [NULL] | [NULL] |\n" +
                "| 4 | [NULL] | [NULL] |\n" +
                "| 5 | [NULL] | [NULL] |\n" +
                "| 6 | [NULL] | [NULL] |\n" +
                "| 7 | a | [NULL] |\n" +
                "| 8 | b | [NULL] |\n" +
                "| 9 | c | [NULL] |\n", ht.toString());
    }

    @Test
    public void getStatsLog()
    {
        assertEquals("", ht.getStatsLog());
        for (int i = 0; i < 1000; i++)
            assertTrue(ht.insert(Integer.toString(i)));
        assertEquals("Before rehash # 1: load factor 0.55, 1 evictions.\n" +
                "Before rehash # 2: load factor 0.53, 10 evictions.\n" +
                "Before rehash # 3: load factor 0.51, 8 evictions.\n", ht.getStatsLog());
        ht = new HashTable();
        for (int i = 0; i < 15; i++)
            assertTrue(ht.insert(Integer.toString(i)));
        assertEquals("Before rehash # 1: load factor 0.55, 1 evictions.\n", ht.getStatsLog());
    }
}