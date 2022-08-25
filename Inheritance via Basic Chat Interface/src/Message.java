/*
    Name: Viswesh Uppalapati
    PID: A15600068
 */

//imports
import java.time.LocalDate;

/**
 * This abstract class contains functionalities for the messages sent by
 * users. It stores the information of senders and their contents.
 */
public abstract class Message
{

    // Use these variable names as the msgType argument of sendMessage()
    // DO NOT MODIFY!
    public static final int TEXT    = 1000;
    public static final int PHOTO   = 1001;
    public static final int STICKER = 1002;

    // Error message to use in OperationDeniedException
    protected static final String DENIED_USER_GROUP =
            "This operation is disabled in your user group.";

    // instance variables
    private LocalDate date;
    private User sender;
    protected String contents;

    /**
     * Constructor for the class that initializes instances and stores info
     * of the sender and stores the date of the message creation.
     * @param sender    The sender information coming from one of the User
     *                  classes.
     */
    public Message(User sender)
    {
        //throw exception if sender doesnt exist
        if(sender == null)
            throw new IllegalArgumentException();

        this.date = LocalDate.now(); //store current date
        this.sender = sender; //store sender info
    }

    /**
     * Getter for the date.
     * @return  The date of the message creation.
     */
    public LocalDate getDate() { return date; }

    /**
     * Getter for the sender info.
     * @return  The object containing sender information.
     */
    public User getSender() { return sender; }

    /**
     *
     * @return The contents of the message.
     */
    public abstract String getContents();

}