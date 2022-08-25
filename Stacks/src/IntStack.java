/*
    Name: Viswesh Uppalapati
    PID:  A15600068
 */

//import statements
import java.util.EmptyStackException;

/**
 * This class IntStack.java contains the stack functionality for PA2, implemented
 * using an integer array.
 *
 * @author Viswesh Uppalapati
 * @since  April 9th, 2020
 */
public class IntStack
{

    /* static constants, feel free to add more if you need */
    private static final int    MIN_INIT_CAPACITY = 3;
    private static final int    RESIZE_FACTOR     = 2;
    private static final double DEF_LOAD_FACTOR   = 0.75;
    private static final double MIN_LOAD_FACTOR   = 0.6;
    private static final double DEF_SHRINK_FACTOR = 0.2;
    private static final double MAX_SHRINK_FACTOR = 0.4;

    /* instance variables, feel free to add more if you need */
    private int[] data;
    private int nElems;
    private double loadFactor;
    private double shrinkFactor;
    private int initialCapacity; //the initial capacity of Stack used in Check

    /**
     * Constructor that initializes the Stack.
     * @param capacity  The max number of elements the stack can hold.
     * @param loadF     The loading factor for when we increase the capacity.
     * @param shrinkF   The shrinking factor for decreasing capacity.
     */
    public IntStack(int capacity, double loadF, double shrinkF)
    {
        //throws illegal argument exception when the values passed
        //are outside of the range required
        if (capacity < MIN_INIT_CAPACITY)
            throw new IllegalArgumentException();
        else if (loadF < MIN_LOAD_FACTOR || loadF > 1)
            throw new IllegalArgumentException();
        else if (shrinkF <= 0 || shrinkF > MAX_SHRINK_FACTOR)
            throw new IllegalArgumentException();

        //initializes the int stack and the instance variables
        data = new int[capacity];
        initialCapacity = capacity;
        nElems = 0;
        loadFactor = loadF;
        shrinkFactor = shrinkF;
    }

    /**
     * Another constructor with an unspecified shrink factor, calls main
     * constructor using java keyword this and the DEF_SHRINK_FACTOR.
     * @param capacity  The max number of elements the stack can hold.
     * @param loadF     The loading factor for increasing capacity.
     */
    public IntStack(int capacity, double loadF)
    {
        this(capacity, loadF, DEF_SHRINK_FACTOR);
    }

    /**
     * Another constructor with an unspecified shrink or load factor, calls main
     * constructor using java keyword this and the DEF_SHRINK_FACTOR and
     * DEF_LOAD_FACTOR.
     * @param capacity  The max number of elements the stack can hold.
     */
    public IntStack(int capacity)
    {
        this(capacity, DEF_LOAD_FACTOR, DEF_SHRINK_FACTOR);
    }

    /**
     * Method that checks whether the stack contains any elements.
     * @return  Boolean value of whether the stack is empty.
     */
    public boolean isEmpty()
    {
        return (nElems == 0);
    }

    /**
     * Method that empties the stack and resets it to the size of the
     * initial capacity that was passed into the constructor.
     */
    public void clear()
    {
        //data now references a new empty stack that is set to initial capacity
        data = new int[initialCapacity];
        nElems = 0;
    }

    /**
     * Method that returns the number of elements that the stack contains
     * @return  The number of elements in the stack at that moment.
     */
    public int size()
    {
        return nElems;
    }

    /**
     * This method returns the max number of elements that can be stored in
     * the current stack or the length of the int array that represents the stack.
     * @return  The max number of elements.
     */
    public int capacity()
    {
        return data.length;
    }

    /**
     * Peek returns the last element or the element that is one top of the stack,
     * but does not remove it from the stack.
     * @return  The last element of the stack.
     */
    public int peek()
    {
        //returns element at nElems - 1 because array indices are one lower
        if (!isEmpty())
            return data[nElems - 1];
        else //throw exception if stack has nothing to peek at
            throw new EmptyStackException();
    }

    /**
     * Pushes elements on to the stack, while resizing the capacity if the
     * ratio of elements exceeds laodFactor.
     * @param element   The element to push on to the stack.
     */
    public void push(int element)
    {
        //resize algorithm to increase the capacity of the stack
        if ((double) size() / (double) capacity() >= loadFactor)
        {
            //calculate the new_capacity and reinitialize stack
            int newCapacity = capacity() * RESIZE_FACTOR;
            int [] temp = data;
            data = new int[newCapacity];

            //copy elements from the old stack to the new one
            for (int i = 0; i < size(); i++)
                data[i] = temp[i];
        }

        //push the elements and update nElems
        data[nElems] = element;
        nElems++;
    }

    /**
     * Pop takes out the last element in the stack or the top element of the stack
     * and returns it, it also shrinks the capacity of the stack whenever
     * appropriate.
     * @return  The popped element.
     */
    public int pop()
    {
        // nothing to pop if the stack is empty, throw exception
        if (isEmpty())
            throw new EmptyStackException();

        //update nElems and store the top element
        nElems--;
        int popped = data[nElems];

        //resizing algorithm to shrink stack
        if ((double) size() / (double) capacity() <= shrinkFactor)
        {
            //calculate new capacity
            int newCapacity = capacity() / RESIZE_FACTOR;
            int [] temp = data;

            //in case new capacity is less than the size of the current stack,
            //set the capacity to the size
            if (size() > newCapacity)
                newCapacity = size();

            data = new int[newCapacity];

            //copy elements of old stack onto new
            for (int i = 0; i < size(); i++)
                data[i] = temp[i];
        }

        return popped; //return popped element
    }

    /**
     * This method pushes an array of values onto the stack at once.
     * @param elements  The array of elements to push to stack.
     */
    public void multiPush(int[] elements)
    {
        //if the input array is of null reference, throw exception
        if (elements == null)
            throw new IllegalArgumentException();

        //push elements onto stack
        for (int element : elements)
            push(element);
    }

    /**
     * This methods pops a certain number of elements from the stack
     * based on the input parameter amount and returns them.
     * @param amount    The number of elements to pop.
     * @return          The int array of the popped elements.
     */
    public int[] multiPop(int amount)
    {
        //amount cannot be negative, throw exception
        if (amount < 0)
            throw new IllegalArgumentException();

        //reset amount if it exceeds size of stack.
        if (amount > size())
            amount = size();

        int [] result = new int[amount]; //array to store popped elements

        //popping and storing the popped values
        for (int i = 0; i < amount; i++)
            result[i] = pop();

        return result; //return the popped elements
    }
}
