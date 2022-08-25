import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BSTreeTester
{
    BSTree tree;
    Iterator<Integer> iter;

    @Before
    public void setup()
    {
        tree = new BSTree();
    }


    @Test
    public void getRoot()
    {
        tree.insert(new Integer(49));
        assertEquals(new Integer(49), tree.getRoot().getKey());
        tree = new BSTree();
        tree.insert(new Integer(100));
        assertEquals(new Integer(100), tree.getRoot().getKey());
        tree = new BSTree();
        tree.insert(new Integer(1));
        assertEquals(new Integer(1), tree.getRoot().getKey());
    }

    @Test
    public void getSize()
    {
        tree.insert(new Integer(49));
        assertEquals(1, tree.getSize());
        tree.insert(new Integer(100));
        assertEquals(2, tree.getSize());
        tree.insert(new Integer(100));
        assertEquals(2, tree.getSize());
    }

    @Test
    public void insert()
    {
        assertTrue(tree.insert(new Integer(49)));
        assertTrue(tree.insert(new Integer(100)));
        assertTrue(tree.insert(new Integer(1)));
        assertFalse(tree.insert(new Integer(1)));
        assertEquals(3, tree.getSize());
        assertEquals(new Integer(100), tree.getRoot().getRight().getKey());
        assertEquals(new Integer(1), tree.getRoot().getLeft().getKey());
        assertEquals(new Integer(49), tree.getRoot().getKey());
    }

    @Test (expected = NullPointerException.class)
    public void testInsertExcept()
    {
        tree.insert(null);
    }

    @Test
    public void findKey()
    {
        assertFalse(tree.findKey(new Integer(1)));
        tree.insert(new Integer(49));
        tree.insert(new Integer(1));
        assertTrue(tree.findKey(new Integer(49)));
        assertTrue(tree.findKey(new Integer(1)));
    }

    @Test (expected = NullPointerException.class)
    public void testFindKeyExcept()
    {
        tree.findKey(null);
    }


    @Test
    public void insertData()
    {
        tree.insert(new Integer(49));
        tree.insert(new Integer(1));
        tree.insertData(new Integer(1), "Here at 1");
        assertEquals("Here at 1", tree.getRoot().getLeft().getDataList().get(0));
        tree.insertData(new Integer(1), "Here at 1 again");
        assertEquals("Here at 1 again", tree.getRoot().getLeft().getDataList().get(1));
        tree.insertData(new Integer(49), "Here at 49");
        assertEquals("Here at 49", tree.getRoot().getDataList().get(0));
    }

    @Test (expected = NullPointerException.class)
    public void testInsertDataExcept()
    {
        tree.insertData(null, new Integer(1));
        tree.insertData(new Integer(1), null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInsertDataExcept2()
    {
        tree.insertData(new Integer(1), new Integer(1));
    }

    @Test
    public void findDataList()
    {
        tree.insert(new Integer(49));
        tree.insert(new Integer(1));
        tree.insertData(new Integer(1), "Here at 1");
        assertEquals(1, tree.findDataList(new Integer(1)).size());
        tree.insertData(new Integer(1), "Here at 1 again");
        assertEquals(2, tree.findDataList(new Integer(1)).size());
        tree.insertData(new Integer(49), "Here at 49");
        assertEquals(1, tree.findDataList(new Integer(49)).size());
    }

    @Test (expected = NullPointerException.class)
    public void testFindDataListExcept()
    {
        tree.findDataList(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFindDataListExcept2()
    {
        tree.findDataList(new Integer(1));
    }

    @Test
    public void findHeight()
    {
        assertEquals(-1, tree.findHeight());
        tree.insert(new Integer(49));
        assertEquals(0, tree.findHeight());
        tree.insert(new Integer(9));
        tree.insert(new Integer(1));
        tree.insert(new Integer(110));
        assertEquals(2, tree.findHeight());
        tree.insert(new Integer(12));
        tree.insert(new Integer(60));
        assertEquals(2, tree.findHeight());
        tree.insert(new Integer(13));
        tree.insert(new Integer(14));
        assertEquals(4, tree.findHeight());
    }

    @Test
    public void leafCount()
    {
        assertEquals(0, tree.leafCount());
        tree.insert(new Integer(49));
        assertEquals(1, tree.leafCount());
        tree.insert(new Integer(9));
        tree.insert(new Integer(100));
        assertEquals(2, tree.leafCount());
        tree.insert(new Integer(12));
        tree.insert(new Integer(110));
        tree.insert(new Integer(60));
        tree.insert(new Integer(1));
        assertEquals(4, tree.leafCount());
    }

    @Test
    public void testIteratorConstructor()
    {
        tree.insert(100);
        tree.insert(50);
        tree.insert(10);
        iter = tree.iterator();
        assertEquals(new Integer(10), iter.next());
        assertEquals(new Integer(50), iter.next());
        assertEquals(new Integer(100), iter.next());
    }

    @Test
    public void testHasNext()
    {
        iter = tree.iterator();
        assertFalse(iter.hasNext());
        tree.insert(100);
        iter = tree.iterator();
        assertTrue(iter.hasNext());
        iter.next();
        assertFalse(iter.hasNext());
    }

    @Test
    public void testNext()
    {
        tree.insert(100);
        tree.insert(50);
        tree.insert(60);
        tree.insert(55);
        tree.insert(70);
        tree.insert(110);
        iter = tree.iterator();
        assertEquals(new Integer(50), iter.next());
        assertEquals(new Integer(55), iter.next());
        assertEquals(new Integer(60), iter.next());
        assertEquals(new Integer(70), iter.next());
        assertEquals(new Integer(100), iter.next());
        assertEquals(new Integer(110), iter.next());
    }

    @Test (expected = NoSuchElementException.class)
    public void testNextExcept()
    {
        iter = tree.iterator();
        iter.next();
    }

    @Test
    public void testIntersection()
    {
        BSTree<Integer> tree2 = new BSTree<>();
        Integer [] list;
        ArrayList<Integer> empty = new ArrayList<>();
        assertEquals(empty, tree.intersection(tree.iterator(), tree2.iterator()));
        tree.insert(1);
        assertEquals(empty, tree.intersection(tree.iterator(), tree2.iterator()));
        tree.insert(5);
        tree.insert(1);
        tree.insert(10);
        tree.insert(0);
        tree.insert(4);
        tree.insert(7);
        tree.insert(9);
        tree2.insert(10);
        tree2.insert(20);
        tree2.insert(7);
        tree2.insert(4);
        tree2.insert(9);
        list = new Integer [] {4, 7, 9, 10};
        assertArrayEquals(list, tree.intersection(tree.iterator(), tree2.iterator()).toArray());
    }

    @Test
    public void testLevelCount()
    {
        tree.insert(5);
        tree.insert(3);
        tree.insert(6);
        tree.insert(4);
        tree.insert(2);
        assertEquals(1, tree.levelCount(0));
        assertEquals(2, tree.levelCount(1));
        assertEquals(2, tree.levelCount(2));
        assertEquals(-1, tree.levelCount(3));
    }


}