/**
 * Queue implementation using Doubly-linked list.
 * @param <T> generic container
 */
public class DLLQueue<T>
{

    private DoublyLinkedList<T> queue;

    /**
     * Constructor, initializes queue to a new
     * DoublyLinkedList.
     */
    public DLLQueue()
    {
        queue = new DoublyLinkedList<>();
    }

    /**
     * Getter for number of elements in queue.
     * @return  The size of the queue
     */
    public int size() { return queue.size(); }

    /**
     * Check if the queue has 0 elements.
     * @return  Whether the queue is empty
     */
    public boolean isEmpty() { return queue.isEmpty(); }

    /**
     * Adds an element onto the queue.
     * @param data  The element to add
     */
    public void enqueue(T data) { queue.add(data); }

    /**
     * Removes and returns the element at the front
     * of the queue.
     * @return  The element at the front of the queue
     */
    public T dequeue()
    {
        if (isEmpty())
            return null;

        return queue.remove(0);
    }

    /**
     * Looks at the element in the front.
     * @return  The element in the front
     */
    public T peek()
    {
        if (isEmpty())
            return null;

        return queue.get(0);
    }

}
