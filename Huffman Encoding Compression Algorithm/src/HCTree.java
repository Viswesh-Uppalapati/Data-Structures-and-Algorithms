/*
 * Name: Viswesh Uppalapati
 * PID: A15600068
 */

import java.io.*;
import java.util.PriorityQueue;

/**
 * The Huffman Coding Tree
 * @author Viswesh Uppalpaati
 * @since 05/28/20
 */
public class HCTree
{
    // alphabet size of extended ASCII
    private static final int NUM_CHARS = 256;
    // number of bits in a byte
    private static final int BYTE_BITS = 8;

    // the root of HCTree
    private HCNode root;
    // the leaves of HCTree that contain all the symbols
    private HCNode[] leaves = new HCNode[NUM_CHARS];

    /**
     * The Huffman Coding Node
     */
    protected class HCNode implements Comparable<HCNode>
    {

        byte symbol; // the symbol contained in this HCNode
        int freq; // the frequency of this symbol
        HCNode c0, c1, parent; // c0 is the '0' child, c1 is the '1' child

        /**
         * Initialize a HCNode with given parameters
         *
         * @param symbol the symbol contained in this HCNode
         * @param freq   the frequency of this symbol
         */
        HCNode(byte symbol, int freq)
        {
            this.symbol = symbol;
            this.freq = freq;
        }

        /**
         * Getter for symbol
         *
         * @return the symbol contained in this HCNode
         */
        byte getSymbol() { return this.symbol; }

        /**
         * Setter for symbol
         *
         * @param symbol the given symbol
         */
        void setSymbol(byte symbol) { this.symbol = symbol; }

        /**
         * Getter for freq
         *
         * @return the frequency of this symbol
         */
        int getFreq() { return this.freq; }

        /**
         * Setter for freq
         *
         * @param freq the given frequency
         */
        void setFreq(int freq) { this.freq = freq; }

        /**
         * Getter for '0' child of this HCNode
         *
         * @return '0' child of this HCNode
         */
        HCNode getC0() { return c0; }

        /**
         * Setter for '0' child of this HCNode
         *
         * @param c0 the given '0' child HCNode
         */
        void setC0(HCNode c0) { this.c0 = c0; }

        /**
         * Getter for '1' child of this HCNode
         *
         * @return '1' child of this HCNode
         */
        HCNode getC1() { return c1; }

        /**
         * Setter for '1' child of this HCNode
         *
         * @param c1 the given '1' child HCNode
         */
        void setC1(HCNode c1) { this.c1 = c1; }

        /**
         * Getter for parent of this HCNode
         *
         * @return parent of this HCNode
         */
        HCNode getParent() { return parent; }

        /**
         * Setter for parent of this HCNode
         *
         * @param parent the given parent HCNode
         */
        void setParent(HCNode parent) { this.parent = parent; }

        /**
         * Check if the HCNode is leaf (has no children)
         *
         * @return if it's leaf, return true. Otherwise, return false.
         */
        boolean isLeaf() { return (c0 == null && c1 == null); }

        /**
         * String representation
         *
         * @return string representation
         */
        public String toString() { return "Symbol: " + this.symbol + "; Freq: " + this.freq; }

        /**
         * Compare two nodes
         *
         * @param o node to compare
         * @return int positive if this node is greater
         */
        @Override
        public int compareTo(HCNode o)
        {
            // returns 1 if the current symbol ASCII is less than
            // other ASCII, which means higher priority
            if (this.freq == o.freq)
                if (this.symbol < o.symbol)
                    return -1;
                else
                    return 1;
            // otherwise priority is based on frequency
            else if (this.freq > o.freq)
                return 1;
            else
                return -1;
        }
    }

    /**
     * Returns the root node
     *
     * @return root node
     */
    public HCNode getRoot() { return root; }

    /**
     * Sets the root node
     *
     * @param root node to set
     */
    public void setRoot(HCNode root) { this.root = root; }

    /**
     * This method takes in an array input of the frequency of
     * each character in a file and builds a Huffman tree out of
     * it.
     * @param freq  The array containing the frequency of the letters
     */
    public void buildTree(int[] freq)
    {
        // create the HCNodes out of the frequency array
        for (int i = 0; i < freq.length; i++)
            if (freq[i] != 0)
                leaves[i] = new HCNode((byte) i, freq[i]);

        // add the created nodes to a priority queue
        PriorityQueue pq = new PriorityQueue();
        for (int i = 0; i < leaves.length; i++)
            if (leaves[i] != null)
                pq.add(leaves[i]);

        //System.out.println(pq.toString());

        // build the tree
        while (pq.size() > 1)
        {
            HCNode child0 = (HCNode) pq.poll();
            HCNode child1 = (HCNode) pq.poll();

            root = new HCNode(child0.getSymbol(),
                    child0.getFreq() + child1.getFreq());

            root.setC0(child0);
            root.setC1(child1);
            child0.setParent(root);
            child1.setParent(root);

            pq.add(root);
        }

    }

    /**
     * Encodes the symbol that is provided as input to the function
     * by starting at the leaf and going up to the root and prints
     * it to file using an outputStream.
     *
     * @param symbol        The Symbol to encode.
     * @param out              The outputStream used to print.
     * @throws IOException  Exception thrown by the Stream.
     */
    public void encode(byte symbol, BitOutputStream out) throws IOException
    {
        // get the ascii value denoted by teh symbol and find
        // the respective node in the leaves array
        int ascii = symbol & 0xff;
        HCNode temp = leaves[ascii];
        String representation = "";

        // loop from leaf to parent
        while (temp.getParent() != null)
        {
            // if the symbol of the parent is the same as that of the current
            // node, then the current node is the left child, so add a 0
            if (temp.getParent().getSymbol() == temp.getSymbol())
                representation = 0 + representation;
            // otherwise it comes from the right
            else
                representation = 1 + representation;

            // advance the pointer
            temp = temp.getParent();
        }

        // loop through the string representation and add each bit to out
        for (int i = 0; i < representation.length(); i++)
            out.writeBit(Integer.parseInt(Character.toString
                    (representation.charAt(i))));
    }

    /**
     * Decodes the next symbol that occurs in the Input stream
     * and stops when it finds one byte according to the tree.
     *
     * @param in            The input stream containing the encoded letters.
     * @return              The decoded symbol as a byte.
     * @throws IOException  The exception thrown by the inputStream.
     */
    public byte decode(BitInputStream in) throws IOException
    {
        // start at the root
        HCNode ptr = root;

        // traverse down the tree based on encoding
        while (ptr != null && !ptr.isLeaf())
        {
            int currentBit = in.readBit();
            // if read bit is 0, traverse left
            if (currentBit == 0)
                ptr = ptr.getC0();
            // otherwise, go right
            else
                ptr = ptr.getC1();
        }

        // return the symbol of the node after the traversal
        return ptr.getSymbol();
    }

    /**
     * Provides a recursive inorder traversal of the tree.
     * @param ptr   The root of the tree to traverse it.
     */
    public void inorder(HCNode ptr)
    {
        // if ptr is null end recursion
        if (ptr == null)
            return;
        else
        {
            // go left
            inorder(ptr.getC0());
            // visit
            System.out.println(ptr.toString());
            // go right
            inorder(ptr.getC1());
        }
    }

    /**
     * This method encodes the tree itself into the output file.
     * This is necessary for the computer to rebuild the tree
     * so that it can properly decode the contents that were
     * encoded by the tree.
     *
     * @param node  The root node of the tree to encode
     * @param out   The output stream to write to
     * @throws IOException
     */
    public void encodeHCTree(HCNode node, BitOutputStream out) throws IOException
    {
        // pre order traversal, visit node, go left, go right

        // if node is null, stop recursion
        if (node == null)
            return;
        // if node is a leaf, then store a 1 and its symbol
        else if (node.isLeaf())
        {
            out.writeBit(1);
            out.writeByte(node.getSymbol());
        }
        // otherwise it is a parent node with no symbol, store it as 0
        else
            out.writeBit(0);

        // recurse to the left and right subtrees of each node
        encodeHCTree(node.getC0(), out);
        encodeHCTree(node.getC1(), out);
    }

    /**
     * This method takes in an inputStream and reads the header of
     * it to build the HCTTree that encodes the rest of the file.
     *
     * @param in            The input stream to read from
     * @return              The root of the HCTree that is built
     * @throws IOException  Exception thrown by the inputStream
     */
    public HCNode decodeHCTree(BitInputStream in) throws IOException
    {
        // get the next bit in the file
        int nextBit = in.readBit();


        if (nextBit == 1)
        {
            // if bit is 1, get the byte representing the symbol
            // then look at the bit after that
            byte symbol1 = in.readByte();
            //int anotherBit = in.readBit();
            HCNode leaf = new HCNode(symbol1, 0);
            leaves[leaf.getSymbol() & 0xff] = leaf;
            return leaf;
        }
        // if the first bit is 0, then recurse
        else
        {
            // store the two children returned by recursive call
            HCNode child0 = decodeHCTree(in);
            HCNode child1 = decodeHCTree(in);
            // create a parent node with the symbol of child0
            HCNode parent = new HCNode(child0.getSymbol(), 0);

            // set pointers
            child0.setParent(parent);
            child1.setParent(parent);
            parent.setC0(child0);
            parent.setC1(child1);

            // return parent to be linked recursively
            return parent;
        }
    }
}
