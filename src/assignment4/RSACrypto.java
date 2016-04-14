package assignment4;

import javax.crypto.Cipher;
import java.security.InvalidKeyException;
import java.security.Key;

/**
 * Created by stevenlyall on 2016-04-14.
 */
public class RSACrypto implements Crypto {
    private final String RSA_CIPHER = "RSA/ECB/PKCS1padding";
    private Cipher rsaCipher;

    public RSACrypto() {
        try {
            rsaCipher = Cipher.getInstance(RSA_CIPHER);
        } catch (Exception e) {
        }
    }

    @Override
    public byte[] encrypt(byte[] plaintext, Key key) {
        byte[] ciphertext = new byte[0];
        try {
            rsaCipher.init(Cipher.ENCRYPT_MODE, key);
            ciphertext = rsaCipher.doFinal(plaintext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ciphertext;
    }

    @Override
    public byte[] decrypt(byte[] ciphertext, Key key) {
        byte[] plaintext = new byte[0];
        try {
            rsaCipher.init(Cipher.DECRYPT_MODE, key);
            plaintext = rsaCipher.doFinal(ciphertext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plaintext;
    }
}
