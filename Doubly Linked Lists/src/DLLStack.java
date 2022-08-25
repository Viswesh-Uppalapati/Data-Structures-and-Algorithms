/**
 * Stack implementation using Doubly-linked list.
 * @param <T> generic container
 */
public class DLLStack<T>
{

    private DoublyLinkedList<T> stack;

    /**
     * Constructor, instanitates the stack used to a
     * new DoublyLinkedList.
     */
    public DLLStack()
    {
        stack = new DoublyLinkedList<>();
    }

    /**
     * Getter for size of the stack.
     * @return  The size of stack
     */
    public int size() { return stack.size(); }

    /**
     * Check for if stack is empty.
     * @return  Whether the stack is empty
     */
    public boolean isEmpty() { return stack.isEmpty(); }

    /**
     * Pushes an element onto the stack.
     * @param data  The data pushed
     */
    public void push(T data) { stack.add(data); }

    /**
     * Pops the element at the top of the stack.
     * @return  The element that is at the top
     */
    public T pop()
    {
        if (isEmpty())
            return null;

        return stack.remove(size() - 1);
    }

    /**
     * Looks at the element at the top of stack.
     * @return  The element at the top.
     */
    public T peek()
    {
        if(isEmpty())
            return null;

        return stack.get(size() - 1);
    }

}
