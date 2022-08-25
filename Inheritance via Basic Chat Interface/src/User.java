/*
    Name: Viswesh Uppalapati
    PID: A15600068
 */

import java.util.ArrayList; //imports

/**
 * This abstract class defines the basis of a User that can access the
 * chat rooms. It contains the definitions for the users and contains
 * common methods shared by them.
 */
public abstract class User
{

    // Error message to use in OperationDeniedException
    protected static final String JOIN_ROOM_FAILED =
            "Failed to join the chat room.";
    protected static final String INVALID_MSG_TYPE =
            "Cannot send this type of message to the specified room.";

    // instance variables
    protected String username;
    protected String bio;
    protected ArrayList<MessageExchange> rooms;

    /**
     * Constructor, initialized the username of the user and his or her
     * bio.
     * @param username      The username of the user.
     * @param bio           The bio of the user.
     */
    public User(String username, String bio)
    {
        // if inputs are null, throw exception
        if (username == null || bio == null)
            throw new IllegalArgumentException();

        // instantiate variables
        this.username = username; // sets username
        this.bio = bio; // sets bio
        this.rooms = new ArrayList<MessageExchange>(); // all rooms a user is part of
    }

    /**
     * Setter method for the bio of the user
     * @param newBio    The new bio of the user.
     */
    public void setBio(String newBio)
    {
        // if inputs is null, throw exception
        if (newBio == null)
            throw new IllegalArgumentException();

        // set the new bio
        this.bio = newBio;
    }

    /**
     * Getter method for the bio of the user.
     * @return  The bio of the user.
     */
    public String displayBio() { return this.bio; }

    /**
     * This method allows the user to join a chat room. Adds the user to
     * list of users in the chatroom and adds the chatroom to the list of
     * rooms the user is a part of.
     * @param me    The chatroom to add to.
     * @throws OperationDeniedException When user is already in the room
     *                                  or when the addUser to chatroom fails.
     */
    public void joinRoom(MessageExchange me) throws OperationDeniedException
    {
        // if room doesnt exist, throw exception
        if (me == null)
            throw new IllegalArgumentException();

        // add user to the room by calling addUser and store success
        boolean check = me.addUser(this);

        // if operation fails or if user is already a part of the room
        // throw exception
        if (this.rooms.contains(me) || !check)
            throw new OperationDeniedException(JOIN_ROOM_FAILED);

        // adds the chat room to the list of rooms the user is a part of
        this.rooms.add(me);
    }

    /**
     * Removes the user from a chat room and removes the room from the
     * list of rooms the user is a part of.
     * @param me    The chat room to remove from.
     */
    public void quitRoom(MessageExchange me)
    {
        // if input room deosnt exist, throw exception
        if (me == null)
            throw new IllegalArgumentException();

        // remove the user from the list of users in room and remove room
        // from the list of rooms of the user
        me.removeUser(this);
        if (this.rooms.contains(me))
            this.rooms.remove(me);
    }

    /**
     * Creates a regular chat room and adds the list of users along with
     * the current user to the room.
     * @param users     The users to add to the room.
     * @return          The instance of the chat room created.
     */
    public MessageExchange createChatRoom(ArrayList<User> users)
    {
        // if the users are null, throw exception
        if (users == null)
            throw new IllegalArgumentException();

        // instance of chatroom created
        ChatRoom cr = new ChatRoom();

        // add current user to list of users
        if (!users.contains(this))
            users.add(this);

        // add users to the room and handle exceptions thrown by joinRoom
        for (User user : users)
        {
            try
            {
                // allows each user to join room
                user.joinRoom(cr);
            }
            catch (OperationDeniedException ode)
            {
                // print error message to console and skip the user
                System.out.println(ode.getMessage());
                continue;
            }
        }

        return cr; // return instance of room
    }

    /**
     * Sends a message from a given user to a chat room.
     * @param me        The chat room to send to.
     * @param msgType   The type of message sent.
     * @param contents  The contents of the message.
     */
    public void sendMessage(MessageExchange me, int msgType, String contents)
    {
        // if inputs are invalid, or if msgType is invalid, or
        // if user is not part of the room, throw exception
        if (me == null || contents == null)
            throw new IllegalArgumentException();
        else if (!(msgType == Message.TEXT || msgType == Message.PHOTO
                || msgType == Message.STICKER))
            throw new IllegalArgumentException();
        else if (!this.rooms.contains(me))
            throw new IllegalArgumentException();

        // create a new message of msgType, handle exceptions thrown
        Message msg = null;
        try
        {
            if (msgType == Message.TEXT)
                msg = new TextMessage(this, contents);
            else if (msgType == Message.PHOTO)
                msg = new PhotoMessage(this, contents);
            else
                msg = new StickerMessage(this, contents);
        }
        catch (OperationDeniedException ode)
        {
            // print why exception was caused to console
            System.out.println(ode.getMessage());
        }

        // records the message in the chat log of the room
        boolean check = me.recordMessage(msg);
        // print invalid message to console
        if (!check)
            System.out.println(INVALID_MSG_TYPE);
    }

    /**
     * Abstract definition of a method ot get the messages sent to the
     * chat room.
     * @param me    The chat room.
     * @return      The messages from the chat room combined into a string.
     */
    public abstract String fetchMessage(MessageExchange me);

    /**
     * Abstract definition to display the name of the user.
     * @return      The formatted name of the user.
     */
    public abstract String displayName();
}
