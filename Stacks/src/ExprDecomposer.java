/*
    Name: Viswesh Uppalapati
    PID:  A15600068
 */

import java.util.EmptyStackException;

/**
 * This class contains the CharStack and an algorithm to decompose simple
 * mathematical expressions to postfix notation.
 *
 * @author Viswesh Uppalapati
 * @since  April 12th, 2020
 */
public class ExprDecomposer
{

    /**
     * This method contains the general algorithm to decompose a math
     * expression. Takes input of expression as a string ans outputs
     * char array with postfix order of elements.
     * @param expr  The expression to decompose.
     * @return      The char array with the special expression.
     */
    public char[] decompose(String expr)
    {
        //char stack to store operators and parenthesis
        CharStack chStack = new CharStack(expr.length());
        char [] temp = new char[expr.length()]; //stores special expression
        int index = 0; //to traverse the input string
        int count = 0; //to keep count of elements in the result array

        //till we loop through every character in input
        while (index < expr.length())
        {
            char current = expr.charAt(index); //get char at current index

            //if digit, add it directly to result array
            if (isDigit(current))
            {
                temp[count] = current;
                count++;
            }
            //if it is an open parenthesis or operator push it to stack
            else if (isOperator(current) || current == '(')
                chStack.push(current);
            //if it is a close parenthesis
            else if (current == ')')
            {
                //it means that current sub-expr has ended, so pop the char
                //stack till the previous open parenthesis due to PEMDAS
                while (chStack.peek() != '(')
                {
                    temp[count] = chStack.pop();
                    count++;
                }
                chStack.pop(); //get rid of open parenthesis that was pushed
            }
            index++; //go to the next value in the input expression
        }

        //to take care of operation/expressions that occur outside of all
        //the parenthesis, pop char stack till it is empty in the end
        while (!chStack.isEmpty())
        {
            temp[count] = chStack.pop();
            count++;
        }

        //transfer postfix notation to a new array, that is the exact
        //size of the result based on the count of elements added to temp
        char [] result = new char[count];
        for (int i = 0; i < count; i++)
            result[i] = temp[i];

        return result; //return array with special expression
    }

    /**
     * UTILITY METHOD, DO NOT MODIFY *
     * Check if the given token represents a digit
     * @param token to check
     * @return boolean true if token is a digit, false otherwise
     */
    private boolean isDigit(char token) {
        return (token >= '0') && (token <= '9');
    }

    /**
     * UTILITY METHOD, DO NOT MODIFY *
     * Check if the given token represents an operator
     * @param token to check
     * @return boolean true if token is an operator, false otherwise
     */
    private boolean isOperator(char token)
    {
        return (token == '+') || (token == '-') || (token == '*') || (token == '/');
    }

    /**
     * Inner class CharStack.
     * Note: You can remove methods and variables that you will not use for
     * this question, but you must keep both push() and pop() methods and they
     * should function properly.
     */
    protected class CharStack
    {
        /* static constants, feel free to add more if you need */
        private static final int    MIN_INIT_CAPACITY = 3;
        private static final int    RESIZE_FACTOR     = 2;
        private static final double DEF_LOAD_FACTOR   = 0.75;
        private static final double MIN_LOAD_FACTOR   = 0.6;
        private static final double DEF_SHRINK_FACTOR = 0.2;
        private static final double MAX_SHRINK_FACTOR = 0.4;

        /* instance variables, feel free to add more if you need */
        private char[] data;
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
        public CharStack(int capacity, double loadF, double shrinkF)
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
            data = new char[capacity];
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
        public CharStack(int capacity, double loadF)
        {
            this(capacity, loadF, DEF_SHRINK_FACTOR);
        }

        /**
         * Another constructor with an unspecified shrink or load factor, calls main
         * constructor using java keyword this and the DEF_SHRINK_FACTOR and
         * DEF_LOAD_FACTOR.
         * @param capacity  The max number of elements the stack can hold.
         */
        public CharStack(int capacity)
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
            data = new char[initialCapacity];
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
        public void push(char element)
        {
            //resize algorithm to increase the capacity of the stack
            if ((double) size() / (double) capacity() >= loadFactor)
            {
                //calculate the new_capacity and reinitialize stack
                int newCapacity = capacity() * RESIZE_FACTOR;
                char [] temp = data;
                data = new char[newCapacity];

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
        public char pop()
        {
            // nothing to pop if the stack is empty, throw exception
            if (isEmpty())
                throw new EmptyStackException();

            //update nElems and store the top element
            nElems--;
            char popped = data[nElems];

            //resizing algorithm to shrink stack
            if ((double) size() / (double) capacity() <= shrinkFactor)
            {
                //calculate new capacity
                int newCapacity = capacity() / RESIZE_FACTOR;
                char [] temp = data;

                //in case new capacity is less than the size of the current stack,
                //set the capacity to the size
                if (size() > newCapacity)
                    newCapacity = size();

                data = new char[newCapacity];

                //copy elements of old stack onto new
                for (int i = 0; i < size(); i++)
                    data[i] = temp[i];
            }

            return popped; //return popped element
        }
    }
}
