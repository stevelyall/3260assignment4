package assignment4;

import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.*;

/**
 * JUnit tests for RSA encrypt/decrypt.
 */

public class RSACryptoTest {

    private RSACrypto crypto;
    private User user;
    final String TEST_STRING = "Are you suggesting coconuts migrate?";

    @Before
    public void setUp() throws Exception {
        user = new User("Alice");
        user.generateRSAKeyPair();
        crypto = new RSACrypto();
    }

    @Test
    public void testEncrypt() throws Exception {
        byte[] plaintext = TEST_STRING.getBytes();
        PrivateKey key = user.getRsaKeyPair().getPrivate();
        byte[] ciphertext = crypto.encrypt(plaintext, key);
        assertFalse(plaintext.equals(ciphertext));
    }

    @Test
    public void testDecrypt() throws Exception {
        byte[] originalPlaintext = TEST_STRING.getBytes();
        PrivateKey key1 = user.getRsaKeyPair().getPrivate();
        byte[] ciphertext = crypto.encrypt(originalPlaintext, key1);

        PublicKey key2 = user.getRsaKeyPair().getPublic();
        byte[] plaintext = crypto.decrypt(ciphertext, key2);
        assertArrayEquals(originalPlaintext, plaintext);
    }
}