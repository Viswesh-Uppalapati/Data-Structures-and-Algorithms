
/*
 * Name: Viswesh Uppalapati
 * PID:  A15600068
 */

import java.util.*;

/**
 * Binary search tree implementation.
 * 
 * @author Viswesh Uppalpaati
 * @since  05/07/2020
 */
public class BSTree<T extends Comparable<? super T>> implements Iterable
{

    /* * * * * BST Instance Variables * * * * */

    private int nelems; // number of elements stored
    private BSTNode root; // reference to root node

    /* * * * * BST Node Inner Class * * * * */

    protected class BSTNode
    {

        T key;
        LinkedList<T> dataList;
        BSTNode left;
        BSTNode right;

        /**
         * A constructor that initializes the BSTNode instance variables.
         *
         * @param left     Left child
         * @param right    Right child
         * @param dataList Linked list of related info
         * @param key      Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, LinkedList<T> dataList, T key)
        {
            this.left = left;
            this.right = right;
            this.dataList = dataList;
            this.key = key;
        }

        /**
         * A constructor that initializes BSTNode variables. Note: This constructor is
         * used when you want to add a key with no related information yet. In this
         * case, you must create an empty LinkedList for the node.
         *
         * @param left  Left child
         * @param right Right child
         * @param key   Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, T key)
        {
            this.left = left;
            this.right = right;
            this.key = key;
            this.dataList = new LinkedList<>();
        }

        /**
         * Return the key
         *
         * @return The key
         */
        public T getKey() { return this.key; }

        /**
         * Return the left child of the node
         *
         * @return The left child of the node
         */
        public BSTNode getLeft() { return this.left; }

        /**
         * Return the right child of the node
         *
         * @return The right child of the node
         */
        public BSTNode getRight() { return this.right; }

        /**
         * Return the linked list of the node
         *
         * @return The linked list of the node
         */
        public LinkedList<T> getDataList() { return this.dataList; }

        /**
         * Setter for left child of the node
         *
         * @param newleft New left child
         */
        public void setleft(BSTNode newleft) { this.left = newleft; }

        /**
         * Setter for right child of the node
         *
         * @param newright New right child
         */
        public void setright(BSTNode newright) { this.right = newright; }

        /**
         * Setter for the linked list of the node
         *
         * @param newData New linked list
         */
        public void setDataList(LinkedList<T> newData) { this.dataList = newData; }

        /**
         * Append new data to the end of the existing linked list of the node
         *
         * @param data New data to be appended
         */
        public void addNewInfo(T data) { dataList.add(data); }

        /**
         * Remove 'data' from the linked list of the node and return true. If the linked
         * list does not contain the value 'data', return false.
         *
         * @param data Info to be removed
         * @return True if data was found, false otherwise
         */
        public boolean removeInfo(T data)
        {
            if (dataList.contains(data))
            {
                dataList.remove(data);
                return true;
            }
            return false;
        }
    }

    /* * * * * BST Methods * * * * */

    /**
     * 0-arg constructor that initializes root to null and nelems to 0
     */
    public BSTree()
    {
        this.root = null;
        this.nelems = 0;
    }

    /**
     * Return the root of BSTree. Returns null if the tree is empty
     *
     * @return The root of BSTree, null if the tree is empty
     */
    public BSTNode getRoot() { return this.root; }

    /**
     * Return the BST size
     *
     * @return The BST size
     */
    public int getSize() { return nelems; }

    /**
     * Insert a key into BST
     * 
     * @param key
     * @return true if insertion is successful and false otherwise
     */
    public boolean insert(T key)
    {
        // check exceptions
        if (key == null)
            throw new NullPointerException();

        // if tree is empty, insert at root
        if (root == null)
        {
            root = new BSTNode(null, null, key);
            nelems++;
            return true;
        }

        // if value already in tree, insert fails
        if (findKey(key))
            return false;

        // create temp root pointer, call recursive insert
        BSTNode tempRoot = root;
        recursiveInsert(tempRoot, new BSTNode(null, null, key));
        nelems++;
        return true; // at this point, insertion will be true
    }

    /**
     * The recursive helper method that inserts a node other than
     * the root at it's given location in the BST
     * @param ptr       The pointer used to traverse the tree.
     * @param node      The node to add into the tree
     */
    private void recursiveInsert(BSTNode ptr, BSTNode node)
    {
        // ptr will never be null (already taken care of), check if
        // it is greater than current node, traverse right of tree
        if (node.getKey().compareTo(ptr.getKey()) > 0)
        {
            // if the right pointer is null, add it there
            if (ptr.getRight() == null)
                ptr.setright(node);
            // continue traversal
            else
                recursiveInsert(ptr.getRight(), node);
        }
        // if it is less than, traverse left to insert
        else if (node.getKey().compareTo(ptr.getKey()) < 0)
        {
            // if the left pointer is null, then insert there
            if (ptr.getLeft() == null)
                ptr.setleft(node);
            // continue traversal
            else
                recursiveInsert(ptr.getLeft(), node);
        }
    }


    /**
     * Return true if the 'key' is found in the tree, false otherwise
     *
     * @param key To be searched
     * @return True if the 'key' is found, false otherwise
     * @throws NullPointerException If key is null
     */
    public boolean findKey(T key)
    {
        // check inputs
        if (key == null)
            throw new NullPointerException();

        // call the recursiveFind method
        BSTNode tempRoot = root;
        BSTNode node =  recursiveFind(tempRoot, key);

        // if the returned node is null, key is not in tree
        if (node == null)
            return false;

        // otherwise, it is in tree
        return true;
    }

    /**
     * Helper method for find, insertData, and findDataList. It
     * finds and returns the node with value key.
     * @param ptr       The ptr used to traverse the tree
     * @param key       The key to find in the tree
     * @return          The node that matches key or null
     */
    private BSTNode recursiveFind(BSTNode ptr, T key)
    {
        // if it is equal or null, return the ptr
        // when ptr is null, the key in not in the tree
        if (ptr == null || ptr.getKey().compareTo(key) == 0)
            return ptr;
        else if (key.compareTo(ptr.getKey()) > 0) // traverse right
            return recursiveFind(ptr.getRight(), key);
        else
            return recursiveFind(ptr.getLeft(), key); // traverse left
    }


    /**
     * Insert 'data' into the LinkedList of the node whose key is 'key'
     *
     * @param key  Target key
     * @param data To be added to key's LinkedList
     * @throws NullPointerException     If eaither key or data is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public void insertData(T key, T data)
    {
        // check inputs
        if (key == null || data == null)
            throw new NullPointerException();
        else if (!findKey(key))
            throw new IllegalArgumentException();

        // call recursiveFind to get the node and call method to add the info
        BSTNode tempRoot = root;
        recursiveFind(tempRoot, key).addNewInfo(data);
    }

    /**
     * Return the LinkedList of the node with key value 'key'
     *
     * @param key Target key
     * @return LinkedList of the node whose key value is 'key'
     * @throws NullPointerException     If key is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public LinkedList<T> findDataList(T key)
    {
        // check inputs
        if (key == null)
            throw new NullPointerException();
        else if (!findKey(key))
            throw new IllegalArgumentException();

        // call recursiveFind to get the node matching key, and get it's list
        BSTNode tempRoot = root;
        return recursiveFind(tempRoot, key).getDataList();
    }

    /**
     * Return the height of the tree
     *
     * @return The height of the tree, -1 if BST is empty
     */
    public int findHeight()
    {
        // if tree is empty, height is -1
        if (root == null)
            return -1;

        // call helper method to find the height
        BSTNode tempRoot = root;
        return findHeightHelper(tempRoot);
    }

    /**
     * Helper for the findHeight method
     *
     * @param root Root node
     * @return The height of the tree, -1 if BST is empty
     */
    private int findHeightHelper(BSTNode root)
    {
        // if it is a leaf, return zero (no more depth left)
        if (root.getRight() == null && root.getLeft() == null)
            return 0;

        // create two vars to store height of left and right subtrees
        int leftHeight = 0;
        int rightHeight = 0;

        // if left doesnt exist, traverse right and add 1 for height
        if (root.getLeft() == null)
            rightHeight = findHeightHelper(root.getRight());
        /// if right doesnt exist, traverse left and add 1 to height
        else if (root.getRight() == null)
            leftHeight = findHeightHelper(root.getLeft());
        // if both exist, traverse both sides
        else
        {
            rightHeight = findHeightHelper(root.getRight());
            leftHeight = findHeightHelper(root.getLeft());
        }

        // return the height that is greater (indicates the height of subtree)
        return Math.max(1 + leftHeight, 1 + rightHeight);
    }

    /**
     * Return the number of leaf nodes in the tree
     *
     * @return The number of leaf nodes in the tree
     */
    public int leafCount()
    {
        // if tree is empty, there are 0 nodes
        if (root == null)
            return 0;

        // call helper method to find leaf count
        BSTNode tempRoot = root;
        return leafCountHelper(tempRoot);
    }

    /**
     * Helper for the leafCount method
     *
     * @param root Root node
     * @return The number of leaf nodes in the tree
     */
    private int leafCountHelper(BSTNode root)
    {
        // if both sides are null, it is a leaf, add to count
        if (root.getLeft() == null && root.getRight() == null)
            return 1;
        // if left is null, traverse the right to find leaves
        else if (root.getLeft() == null)
            return leafCountHelper(root.getRight());
        // if right is null, traverse left
        else if (root.getRight() == null)
            return leafCountHelper(root.getLeft());
        // otherwise traverse both sides
        else
            return leafCountHelper(root.getLeft()) + leafCountHelper(root.getRight());
    }

    /* * * * * BST Iterator * * * * */

    /**
     * This is an inner class within BSTree that acts as an iterator
     * for the tree. This allows us to iterate through the tree in order.
     */
    public class BSTree_Iterator implements Iterator<T>
    {
        Stack<BSTNode> inOrder; // holds nodes in order

        /**
         * Constructor, initializes stack and pushes the leftPath of
         * root node in order.
         */
        public BSTree_Iterator()
        {
            inOrder = new Stack<>();
            BSTNode tempRoot = root;

            // push left path onto stack
            while (tempRoot != null)
            {
                inOrder.push(tempRoot);
                tempRoot = tempRoot.getLeft();
            }
        }

        /**
         * Method that checks if tree has elements left within it
         * to iterate through.
         * @return  Whether there are elements left
         */
        public boolean hasNext() { return !inOrder.empty(); }

        /**
         * Method that returns the current element on top of the
         * inOrder stack and repushes the left path of the right
         * node if one exists.
         * @return  The element at the top of the stack.
         */
        public T next()
        {
            // if stack is not empty
            if (hasNext())
            {
                // store current node on top
                BSTNode current = inOrder.pop();

                // if the current node has a right node, push the
                // left path of the right subtree onto stack
                if (current.getRight() != null)
                {
                    BSTNode tempNode = current.getRight();

                    while (tempNode != null)
                    {
                        inOrder.push(tempNode);
                        tempNode = tempNode.getLeft();
                    }
                }

                return current.getKey(); // the element in order
            }
            // throw exception if stack is empty
            else
                throw new NoSuchElementException();
        }
    }

    /**
     * Returns the iterator instance.
     * @return  The iterator instance
     */
    public Iterator<T> iterator() { return new BSTree_Iterator(); }

    /* * * * * Extra Credit Methods * * * * */

    /**
     * This is a method that returns an arraylist of the elements
     * common to both trees that are passed in.
     * @param iter1     The iterator of the first tree
     * @param iter2     The iterator of the second tree
     * @return          The arraylist with the common elements
     */
    public ArrayList<T> intersection(Iterator<T> iter1, Iterator<T> iter2)
    {
        // initialize two lists to store elements
        ArrayList<T> list1 = new ArrayList<>();
        ArrayList<T> list2 = new ArrayList<>();

        // get all elements in the first tree
        while (iter1.hasNext())
            list1.add(iter1.next());

        // get all elements in the second tree
        while (iter2.hasNext())
            list2.add(iter2.next());

        // retain all the elements in first list from second list
        list1.retainAll(list2);
        return list1; // return the list
    }

    /**
     * Method that returns the number of nodes on a given level.
     * @param level     The level to count
     * @return          The count of nodes on that level
     */
    public int levelCount(int level)
    {
        BSTNode tempRoot = root;
        return findLevelCountHelper(tempRoot, level, 0);
    }

    /**
     * Helper method for levelCount, counts the number of nodes on a
     * level.
     * @param ptr           The ptr to traverse the tree
     * @param level         The level to count the nodes on
     * @param currentLevel  The level of the current node being traversed
     * @return              The count of the nodes on a level
     */
    private int findLevelCountHelper(BSTNode ptr, int level, int currentLevel)
    {
        // if level is greater than the height of the tree, return -1
        if (level > findHeight())
            return -1;
        // if current level matches level param, we found a node to count
        else if (currentLevel == level)
            return 1;
        // if it is a leaf, return 0
        else if (ptr.getLeft() == null && ptr.getRight() == null)
            return 0;
        // if left subtree is null, traverse right
        else if (ptr.getLeft() == null)
            return findLevelCountHelper(ptr.getRight(), level, currentLevel + 1);
        // if right subtree is null traverse left
        else if (ptr.getRight() == null)
            return findLevelCountHelper(ptr.getLeft(), level, currentLevel + 1);
        // traverse both subtrees otherwise
        else
            return findLevelCountHelper(ptr.getLeft(), level, currentLevel + 1)
                + findLevelCountHelper(ptr.getRight(), level, currentLevel + 1);
    }
}
