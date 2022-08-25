/*
 * NAME: Viswesh Uppalapati
 * PID:  A15600068
 */
import java.util.ArrayList;

/**
 * Sorts class
 * @param <T> Generic type
 * @author Viswesh Uppalapati
 * @since  5th May, 2020
 */
public class Sorts<T extends Comparable<? super T>>
{

    private static final int HALF_LIST = 2;
    private static final int SIZE_SCALE = 2;

    /**
     * This method performs insertion sort on the input arraylist
     *
     * @param list The arraylist we want to sort
     * @param start The inital index on subsection of Arraylist we want to sort
     * @param end The final index of the subsection of Arraylist we want to sort
     */
    public void InsertionSort(ArrayList<T> list, int start, int end)
    {
        // assume first element sorted and start on second
        for (int i = start + 1; i <= end; i++)
        {
            // move current element down to appropriate spot
            for (int j = i; j > start; j--)
            {
                // swap element
                if (list.get(j).compareTo(list.get(j-1)) < 0)
                {
                    T temp = list.get(j);
                    list.set(j, list.get(j-1));
                    list.set(j-1, temp);
                }
            }
        }
    }

    /**
     * This method performs merge sort on the input arraylist
     *
     * @param list The arraylist we want to sort
     * @param start The inital index on subsection of Arraylist we want to sort
     * @param end The final index of the subsection of Arraylist we want to sort
     */
    public void MergeSort(ArrayList<T> list, int start, int end) {

        if (start < end)
        {
            int mid = start + (end - start) / HALF_LIST;
            MergeSort(list, start, mid);
            MergeSort(list , mid + 1, end);

            merge(list, start, mid, end);
        }
    }

    /**
     * merge helper function for MergeSort
     *
     * @param arr The arraylist we want to sort
     * @param l left-most index we want to merge
     * @param m the middle index we want to merge
     * @param r right-most index we want to merge
     */
    private void merge(ArrayList<T> arr, int l, int m, int r) {

        int mergedSize = r - l + 1;

        ArrayList<T> mergedNums = new ArrayList<>();
        int left = l, right = m + 1;
        while (left <= m && right <= r) {
            if (arr.get(left).compareTo(arr.get(right)) <= 0) {
                mergedNums.add(arr.get(left));
                left++;
            }
            else {
                mergedNums.add(arr.get(right));
                right++;
            }
        }

        while (left <= m) {
            mergedNums.add(arr.get(left));
            left++;
        }
        while (right <= r) {
            mergedNums.add(arr.get(right));
            right++;
        }
        for (int i = 0; i < mergedSize; i++) {
            arr.set(l + i, mergedNums.get(i));
        }
    }

    /**
     * This method performs quick sort on the input arraylist
     *
     * @param list The arraylist we want to sort
     * @param start The inital index on subsection of Arraylist we want to sort
     * @param end The final index of the subsection of Arraylist we want to sort
     */
    public void QuickSort(ArrayList<T> list, int start, int end)
    {
        // if one element left, then sorted
        if (start >= end)
            return;

        int index = partition(list, start, end);
        //System.out.println(list.toString());
        QuickSort(list, start, index); // sort lower half
        QuickSort(list, index + 1, end); // sort upper half
    }

    /**
     * partition helper function for QuickSort
     *
     * @param arr The arraylist we want to sort
     * @param l left-most index we want to merge
     * @param h right-most index we want to merge
     * @result  The last index of the lower partition
     */
    private int partition(ArrayList<T> arr, int l, int h)
    {
        T pivot = arr.get(l + (h - l) / HALF_LIST);
        //System.out.println(l + " " + h + " " + pivot);

        boolean terminate = false;

        // loop to partition
        while (!terminate)
        {
            // loop until number doesn't belong in partition
            while (arr.get(l).compareTo(pivot) < 0)
                l++;

            while (arr.get(h).compareTo(pivot) > 0)
                h--;

            if (l >= h)
                terminate = true;
            else
            {
                // swap
                T temp = arr.get(h);
                arr.set(h, arr.get(l));
                arr.set(l, temp);
                h--; l++;
            }
        }

        return h; // index of the end of lower partition
    }

    /**
     * This method performs a modified QuickSort that switches to insertion sort after a certina cutoff
     *
     * @param list The arraylist we want to sort
     * @param start The inital index on subsection of Arraylist we want to sort
     * @param end The final index of the subsection of Arraylist we want to sort
     * @param cutoff the minimum length of an subsection of the arraylist such that we switch to Insertion Sort
     */
    public void Modified_QuickSort(ArrayList<T> list, int start, int end, int cutoff)
    {
        if (start >= end)
            return;

        // checks to see if we can use InsertionSort
        int index = partition(list, start, end);
        boolean checkLow = (index - start) + 1 <= cutoff;
        boolean checkHigh = end - index <= cutoff;

        // for both partitions
        if (checkLow && checkHigh)
        {
            InsertionSort(list, start, index);
            InsertionSort(list, index + 1, end);
        }
        else if (checkHigh) // for upper partition
        {
            InsertionSort(list, index + 1, end);
            Modified_QuickSort(list, start, index, cutoff);
        }
        else if (checkLow) // for lower
        {
            InsertionSort(list, start, index);
            Modified_QuickSort(list, index + 1, end, cutoff);
        }
        else // regular quick
        {
            Modified_QuickSort(list, start, index, cutoff);
            Modified_QuickSort(list, index + 1, end, cutoff);
        }
    }

    /**
     * This method performs a modified QuickSort that switches to insertion sort after a certina cutoff
     *
     * @param list The arraylist we want to sort
     * @param start The inital index on subsection of Arraylist we want to sort
     * @param end The final index of the subsection of Arraylist we want to sort
     * @param param The length of the initial splits that are sorted prior to merging
     */
    public void TimSort(ArrayList<T> list, int start, int end, int param)
    {
        // sort sub arrays of length param
        for (int i = start; i <= end; i += param)
        {
            int shifted = i + param - 1;
            if (shifted >= end)
                InsertionSort(list, i, end);
            else
                InsertionSort(list, i, shifted);
        }
        //System.out.println(list.toString());

        // first and second sweeps
        int tempPar = SIZE_SCALE*param;
        while (start + tempPar < end)
        {
            System.out.println(tempPar);
            for (int i = start; i <= end; i += tempPar)
            {
                int shifted = i + tempPar-1;
                if (shifted >= end)
                    merge(list, i, (i + (end - i)/ HALF_LIST), end);
                else
                    merge(list, i, (i + (shifted - i) / HALF_LIST), shifted);
            }
            //System.out.println(list.toString());
            tempPar += tempPar;
        }

        // last sweep
        merge(list, start, start + (end - start)/HALF_LIST, end);
    }
}