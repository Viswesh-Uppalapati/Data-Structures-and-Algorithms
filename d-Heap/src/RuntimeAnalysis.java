/*
 * Name: Viswesh Upppalapati
 * PID:  A1600068
 */

import java.util.Random;

/**
 * Runtime Analysis of d-Heap.
 * 
 * @author Viswesh Uppalapati
 * @since 05/18/20
 */
public class RuntimeAnalysis {

    /* Shared parameters */
    private static final int RAND_SEED = 42; // DO NOT MODIFY!!!
    private static final int NUM_RUN = 10; // Make this >= 10 for accurate outcome

    /* Constants for test 1 */
    private static final int TEST_1_D_VALUE = 4;
    private static final int[] TEST_1_DATA_SIZES = { 500000, 1000000, 2000000, 4000000 };

    /* Constants for test 2 */
    private static final int[] TEST_2_D_VALUES = { 2, 3, 4, 6, 8, 12, 16 };
    private static final int TEST_2_DATA_SIZE = 2000000;

    /**
     * Generate random numbers by shuffling an array between range 1 to size.
     * 
     * @param size the size of returned array (and the max number)
     * @return array of random numbers
     */
    public static int[] randomNumbers(int size) {
        // generate an array of numbers from 1 to size
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = i + 1;
        }

        // shuffle numbers using Fisher-Yates shuffle algorithm
        Random rand = new Random(RAND_SEED);
        int idx, temp;
        for (int i = data.length - 1; i > 0; i--) {
            idx = rand.nextInt(i + 1);
            temp = data[idx];
            data[idx] = data[i];
            data[i] = temp;
        }

        return data;
    }

    /**
     * Generate an array of numbers ranging 1 to size in ascending or descending
     * order.
     * 
     * @param size      the size of returned array (and the max number)
     * @param ascending true to generate ascending array, false to generate
     *                  descending array
     * @return array of ordered numbers
     */
    public static int[] orderedNumbers(int size, boolean ascending) {
        int[] data = new int[size];
        if (ascending) {
            // ascending, generate an array of numbers from 1 to size
            for (int i = 0; i < size; i++) {
                data[i] = i + 1;
            }
        } else {
            // descending, generate an array of numbers from size to 1
            for (int i = 0; i < size; i++) {
                data[i] = size - i;
            }
        }

        return data;
    }

    /**
     * Test 1 runner: time the insertion of max d-Heap.
     * 
     * @param data   numbers to populate the heap
     * @param d      branching factor
     * @param numRun number of runs
     */
    public static void timeMaxDHeapInsertion(int[] data, int d, int numRun) {
        long startTime = 0, endTime = 0, totalTime = 0;
        for (int i = 0; i < numRun; i++) {
            dHeap<Integer> heap = new dHeap<>(d, data.length, true);
            for (int num : data) {
                startTime = System.currentTimeMillis();
                heap.add(num);
                endTime = System.currentTimeMillis();
                totalTime += (endTime - startTime);
            }
        }
        System.out.println("Benchmarking Max d-heap insertion with d = " + d);
        System.out.println("Number of data to insert: " + data.length);
        System.out.println("Average time taken to insert: " + totalTime / numRun + " ms");
        System.out.println();
    }

    /**
     * Test 2 runner: time the insertion and deletion of min d-Heap.
     * 
     * @param data   numbers to populate the heap
     * @param d      branching factor
     * @param numRun number of runs
     */
    public static void timeMinDHeapInsertionDeletion(int[] data, int d, int numRun) {
        // setup timers and record size
        long startTimeInsert = 0, endTimeInsert = 0, totalTimeInsert = 0;
        long startTimeDelete = 0, endTimeDelete = 0, totalTimeDelete = 0;
        int size = data.length;

        for (int i = 0; i < numRun; i++) {
            dHeap<Integer> heap = new dHeap<>(d, size, false);
            // time insertion
            for (int num : data) {
                startTimeInsert = System.currentTimeMillis();
                heap.add(num);
                endTimeInsert = System.currentTimeMillis();
                totalTimeInsert += (endTimeInsert - startTimeInsert);
            }

            // time deletion
            for (int j = 0; j < size; j++) {
                startTimeDelete = System.currentTimeMillis();
                heap.remove();
                endTimeDelete = System.currentTimeMillis();
                totalTimeDelete += (endTimeDelete - startTimeDelete);
            }
        }
        System.out.println("Benchmarking Min d-heap with d = " + d);
        System.out.println("Number of data: " + size);
        System.out.println("Average time taken to insert: " + totalTimeInsert / numRun + " ms");
        System.out.println("Average time taken to delete: " + totalTimeDelete / numRun + " ms");
        System.out.println();
    }

    /**
     * Main method to run tests.
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        int[] ascData, descData, randData;

        /*System.out.println("=============== TEST 1 ===============");
        for (int size : TEST_1_DATA_SIZES) {
            ascData = orderedNumbers(size, true);
            descData = orderedNumbers(size, false);
            randData = randomNumbers(size);

            System.out.println("Ascending data: ");
            timeMaxDHeapInsertion(ascData, TEST_1_D_VALUE, NUM_RUN);
            System.out.println("Descending data: ");
            timeMaxDHeapInsertion(descData, TEST_1_D_VALUE, NUM_RUN);
            System.out.println("Random data: ");
            timeMaxDHeapInsertion(randData, TEST_1_D_VALUE, NUM_RUN);
        }*/

        System.out.println("=============== TEST 2 ===============");
        for (int d : TEST_2_D_VALUES) {
            randData = randomNumbers(TEST_2_DATA_SIZE);
            ascData = orderedNumbers(TEST_2_DATA_SIZE, true);
            descData = orderedNumbers(TEST_2_DATA_SIZE, false);
            timeMinDHeapInsertionDeletion(ascData, d, NUM_RUN);
            timeMinDHeapInsertionDeletion(descData, d, NUM_RUN);
            timeMinDHeapInsertionDeletion(randData, d, NUM_RUN);
        }
    }
}
