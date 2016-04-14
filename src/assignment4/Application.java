package assignment4;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Application {
    Message msg;
    User alice, bob;
    RSACrypto rsa;

    public static void main(String[] args) {
        Application app = new Application();
        app.init();
    }

    private byte[] aliceEncryptDesKey(PublicKey bobPublicKey) {
        byte[] desKeyBytes = alice.getDESKey().getEncoded();

        System.out.println(alice.getName());
        System.out.println("Encrypts DES key with " + bob.getName() + "'s public key.");
        return rsa.encrypt(desKeyBytes, bobPublicKey);

    }

    private void init() {
        alice = new User("Alice");
        bob = new User("Bob");

        rsa = new RSACrypto();

        // 1 generate RSA key pairs
        aliceAndBobGenerateKeyPairs();

        // 2 Alice generates DES key

        aliceGenerateDESKey();

        // 3 Alice encrypts message
        aliceEncryptMessage();

        // 4 Alice digitally signs message
        aliceDigitallySignMessage();

        // 5 Alice encrypts DES key with Bob's public key
        PublicKey bobPublicKey = bob.getRsaKeyPair().getPublic();
        byte[] encryptedKey = aliceEncryptDesKey(bobPublicKey);

        // 5 Alice sends signed message and encrypted key to Bob
        System.out.println(alice.getName());
        System.out.println("Sends signed message and encrypted key to " + bob.getName());
        System.out.println("\n\n");
        System.out.println("......");
        System.out.println("\n\n");

        // 6 Bob verifies digital signature
        bobVerifyDigitalSignature();

        // 7 Bob decrypts DES key with RSA private key
        PrivateKey bobPrivateKey = bob.getRsaKeyPair().getPrivate();
        SecretKey key = bobDecryptDESKey(encryptedKey, bobPrivateKey);

        // 8 Bob decrypts message with DES key
        bobDecryptMessage(key);
    }

    private void bobDecryptMessage(SecretKey key) {
        System.out.println(bob.getName());
        printMessageContent(msg);
        System.out.println("Bob decrypts message...");
        msg.desDecrypt(key);
        printMessageContent(msg);
    }

    private SecretKey bobDecryptDESKey(byte[] encryptedKey, PrivateKey bobPrivateKey) {

        byte[] desKey = rsa.decrypt(encryptedKey, bobPrivateKey);
        SecretKey key = new SecretKeySpec(desKey, 0, desKey.length, "DES");
        System.out.println(bob.getName());
        System.out.println("The decrypted DES key is:\n" + Base64.encode(key.getEncoded()));

        System.out.println();
        return key;
    }

    private void bobVerifyDigitalSignature() {
        System.out.println(bob.getName());
        System.out.println("Verifies the digital signature...");

        byte[] recievedMessageDigest = msg.calculateMessageDigest();

        if (msg.verifyDigitalSignature(recievedMessageDigest, alice.getRsaKeyPair().getPublic())) {
            System.out.println("Message is signed by Alice!");
        } else {
            System.out.println("Message is not signed by Alice!");
        }
        System.out.println();
    }

    private void aliceDigitallySignMessage() {
        PrivateKey keySign = alice.getRsaKeyPair().getPrivate();
        byte[] messageDigest = msg.calculateMessageDigest();
        msg.sign(messageDigest, keySign);

        System.out.println(alice.getName());
        printMessageDigest(messageDigest);
        printDigitalSignature(msg);

        System.out.println();
    }

    private void aliceEncryptMessage() {
        msg = new Message("Protect your network as if it would be a hotel " +
                "not as if it would be a castle");
        System.out.println(alice.getName() + ":");
        System.out.println("Plaintext: " + msg.getContent());
        msg.desEncrypt(alice.getDESKey());
        printDESKey(alice);
        System.out.println("Ciphertext: " + msg.getContent());

        System.out.println();
    }

    private void aliceGenerateDESKey() {
        alice.generateDESKey();
        System.out.println(alice.getName());
        printDESKey(alice);

        System.out.println();
    }

    private void aliceAndBobGenerateKeyPairs() {
        alice.generateRSAKeyPair();
        bob.generateRSAKeyPair();

        System.out.println();

        System.out.println(alice.getName());
        printRSAKeyPair(alice);

        System.out.println();

        System.out.println(bob.getName());
        printRSAKeyPair(bob);

        System.out.println();
    }

    private void printDESKey(User user) {
        System.out.println(user.getDESKeyString());
    }

    private void printRSAKeyPair(User user) {
        System.out.println(user.getRSAKeyPairString());
    }

    private void printMessageDigest(byte[] digest) {
        String str = Base64.encode(digest);
        System.out.println("Message digest of the plaintext using SHA-256 is:\n" + str);
    }

    private void printDigitalSignature(Message msg) {
        System.out.println("Digitally signed message is:\n" + msg.getSignature());
    }

    private void printMessageContent(Message message) {
        System.out.println("Message is:\n" + message.getContent());
    }
}
