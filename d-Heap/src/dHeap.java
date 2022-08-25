/*
 * Name: Viswesh Uppalapati
 * PID:  A15600068
 */

import java.util.*;

/**
 * @Name: Viswesh Uppalapati
 * @Since: 05/18/20
 *
 * This class contains the functionality for the heap
 * @param <T> Generic type
 */
public class dHeap<T extends Comparable<? super T>> implements dHeapInterface<T>
{
    private static final int DEFAULT_SIZE = 6; // default length of heap
    private static final int DEFAULT_BF = 2;  // default branch factor 2

    private T[] heap; // heap array
    private int d; // branching factor
    private int nelems; // number of elements
    private boolean isMaxHeap; // boolean to indicate whether heap is max or min

    /**
     * Initializes a binary max heap with capacity = 6
     */
    @SuppressWarnings("unchecked")
    public dHeap()
    {
        this.heap = (T[]) new Comparable[DEFAULT_SIZE];
        this.d = DEFAULT_BF;
        this.nelems = 0;
        this.isMaxHeap = true;
    }

    /**
     * Initializes a binary max heap with a given initial capacity.
     *
     * @param heapSize The initial capacity of the heap.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int heapSize)
    {
        this.heap = (T[]) new Comparable[heapSize];
        this.d = DEFAULT_BF;
        this.nelems = 0;
        this.isMaxHeap = true;
    }

    /**
     * Initializes a d-ary heap (with a given value for d), with a given initial
     * capacity.
     *
     * @param d         The number of child nodes each node in the heap should have.
     * @param heapSize  The initial capacity of the heap.
     * @param isMaxHeap indicates whether the heap should be max or min
     * @throws IllegalArgumentException if d is less than one.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int d, int heapSize, boolean isMaxHeap) throws IllegalArgumentException
    {
        this.heap = (T[]) new Comparable[heapSize];
        this.d = d;
        this.nelems = 0;
        this.isMaxHeap = isMaxHeap;
    }

    /**
     * Size method returns the number of elements in the current heap.
     * @return  The size of the heap
     */
    @Override
    public int size() { return nelems; }

    /**
     * Adds data to the heap in the proper order by calling
     * the bubble up method.
     * @param data  The data to add to the heap
     * @throws NullPointerException  when data is null
     */
    @Override
    public void add(T data) throws NullPointerException
    {
        // check input
        if (data == null)
            throw new NullPointerException();

        // resize if no more elements can be added to current heap
        if(nelems == heap.length)
            resize();

        // add the data to the heap
        heap[nelems] = data;
        bubbleUp(nelems);
        nelems++;
    }

    /**
     * The remove metod removes the root of the heap that
     * contains the largest/smallest element depending on
     * max or min heap and returns it and reorders the heap.
     * @return  The smallest/largest value
     * @throws NoSuchElementException   when heap is empty
     */
    @Override
    public T remove() throws NoSuchElementException
    {
        // check if heap is empty
        if (nelems == 0)
            throw new NoSuchElementException();

        // store the root and then remove it from the heap
        T result = heap[0];
        heap[0] = heap[nelems-1];
        nelems--;
        trickleDown(0);
        return result;
    }

    /**
     * This method clears the current heap.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() { nelems = 0; }


    /**
     * This method returns the element at the root of the
     * heap, but does not remove it from the heap.
     * @return  The element at root
     * @throws NoSuchElementException
     */
    public T element() throws NoSuchElementException
    {
        if (nelems == 0)
            throw new NoSuchElementException();

        return heap[0];
    }

    /**
     * Helper method for remove that uses recursion to reorder
     * the heap to maintain heap order by reordering the elements
     * at index.
     * @param index The current index to trickle down
     */
    private void trickleDown(int index)
    {
        int Ptr = d * index + 1;
        boolean toSwap = false;

        // finds the max or min child
        for (int i = d * index + 1; i <= (d * index + d); i++)
        {
            if (isMaxHeap && i < nelems && heap[Ptr].compareTo(heap[i]) < 0)
                Ptr = i;
            else if (!isMaxHeap && i < nelems && heap[Ptr].compareTo(heap[i]) > 0)
                Ptr = i;
        }

        // if recursion goes out of bounds of the heap, end recursion
        if (Ptr < 0 || Ptr > nelems)
            return;
        else if (index < 0 || index > nelems)
            return;
        // for max heap, swap if condition holds
        else if (isMaxHeap && heap[index].compareTo(heap[Ptr]) < 0)
            toSwap = true;
        // for min heap, swap if condition holds
        else if (!isMaxHeap && heap[index].compareTo(heap[Ptr]) > 0)
            toSwap = true;

        // if we must swap, recurse down tree
        if (toSwap)
        {
            swap(index, Ptr);
            trickleDown(Ptr); // recurse down the tree
        }
    }

    /**
     * Helper method for add that bubbles the value at
     * index to its right spot in the heap.
     * @param index     The element to bubble
     */
    private void bubbleUp(int index)
    {
        // get the parent index
        int parInd = parent(index);
        boolean toSwap = false;

        // if index becomes less than 0, end recursion
        if (parInd < 0 || index < 0)
            return;
        // maxHeap swap if condition holds
        else if (isMaxHeap && heap[index].compareTo(heap[parInd]) > 0)
            toSwap = true;
        // minHeap swap if condition holds
        else if (!isMaxHeap && heap[index].compareTo(heap[parInd]) < 0 )
            toSwap = true;

        // recurse up if we swap the elements
        if (toSwap)
        {
            swap(index, parInd);
            bubbleUp(parInd);
        }
    }

    /**
     * Swap helper method for trickledown and bubbleUp that
     * swaps elements at two different indices.
     * @param index1    The first index to swap
     * @param index2    The second index to swap
     */
    private void swap(int index1, int index2)
    {
        T temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }


    /**
     * Method for add that resizes the heap if no more
     * elements can be added onto it.
     */
    @SuppressWarnings("unchecked")
    private void resize()
    {
        // create heap with twice the capacity
        int currentSize = heap.length;
        T[] temp = heap;
        heap = (T[]) new Comparable[currentSize + currentSize];

        // copy data from old heap to new
        for (int i = 0; i < temp.length; i++)
            heap[i] = temp[i];
    }

    /**
     * Helper method for bubble up that calculates the index of the
     * parent node of a given node.
     * @param index The index of the child
     * @return      The index of the parent
     */
    private int parent(int index) { return (index - 1) / d; }

}
