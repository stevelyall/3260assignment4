package assignment4;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Represents a message sent over the network, including its digital signature.
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
        return Base64.encode(signature);
    }

    public void desEncrypt(SecretKey key) {
        DESCrypto des = new DESCrypto();
        content = des.encrypt(content, key);
    }

    public void desDecrypt(SecretKey key) {
        DESCrypto des = new DESCrypto();
        content = des.decrypt(content, key);

    }

    /**
     * gets message digest of the message content
     *
     * @return byte array containing the message digest
     */
    public byte[] calculateMessageDigest() {
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        mDigest.update(content);
        return mDigest.digest();

    }

    /**
     * Creates the digital signature for the message
     *
     * @param messageDigest SHA-256 message digest
     * @param privateKey    sender's private key
     * @return newly generated signature
     */
    public byte[] sign(byte[] messageDigest, PrivateKey privateKey) {
        RSACrypto rsaEncrypt = new RSACrypto();
        signature = rsaEncrypt.encrypt(messageDigest, privateKey);
        return signature;
    }

    /**
     * Checks whether the message digest and the recieved message digest match
     *
     * @param receivedMessageDigest digest for the recieved message
     * @param key                   public key of sender
     * @return true if the signature is valid, false otherwise
     */
    public boolean verifyDigitalSignature(byte[] receivedMessageDigest, PublicKey key) {

        RSACrypto rsaDecrypt = new RSACrypto();
        byte[] decryptedSignature = rsaDecrypt.decrypt(this.signature, key);
        for (int i = 0; i < receivedMessageDigest.length; i++) {
            if (decryptedSignature[i] != receivedMessageDigest[i]) {
                return false;
            }
        }
        return true;
    }

}


