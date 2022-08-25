
import org.junit.Test;
import org.junit.Before;

import java.io.*;
import java.util.Scanner;


public class HCTreeTester
{
    HCTree huff;

    @Before
    public void setUp()
    {
        huff = new HCTree();
    }


    @Test
    public void buildTree()
    {
        int [] arr = new int[256];
        try
        {
            File file = new File("./src/io/check1.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
            {
                String str = sc.nextLine();
                for (int i = 0; i < str.length(); i++)
                    arr[(int) str.charAt(i)]++;
            }
            arr[(int) '\n']++;
            /*for (int i = 0; i < arr.length; i++)
                if (arr[i] != 0)
                    System.out.println(i + ":" + arr[i]);*/
            huff.buildTree(arr);
            huff.inorder(huff.getRoot());
            //try {
                //huff.encode((byte) 100, new BitOutputStream());
            //}
            //catch (IOException e) {}
        }
        catch (FileNotFoundException e){}
    }
}
