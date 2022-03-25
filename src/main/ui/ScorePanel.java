package ui;

import model.Game;

import java.awt.*;


import javax.swing.JLabel;
import javax.swing.JPanel;


/*
 * Represents the panel in which the scoreboard is displayed.
 * Taken from SpaceInvaders
 */

public class ScorePanel extends JPanel {
    private static final String SCORE_TXT = "Score: ";
    private static final int LBL_WIDTH = Game.WIDTH;
    private static final int LBL_HEIGHT = 30;
    private Game game;
    private JLabel scoreLbl;


    // EFFECTS: sets the background colour and draws the initial labels;
    //          updates this with the game whose score is to be displayed
    public ScorePanel(Game game) {
        this.game = game;
        setBackground(new Color(31, 28, 28));
        scoreLbl = new JLabel(SCORE_TXT + game.getScore());
        scoreLbl.setFont(new Font("Impact", Font.PLAIN, 30));
        scoreLbl.setForeground(Color.green);
        scoreLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        add(scoreLbl);

    }

    // MODIFIES: this
    // EFFECTS:  updates number of invaders shot and number of missiles
    //           remaining to reflect current state of game
    public void update() {
        scoreLbl.setText(SCORE_TXT + game.getScore());
        repaint();
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
