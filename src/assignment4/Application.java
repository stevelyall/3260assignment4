package assignment4;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;

public class Application {

    public static void main(String[] args) {
        Application app = new Application();
            app.init();
    }

    private void init() {
        User alice = new User("Alice");
        User bob = new User("Bob");

        // 1 Alice and Bob generate RSA key pairs
        alice.generateRSAKeyPair();
        bob.generateRSAKeyPair();

        System.out.println();

        System.out.println(alice.getName());
        printRSAKeyPair(alice);

        System.out.println();

        System.out.println(bob.getName());
        printRSAKeyPair(bob);

        System.out.println();

        // 2 Alice generates DES key
        alice.generateDESKey();
        System.out.println(alice.getName());
        printDESKey(alice);

        System.out.println();

        // 3 Alice encyrpts message
        Message msg = new Message("Protect your network as if it would be a hotel " +
                "not as if it would be a castle");
        System.out.println(alice.getName() + ":");
        System.out.println("Plaintext: " + msg.getContent());
        msg.desEncrypt(alice.getDESKey());
        printDESKey(alice);
        System.out.println("Ciphertext: " + msg.getContent());

        System.out.println();

        // 4 Alice digitally signs message
        PrivateKey keySign = alice.getRsaKeyPair().getPrivate();
        byte[] messageDigest = msg.calculateMessageDigest();
        byte[] signature = msg.sign(messageDigest, keySign);

        System.out.println(alice.getName());
        printMessageDigest(messageDigest);
        printDigitalSignature(signature);


    }

    private void printDESKey(User user) {
        System.out.println(user.getDESKeyString());
    }

    private void printRSAKeyPair(User user) {
        System.out.println(user.getRSAKeyPairString());
    }

    private void printMessageDigest(byte[] digest) {
        String str = "";
        try {
            str = new String(digest, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("Message digest of the plaintext using SHA-256 is:\n" + str);
    }

    private void printDigitalSignature(byte[] signature) {
        String str = "";
        try {
            str = new String(signature, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("Digitally signed message is:\n" + str);
    }
}
