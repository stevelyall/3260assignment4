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

    public byte[] getSignature() {
        return signature;
    }

    public void desEncrypt(SecretKey key) {
        DESCrypto des = new DESCrypto();
        content = des.encrypt(content, key);
    }

    public void desDecrypt(SecretKey key) {
        DESCrypto des = new DESCrypto();
        content = des.decrypt(content, key);

    }


    public void sign(PrivateKey privateKey) {
        try {

            // get message digest from message content
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(content);
            byte[] messageDigest = mDigest.digest();

//            KeyGenerator generator = KeyGenerator.getInstance("HmacMD5");
//            SecretKey key = generator.generateKey();
//            Mac mac = Mac.getInstance("HmacMD5");
//            mac.init(key);
//            mac.update(content);
//
//            signature = mac.doFinal();

            // encrypt with sender private key to get digital signature
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            signature = cipher.doFinal(messageDigest);



        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO
    }
}
