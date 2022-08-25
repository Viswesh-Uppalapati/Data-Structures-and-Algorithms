/*
 * NAME: Viswesh Uppalapati
 * PID:  05/04/2020
 */
 
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class that contains the functionality to test the run time of the sorts
 * and compare them.
 * @Author  Teachers/TAs
 * @since   05/04/2020
 */
public class RuntimeAnalysis {

    private static final int NUM_DATA = 10000;
    private static final int NUM_RUN = 10;
    private static final int NUM_TEST = 5;
    private static final int MIN = 0;
    private static final int MAX = 100000;
    private static final int[] TimSortParamValues = {4,8,16,32,64,128};
    private static final int[] QuickSortCutoffValues = {2,4,8,16,32,64,128};

    /**
     * Returns an ArrayList of random numbers
     *
     * @param size the number of random numbers wanted
     * @param min the min value for random number
     * @param max the max value for random number
     * @return an ArrayList of random numbers
     */
    public static ArrayList<Integer> randomNumbers(int size, int min, int max) {

        ArrayList<Integer> randNums = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            randNums.add((int) (Math.random() * ((max - min) + 1)) + min);
        }
        return randNums;
    }

    private static ArrayList<Integer> deepCopyArrayList(ArrayList<Integer> data) {
        ArrayList<Integer> result = new ArrayList<>();
        for (Integer d : data) {
            result.add(d);
        }
        return result;
    }

    public static void timeInsertionSort(ArrayList<Integer> data, int numRun) {
        long startTime = 0, endTime = 0, totalTime = 0;
        Sorts<Integer> sorts = new Sorts<>();
        ArrayList<Integer> temp = deepCopyArrayList(data);
        for (int i = 0; i < numRun; i++) {
            data = deepCopyArrayList(temp);
            startTime = System.currentTimeMillis();
            sorts.InsertionSort(data, 0, data.size() - 1);
            endTime = System.currentTimeMillis();
            totalTime += (endTime - startTime);
        }
        System.out.println("Benchmarking insertion sort: ");
        System.out.println("Number of data to sort: " + data.size());
        System.out.println("Average time taken to sort: " + totalTime / numRun + " ms");
        System.out.println();
    }

    public static void timeMergeSort(ArrayList<Integer> data, int numRun) {
        long startTime = 0, endTime = 0, totalTime = 0;
        Sorts<Integer> sorts = new Sorts<>();
        ArrayList<Integer> temp = deepCopyArrayList(data);
        for (int i = 0; i < numRun; i++) {
            data = deepCopyArrayList(temp);
            startTime = System.currentTimeMillis();
            sorts.MergeSort(data, 0, data.size() - 1);
            endTime = System.currentTimeMillis();
            totalTime += (endTime - startTime);
        }
        System.out.println("Benchmarking merge sort: ");
        System.out.println("Number of data to sort: " + data.size());
        System.out.println("Average time taken to sort: " + totalTime / numRun + " ms");
        System.out.println();
    }

    public static void timeQuickSort(ArrayList<Integer> data, int numRun) {
        long startTime = 0, endTime = 0, totalTime = 0;
        Sorts<Integer> sorts = new Sorts<>();
        ArrayList<Integer> temp = deepCopyArrayList(data);
        for (int i = 0; i < numRun; i++) {
            data = deepCopyArrayList(temp);
            startTime = System.currentTimeMillis();
            sorts.QuickSort(data, 0, data.size() - 1);
            endTime = System.currentTimeMillis();
            totalTime += (endTime - startTime);
        }
        System.out.println("Benchmarking quick sort: ");
        System.out.println("Number of data to sort: " + data.size());
        System.out.println("Average time taken to sort: " + totalTime / numRun + " ms");
        System.out.println();
    }
    public static void timeModifiedQuickSort(ArrayList<Integer> data, int numRun, int param) {
        long startTime = 0, endTime = 0, totalTime = 0;
        Sorts<Integer> sorts = new Sorts<>();
        ArrayList<Integer> temp = deepCopyArrayList(data);

        for (int i = 0; i < numRun; i++) {
            data = deepCopyArrayList(temp);
            startTime = System.currentTimeMillis();
            sorts.Modified_QuickSort(data, 0, data.size() - 1, param);
            endTime = System.currentTimeMillis();
            totalTime += (endTime - startTime);
        }
        System.out.println("Modified QuickSort Cutoff Value: " + param);
        System.out.println("Benchmarking quick sort: ");
        System.out.println("Number of data to sort: " + data.size());
        System.out.println("Average time taken to sort: " + totalTime / numRun + " ms");
        System.out.println();


    }

    public static void timeTimSort(ArrayList<Integer> data, int numRun, int param) {
        long startTime = 0, endTime = 0, totalTime = 0;
        Sorts<Integer> sorts = new Sorts<>();
        ArrayList<Integer> temp = deepCopyArrayList(data);
        for (int i = 0; i < numRun; i++) {
            data = deepCopyArrayList(temp);
            startTime = System.currentTimeMillis();
            sorts.TimSort(data, 0, data.size() - 1, param);
            endTime = System.currentTimeMillis();
            totalTime += (endTime - startTime);
        }
        System.out.println("TimSort Parameter Value: " + param);
        System.out.println("Benchmarking quick sort: ");
        System.out.println("Number of data to sort: " + data.size());
        System.out.println("Average time taken to sort: " + totalTime / numRun + " ms");
        System.out.println();


    }

    public static void main(String[] args) {
        int numData;

        //1
        /*numData = NUM_DATA;
        for (int i = 0; i < NUM_TEST; i++)
        {
            ArrayList<Integer> data = randomNumbers(numData, MIN, MAX);
            timeMergeSort(data, NUM_RUN);
            timeInsertionSort(data, NUM_RUN);
            timeQuickSort(data, NUM_RUN);
            numData += numData;
        }*/

        //2
        /*int size = 1000000;
        int test = 7;
        int cutoff = 2;
        for (int i = 0; i < test; i++)
        {
            ArrayList<Integer> data = randomNumbers(size, MIN, MAX);
            timeModifiedQuickSort(data, NUM_RUN, cutoff);
            cutoff += cutoff;
        }*/

        int size = 100000;
        int test = 6;
        int cutoff = 4;
        for (int i = 0; i < test; i++)
        {
            ArrayList<Integer> data = randomNumbers(size, MIN, MAX);
            timeQuickSort(data, NUM_RUN);
            timeModifiedQuickSort(data, NUM_RUN, cutoff);
            size += 100000;
        }




    }

}
