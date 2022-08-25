/*
    Name: Viswesh Uppalapati
    PID: A15600068
 */

import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class IntStackTest {

    IntStack st1;

    @Before
    public void setup() { st1 = new IntStack(5); }

    @Test
    public void isEmpty()
    {
        st1.clear();
        assertTrue(st1.isEmpty());

        st1.push(1);
        assertFalse(st1.isEmpty());

        st1.clear();
        assertTrue(st1.isEmpty());


    }

    @Test
    public void clear()
    {
        st1.clear();
        st1.push(0);
        st1.push(1);
        st1.clear();

        assertTrue(st1.isEmpty());

        assertEquals(0, st1.size());

        assertEquals(5, st1.capacity());
    }

    @Test
    public void size()
    {
        st1.clear();
        assertEquals(0, st1.size());
        st1.push(0);
        assertEquals(1, st1.size());
        st1.push(1);
        st1.pop();
        assertEquals(1, st1.size());
        st1.clear();
    }

    @Test
    public void capacity()
    {
        st1.clear();
        assertEquals(5, st1.capacity());
        int [] temp = {1, 2, 3, 4, 5};
        st1.multiPush(temp);
        assertEquals(10, st1.capacity());
        st1.clear();
        assertEquals(5, st1.capacity());
    }

    @Test (expected = EmptyStackException.class)
    public void Peek()
    {
        st1.clear();
        st1.push(1);
        st1.push(2);
        st1.push(3);
        assertEquals(3, st1.peek());
        st1.push(4);
        assertEquals(4, st1.peek());
        st1.pop();
        assertEquals(3, st1.peek());
        st1.clear();
        st1.peek(); //throw exception
    }

    @Test
    public void push()
    {
        st1.clear();
        st1.push(0);
        assertEquals(0, st1.pop());
        st1.push(100);
        assertEquals(100, st1.peek());
        st1.push(50);
        assertEquals(50, st1.pop());
    }

    @Test (expected = EmptyStackException.class)
    public void pop()
    {
        st1.clear();
        int [] temp = {1, 2, 3, 4, 5, 6};
        st1.multiPush(temp);
        assertEquals(6, st1.pop());
        st1.pop();
        st1.pop();
        st1.pop();
        assertEquals(5, st1.capacity());
        assertEquals(2, st1.peek());
        st1.clear();
        st1.pop(); //throw exception
    }

    @Test (expected = IllegalArgumentException.class)
    public void multiPush()
    {
        st1.clear();
        assertEquals(0, st1.size());
        int [] temp = null;
        st1.multiPush(temp); //throw exception
        temp = new int[]{1, 2, 3, 4, 5, 6};
        st1.multiPush(temp);
        assertEquals(6, st1.size());
        assertEquals(10, st1.capacity());

    }

    @Test (expected = IllegalArgumentException.class)
    public void testMultiPop()
    {
        st1.clear();
        int [] temp = {1, 2, 3, 4, 5, 6};
        st1.multiPush(temp);
        assertEquals(6, st1.size());
        int [] result = {6, 5, 4, 3, 2, 1};
        assertArrayEquals(result, st1.multiPop(6));
        assertEquals(0, st1.size());
        st1.multiPop(-1); //throw exception
        st1.clear();
        result = new int[0];
        assertArrayEquals(result, st1.multiPop(20));
    }
}