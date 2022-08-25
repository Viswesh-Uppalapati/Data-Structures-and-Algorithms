/*
    Name: Viswesh Uppalapati
    PID: A15600068
 */
import java.util.ArrayList;

/**
 * This class containst he functionality of a premium user
 * in the messenger application
 */
public class PremiumUser extends User
{

    private static final String TITLE = "Premium"; // default title
    // instance variable
    private String customTitle;

    /**
     * Sets the info of the user by calling constructor of User.java
     * and sets a custom title.
     * @param username  The username of the user.
     * @param bio       The bio of the user.
     */
    public PremiumUser(String username, String bio)
    {
        super(username, bio);
        customTitle = TITLE;
    }

    /**
     * This method gets all the messages sent to a chat room and returns
     * them in a string.
     * @param me    The chat room.
     * @return      The string with all the messages.
     */
    public String fetchMessage(MessageExchange me)
    {
        // validate input
        if (me == null)
            throw new IllegalArgumentException();

        // get the messages from the chat room
        ArrayList<Message> messages = me.getLog();
        String result = "";

        // add all of them to the result string
        for (int i = 0; i < messages.size(); i++)
            result += messages.get(i) + "\n";

        return result; //return the result
    }

    /**
     * This method creates a photo room which is a chat room where
     * people can only communicate with photo messages and adds
     * the users.
     * @param users     The users to add to the room.
     * @return          The reference of the photo room.
     */
    public MessageExchange createPhotoRoom(ArrayList<User> users)
    {
        // validate input
        if (users == null)
            throw new IllegalArgumentException();

        // create the room
        PhotoRoom pr = new PhotoRoom();

        // add the current user to the list of users
        if (!users.contains(this))
            users.add(this);

        // add each user to the room, and print error to console
        // if it fails
        for (User user : users)
        {
            try
            {
                user.joinRoom(pr);
            }
            catch (OperationDeniedException ode)
            {
                System.out.println(ode.getMessage());
                continue; // skip current user
            }
        }

        return pr; // return the room instance
    }

    /**
     * Method that formats the title and username and returns it as
     * a string.
     * @return  The formatted name.
     */
    public String displayName() { return "<" + customTitle + "> " + username; }

    /**
     * Setter method for the customTitle.
     * @param newTitle  The new title of the user.
     */
    public void setCustomTitle(String newTitle) { customTitle = newTitle; }

}
