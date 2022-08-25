/*
 * NAME: Viswesh Uppalapati
 * PID: A15600068
 */

import java.util.AbstractList;

/**
 * Doubly-Linked List Implementation
 *
 * @author Viswesh Uppalapati
 * @since 24th April 2020
 */
public class DoublyLinkedList<T> extends AbstractList<T>
{
    private int nelems;
    private Node head;
    private Node tail;

    /**
     * Node for chaining together to create a linked list
     */
    protected class Node
    {
        T data;
        Node next;
        Node prev;

        /**
         * Constructor to create singleton Node
         */
        private Node(T element)
        {
            this.data = element;
            this.next = this.prev = null;
        }

        /**
         * Constructor to create singleton link it between previous and next
         *
         * @param element  Element to add, can be null
         * @param nextNode successor Node, can be null
         * @param prevNode predecessor Node, can be null
         */
        private Node(T element, Node nextNode, Node prevNode)
        {
            this.data = element;
            this.next = nextNode;
            this.prev = prevNode;
        }

        /**
         * Set the element
         *
         * @param element new element
         */
        public void setElement(T element) { this.data = element; }

        /**
         * Accessor to get the Nodes Element
         */
        public T getElement() { return this.data; }

        /**
         * Set the next node in the list
         *
         * @param n new next node
         */
        public void setNext(Node n) { this.next = n; }

        /**
         * Get the next node in the list
         *
         * @return the successor node
         */
        public Node getNext() { return this.next; }

        /**
         * Set the previous node in the list
         *
         * @param p new previous node
         */
        public void setPrev(Node p) { this.prev = p; }

        /**
         * Accessor to get the prev Node in the list
         *
         * @return predecessor node
         */
        public Node getPrev() { return this.prev; }

        /**
         * Remove this node from the list.
         * Update previous and next nodes
         */
        public void remove()
        {
            this.next = this.prev = null;
        }
    }

    /**
     * Creates a new, empty doubly-linked list.
     */
    public DoublyLinkedList()
    {
        head = new Node(null);
        tail = new Node(null);

        // head and tail set to each other for empty list
        head.setNext(tail);
        tail.setPrev(head);
        nelems = 0;
    }

    /**
     * Add an element to the end of the list
     *
     * @param element data to be added
     * @return whether or not the element was added
     * @throws NullPointerException if data received is null
     */
    @Override
    public boolean add(T element) throws NullPointerException
    {
        if (element == null)
            throw new NullPointerException();

        //add to end of list by using the tail pointer
        Node temp = new Node(element, tail, tail.getPrev());
        tail.getPrev().setNext(temp);
        tail.setPrev(temp);
        nelems++;

        return true;
    }


    /**
     * Adds an element to a certain index in the list, shifting exist elements
     * create room. Does not accept null values.
     * @param element data to be added
     * @param index the index to add at
     * @throws NullPointerException if data received is null
     * @throws IndexOutOfBoundsException if the index is out of range
     *
     */
    @Override
    public void add(int index, T element)
            throws IndexOutOfBoundsException, NullPointerException
    {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        else if (element == null)
            throw new NullPointerException();

        // if adding to last index, run other add for shorter time
        if (index == size())
            add(element);
        else
        {
            // get the node at index and add new node at the index
            Node current = getNth(index);
            Node toAdd = new Node(element, current, current.getPrev());
            current.getPrev().setNext(toAdd);
            current.setPrev(toAdd);
            nelems++;
        }
    }

    /**
     * Clear the linked list
     */
    @Override
    public void clear()
    {
        // point head and tail to each other for empty list
        // un-links all the elements
        head.setNext(tail);
        tail.setPrev(head);
        nelems = 0;
    }

    /**
     * Determine if the list contains the data element anywhere in the list.
     * @param element the data to check
     * @return whether the data exists in the list
     */
    @Override
    public boolean contains(Object element)
    {
        T data = (T)element;
        Node ptr = head;

        // traverse list and see if the data matches any of the nodes
        while(ptr.getNext() != null)
        {
            ptr = ptr.getNext();
            if (data.equals(ptr.getElement()))
                return true;
        }

        return false;
    }

    /**
     * Retrieves the element stored with a given index on the list.
     * @param index the index at which to get the data
     * @return the data
     * @throws IndexOutOfBoundsException when the index is out of range of list
     */
    @Override
    public T get(int index) throws IndexOutOfBoundsException
    {
        if (index < 0 || index > size() - 1)
            throw new IndexOutOfBoundsException();

        return getNth(index).getElement();
    }

    /**
     * Helper method to get the Nth node in our list
     * @param index the node at index to get
     * @return the node at the index
     */
    private Node getNth(int index)
    {
        Node ptr = head;

        // loop through list to the node at index
        for(int i = 0; i <= index; i++)
            if(ptr.getNext() != null)
                ptr = ptr.getNext();

        return ptr;
    }

    /**
     * Determine if the list empty
     * @return whether the list is empty
     */
    @Override
    public boolean isEmpty() { return (nelems == 0); }

    /**
     * Remove the element from position index in the list
     * @param index The index to remove
     * @return the data of the previous node
     * @throws IndexOutOfBoundsException when the index isn't in list
     */
    @Override
    public T remove(int index) throws IndexOutOfBoundsException
    {
        if (index < 0 || index > size() - 1)
            throw new IndexOutOfBoundsException();

        // get the node at index, change the pointers of the previous and next
        // node to point to each other, then remove current node and decrement nelems
        Node current = getNth(index);
        T oldData = current.getElement();
        current.getPrev().setNext(current.getNext());
        current.getNext().setPrev(current.getPrev());
        current.remove();
        nelems--;

        return oldData;
    }

    /**
     * Set the value of an element at a certain index in the list.
     * @param index the index to change
     * @param element the element to set to
     * @return the data at the old node
     * @throws IndexOutOfBoundsException when index isn't in list
     * @throws NullPointerException when input is null
     */
    @Override
    public T set(int index, T element)
            throws IndexOutOfBoundsException, NullPointerException
    {
        if (index < 0 || index > size() - 1)
            throw new IndexOutOfBoundsException();
        else if (element == null)
            throw new NullPointerException();

        // get node at index, get old data, set new data
        Node current = getNth(index);
        T oldData = current.getElement();
        current.setElement(element);

        return oldData;
    }

    /**
     * Retrieves the amount of elements that are currently on the list.
     * @return the number of elements in the list
     */
    @Override
    public int size() { return nelems; }

    /**
     * String representation of this list in the form of:
     * "[(head) -> elem1 -> elem2 -> ... -> elemN -> (tail)]"
     * @return the string representation of the list
     */
    @Override
    public String toString()
    {
        String result = "[(head) ->";

        for(int i = 0; i < nelems; i++)
            result += " " + get(i) + " ->";

        return result + " (tail)]";
    }

    /* ==================== EXTRA CREDIT ==================== */

    /**
     * Inserts another linked list of the same type into this one
     * @param index     The index to splice at
     * @param otherList The other list to add to list
     */
    public void splice(int index, DoublyLinkedList<T> otherList) throws IndexOutOfBoundsException
    {
        // check index
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();

        // access first and last Node of otherList using the dummy nodes
        // and get the current node in list at index
        Node current = getNth(index);
        Node otherFirst = otherList.head.getNext();
        Node otherLast = otherList.tail.getPrev();

        // Relink the Nodes so that otherList is inserted into
        // the current list
        otherFirst.setPrev(current.getPrev());
        otherLast.setNext(current);
        current.getPrev().setNext(otherFirst);
        current.setPrev(otherLast);
        nelems += otherList.size();
    }

    /**
     * Determine the starting indices that match the subSequence
     * @param subsequence   The sequence to find in list.
     * @return              The int array with the starting indices
     */
    public int[] match(DoublyLinkedList<T> subsequence)
    {
        //A list to hold all the starting indices found
        DoublyLinkedList<Integer> indices = new DoublyLinkedList<>();

        // local variables needed
        int index = 0; // keeps track of the index
        Node ptrOrigList = head.getNext(); // ptr to traverse original list
        Node temp = subsequence.head.getNext(); // start of subsequence list
        Node ptrSubList = temp; // ptr to traverse subsequence list
        boolean found; // if the sequence is found

        // if there is a subsequence to find
        if (subsequence.size() != 0)
            while (ptrOrigList.getNext() != null) // loop through each element of original list
            {
                found = true; // default found set to true
                while (ptrSubList.getNext() != null) // loop through subsequence
                {
                    // if data of current node is equal to data of subsequence node
                    if (ptrOrigList.getElement().equals(ptrSubList.getElement()))
                    {
                        // advance pointers to the next element list
                        ptrSubList = ptrSubList.getNext();
                        ptrOrigList = ptrOrigList.getNext();
                        index++;
                    }
                    else
                    {
                        // if it is not equal, reset subsequence ptr to the
                        // front of subsequence, found is false, break loop
                        ptrSubList = temp;
                        found = false;
                        break;
                    }
                }

                // if found stays true through second while, we found the sequence
                if (found)
                {
                    // add starting index to result list
                    indices.add(new Integer(index - subsequence.size()));
                    // set subsequence ptr to the beginning of subsequence to
                    // check for other occurances
                    ptrSubList = temp;
                }
                else
                {
                    // move to the next node of original list
                    ptrOrigList = ptrOrigList.getNext();
                    index++;
                }
            }


        // Array Conversion
        int[] startingIndices = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            startingIndices[i] = indices.get(i);
        }
        return startingIndices;
    }

}