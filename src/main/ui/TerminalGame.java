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

import java.io.IOException;

/**
 * Class representing the user interface of the game
 */
public class TerminalGame {
    private Game game;

    private Screen screen;
    private WindowBasedTextGUI endGui;

    /**
     * Begins the game and method does not leave execution
     * until game is complete.
     */
    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
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

    /**
     * Begins the game cycle. Ticks once every Game.TICKS_PER_SECOND until
     * game has ended and the endGui has been exited.
     */
    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    private void beginTicks() throws IOException, InterruptedException {
        while (!game.isEnded() || endGui.getActiveWindow() != null) {
            tick();
            Thread.sleep(1000L / Game.TICKS_PER_SECOND);
        }

        System.exit(0);
    }

    /**
     * Handles one cycle in the game by taking user input,
     * ticking the game internally, and rendering the effects
     */
    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
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


    /**
     * Sets the snake's direction corresponding to the
     * user's keystroke
     */
    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
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


        } else if (stroke.getCharacter() == ' '
                   && (game.getPlayer().getPos().getY() == 0
                   || game.getPlayer().getPos().getY() == game.getMaxY())) {
            game.getPlayer().jump(game);


        }


    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    private void handleGravitating(int posY) {
        if (game.getGravity().getGravitating()  && (posY == 0
                || posY == game.getMaxY())) {

            game.getGravity().noLongerGravitating();
        }
    }

    /**
     * Renders the current screen.
     * Draws the end screen if the game has ended, otherwise
     * draws the score, player, points and obstacles.
     */
    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
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
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
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
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    private void drawScore() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.GREEN);
        text.putString(1, 0, "Score: ");

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(8, 0, String.valueOf(game.getScore()));
    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    private void drawPlayer() {
        PlayerCharacter player = game.getPlayer();
        drawPosition(player.getPos(), TextColor.ANSI.GREEN, '█');

    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    private void drawPoints() {
        for (Position points : game.getPoints()) {
            drawPosition(points, TextColor.ANSI.CYAN, '◆');
        }
    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
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



    /**
     * Draws a character in a given position on the terminal.
     */
    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    private void drawPosition(Position pos, TextColor color, char c) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(pos.getX(), pos.getY() + 1, String.valueOf(c));

    }


}

