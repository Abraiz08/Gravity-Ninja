package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Class used to run the game
 */
public class Main extends JFrame {
    private Game game;
    private TerminalGame tg;
    private ScorePanel sp;

    // EFFECTS: sets up window in which Space Invaders game will be played
    public Main() {
        super("GRAVITY NINJA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        game = new Game(tg.TERMINAL_WIDTH, tg.TERMINAL_HEIGHT);
        tg = new TerminalGame(game);
        sp = new ScorePanel(game);
        add(tg);
        add(sp, BorderLayout.NORTH);
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
        Timer t = new Timer(game.TICKS_PER_SECOND, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.tick();
                tg.repaint();
                sp.update();
            }
        });

        t.start();
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
            try {
                tg.handleUserInput(e.getKeyCode());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Play the game
    public static void main(String[] args) {
        new Main();
    }
}