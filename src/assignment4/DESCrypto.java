package assignment4;

import assignment4.interfaces.Crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

/**
 * Handles DES encryption and decryption
 */
public class DESCrypto implements Crypto {
    private final String DES_CIPHER = "DES/CBC/PKCS5Padding";
    private final byte[] DES_IV = {0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8};
    private Cipher desCipher;
    private IvParameterSpec ivParameter;

    public DESCrypto() {
        try {
            desCipher = Cipher.getInstance(DES_CIPHER);
            ivParameter = new IvParameterSpec(DES_IV);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] encrypt(byte[] plaintext, Key key) {
        byte[] ciphertext = new byte[0];
        try {
            desCipher.init(Cipher.ENCRYPT_MODE, key, ivParameter);

            ciphertext = desCipher.doFinal(plaintext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ciphertext;
    }

    public byte[] decrypt(byte[] ciphertext, Key key) {
        byte[] plaintext = new byte[0];
        try {
            desCipher.init(Cipher.DECRYPT_MODE, key, ivParameter);

            plaintext = desCipher.doFinal(ciphertext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plaintext;
    }

}
