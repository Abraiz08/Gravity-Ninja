package ui;

/**
 * Class used to run the game
 */
public class Main {

    /*
     * EFFECTS: Runs the game
     */
    public static void main(String[] args) throws Exception {
        // create and start the game
        TerminalGame gameHandler = new TerminalGame();

        gameHandler.start();
    }
}
