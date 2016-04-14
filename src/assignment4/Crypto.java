package assignment4;

import java.security.Key;

/**
 * Created by stevenlyall on 2016-04-14.
 */
public interface Crypto {

    byte[] encrypt(byte[] plaintext, Key key);
    byte[] decrypt(byte[] ciphertext, Key key);
}
