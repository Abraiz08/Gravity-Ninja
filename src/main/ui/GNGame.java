package ui;

import model.Game;
import persistence.JsonReader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private SplashScreen splashScreen;

    private JMenuBar menuBar;
    JMenu file = new JMenu("File");
    JMenuItem save = new JMenuItem("Save");
    JMenuItem load = new JMenuItem("Load");
    JMenu exit = new JMenu("Exit");
    JMenuItem close = new JMenuItem("Close Game");


    private static final String JSON_STORE = "./data/gameFiles.json";
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    /*
     * MODIFIES: this
     * EFFECTS: Constructs a game panel and renders all graphics
     */
    public GNGame() {
        super("GRAVITY NINJA");
        setUndecorated(true);

        splashScreen = new SplashScreen();
        putSplashScreen();

        game = new Game();
        gamePanel = new TerminalGame(game);
        scorePanel  = new ScorePanel(game);
        createMenuBar();
        setJMenuBar(menuBar);
        add(gamePanel);
        add(scorePanel, BorderLayout.NORTH);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        addTimer();
    }

    /*
    EFFECTS: Displays the splash screen
     */
    private void putSplashScreen() {
        add(splashScreen);
        pack();
        centreOnScreen();
        setVisible(true);
        try {
            Thread.sleep(5000);
            remove(splashScreen);
        } catch (Exception e) {
            System.out.println("Startup Failed");
        }
    }

    /* MODIFIES: none
     * EFFECTS:  initializes a timer that updates game each INTERVAL
     */
    //taken from SpaceInvaders
    private void addTimer() {
        timer = new Timer(INTERVAL, ae -> {
            game.tick();
            gamePanel.repaint();
            scorePanel.update();
            checkGameEnded();

        });

        timer.start();
    }

    /*
    MODIFIES: this
    EFFECTS: Constructs a menuBar
     */
    public void createMenuBar() {
        menuBar = new JMenuBar();

        menuBar.add(file);
        menuBar.add(exit);

        file.add(save);
        file.add(load);

        exit.add(close);

        createActionListeners();

    }

    /*
    MODIFIES: this, game
    EFFECTS: Creates action listeners for the menu bar
     */
    public void createActionListeners() {
        class SaveAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                gamePanel.saveGame();

            }
        }

        class LoadAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        }

        class CloseAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }

        save.addActionListener(new SaveAction());
        load.addActionListener(new LoadAction());
        close.addActionListener(new CloseAction());


    }


    /*
     * MODIFIES: this
     * EFFECTS:  location of frame is set so frame is centred on desktop
     */
    //taken from SpaceInvaders
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    /*
     * A key handler to respond to key events
     */
    //taken from SpaceInvaders
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_L) {
                loadGame();
            }
            gamePanel.handleUserInput(e.getKeyCode());
        }
    }



    /*
     * MODIFIES: this, game
     * EFFECTS: loads game from file
     */
    // Method taken from JsonSerializationDemo
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

    /*
     * EFFECTS: Checks if the game has ended or not
     */
    public void checkGameEnded() {
        if (game.isEnded()) {
            timer.stop();
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }
}

