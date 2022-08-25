/*
    Name: Viswesh Uppalapati
    PID: A15600068
 */

/**
 * This class contains the functionalities of the TextMessage that can
 * be sent by any user.
 */
public class TextMessage extends Message
{
    // Error message to use in OperationDeniedException
    private static final String EXCEED_MAX_LENGTH =
            "Your input exceeded the maximum length limit.";

    // input validation criteria
    private static final int MAX_TEXT_LENGTH = 1000;

    /**
     * Constructor, validates and initializes input. Throws exceptions when
     * the inputs are invalid or when text message exceeds the character
     * length.
     * @param sender    The obj containing sender info.
     * @param text      The text message String of the user.
     */
    public TextMessage(User sender, String text)
            throws OperationDeniedException
    {
        super(sender); // initialize the sender info

        // validate input to throw exceptions
        if (text == null)
            throw new IllegalArgumentException();
        else if (text.length() > MAX_TEXT_LENGTH)
            throw new OperationDeniedException(EXCEED_MAX_LENGTH);

        // set the contents of the text message in parent class
        this.contents = text;
    }

    /**
     * Returns the contents of the sender and the text message that
     * the user wants to send.
     * @return  The String with user and message information.
     */
    public String getContents()
    {
        //return formatted string
        return String.format("%s [%s]: %s", this.getSender().displayName(),
                this.getDate().toString(), this.contents);
    }

}
