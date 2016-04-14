package test;

import assignment4.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * JUnit tests for User
 */

public class UserTest {

    User user;

    @Before
    public void setUp() throws Exception {
        user = new User("Bob");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateUserInstantiatesObjectCorrectly() {
        assertEquals("Bob", user.getName());
        assertEquals(null, user.getRsaKeyPair());
    }

    @Test
    public void testGenerateRSAKeyPairGeneratesKeyPairCorrectly() {
        user.generateRSAKeyPair();
        System.out.println(user.getRSAKeyPairString());
        KeyPair pair = user.getRsaKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        byte[] priv = privateKey.getEncoded();
        byte[] pub = publicKey.getEncoded();

        assertNotNull(privateKey);
        assertNotNull(publicKey);
    }

    @Test
    public void testGenerateDESKeyGeneratesKeyCorrectly() {
        user.generateDESKey();
        System.out.println(user.getDESKeyString());
        byte[] key = user.getDESKey().getEncoded();
        assertEquals(64, key.length * 8);
    }
}