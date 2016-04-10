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

        printRSAKeyPair(alice);
        printRSAKeyPair(bob);




    }

    private void printRSAKeyPair(User user) {
        System.out.println(user.getName());
        System.out.println(user.getRSAKeyPairString());
    }

}
