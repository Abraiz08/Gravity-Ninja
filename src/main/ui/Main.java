package ui;

/**
 * Class used to run the game
 */
public class Main {

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    public static void main(String[] args) throws Exception {
// create and start the game
        TerminalGame gameHandler = new TerminalGame();

        gameHandler.start();
    }
}
