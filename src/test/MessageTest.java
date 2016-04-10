package test;

import assignment4.Message;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.events.EventException;

import static org.junit.Assert.*;

/**
 * Created by stevenlyall on 2016-04-10.
 */
public class MessageTest {

    Message message;
    final String testString = "Are you suggesting coconuts migrate?";

    @Before
    public void setUp() throws Exception {
        message = new Message(testString);
    }

    @Test
    public void testCreateMessageInstantiatesObjectCorrectly() {
        assertEquals(testString, message.getContent());
    }
}
