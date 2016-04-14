package assignment4.interfaces;

import java.security.Key;

public interface Crypto {

    byte[] encrypt(byte[] plaintext, Key key);

    byte[] decrypt(byte[] ciphertext, Key key);
}
