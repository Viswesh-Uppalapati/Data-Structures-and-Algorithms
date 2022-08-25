/*
 * Name: Viswesh Uppalapati
 * PID: A15600068
 */

import java.io.*;

/**
 * Decompress the first given file to the second given file using Huffman coding
 * 
 * @author Viswesh Uppalapati
 * @since 05/30/20
 */
public class Decompress
{
    private static final int EXP_ARG = 2; // number of expected arguments

    /**
     * The main method process the input files and decodes the information
     * presented in the first file using the code that was used to encode
     * it and writes it to the second file.
     * @param args          The file to decode and file to write to
     * @throws IOException  The
     */
    public static void main(String[] args) throws IOException
    {

        // Check if the number of arguments is correct
        if (args.length != EXP_ARG)
        {
            System.out.println("Invalid number of arguments.\n" + 
            "Usage: ./decompress <infile outfile>.\n");
            return;
        }

        FileInputStream inFile = new FileInputStream(args[0]);
        DataInputStream in = new DataInputStream(inFile);
        BitInputStream bitIn = new BitInputStream(in);

        FileOutputStream outFile = new FileOutputStream(args[1]);
        DataOutputStream out = new DataOutputStream(outFile);

        // read the number of byte from the file
        int bytesToRead = in.readInt();

        // decode and build the tree from the "header"
        HCTree tree = new HCTree();
        tree.setRoot(tree.decodeHCTree(bitIn));

        // decode the file and write the results
        for (int i = 0; i < bytesToRead; i++)
            out.writeByte(tree.decode(bitIn));

        inFile.close();
        in.close();
        outFile.close();
        out.close();
        return;
    }
}
