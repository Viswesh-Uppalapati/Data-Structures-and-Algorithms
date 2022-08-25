import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class DLLStackTest
{
    DLLStack st;
    DLLStack st2;
    DLLStack st3;

    @Before
    public void setup()
    {
        st = new DLLStack();
        st2 = new DLLStack();
        st3 = new DLLStack();
    }

    @Test
    public void size()
    {
        assertEquals(0, st.size());
        st.push(new Integer(2));
        st.push(new Integer(3));
        st.push(new Integer(4));
        assertEquals(3, st.size());
        st.pop();
        st.pop();
        assertEquals(1, st.size());
    }

    @Test
    public void isEmpty()
    {
        assertTrue(st2.isEmpty());
        st2.push(new Integer(1));
        assertFalse(st2.isEmpty());
        st2.pop();
        assertTrue(st2.isEmpty());
    }

    @Test
    public void push()
    {
        assertTrue(st.isEmpty());
        st.push(new Integer(2));
        st.push(new Integer(3));
        st.push(new Integer(4));
        assertEquals(3, st.size());
        assertEquals(new Integer(4), st.pop());
        assertEquals(new Integer(3), st.pop());
        assertEquals(new Integer(2), st.pop());
        assertEquals(0, st.size());
    }

    @Test
    public void pop()
    {
        st.push(new Integer(2));
        st.push(new Integer(3));
        st.push(new Integer(4));
        assertEquals(new Integer(4), st.pop());
        assertEquals(new Integer(3), st.pop());
        assertEquals(new Integer(2), st.pop());
        assertNull(st.pop());
    }

    @Test
    public void peek()
    {
        st.push(new Integer(2));
        assertEquals(new Integer(2), st.peek());
        st.push(new Integer(3));
        st.push(new Integer(4));
        assertEquals(new Integer(4), st.peek());
        assertEquals(new Integer(4), st.peek());
        st.pop();
        st.pop();
        st.pop();
        assertNull(st.peek());
    }
}