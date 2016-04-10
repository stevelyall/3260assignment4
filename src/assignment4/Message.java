package assignment4;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;

/**
 * Created by stevenlyall on 2016-04-10.
 */
public class Message {

    private byte[] content;

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

    public void desEncrypt(SecretKey key) {
        DESCrypto des = new DESCrypto();
        content = des.encrypt(content, key);
    }

    public void desDecrypt(SecretKey key) {
        DESCrypto des = new DESCrypto();
        content = des.decrypt(content, key);

    }

    public void sign() {
        // TODO
    }
}
