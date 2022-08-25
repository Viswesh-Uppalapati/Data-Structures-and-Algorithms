
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import java.util.NoSuchElementException;

public class dHeapTester
{
    dHeap heap2;
    dHeap heap3;
    dHeap heap4;
    dHeap minHeap;

    @Before
    public void setup()
    {
        heap2 = new dHeap();
        heap3 = new dHeap(3, 6, true);
        heap4 = new dHeap(4, 5, true);
        minHeap = new dHeap(4, 5, false);
    }

    @Test
    public void size()
    {
        assertEquals(0, heap4.size());
        heap4.add(10);
        heap4.add(0);
        assertEquals(2, heap4.size());
        heap4.clear();
        assertEquals(0, heap4.size());
    }

    @Test
    public void add()
    {
        for (int i = 0; i <= 100; i+=10) {
            heap4.add(i);
            heap3.add(i);
            heap2.add(i);
        }
        assertEquals(11, heap4.size());
        assertEquals(11, heap3.size());
        assertEquals(11, heap2.size());
        for (int i = 100; i >= 30; i-=10) {
            assertEquals(i, heap4.remove());
            assertEquals(i, heap3.remove());
            assertEquals(i, heap2.remove());
        }
        heap4.add(100);
        assertEquals(100, heap4.remove());
        heap4.clear();
        heap4.add(1000);
        assertEquals(1000, heap4.element());


        for (int i = 100; i >= 0; i -= 10)
            minHeap.add(i);
        assertEquals(11, minHeap.size());
        for (int i = 0; i <= 70; i+=10)
            assertEquals(i, minHeap.remove());
        minHeap.add(1);
        minHeap.add(5);
        assertEquals(1, minHeap.remove());
        assertEquals(5, minHeap.remove());

    }

    @Test (expected = NullPointerException.class)
    public void testAddExcept()
    {
        heap4.add(null);
    }


    @Test
    public void remove()
    {
        for (int i = 0; i <= 100; i+=10) {
            heap4.add(i);
            heap3.add(i);
            heap2.add(i);
        }
        assertEquals(11, heap4.size());
        assertEquals(11, heap3.size());
        assertEquals(11, heap2.size());
        for (int i = 100; i >= 0; i-=10) {
            assertEquals(i, heap4.remove());
            assertEquals(i, heap3.remove());
            assertEquals(i, heap2.remove());
        }
        assertEquals(0, heap4.size());
        assertEquals(0, heap3.size());
        assertEquals(0, heap2.size());

        for (int i = 100; i >= 0; i -= 10)
            minHeap.add(i);
        assertEquals(11, minHeap.size());
        for (int i = 0; i <= 70; i+=10)
            assertEquals(i, minHeap.remove());
        minHeap.add(1);
        minHeap.add(5);
        assertEquals(1, minHeap.remove());
        assertEquals(5, minHeap.remove());
    }

    @Test (expected = NoSuchElementException.class)
    public void testRemoveExcept()
    {
        heap4.remove();
    }

    @Test
    public void clear()
    {
        assertEquals(0, heap2.size());
        assertEquals(0, heap3.size());
        assertEquals(0, heap4.size());
        heap4.add(100);
        heap4.add(10);
        assertEquals(2, heap4.size());
        heap4.clear();
        assertEquals(0, heap4.size());
    }

    @Test
    public void element()
    {
        heap4.add(100);
        assertEquals(100, heap4.element());
        heap4.add(999);
        heap4.add(1000);
        assertEquals(1000, heap4.element());
        heap4.add(8888);
        assertEquals(8888, heap4.element());

        minHeap.add(100);
        assertEquals(100, minHeap.element());
        minHeap.add(999);
        minHeap.add(1000);
        assertEquals(100, minHeap.element());
        minHeap.add(1);
        assertEquals(1, minHeap.element());
    }

    @Test (expected = NoSuchElementException.class)
    public void testElementExcept()
    {
        heap4.element();
    }
}