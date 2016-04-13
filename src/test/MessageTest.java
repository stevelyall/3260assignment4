package test;

import assignment4.Message;
import assignment4.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        alice.generateRSAKeyPair();
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

    // TODO fix this?
    @Ignore
    @Test
    public void testSignGeneratesSignatureCorrectly() {
        message = new Message(TEST_STRING);
        message.desEncrypt(alice.getDESKey());


        message.sign(alice.getRsaKeyPair().getPrivate());


        //decrypt signature to get message digest
        byte[] decryptedSignature = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1padding");
            cipher.init(Cipher.DECRYPT_MODE, alice.getRsaKeyPair().getPublic());
            decryptedSignature = cipher.doFinal(message.getSignature());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // get message digest from message
        byte[] checkMD = new byte[0];
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(message.getContent().getBytes());
            checkMD = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assertEquals(checkMD, decryptedSignature);
    }
}
