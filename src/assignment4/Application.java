package assignment4;

public class Application {

    public static void main(String[] args) {
        Application app = new Application();
            app.init();
    }

    private void init() {
        User alice = new User("Alice");
        User bob = new User("Bob");

        alice.generateRSAKeyPair();
        bob.generateRSAKeyPair();

        System.out.println();

        System.out.println(alice.getName());
        printRSAKeyPair(alice);

        System.out.println();

        System.out.println(bob.getName());
        printRSAKeyPair(bob);

        System.out.println();

        alice.generateDESKey();
        System.out.println(alice.getName());
        printDESKey(alice);

        System.out.println();

        Message msg = new Message("Protect your network as if it would be a hotel " +
                "not as if it would be a castle");
        System.out.println(alice.getName() + ":");
        System.out.println("Plaintext: " + msg.getContent());
        msg.desEncrypt(alice.getDESKey());
        printDESKey(alice);
        System.out.println("Ciphertext: " + msg.getContent());

    }

    private void printDESKey(User user) {
        System.out.println(user.getDESKeyString());
    }
    private void printRSAKeyPair(User user) {
        System.out.println(user.getRSAKeyPairString());
    }

}
