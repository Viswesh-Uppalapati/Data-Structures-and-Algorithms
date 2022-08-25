/*
    Name: Viswesh Uppalpati
    PID: A15600068
 */

import java.util.ArrayList;

/**
 * This class contains the functionality of a chat room in the
 * messenger application.
 */
public class ChatRoom implements MessageExchange
{

    // instance variables
    private ArrayList<User> users; // stores users in the room
    private ArrayList<Message> log; // stores messages from the room

    /**
     * Constructor, Initializes the lists that contain the users
     * and messages in the room.
     */
    public ChatRoom()
    {
        users = new ArrayList<User>();
        log = new ArrayList<Message>();
    }

    /**
     * Getter for the message log of the room.
     * @return  The list of messages.
     */
    public ArrayList<Message> getLog() { return log; }

    /**
     * Method that adds users to the user list of the chat room.
     * @param u User to add.
     * @return  Whether the add was successful.
     */
    public boolean addUser(User u)
    {
        users.add(u);
        return true;
    }

    /**
     * Method that removes a user from the current room.
     * @param u User to remove.
     */
    public void removeUser(User u) { users.remove(u); }

    /**
     * Getter for the user list of the chat room.
     * @return      The list of users in the room.
     */
    public ArrayList<User> getUsers() { return users; }

    /**
     * Method that stores the messages sent to the room on an
     * arraylist
     * @param m     Message to add.
     * @return      Whether the add was successful
     */
    public boolean recordMessage(Message m)
    {
        log.add(m);
        return true;
    }
}
