import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DLLQueueTest
{
    DLLQueue q1;
    DLLQueue q2;
    DLLQueue q3;

    @Before
    public void setup()
    {
        q1 = new DLLQueue();
        q2 = new DLLQueue();
        q3 = new DLLQueue();
    }

    @Test
    public void size()
    {
        assertEquals(0, q1.size());
        q1.enqueue(new Integer(2));
        q1.enqueue(new Integer(3));
        q1.enqueue(new Integer(4));
        assertEquals(3, q1.size());
        q1.dequeue();
        q1.dequeue();
        assertEquals(1, q1.size());
    }

    @Test
    public void isEmpty()
    {
        assertTrue(q2.isEmpty());
        q2.enqueue(new Integer(1));
        assertFalse(q2.isEmpty());
        q2.dequeue();
        assertTrue(q2.isEmpty());
    }

    @Test
    public void enqueue()
    {
        assertTrue(q1.isEmpty());
        q1.enqueue(new Integer(2));
        q1.enqueue(new Integer(3));
        q1.enqueue(new Integer(4));
        assertEquals(3, q1.size());
        assertEquals(new Integer(2), q1.dequeue());
        assertEquals(new Integer(3), q1.dequeue());
        assertEquals(new Integer(4), q1.dequeue());
        assertEquals(0, q1.size());
    }

    @Test
    public void dequeue()
    {
        q1.enqueue(new Integer(2));
        q1.enqueue(new Integer(3));
        q1.enqueue(new Integer(4));
        assertEquals(new Integer(2), q1.dequeue());
        assertEquals(new Integer(3), q1.dequeue());
        assertEquals(new Integer(4), q1.dequeue());
        assertNull(q1.dequeue());
    }

    @Test
    public void peek()
    {
        q1.enqueue(new Integer(2));
        assertEquals(new Integer(2), q1.peek());
        q1.enqueue(new Integer(3));
        q1.enqueue(new Integer(4));
        assertEquals(new Integer(2), q1.peek());
        assertEquals(new Integer(2), q1.peek());
        q1.dequeue();
        q1.dequeue();
        q1.dequeue();
        assertNull(q1.peek());
    }
}