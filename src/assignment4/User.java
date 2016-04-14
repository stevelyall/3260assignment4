package assignment4;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;

/**
 * Represents a user, each user has RSA key pair and symmetric DES key.
 */
public class User {
    private String name;
    private KeyPair rsaKeyPair;
    private SecretKey desKey;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public KeyPair getRsaKeyPair() {
        return rsaKeyPair;
    }

    /**
     * Generates the user's RSA keys.
     */
    public void generateRSAKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            rsaKeyPair = generator.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getRSAKeyPairString() {
        PrivateKey privateKey = rsaKeyPair.getPrivate();
        PublicKey publicKey = rsaKeyPair.getPublic();

        return "Public Key:\n" + Base64.encode(publicKey.getEncoded()) +
                "\nPrivate Key:\n" + Base64.encode(privateKey.getEncoded());
    }

    /**
     * Generates the user's DES key
     */
    public void generateDESKey() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            desKey = generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public SecretKey getDESKey() {
        return desKey;
    }

    public String getDESKeyString() {
        return "DES key:\n" + Base64.encode(desKey.getEncoded());
    }
}
