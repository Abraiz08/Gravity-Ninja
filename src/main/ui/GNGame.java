package ui;

import model.Game;
import persistence.JsonReader;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.*;

/**
 * Class used to run the game
 */
public class GNGame extends JFrame {

    public static final int INTERVAL = 1000 / Game.TICKS_PER_SECOND;
    private Game game;
    private TerminalGame gamePanel;
    private ScorePanel scorePanel;
    private Timer timer;

    private static final String JSON_STORE = "./data/gameFiles.json";
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    public GNGame() {
        super("GRAVITY NINJA");
        setUndecorated(true);
        game = new Game();
        gamePanel = new TerminalGame(game);
        scorePanel  = new ScorePanel(game);
        add(gamePanel);
        add(scorePanel, BorderLayout.NORTH);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
    }




    // MODIFIES: none
    // EFFECTS:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        timer = new Timer(INTERVAL, ae -> {
            game.tick();
            gamePanel.repaint();
            scorePanel.update();
            checkGameEnded();

        });

        timer.start();
    }


    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    /*
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_L) {
                loadGame();
            }
            gamePanel.handleUserInput(e.getKeyCode());
        }
    }


    // Method taken from JsonSerializationDemo
    // MODIFIES: this, game
    // EFFECTS: loads game from file
    private void loadGame() {
        try {
            game = jsonReader.read();
            gamePanel.setGame(game);
            scorePanel.setGame(game);
            System.out.println("Loaded game from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    /*
     * EFFECTS: Runs the game
     */
    public static void main(String[] args) throws Exception {
        // create and start the game
        new GNGame();
    }

    public void checkGameEnded() {
        if (game.isEnded()) {
            timer.stop();
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
