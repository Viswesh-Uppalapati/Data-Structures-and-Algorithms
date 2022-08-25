/*
    Name: Viswesh Uppalapati
    PID: A15600068
 */

/**
 * Contains the functionalities of a photo message that only
 * premium users are allowed to send.
 */
public class PhotoMessage extends Message
{

    // Error message to use in OperationDeniedException
    private static final String INVALID_INPUT =
            "The source path given cannot be parsed as photo.";

    // input validation criteria
    private static final String[] ACCEPTABLE_EXTENSIONS =
            new String[] {"jpg", "jpeg", "gif", "png", "tif"};

    // instance variable
    private String extension;

    /**
     * Constructor, checks and validates input and intializes the instances.
     * Throws exceptions when inputs are invalid or when the user is not
     * a premium user.
     * @param sender        The obj containing user info.
     * @param photoSource   The String source for the photo message.
     */
    public PhotoMessage(User sender, String photoSource)
                        throws OperationDeniedException
    {
        super(sender); // initialize user

        // extract the extension of the photo source
        boolean validExt = false;
        String givenExt = photoSource.substring(photoSource.lastIndexOf('.') + 1)
                .toLowerCase();

        //checks extension is valid
        for (String ext : ACCEPTABLE_EXTENSIONS)
            if (ext.equals(givenExt))
                validExt = true;

        // throw exceptions
        if (photoSource == null)
            throw new IllegalArgumentException();
        else if(!(sender instanceof PremiumUser)) // checks if user is premium
            throw new OperationDeniedException(this.DENIED_USER_GROUP);
        else if(!validExt)
            throw new OperationDeniedException(INVALID_INPUT);

        // initialize instances
        this.contents = photoSource;
        this.extension = givenExt;
    }

    /**
     * Methods returns a String of the user info, date when the message was
     * created and what the message contained and where it is from.
     * @return  String with the contents of the message and user.
     */
    public String getContents()
    {
        // return the formatted string
        return String.format("%s [%s]: Picture at %s.", this.getSender().displayName(),
                this.getDate().toString(), this.contents);
    }

    /**
     * Getter method that returns the extension of the photo source.
     * @return  The String extension.
     */
    public String getExtension() { return extension; }

}
