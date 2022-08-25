/*
 * Name: Viswesh Uppalapati
 * PID: A15600068
 */

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Compress the first given file to the second given file using Huffman coding
 * 
 * @author Viswesh Uppalapati
 * @since 05/30/20
 */
public class Compress
{

    private static final int EXP_ARG = 2; // number of expected arguments
    private static final int NUM_BYTES = 256; // length of ascii

    /**
     * The main method process the passed in input files as argument and
     * encodes the input along with the code that was used to encode the
     * file.
     * @param args          The input file to compress and output file to write to
     * @throws IOException  Exception thrown by the Streams
     */
    public static void main(String[] args) throws IOException
    {

        // Check if the number of arguments is correct
        if (args.length != EXP_ARG)
        {
            System.out.println("Invalid number of arguments.\n" + 
            "Usage: ./compress <infile outfile>.\n");
            return;
        }

        // read all the bytes from the given file and make it to a byte array
        byte[] input = Files.readAllBytes(Paths.get(args[0]));

        FileOutputStream file = new FileOutputStream(args[1]);
        DataOutputStream out = new DataOutputStream(file);
        BitOutputStream bitOut = new BitOutputStream(out);

        // count the frequency of each character in input
        int [] freq = new int[NUM_BYTES];
        for (int i = 0; i < input.length; i++)
            freq[input[i] & 0xff]++;

        // construct HCTree from the file
        HCTree tree = new HCTree();
        tree.buildTree(freq);

        // write number of bytes to out file
        out.writeInt(input.length);

        // encode HCTree and every byte
        tree.encodeHCTree(tree.getRoot(), bitOut);

        for (int i = 0; i < input.length; i++)
            tree.encode(input[i], bitOut);

        // There might be several padding bits in the bitOut that we haven't written, so
        // flush it first.
        bitOut.flush();
        out.close();
        file.close();
    }
}
