package ui;

import model.Game;
import model.Obstacle;
import model.PlayerCharacter;
import model.Position;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class representing the user interface of the game
 */
public class TerminalGame extends JPanel {
    private Game game;

    public static final int TERMINAL_WIDTH = 1200;
    public static final int TERMINAL_HEIGHT = 800;

    // private Screen screen;
    //private WindowBasedTextGUI endGui;

    private static final String JSON_STORE = "./data/gameFiles.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    /*
     * MODIFIES: this, game
     * EFFECTS: Begins the game and method does not leave execution
     * until game is complete.
     */
    public TerminalGame(Game game) {

        setPreferredSize(new Dimension(TERMINAL_WIDTH, TERMINAL_HEIGHT));
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
     * MODIFIES: game
     * EFFECTS: Makes the player character perform a certain action depending on the KeyStroke inputted by
     * the user
     * Any input that isn't a character is passed to move()
     * 'x' flips the gravity of the game arena
     * ' ' causes the player to either jump, double jump, or do nothing based on a number of conditions
     * 's' saves the state of the game
     * 'l' loads the last save state from file
     */
    public void handleUserInput(int e) throws IOException {


        if (e == KeyEvent.VK_RIGHT || e == KeyEvent.VK_LEFT) {
            game.getPlayer().move(e,game);

        } else if (e == KeyEvent.VK_X) {
            game.getGravity().flipGravity();




//double jump
        } else if (game.getPlayer().getPos().getY() != 0
                && game.getPlayer().getPos().getY() != game.getMaxY()
                && e == KeyEvent.VK_SPACE
                && game.getPlayer().getMaxJumps() != 0) {
            game.getPlayer().setMaxJumpsToZero();
            game.getPlayer().jump(game);

//jump
        } else if (e == KeyEvent.VK_SPACE
                   && (game.getPlayer().getPos().getY() == 0
                   || game.getPlayer().getPos().getY() == game.getMaxY())) {
            game.getPlayer().jump(game);


        } else if (e == KeyEvent.VK_S) {
            saveGame();

        } else if (e == KeyEvent.VK_L) {
            loadGame();
        }


    }

    // Method taken from JsonSerializationDemo
    // EFFECTS: saves the game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    // Method taken from JsonSerializationDemo
    // MODIFIES: this, game
    // EFFECTS: loads game from file
    private void loadGame() {
        try {
            game = jsonReader.read();
            System.out.println("Loaded game from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    /*
     * MODIFIES: game
     * EFFECTS: Sets gravitating to false once the user touches the floor or ceiling
     * of the game arena
     */
    //move
    private void handleGravitating(int posY) {
        if (game.getGravity().getGravitating()  && (posY == 0
                || posY == game.getMaxY())) {

            game.getGravity().noLongerGravitating();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Renders the current screen.
     * Draws the end screen if the game has ended, otherwise
     * draws the score, player, points and obstacles.
     */
    private void render(Graphics g) {

        drawPoints(g);
        drawPlayer(g);
        drawObstacles(g);
    }


    /*
     * MODIFIES: this
     * EFFECTS: draws the player
     */
    //found in SnakeConsole-Lanterna Project
    private void drawPlayer(Graphics g) {
        PlayerCharacter player = game.getPlayer();
        Color savedCol = g.getColor();
        g.setColor(Color.GREEN);
        g.fillRect(player.getPos().getX(),
                player.getPos().getY(),
                player.SIZE_X, player.SIZE_Y);
        g.setColor(savedCol);

    }

    /*
     * MODIFIES: this
     * EFFECTS: draws the points
     */
    private void drawPoints(Graphics g) {
        for (Position points : game.getPoints()) {
            g.fillOval(points.getX(), points.getY(), 5, 5);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: draws all obstacle in obstacles
     */
    private void drawObstacles(Graphics g) {
        for (Obstacle obstacle : game.getObstacles()) {
            if (obstacle.getObstacleDirection().equals("right")
                    || obstacle.getObstacleDirection().equals("left")) {
                drawObstacleHorizontal(g, obstacle);
            } else {
                drawObstacleVertical(g, obstacle);
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: draws a horizontal obstacle in obstacles
     */
    private void drawObstacleHorizontal(Graphics g, Obstacle obstacle) {
        Color savedCol = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(obstacle.getPos().getX(),
                obstacle.getPos().getY(),
                obstacle.SIZE_X, obstacle.SIZE_Y);
        g.setColor(savedCol);
    }

    /*
     * MODIFIES: this
     * EFFECTS: draws a vertical obstacle in obstacles
     */
    private void drawObstacleVertical(Graphics g, Obstacle obstacle) {
        Color savedCol = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(obstacle.getPos().getX() - obstacle.SIZE_X / 2,
                obstacle.getPos().getY() - obstacle.SIZE_Y / 2,
                obstacle.SIZE_X, obstacle.SIZE_Y);
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS:  draws "game over" and replay instructions onto g
    // taken from spaceinvaders
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString("Game Over", g, fm, game.getMaxY() / 2);
        g.setColor(saved);
    }

    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position yPos
    // taken from spaceinvaders
    private void centreString(String str, Graphics g, FontMetrics fm, int ypos) {
        int width = fm.stringWidth(str);
        g.drawString(str, (game.getMaxX() - width) / 2, ypos);
    }
}

