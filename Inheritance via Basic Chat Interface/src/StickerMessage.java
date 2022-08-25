/*
    Name: Viswesh Uppalapati
    PID: A15600068
 */

/**
 * This class contains the functionality of a sticker message
 * only available to premium users in this messenger application.
 */
public class StickerMessage extends Message
{

    // instance variable
    private String packName;

    /**
     * Constructor that initializes the user information and stores
     * the type of sticker the user wants to use and its source. It
     * also checks and validates the inputs and throws an exception
     * when the user is not premium or when the stickerSource doest
     * exist.
     * @param sender        User obj with the user info that sends message.
     * @param stickerSource String that contains the message information.
     */
    public StickerMessage(User sender, String stickerSource)
            throws OperationDeniedException
    {
        super(sender); // stores user info in parent class.

        // validates input
        if (stickerSource == null)
            throw new IllegalArgumentException();
        else if (!(sender instanceof PremiumUser))
            throw new OperationDeniedException(this.DENIED_USER_GROUP);

        // stores the sticker and its source
        int temp = stickerSource.lastIndexOf('/');
        this.packName = stickerSource.substring(0, temp);
        this.contents = stickerSource.substring(temp + 1);
    }

    /**
     * Method that returns the message, date, and name of user in a
     * formatted string to represent as a message on the application.
     * @return      The formatted string.
     */
    public String getContents()
    {
        return String.format("%s [%s]: Sticker [%s] from Pack [%s].",
                this.getSender().displayName(), this.getDate().toString(),
                this.contents, packName);
    }

    /**
     * Getter method that returns the pack that the sticker is from.
     * @return      The pack of the sticker as a String.
     */
    public String getPackName() { return packName; }

}
