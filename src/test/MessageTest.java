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
import java.security.PrivateKey;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Junit tests for Message
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

    @Test
    public void testCalculateMessageDigestCalculatesDigestCorrectly() {
        message = new Message(TEST_STRING);
        Message message1 = new Message(TEST_STRING);
        byte[] digest = message.calculateMessageDigest();
        byte[] sameDigest = message1.calculateMessageDigest();

        assertEquals(digest.length, sameDigest.length);
        assertArrayEquals(digest, sameDigest);

        Message message3 = new Message("Different message");
        byte[] differentDigest = message3.calculateMessageDigest();

        assertFalse(Arrays.equals(digest, differentDigest));
    }

    @Test
    public void testSignGeneratesSignatureCorrectly() {
        message = new Message(TEST_STRING);
        message.desEncrypt(alice.getDESKey());
        byte[] digest = message.calculateMessageDigest();

        // get signature
        PrivateKey alicePriv = alice.getRsaKeyPair().getPrivate();
        byte[] signature = message.sign(digest, alicePriv);

        //decrypt signature to get message digest
        byte[] decryptedSignature = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1padding");
            cipher.init(Cipher.DECRYPT_MODE, alice.getRsaKeyPair().getPublic());
            decryptedSignature = cipher.doFinal(signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertArrayEquals(digest, decryptedSignature);
    }
}
