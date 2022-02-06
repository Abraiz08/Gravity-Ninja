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
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.sun.xml.internal.bind.v2.TODO;
import model.Game;
import model.PlayerCharacter;
import model.Position;

import java.io.IOException;

public class TerminalGame {
    private Game game;

    private Screen screen;
    private WindowBasedTextGUI endGUI;


    /**
     * Begins the game and method does not leave execution
     * until game is complete.
     */
    public void start() throws IOException, InterruptedException {
        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        TerminalSize terminalSize = screen.getTerminalSize();

        game = new Game(

                terminalSize.getColumns() - 1,
                // first row is reserved for us
                terminalSize.getRows() - 2
        );

        beginTicks();
    }

    /**
     * Begins the game cycle. Ticks once every Game.TICKS_PER_SECOND until
     * game has ended and the endGui has been exited.
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
    private void tick() throws IOException {
        handleUserInput();

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
    private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();

        if (stroke == null) {
            return;
        }

        if (stroke.getCharacter() != null) {
            return;
        }

        //TODO
        //Implement moving
        if (stroke.getCharacter() == ' ') {
            game.getPlayer().jump();
        } else if (stroke.getCharacter() == 'x') {
            game.getGravity().flipGravity();
        } else {
            game.getPlayer().move(stroke.getKeyType());

        }
    }

    /**
     * Renders the current screen.
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
        drawPlayer();
        drawPoints();
        drawObstacles();
    }

    private void drawEndScreen() {
        endGui = new MultiWindowTextGUI(screen);

        new MessageDialogBuilder()
                .setTitle("DEAD")
                .setText("Your Score" + game.getScore() + "!")
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(endGui);
    }

    private void drawScore() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.GREEN);
        text.putString(1, 0, "Score: ");

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(8, 0, String.valueOf(game.getScore()));
    }

    private void drawPlayer() {
        PlayerCharacter player = game.getPlayer();

        drawPosition(player.getPosition(), TextColor.ANSI.GREEN, '\u2588', true);

    }

    private void drawPoints() {
        for (Position points : game.getPoints()) {
            drawPosition(points, TextColor.ANSI.RED, '\u2B24', false);
        }
    }

    private void drawObstacles() {
        for (Position obstacles : game.getObstacles()) {
            drawPosition(obstacles, TextColor.ANSI.RED, '\u2B24', false);
        }
    }

    /**
     * Draws a character in a given position on the terminal.
     */
    private void drawPosition(Position pos, TextColor color, char c, boolean wide) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(pos.getX() , pos.getY() + 1, String.valueOf(c));

}

