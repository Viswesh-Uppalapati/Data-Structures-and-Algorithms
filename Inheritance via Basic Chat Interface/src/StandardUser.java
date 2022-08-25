/*
    Name: Viswesh Uppalapati
    PID: A15600068
 */

import java.util.ArrayList;

/**
 * This class contains the functionality of a standard user of the
 * messenger application.
 */
public class StandardUser extends User
{
    private static final int FETCH_FACTOR = 10; // constant for fetchMessage

    // Message to append when fetching non-text message
    private static final String FETCH_DENIED_MSG =
            "This message cannot be fetched because you are not a premium user.";

    /**
     * Constructor, sets the username and bio by calling the constructor
     * of User.java
     * @param username  The username.
     * @param bio       The String bio.
     */
    public StandardUser(String username, String bio)
    {
        super(username, bio);
    }

    /**
     * This method gets the last 10 percent of the messages that were sent
     * to a given chat room.
     * @param me    The chat room.
     * @return      The String containing the messages.
     */
    public String fetchMessage(MessageExchange me)
    {
        // validate input
        if (me == null)
            throw new IllegalArgumentException();

        // get the message log and determine number of messages to get
        String result = "";
        ArrayList<Message> messages = me.getLog();
        int toFetch = messages.size() / FETCH_FACTOR;

        // add these messages to the result string
        for (int i = toFetch - 1; i >= 0; i--)
        {
            Message msg = messages.get(messages.size() - i - 1);
            if (msg instanceof TextMessage)
                result += msg.getContents() + "\n";
            else
                result += FETCH_DENIED_MSG + "\n";
        }

        return result; // return the messages
    }

    /**
     * Getter for the username of the user.
     * @return  The string username.
     */
    public String displayName() { return this.username; }
}
