package assignment4;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;

/**
 * Created by stevenlyall on 2016-04-08.
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

    public KeyPair getRsaKeyPair() {return rsaKeyPair; }

    public void generateRSAKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            rsaKeyPair = generator.generateKeyPair();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public String getRSAKeyPairString() {
        PrivateKey privateKey = rsaKeyPair.getPrivate();
        PublicKey publicKey = rsaKeyPair.getPublic();

        return "Public Key: " + publicKey.getEncoded() +
                "\nPrivate Key: " + privateKey.getEncoded();
        // TODO should these be shown as B64?
    }

    // TODO from key phrase?
    public void generateDESKey() {
        try{
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            desKey = generator.generateKey();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public SecretKey getDESKey() {
        return desKey;
    }

    public String getDESKeyString() {
        return "DES key: " + desKey.getEncoded();
    }
}
