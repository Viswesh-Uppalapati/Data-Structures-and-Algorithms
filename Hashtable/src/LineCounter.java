/*
 * NAME: Viswesh Uppalapati
 * PID: A15600068
 */

import java.io.*;
import java.util.Scanner;

/**
 * This file contains the functionality of a lineCounter which
 * finds the percentage of similarity between different text files.
 * 
 * @author Viswesh Uppalapati
 * @since 05/27/20
 */
public class LineCounter
{

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 10;
    private static final int PERCENT = 100;

    /**
     * Method to print the filename to console
     */
    public static void printFileName(String filename) {
        System.out.println("\n" + filename + ":");
    }

    /**
     * Method to print the statistics to console
     */

    public static void printStatistics(String compareFileName, int percentage) {
        System.out.println(percentage + "% of lines are also in " + compareFileName);
    }

    /**
     * Take the file inputs and checks the percentage of lines
     * that are similar in each one.
     * @param args  Command line args with the file names
     */
    public static void main(String[] args)
    {
        // check inputs
        if (args.length < 2)
        {
            System.err.println("Invalid number of arguments passed");
            return;
        }

        int numArgs = args.length;

        // Create a hash table for every file
        HashTable[] tableList = new HashTable[numArgs];

        // Preprocessing: Read every file and create a HashTable

        for (int i = 0; i < numArgs; i++)
        {
            try
            {
                // create the scanner instance to read file
                File file = new File(args[i]);
                Scanner sc = new Scanner(file);
                HashTable temp = new HashTable(MIN_INIT_CAPACITY);

                // read each line and add to hashtable
                while (sc.hasNextLine())
                    temp.insert(sc.nextLine());

                // add the hash table to the array of hash tables
                tableList[i] = temp;
                sc.close();
            }
            catch (FileNotFoundException e)
            {
                System.out.println("File: " + args[i] + " does not exist.");
            }
        }

        // Find similarities across files

        for (int i = 0; i < numArgs; i++)
        {
            try
            {
                printFileName(args[i]);

                // loop that checks similarity for each array
                for (int j = 0; j < tableList.length; j++)
                {
                    // make scanner instance to read through the file
                    File file = new File(args[i]);
                    Scanner sc = new Scanner(file);
                    int allLines = 0; // counter for all lines in file
                    int matchedLines = 0; // counter for matched lines

                    // go through file and see if the current hashtable has that line
                    // and increment matchedLines and allLines
                    while (sc.hasNextLine())
                    {
                        if (tableList[j].lookup(sc.nextLine()))
                            matchedLines++;

                        allLines++;
                    }

                    // print the lines
                    if (i != j)
                    {
                        int percentage = 0;

                        // avoid divide by zero error and calculate percentage
                        if (allLines == 0)
                            percentage = 0;
                        else
                            percentage = (int) (((double) matchedLines / (double) allLines)
                                        * PERCENT);

                        // print the stats
                        printStatistics(args[j], percentage);
                    }
                }
            }
            catch (FileNotFoundException e)
            {
                System.out.println("File: " + args[i] + " does not exist.");
            }
        }
    }
}