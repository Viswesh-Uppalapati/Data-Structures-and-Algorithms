/*
    Name: Viswesh Uppalapati
    PID: A15600068
 */
import java.util.ArrayList;

/**
 * This class contains the functionality of a photo room
 * in the messenger application, only available to PremiumUsers.
 */
public class PhotoRoom implements MessageExchange
{

    // instance variables
    private ArrayList<User> users; // users in room
    private ArrayList<Message> log; // messages in room

    /**
     * Constructor, initializes the arraylists that contain
     * the users of the room and messages.
     */
    public PhotoRoom()
    {
        users = new ArrayList<User>();
        log = new ArrayList<Message>();
    }

    /**
     * Getter that returns the message log.
     * @return      The arraylist of messages in the room.
     */
    public ArrayList<Message> getLog() { return log; }

    /**
     * Method that adds a user to the current room if he or she is
     * a premium user.
     * @param u User to add.
     * @return  Whether the add was successful.
     */
    public boolean addUser(User u)
    {
        // premium user check
        if (u instanceof PremiumUser)
        {
            users.add(u);
            return true;
        }
        return false;
    }

    /**
     * Removes a user from a chat room.
     * @param u User to remove.
     */
    public void removeUser(User u) { users.remove(u); }

    /**
     * Getter for the list of users in a room.
     * @return      The list of users.
     */
    public ArrayList<User> getUsers() { return users; }

    /**
     * Method that stores a sent message to the room.
     * @param m Message to add.
     * @return  Whether the record was successful.
     */
    public boolean recordMessage(Message m)
    {
        // message has to be photo message
        if (m instanceof PhotoMessage)
        {
            log.add(m);
            return true;
        }
        return false;
    }

}
