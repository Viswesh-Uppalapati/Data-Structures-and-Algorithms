/**
 * Functionality of the round robin method of allocating
 * processors to tasks.
 */
public class RoundRobin
{

    // constants
    private static final int DEFAULT_QUANTUM =  3;
    private static final String TASK_HANDLED = "All tasks are already handled.";

    // instance variables
    private DoublyLinkedList<Task> waitlist, finished;
    private int quantum, burstTime, waitTime;

    /**
     * Constructor, when quantum is not specified. Calls other constructor
     * with DEFAULT_QUANTUM
     * @param toHandle  The tasks to handle.
     */
    public RoundRobin(Task[] toHandle)
    {
        this(DEFAULT_QUANTUM, toHandle);
    }

    /**
     * Constructor, initializes fields adds tasks to waitlist.
     * @param quantum   The quantum of processing time
     * @param toHandle  The tasks to handle in order
     */
    public RoundRobin(int quantum, Task[] toHandle)
    {
        // handle input exceptions
        if (quantum < 1)
            throw new IllegalArgumentException();
        else if (toHandle == null || toHandle.length == 0)
            throw new IllegalArgumentException();

        // initialize waitlist and finished linked lists
        finished = new DoublyLinkedList<>();
        waitlist = new DoublyLinkedList<>();

        // add tasks in order to waitlist
        for (int i = 0; i < toHandle.length; i++)
            waitlist.add(toHandle[i]);

        // initialize rest of fields
        this.quantum = quantum;
        burstTime = waitTime = 0;
    }

    /**
     * The method that handles the processing and allocating the tasks.
     * @return  The order of finished tasks or if task is completed
     */
    public String handleAllTasks()
    {
        // if waitlist is empty, nothing to process
        // return default string that tasks are handled
        if (waitlist.isEmpty())
            return TASK_HANDLED;

        // process each task till waitlist is empty
        while (!waitlist.isEmpty())
        {
            // get the task at the beginning of waitlist
            Task current = waitlist.remove(0);
            int usedTime = 0;

            // handle the task
            for (int i = 0; i < quantum; i++)
            {
                if (!current.isFinished())
                {
                    current.handleTask();
                    burstTime++;
                    usedTime++;
                }
            }

            // calculate the waitTime based on the used burstTime
            waitTime += usedTime * waitlist.size();

            // if task is finished add to completed tasks list
            if (current.isFinished())
                finished.add(current);
            // or else add to back of waitlist
            else
                waitlist.add(current);
        }

        // result string
        String result = "All tasks are handled within " + burstTime +
                " units of burst time and " + waitTime + " units of wait time. " +
                "The tasks are finished in this order:\n";

        // add all the Tasks in order they are completed
        while (!finished.isEmpty())
        {
            result += finished.remove(0).toString();

            if (finished.size() != 0)
                result += " -> ";
        }

        return result;
    }

    /**
     * Main method for testing.
     * @param args command-line arguments
     */
    public static void main(String[] args)
    {
        Task[] test1 = {new Task("A",  3), new Task("B", 4), new Task("C", 4),
                new Task("D", 12), new Task("E", 6), new Task("F", 3)};
        RoundRobin rr1 = new RoundRobin(test1);     // Quantum: 3, ToHandle: test1
        System.out.println(rr1.handleAllTasks());   // Burst: 32, Wait: 86, Order: AFBCED
        System.out.println();
        System.out.println(rr1.handleAllTasks());   // TASK_HANDLED
        System.out.println();

        Task[] test2 = {new Task("A", 9), new Task("B", 8), new Task("C", 6),
                new Task("D", 4), new Task("E", 4), new Task("F", 3)};
        RoundRobin rr2 = new RoundRobin(4, test2);  // Quantum: 4, ToHandle: test2
        System.out.println(rr2.handleAllTasks());   // Burst: 34, Wait: 123, Order: DEFBCA
        System.out.println();
        System.out.println(rr2.handleAllTasks());   // TASK_HANDLED
        System.out.println();

        Task[] test3 = {new Task("A", 7), new Task("B", 5), new Task("C", 3), new Task("D", 1),
                new Task("E", 2), new Task("F", 4), new Task("G", 6), new Task("H", 8)};
        RoundRobin rr3 = new RoundRobin(test3);     // Quantum: 3, ToHandle: test3
        System.out.println(rr3.handleAllTasks());   // Burst: 36, Wait: 148, Order: CDEBFGAH
        System.out.println();
        System.out.println(rr3.handleAllTasks());   // TASK_HANDLED
        System.out.println();
    }
}