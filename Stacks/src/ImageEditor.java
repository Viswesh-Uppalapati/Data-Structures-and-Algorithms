/*
    Name: Viswesh Uppalapati
    PID:  A15600068
 */

/**
 * This class contains the functionality and algorithms for a basic
 * image editor.
 *
 * @author Viswesh Uppalapati
 * @since  April 12th, 2020
 */
public class ImageEditor
{

    /* static constants, feel free to add more if you need */
    private static final int MAX_PIXEL_VALUE      = 255;
    private static final int STACKS_INIT_CAPACITY = 30;
    private static final int NUM_TO_POP = 3; //elements popped during redo/undo
    private static final int COLOR_IND = 0; //index of the original color in popped values
    private static final int COL_IND = 1; //index of col index of pixel in popped values
    private  static final int ROW_IND = 2; //index of row index of pixel in popped values

    /* instance variables, feel free to add more if you need */
    private int[][] image;
    private IntStack undo;
    private IntStack redo;

    /**
     * Constructor, checks input of image for exceptions, and initializes the
     * pixels of the image and the undo and redo stack.
     * @param image     The image to be edited.
     */
    ImageEditor(int[][] image)
    {
        //if image is invalid throw exception
        if (image.length == 0 || image[0].length == 0)
            throw new IllegalArgumentException();

        //if image is distorted throw exception
        for (int i = 0; i < image.length - 1; i++)
            if (image[i].length != image[i + 1].length)
                throw new IllegalArgumentException();

        //assign instances and create stacks
        this.image = image;
        undo = new IntStack(STACKS_INIT_CAPACITY);
        redo = new IntStack(STACKS_INIT_CAPACITY);
    }

    /**
     * Returns the current image/pixel array.
     * @return  The array of pixels that represents the image.
     */
    int[][] getImage()
    {
        return image;
    }

    /**
     * Helper method that is called in the painting operations to check
     * if the pixel being changed exists in the image and throws an
     * IndexOutOfBoundsException if it is not.
     * @param row   The row index of the pixel to change.
     * @param col   The col index of the pixel to change.
     */
    private void checkIndex(int row, int col)
    {
        //if row index is not in the image, throw exception
        if (row < 0 || row >= image.length)
            throw new IndexOutOfBoundsException();
        //if col index is not in hte image, throw exceptions
        else if (col < 0 || col >= image[0].length)
            throw new IndexOutOfBoundsException();
    }

    /**
     * Scales the color of the pixel being changed by a scaling factor.
     * @param i             The row index of the pixel being changed.
     * @param j             The col index of the pixel being changed.
     * @param scaleFactor   The factor to scale the value of the pixel by.
     */
    public void scale(int i, int j, double scaleFactor)
    {
        //calls helper method to see if pixel exists in image
        checkIndex(i, j);

        //checks if scaling factor is valid
        if (scaleFactor < 0)
            throw new IllegalArgumentException();

        //calculate new scaled color and push original color to undo
        int origColor = image[i][j];
        int newColor = (int) (origColor * scaleFactor);
        undo.multiPush(new int[]{i, j, origColor});

        //check for the new color to be valid, if not reset to MAX_PIXEL_VALUE
        if (newColor > MAX_PIXEL_VALUE)
            newColor = MAX_PIXEL_VALUE;

        image[i][j] = newColor; //assign scaled color
        redo.clear(); //empty redo stack
    }

    /**
     * Assigns a passed in Color to the pixel given by the input indices.
     * @param i         The row index of the pixel to be changed.
     * @param j         The col index of the pixel to be changed.
     * @param color     The new color to assign to the pixel.
     */
    public void assign(int i, int j, int color)
    {
        //calls helper method to see if pixel exists in image
        checkIndex(i, j);

        //checks the bounds of the new color passed in
        if (color < 0 || color > MAX_PIXEL_VALUE)
            throw new IllegalArgumentException();

        //push to undo stack to keep track of original values if
        //we want to undo the last operation
        int origColor = image[i][j];
        undo.multiPush(new int[]{i, j, origColor});

        //assign the new color to the pixel and clear the redo stack
        image[i][j] = color;
        redo.clear();
    }

    /**
     * Sets the color of a pixel to 0.
     * @param i     The row index of the pixel to be changed.
     * @param j     The col index of the pixel to be changed.
     */
    public void delete(int i, int j)
    {
        //delete is just assigning 0 to a pixel
        assign(i, j, 0);
    }

    /**
     * This method undoes the last painting operation that took place
     * through the help of a stack.
     * @return      Boolean value of whether the undo was successful.
     */
    public boolean undo()
    {
        //if undo stack is empty, then there were no new operations
        //nothing to undo, return false
        if (undo.isEmpty())
            return false;

        //get the original color and pixel that was changed
        int[] undoVals = undo.multiPop(NUM_TO_POP);
        int origColor = image[undoVals[ROW_IND]][undoVals[COL_IND]];
        //in case we want to redo the undo, push to the info of pixel to redo stack
        redo.multiPush(new int[]{undoVals[ROW_IND], undoVals[COL_IND], origColor});

        //reassign past value to pixel
        image[undoVals[ROW_IND]][undoVals[COL_IND]] = undoVals[COLOR_IND];

        return true; //undo successful
    }

    /**
     * Redoes the last undo based on the information stored in the redo
     * stack.
     * @return      Boolean of whether the redo was successful.
     */
    public boolean redo()
    {
        //if redo is empty, nothing to redo, fails
        if (redo.isEmpty())
            return false;

        //pop the info stored on redo that indicates original color of pixel specified
        int[] redoVals = redo.multiPop(NUM_TO_POP);
        int origColor = image[redoVals[ROW_IND]][redoVals[COL_IND]];
        //store current color of pixel in undo stack in case we want to undo the redo
        undo.multiPush(new int[]{redoVals[ROW_IND], redoVals[COL_IND], origColor});

        //reassign old color at the pixel
        image[redoVals[ROW_IND]][redoVals[COL_IND]] = redoVals[COLOR_IND];

        return true; //redo complete
    }
}
