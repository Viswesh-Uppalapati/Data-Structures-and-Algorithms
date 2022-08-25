import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.AbstractList;


public class DoublyLinkedListTest
{

    DoublyLinkedList<Integer> list;

    @Before
    public void setup()
    {
        list = new DoublyLinkedList<>();
    }

    @Test
    public void testAdd()
    {
        assertTrue(list.add(new Integer(0)));
        assertTrue(list.add(new Integer(5)));
        assertTrue(list.add(new Integer(10)));
        assertEquals("[(head) -> 0 -> 5 -> 10 -> (tail)]", list.toString());
    }

    @Test (expected = NullPointerException.class)
    public void testExceptAdd()
    {
        list.add(null);
    }

    @Test
    public void testAddIndex()
    {
        list.add(0, new Integer(0));
        assertEquals("[(head) -> 0 -> (tail)]", list.toString());
        list.add(0, new Integer(5));
        assertEquals("[(head) -> 5 -> 0 -> (tail)]", list.toString());
        list.add(1, new Integer(10));
        assertEquals("[(head) -> 5 -> 10 -> 0 -> (tail)]", list.toString());
    }

    @Test (expected = NullPointerException.class)
    public void testExceptAddIndex1()
    {
        list.add(0, null);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testExceptAddIndex2()
    {
        list.add(20, new Integer(5));
    }

    @Test
    public void testClear()
    {
        list.clear();
        assertEquals("[(head) -> (tail)]", list.toString());
        list.add(new Integer(0));
        assertEquals("[(head) -> 0 -> (tail)]", list.toString());
        list.clear();
        assertEquals("[(head) -> (tail)]", list.toString());
    }

    @Test
    public void testContains()
    {
        list.add(new Integer(0));
        list.add(new Integer(20));
        assertTrue(list.contains(new Integer(0)));
        assertTrue(list.contains(new Integer(20)));
        assertFalse(list.contains(new Integer(500)));
    }

    @Test
    public void testGet()
    {
        list.add(new Integer(0));
        list.add(new Integer(20));
        list.add(new Integer(500));
        assertEquals(new Integer(0), list.get(0));
        assertEquals(new Integer(20), list.get(1));
        assertEquals(new Integer(500), list.get(2));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetExcept()
    {
        list.get(0);
    }

    @org.junit.Test
    public void testIsEmpty()
    {
        assertTrue(list.isEmpty());
        list.add(new Integer(0));
        list.add(new Integer(20));
        assertFalse(list.isEmpty());
        list.clear();
        assertTrue(list.isEmpty());
    }

    @org.junit.Test
    public void testRemove()
    {
        list.add(new Integer(0));
        list.add(new Integer(20));
        assertEquals(new Integer(0), list.remove(0));
        assertEquals("[(head) -> 20 -> (tail)]", list.toString());
        assertEquals(list.size(), 1);
        assertEquals(new Integer(20), list.remove(0));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testRemoveExcept()
    {
        list.remove(0);
    }

    @org.junit.Test
    public void testSet()
    {
        list.add(new Integer(0));
        list.add(new Integer(20));
        assertEquals(new Integer(0), list.set(0, new Integer(500)));
        assertEquals("[(head) -> 500 -> 20 -> (tail)]", list.toString());
        assertEquals(new Integer(500), list.set(0, new Integer(2000)));
        assertEquals(new Integer(20), list.set(1, new Integer(1)));
    }

    @Test (expected = NullPointerException.class)
    public void testSetExcept1()
    {
        list.add(new Integer(0));
        list.set(0, null);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testSetExcept2()
    {
        list.set(20, new Integer(5));
    }

    @Test
    public void testSize()
    {
        assertEquals(0, list.size());
        list.add(new Integer(0));
        list.add(new Integer(0));
        list.add(new Integer(0));
        assertEquals(3, list.size());
        list.remove(2);
        assertEquals(2, list.size());
    }

    @Test
    public void testToString()
    {
        assertEquals("[(head) -> (tail)]", list.toString());
        list.add(new Integer(0));
        list.add(new Integer(0));
        list.add(new Integer(0));
        assertEquals("[(head) -> 0 -> 0 -> 0 -> (tail)]", list.toString());
        list.remove(0);
        list.remove(0);
        assertEquals("[(head) -> 0 -> (tail)]", list.toString());
    }

    @Test
    public void testSplice()
    {
        DoublyLinkedList<Integer> otherList = new DoublyLinkedList<>();
        list.add(new Integer(1));
        list.add(new Integer(4));
        list.add(new Integer(5));
        otherList.add(new Integer(2));
        otherList.add(new Integer(3));
        list.splice(1, otherList);
        assertEquals("[(head) -> 1 -> 2 -> 3 -> 4 -> 5 -> (tail)]", list.toString());
        otherList.clear();
        otherList.add(6);
        otherList.add(7);
        list.splice(5, otherList);
        assertEquals("[(head) -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> (tail)]", list.toString());
        otherList.clear();
        otherList.add(0);
        list.splice(0, otherList);
        assertEquals("[(head) -> 0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> (tail)]", list.toString());
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testSpliceExcept()
    {
        list.splice(5, list);
    }

    @Test
    public void testMatch()
    {
        DoublyLinkedList<String> list1 = new DoublyLinkedList();
        DoublyLinkedList<String> subsequence = new DoublyLinkedList();
        list1.add("A");
        list1.add("C");
        list1.add("A");
        list1.add("B");
        list1.add("A");
        list1.add("C");
        list1.add("A");
        list1.add("B");
        list1.add("A");
        subsequence.add("A");
        subsequence.add("B");
        subsequence.add("A");
        int [] result = {2, 6};
        assertArrayEquals(result, list1.match(subsequence));
        result = new int[]{1, 5};
        subsequence.clear();
        subsequence.add("C");
        subsequence.add("A");
        assertArrayEquals(result, list1.match(subsequence));
        subsequence.clear();
        result = new int[0];
        assertArrayEquals(result, list1.match(subsequence));
        list1.clear();

        for (int i = 0; i < 5; i++)
            list1.add("A");
        subsequence.clear();
        subsequence.add("A");
        result = new int[]{0, 1, 2, 3, 4};
        assertArrayEquals(result, list1.match(subsequence));
    }






}