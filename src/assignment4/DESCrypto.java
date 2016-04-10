package assignment4;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;

/**
 * Created by stevenlyall on 2016-04-10.
 */
public class DESCrypto {
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

    public byte[] encrypt(byte[] plaintext, SecretKey key) {
        byte[] ciphertext = new byte[0];
        try {
            desCipher.init(Cipher.ENCRYPT_MODE, key, ivParameter);

            ciphertext = desCipher.doFinal(plaintext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ciphertext;
    }

    public byte[] decrypt(byte[] ciphertext, SecretKey key) {
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