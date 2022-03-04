package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Game;
import model.Obstacle;
import model.PlayerCharacter;
import model.Position;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class representing the user interface of the game
 */
public class TerminalGame {
    private Game game;

    private Screen screen;
    private WindowBasedTextGUI endGui;

    private static final String JSON_STORE = "./data/gameFiles.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);


    /*
     * MODIFIES: this, game
     * EFFECTS: Begins the game and method does not leave execution
     * until game is complete.
     */
    //found in SnakeConsole-Lanterna Project
    public void start() throws IOException, InterruptedException {

        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        TerminalSize terminalSize = screen.getTerminalSize();

        game = new Game(
                terminalSize.getColumns(),
                // first row is reserved for us
                terminalSize.getRows() - 2
        );

        beginTicks();
    }

    /*
     * REQUIRES: TICKS_PER_SECOND > 0
     * EFFECTS: Begins the game cycle. Ticks once every Game.TICKS_PER_SECOND until
     * game has ended and the endGui has been exited.
     */
    //found in SnakeConsole-Lanterna Project
    private void beginTicks() throws IOException, InterruptedException {
        while (!game.isEnded() || endGui.getActiveWindow() != null) {
            tick();
            Thread.sleep(1000L / Game.TICKS_PER_SECOND);
        }

        System.exit(0);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Handles one cycle in the game by taking user input, calling resetDoubleJump(...) and
     * handleGravitating(...), ticking the game internally, and rendering the effects
     */
    private void tick() throws IOException {

        handleUserInput();

        game.getPlayer().resetDoubleJump(game);

        handleGravitating(game.getPlayer().getPos().getY());

        game.tick();

        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();
        render();
        screen.refresh();

        screen.setCursorPosition(new TerminalPosition(screen.getTerminalSize().getColumns() - 1, 0));
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
    //some code found in SnakeConsole-Lanterna Project
    private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();


        if (stroke == null) {
            return;
        }


        if (stroke.getCharacter() == null) {

            game.getPlayer().move(stroke.getKeyType(),game);

        } else if (stroke.getCharacter() == 'x') {
            game.getGravity().flipGravity();




//double jump
        } else if (game.getPlayer().getPos().getY() != 0
                && game.getPlayer().getPos().getY() != game.getMaxY()
                && stroke.getCharacter() == ' '
                && game.getPlayer().getMaxJumps() != 0) {
            game.getPlayer().setMaxJumpsToZero();
            game.getPlayer().jump(game);

//jump
        } else if (stroke.getCharacter() == ' '
                   && (game.getPlayer().getPos().getY() == 0
                   || game.getPlayer().getPos().getY() == game.getMaxY())) {
            game.getPlayer().jump(game);


        } else if (stroke.getCharacter() == 's') {
            saveGame();

        } else if (stroke.getCharacter() == 'l') {
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
    private void render() {
        if (game.isEnded()) {
            if (endGui == null) {
                drawEndScreen();
            }

            return;
        }

        drawScore();
        drawPoints();
        drawPlayer();
        drawObstacles();
    }

    /*
     * MODIFIES: this
     * EFFECTS: draws the end screen
     */
    //found in SnakeConsole-Lanterna Project
    private void drawEndScreen() {
        endGui = new MultiWindowTextGUI(screen);

        new MessageDialogBuilder()
                .setTitle("DEAD")
                .setText("Your Score: " + game.getScore())
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(endGui);
    }

    /*
     * MODIFIES: this
     * EFFECTS: draws the score
     */
    //found in SnakeConsole-Lanterna Project
    private void drawScore() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.GREEN);
        text.putString(1, 0, "Score: ");

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(8, 0, String.valueOf(game.getScore()));
    }

    /*
     * MODIFIES: this
     * EFFECTS: draws the player
     */
    //found in SnakeConsole-Lanterna Project
    private void drawPlayer() {
        PlayerCharacter player = game.getPlayer();
        drawPosition(player.getPos(), TextColor.ANSI.GREEN, '█');

    }

    /*
     * MODIFIES: this
     * EFFECTS: draws the points
     */
    //found in SnakeConsole-Lanterna Project
    private void drawPoints() {
        for (Position points : game.getPoints()) {
            drawPosition(points, TextColor.ANSI.CYAN, '◆');
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: draws each obstacle in obstacles
     */
    private void drawObstacles() {
        for (Obstacle obstacle : game.getObstacles()) {
            if (obstacle.getObstacleDirection().equals("right")
                    || obstacle.getObstacleDirection().equals("left")) {
                drawPosition(obstacle.getPos(), TextColor.ANSI.RED, '█');
            } else {
                drawPosition(obstacle.getPos(), TextColor.ANSI.YELLOW, '█');
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Draws a character in a given position on the terminal.
     */
    //found in SnakeConsole-Lanterna Project
    private void drawPosition(Position pos, TextColor color, char c) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(pos.getX(), pos.getY() + 1, String.valueOf(c));

    }


}

