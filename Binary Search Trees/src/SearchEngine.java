
/*
 * Name: Viswesh Uppalapati
 * PID:  A15600068
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Search Engine implementation.
 * 
 * @author Viswesh Uppalapati
 * @since  10th May, 2020
 */
public class SearchEngine
{
    private static final int ACTOR_START = 2; // index to process args

    /**
     * Populate BSTrees from a file
     * 
     * @param movieTree  - BST to be populated with actors
     * @param studioTree - BST to be populated with studios
     * @param ratingTree - BST to be populated with ratings
     * @param fileName   - name of the input file
     * @returns false if file not found, true otherwise
     */
    public static boolean populateSearchTrees(BSTree<String> movieTree, BSTree<String> studioTree,
            BSTree<String> ratingTree, String fileName)
    {
        // open and read file and check for exception
        Scanner sc;
        try
        {
            File file = new File(fileName);
            sc = new Scanner(file);
        }
        catch (FileNotFoundException fe) { return false; }

        // read lines and store them according to descriptions in the write-up

        while (sc.hasNextLine())
        {
            // store each of the 4 lines in order
            String movie = sc.nextLine().trim().toLowerCase();
            String [] actors = sc.nextLine().toLowerCase().split(" ");
            String [] studios = sc.nextLine().toLowerCase().split(" ");
            String rating = sc.nextLine().trim();

            // populate three trees with the information you just read

            // populate first and third trees
            for (String actor : actors)
            {
                movieTree.insert(actor);

                if (!movieTree.findDataList(actor).contains(movie))
                    movieTree.insertData(actor, movie);

                ratingTree.insert(actor);

                if (!ratingTree.findDataList(actor).contains(rating))
                    ratingTree.insertData(actor, rating);
            }

            // populate second tree
            for (String studio : studios)
            {
                studioTree.insert(studio);

                if (!studioTree.findDataList(studio).contains(movie))
                    studioTree.insertData(studio, movie);
            }

            // gets rid of the partition between entries
            if (sc.hasNextLine())
                sc.nextLine();
        }

        return true;
    }

    /**
     * Search a query in a BST
     * 
     * @param searchTree - BST to be searched
     * @param query      - query string
     */
    public static void searchMyQuery(BSTree<String> searchTree, String query)
    {
        // process query
        String [] tokens = query.toLowerCase().split(" ");

        // search and output intersection results
        LinkedList<String> temp = new LinkedList<>();
        for (int i = 0; i < tokens.length; i++)
        {
            try
            {
                // adds all the documents first
                for (String s : searchTree.findDataList(tokens[i]))
                    if (!temp.contains(s))
                        temp.add(s);

                if (i == tokens.length - 1)
                {
                    // retain all the documents common to all the names
                    for (int j = 0; j < tokens.length; j++)
                        temp.retainAll(searchTree.findDataList(tokens[j]));

                    print(query, temp);
                }
            }
            catch (IllegalArgumentException IE)
            {
                print(query, null);
                break;
            }
        }

        // In an if statement to avoid duplicated output for 1 name case
        if (tokens.length != 1)
        {
            // search and output individual results
            LinkedList<String> ind;
            for (int i = 0; i < tokens.length; i++)
            {
                try
                {
                    // get the documents and initialize empty list
                    LinkedList<String> current = searchTree.findDataList(tokens[i]);
                    ind = new LinkedList<>();

                    // add all the documents of current name, avoid repeats
                    for (String s : current)
                        if (!temp.contains(s))
                            ind.add(s);

                    // print the query
                    if (!ind.isEmpty())
                        print(tokens[i], ind);

                    // add all documents printed to temp to avoid repeats
                    temp.addAll(ind);
                }
                catch (IllegalArgumentException IE) { print(tokens[i], null); }
            }
        }
    }

    /**
     * Print output of query
     * 
     * @param query     Query used to search tree
     * @param documents Output of documents from query
     */
    public static void print(String query, LinkedList<String> documents) {
        if (documents == null || documents.isEmpty())
            System.out.println("The search yielded no results for " + query);
        else {
            Object[] converted = documents.toArray();
            Arrays.sort(converted);
            System.out.println("Documents related to " + query + " are: " + Arrays.toString(converted));
        }
    }

    /**
     * Main method that processes and query the given arguments
     * 
     * @param args command line arguments
     */
    public static void main(String[] args)
    {
        // initialize search trees
        BSTree<String> movieTree = new BSTree<>();
        BSTree<String> studioTree = new BSTree<>();
        BSTree<String> ratingTree = new BSTree<>();
        int command = Integer.parseInt(args[1]);
        String fileName = args[0];
        String query = "";

        // process command line arguments
        for (int i = ACTOR_START; i < args.length; i++)
        {
            query += args[i];

            if(i != args.length - 1)
                query += " ";
        }

        // populate search trees
        populateSearchTrees(movieTree, studioTree, ratingTree, fileName);

        // choose the right tree to query
        if (command == 0)
            searchMyQuery(movieTree, query);
        else if (command == 1)
            searchMyQuery(studioTree, query);
        else
            searchMyQuery(ratingTree, query);
    }
}
