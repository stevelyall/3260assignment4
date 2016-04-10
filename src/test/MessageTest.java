package test;

import assignment4.Message;
import assignment4.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by stevenlyall on 2016-04-10.
 */
public class MessageTest {

    Message message;
    User alice;
    final String TEST_STRING = "Are you suggesting coconuts migrate?";

    @Before
    public void setUp() throws Exception {
        message = new Message(TEST_STRING);
        alice = new User("Alice");
        alice.generateDESKey();
    }

    @Test
    public void testCreateMessageInstantiatesObjectCorrectly() {
        assertEquals(TEST_STRING, message.getContent());
    }

    @Test
    public void testDESEncryptEncryptsMessage() {
        String content = message.getContent();

        message.desEncrypt(alice.getDESKey());
        assertNotEquals(content, message.getContent());
    }

    @Test
    public void testDESEncryptDecryptEncryptsThenDecryptsCorrectly() {
        message = new Message(TEST_STRING);
        String originalPlaintext = message.getContent();
        message.desEncrypt(alice.getDESKey());
        String ciphertext = message.getContent();
        assertNotSame(originalPlaintext, ciphertext);

        message.desDecrypt(alice.getDESKey());
        String plaintext = message.getContent();
        assertEquals(originalPlaintext, plaintext);
    }

}
