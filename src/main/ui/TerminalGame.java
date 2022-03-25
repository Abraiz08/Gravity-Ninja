package ui;

import model.Game;
import model.Obstacle;
import model.PlayerCharacter;
import model.Position;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;


/**
 * Class representing the user interface of the game
 */
public class TerminalGame extends JPanel {

    private static final String OVER = "Game Over!";
    private Game game;

    private static final String JSON_STORE = "./data/gameFiles.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);

    public TerminalGame(Game game) {
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(Color.BLACK);
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        render(g);

        if (game.isEnded()) {
            gameOver(g);

        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Renders the current screen.
     * draws the score, player, points and obstacles.
     */
    private void render(Graphics g) {

        drawPoints(g);
        drawPlayer(g);
        drawObstacles(g);
    }

    /*
     * MODIFIES: game
     * EFFECTS: Makes the player character perform a certain action depending on the KeyStroke inputted by
     * the user
     * Any input that isn't a character is passed to move()
     * 'x' flips the gravity of the game arena
     * ' ' causes the player to either jump, double jump, or do nothing based on a number of conditions
     * 's' saves the state of the game
     * 'l' loads the last save state from file
     */
    public void handleUserInput(int e) {


        if (e == KeyEvent.VK_RIGHT || e == KeyEvent.VK_LEFT) {
            game.getPlayer().move(e,game);

        } else if (e == KeyEvent.VK_X) {
            game.getGravity().flipGravity();




//double jump
        } else if (game.getPlayer().getPos().getY() != 0
                && game.getPlayer().getPos().getY() != game.getMaxY() - PlayerCharacter.HEIGHT
                && e == KeyEvent.VK_SPACE
                && game.getPlayer().getMaxJumps() != 0) {
            game.getPlayer().setMaxJumpsToZero();
            game.getPlayer().jump(game);

//jump
        } else if (e == KeyEvent.VK_SPACE
                && (game.getPlayer().getPos().getY() == 0
                || game.getPlayer().getPos().getY() == game.getMaxY() - PlayerCharacter.HEIGHT)) {
            game.getPlayer().jump(game);


        } else if (e == KeyEvent.VK_S) {
            saveGame();

        }

    }

    // Method taken from JsonSerializationDemo
    // EFFECTS: saves the game to file
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    /*
     * MODIFIES: this
     * EFFECTS: draws the player
     */
    //found in SpaceInvaders
    private void drawPlayer(Graphics g) {
        PlayerCharacter player = game.getPlayer();
        Color savedCol = g.getColor();
        g.setColor(PlayerCharacter.COLOR);
        g.fillRect(player.getPos().getX(),
                player.getPos().getY(),
                player.WIDTH,
                player.HEIGHT);
        g.setColor(savedCol);
    }

    /*
     * MODIFIES: this
     * EFFECTS: draws the points
     */
    //found in SpaceInvaders
    private void drawPoints(Graphics g) {
        for (Position points : game.getPoints()) {
            Color savedCol = g.getColor();
            g.setColor(Color.CYAN);
            g.fillOval(points.getX(),
                    points.getY(),
                    points.RADIUS,
                    points.RADIUS);
            g.setColor(savedCol);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: draws each obstacle in obstacles
     */
    //found in SpaceInvaders
    private void drawObstacles(Graphics g) {
        for (Obstacle obstacle : game.getObstacles()) {
            Color savedCol = g.getColor();
            if (obstacle.getObstacleDirection().equals("right")
                    ||
                    obstacle.getObstacleDirection().equals("left")) {
                g.setColor(Color.RED);
                g.fillRect(obstacle.getPos().getX(),
                        obstacle.getPos().getY(),
                        obstacle.WIDTH,
                        obstacle.HEIGHT);
            } else {
                g.setColor(Color.ORANGE);
                g.fillRect(obstacle.getPos().getX(),
                            obstacle.getPos().getY(),
                            obstacle.WIDTH,
                            obstacle.HEIGHT);
            }
            g.setColor(savedCol);
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws "game over" and replay instructions onto g
    //found in SpaceInvaders
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(255, 3, 3));
        g.setFont(new Font("Impact", 30, 50));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, Game.HEIGHT / 2);

        g.setColor(saved);
    }

    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position yPos
    //found in SpaceInvaders
    private void centreString(String str, Graphics g, FontMetrics fm, int ypos) {
        int width = fm.stringWidth(str);
        g.drawString(str, (Game.WIDTH - width) / 2, ypos);
    }

    public void setGame(Game game) {
        this.game = game;
    }
}

