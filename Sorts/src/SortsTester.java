import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;

public class SortsTester
{

    Sorts inst;
    @Before
    public void setup()
    {
        inst = new Sorts();
    }

    @Test
    public void insertionSort()
    {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 3; i >= 0; i--)
            list.add(i);
        inst.InsertionSort(list, 0, 3);
        assertEquals("[0, 1, 2, 3]", list.toString());
        list.clear();
        for (int i = 3; i >= 0; i--)
            list.add(i);
        inst.InsertionSort(list, 0, 1);
        assertEquals("[2, 3, 1, 0]", list.toString());
        inst.InsertionSort(list, 1, 2);
        assertEquals("[2, 1, 3, 0]", list.toString());
    }

    @Test
    public void quickSort()
    {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 5; i >= 0; i--)
            list.add(i);
        inst.QuickSort(list, 2, 5);
        assertEquals("[5, 4, 0, 1, 2, 3]", list.toString());
        inst.QuickSort(list, 0, 1);
        assertEquals("[4, 5, 0, 1, 2, 3]", list.toString());
        inst.QuickSort(list, 0, 5);
        assertEquals("[0, 1, 2, 3, 4, 5]", list.toString());
    }

    @Test
    public void modified_QuickSort()
    {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 5; i >= 0; i--)
            list.add(i);
        inst.Modified_QuickSort(list, 3, 5, 2);
        assertEquals("[5, 4, 3, 0, 1, 2]", list.toString());
        inst.Modified_QuickSort(list, 0, 3, 2);
        assertEquals("[0, 3, 4, 5, 1, 2]", list.toString());
        inst.Modified_QuickSort(list, 0, 5, 2);
        assertEquals("[0, 1, 2, 3, 4, 5]", list.toString());
    }

    @Test
    public void timSort()
    {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 6; i >= 0; i--)
            list.add(i);
        inst.TimSort(list, 1, 4, 2);
        assertEquals("[6, 2, 3, 4, 5, 1, 0]", list.toString());
        inst.TimSort(list, 3, 5, 2);
        assertEquals("[6, 2, 3, 1, 4, 5, 0]", list.toString());
        inst.TimSort(list, 0, 6, 2);
        assertEquals("[0, 1, 2, 3, 4, 5, 6]", list.toString());
    }
}