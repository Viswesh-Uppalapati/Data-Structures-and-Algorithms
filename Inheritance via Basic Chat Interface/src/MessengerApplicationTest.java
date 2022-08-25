/*
  Name: Viswesh Uppalapati
  PID:  A15600068
 */


import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
import java.time.LocalDate;


/**
 * Messenger Application Test
 * @author Viswesh Uppalapati
 * @since  April 15th, 2020
 */
public class MessengerApplicationTest
{

    /*
      Messages defined in starter code. Remember to copy and paste these strings to the
      test file if you cannot directly access them. DO NOT change the original declaration
      to public.
     */
    private static final String INVALID_INPUT =
            "The source path given cannot be parsed as photo.";
    private static final String EXCEED_MAX_LENGTH =
            "Your input exceeded the maximum length limit.";
    protected static final String DENIED_USER_GROUP =
            "This operation is disabled in your user group.";

    /*
      Global test variables. Initialize them in @Before method.
     */
    PremiumUser marina;
    MessageExchange room;
    TextMessage tm;
    PhotoMessage pm;
    StickerMessage sm;

    /*
      The date used in Message and its subclasses. You can directly
      call this in your test methods.
     */
    LocalDate date = LocalDate.now();

    @Before
    public void setup()
    {
        marina = new PremiumUser("Marina", "Instructor");
        room = new ChatRoom();
    }

    /*
      Recap: Assert exception without message
     */
    /*@Test (expected = IllegalArgumentException.class)
    public void testPremiumUserThrowsIAE() {
        marina = new PremiumUser("Marina", null);
    }*/

    /*
      Assert exception with message
     */
    /*@Test
    public void testPhotoMessageThrowsODE() {
        try {
            PhotoMessage pm = new PhotoMessage(marina, "PA02.zip");
            fail("Exception not thrown"); // will execute if last line didn't throw exception
        } catch (OperationDeniedException ode) {
            assertEquals(INVALID_INPUT, ode.getMessage());
        }
    }*/

    @Test (expected = IllegalArgumentException.class)
    public void testNullSenderMessage()
    {
        // constructor test
        try
        {
            tm = new TextMessage(null, "");
        }
        catch (OperationDeniedException ode) {}
    }

    @Test
    public void testGetSenderMessage()
    {
        try
        {
            tm = new TextMessage(marina, "");
            assertEquals(marina, tm.getSender());
        }
        catch (OperationDeniedException ode) {}
    }

    @Test
    public void testGetDateMessage()
    {
        try
        {
            tm = new TextMessage(marina, "");
            assertEquals(LocalDate.now(), tm.getDate());
        }
        catch (OperationDeniedException ode) {}
    }


    @Test
    public void testGetExtensionPhotoMessage()
    {
        try {
            pm = new PhotoMessage(marina, "...gif");
            assertEquals("gif", pm.getExtension());
        }
        catch (OperationDeniedException ode) {}
    }


    @Test
    public void testWordLengthExceptMessage()
    {
        // constructor test
        String temp = "";
        for (int i = 0; i < 1200; i++)
            temp += " ";

        try
        {
            tm = new TextMessage(marina, temp);
            fail("exception not thrown for text message length.");
        }
        catch (OperationDeniedException ode)
        {
            assertEquals(EXCEED_MAX_LENGTH, ode.getMessage());
        }
    }

    @Test
    public void testPremiumUserExceptPhotoMessage()
    {
        try
        {
            StandardUser st = new StandardUser("Invalid", "");
            pm = new PhotoMessage(st, "");
            fail("Photo exception not thrown");
        }
        catch (OperationDeniedException ode)
        {
            assertEquals(DENIED_USER_GROUP, ode.getMessage());
        }
    }

    @Test
    public void testInvalidExtensionExceptPhotoMessage()
    {
        try
        {
            pm = new PhotoMessage(marina, "yes.vish");
            fail("Invalid Extension exception not thrown");
        }
        catch (OperationDeniedException ode)
        {
            assertEquals(INVALID_INPUT, ode.getMessage());
        }
    }

    @Test
    public void testPremiumUserExceptStickerMessage()
    {
        try
        {
            StandardUser st = new StandardUser("Invalid", "");
            sm = new StickerMessage(st, "");
            fail("Sticker exception not thrown");
        }
        catch (OperationDeniedException ode)
        {
            assertEquals(DENIED_USER_GROUP, ode.getMessage());
        }
    }


    @Test
    public void testAllGetContents()
    {
        try
        {
            // for text message
            tm = new TextMessage(marina, "Deadline is Tuesday.");
            assertEquals("<Premium> Marina [" + LocalDate.now() + "]: Deadline is Tuesday.",
                    tm.getContents());

            // for photo message
            pm = new PhotoMessage(marina, "...gif");
            assertEquals("<Premium> Marina [" + LocalDate.now() + "]: Picture at ...gif.",
                    pm.getContents());

            // for Sticker message
            sm = new StickerMessage(marina, "nabi-collections-8/LOLFace");
            assertEquals("<Premium> Marina [2020-04-15]: Sticker [LOLFace] from Pack [nabi-collections-8].",
                    sm.getContents());

        }
        catch (OperationDeniedException ode) {}
    }

}
