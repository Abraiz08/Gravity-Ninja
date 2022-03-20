package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;

//taken from spaceinvaders
public class ScorePanel extends JPanel {
    private static final String SCORE_TXT = "Score: ";
    private static final int LBL_WIDTH = 200;
    private static final int LBL_HEIGHT = 30;
    private Game game;
    private JLabel scoreLbl;


    // EFFECTS: sets the background colour and draws the initial labels;
    //          updates this with the game whose score is to be displayed
    public ScorePanel(Game g) {
        game = g;
        setBackground(new Color(180, 180, 180));
        scoreLbl = new JLabel(SCORE_TXT + String.valueOf(game.getScore()));
        scoreLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        add(scoreLbl);
        add(Box.createHorizontalStrut(10));
    }

    // MODIFIES: this
    // EFFECTS:  updates number of invaders shot and number of missiles
    //           remaining to reflect current state of game
    public void update() {
        scoreLbl.setText(SCORE_TXT + String.valueOf(game.getScore()));
        repaint();
    }
}
