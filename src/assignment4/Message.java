package assignment4;

import com.sun.crypto.provider.HmacMD5KeyGenerator;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;

/**
 * Created by stevenlyall on 2016-04-10.
 */
public class Message {

    private byte[] content;
    private byte[] signature;

    public Message(String text) {
        content = text.getBytes();
    }

    public String getContent() {
        try {
            return new String(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSignature() {
        try {
            return new String(signature, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void desEncrypt(SecretKey key) {
        DESCrypto des = new DESCrypto();
        content = des.encrypt(content, key);
    }

    public void desDecrypt(SecretKey key) {
        DESCrypto des = new DESCrypto();
        content = des.decrypt(content, key);

    }

    public byte[] calculateMessageDigest() {
        // get message digest from message content
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        mDigest.update(content);
        return mDigest.digest();

    }

    public byte[] sign(byte[] messageDigest, PrivateKey privateKey) {
        try {
            // encrypt with sender private key to get digital signature
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            signature = cipher.doFinal(messageDigest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return signature;
    }
}
