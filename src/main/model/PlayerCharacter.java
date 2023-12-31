package model;

import org.json.JSONObject;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Class representing the player character
 */
public class PlayerCharacter {

    public static final Color COLOR = Color.GREEN;
    public static final int WIDTH = 15;
    public static final int HEIGHT = 20;


    private final int playerJumpHeight = 40;
    private final int playerMovementSpeed = 3;
    private final int playerGravSpeed = 5;
    private int maxJumps = 1;

    private Position pos;

    /*
     * REQUIRES: int x and int y should be greater than 0 and less
     * than maxX and maxY respectively,as defined in the Game class.
     * 0 <= x <= maxX
     * 0 <= y <= maxY
     * EFFECTS: Constructs a PlayerCharacter with its X coordinate
     * equal to x and its Y coordinate equal to y
     */
    public PlayerCharacter(int x, int y) {
        pos = new Position(x, y);
    }

    /*
     * REQUIRES: 0 <= x <= maxX
     * 0 <= y <= maxY
     * MODIFIES: this, game
     * EFFECTS: Moves the player left and right on the screen depending on the arrow key pressed
     */
    public void move(int key, Game game) {

        int x = game.getPlayer().getPos().getX();
        int y = game.getPlayer().getPos().getY();

//for x
        if (key == KeyEvent.VK_LEFT) {
            if (x > 0) {
                pos = new Position(
                        updateX(x, game, -1),
                        y
                );
            } else {
                pos = game.getPlayer().getPos();
            }
//for y
        } else if (key == KeyEvent.VK_RIGHT) {
            if (x < game.getMaxX() - PlayerCharacter.WIDTH / 2) {
                pos = new Position(
                        updateX(x, game, 1),
                        y
                );
            } else {
                pos = game.getPlayer().getPos();
            }
        } else {
            pos = game.getPlayer().getPos();
        }

    }

    /*
     * REQUIRES: int x should be greater than 0 and less
     * than maxX, as defined in the Game class.
     * 0 <= x <= maxX
     * playerMovementSpeed > 0
     * MODIFIES: this, game
     * EFFECTS: Updates the x position of the player on the game screen depending on the direction
     * passed by move and the playerMovementSpeed
     */
    public int updateX(int x, Game game, int direction) {
        if (direction == 1) {
            if ((x + (direction * playerMovementSpeed)) < game.getMaxX()) {
                return x + (direction * playerMovementSpeed);

            } else {
                return x;
            }

        } else {
            if ((x + (direction * playerMovementSpeed)) > 0) {
                return x + (direction * playerMovementSpeed);
            } else {
                return 0;
            }

        }

    }

    /*
     * REQUIRES: playerJumpHeight > 0
     * MODIFIES: this, game
     * EFFECTS: Allows the player to jump and double jump depending on the playerJumpHeight and the
     * direction of gravity
     */
    public void jump(Game game) {

        if (pos.getY() == 0) {
            pos = new Position(
                    pos.getX(),
                    pos.getY() + playerJumpHeight
            );

        } else if (pos.getY() == game.getMaxY() - PlayerCharacter.HEIGHT) {
            pos = new Position(
                    pos.getX(),
                    pos.getY() - playerJumpHeight
            );

        } else if (game.getGravity().getGravDirection() == -1) {
            pos = new Position(
                    pos.getX(),
                    pos.getY() - playerJumpHeight
            );

        } else if (game.getGravity().getGravDirection() == 1) {
            pos = new Position(
                    pos.getX(),
                    pos.getY() + playerJumpHeight
            );


        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Sets maxJumps to 1 once the player touches the floor or ceiling after gravitating,
     *  resetting the double jump and allowing the player to execute it again.
     */
    public void resetDoubleJump(Game game) {
        if ((game.getPlayer().getPos().getY() == 0
                || game.getPlayer().getPos().getY() == game.getMaxY() - PlayerCharacter.HEIGHT)
                && game.getGravity().getGravitating() == false) {
            game.getPlayer().setMaxJumpsToOne();
        }
    }

    /*
     * REQUIRES: 0 <= pos.getX() <= maxX
     * 0 <= pos.getY() <= maxY
     * MODIFIES: this, game
     * EFFECTS: Pulls the player to the floor or ceiling, depending on the direction of gravity
     */
    public void getPulledByGravity(int gravDirection, int maxY) {

        if (gravDirection == 1) {
            if (pos.getY() != 0) {

                pos = new Position(
                        pos.getX(),
                        pos.getY() - playerGravSpeed
                );

            }
        } else {

            if (pos.getY() != maxY - PlayerCharacter.HEIGHT) {

                pos = new Position(
                        pos.getX(),
                        pos.getY() + playerGravSpeed
                    );

            }
        }
    }

    /*
     * EFFECTS: returns true if the position of the player is lies within the position of the object passed as
     * an argument
     */
    public boolean hasCollidedWithObstacle(Position p) {
        Rectangle o = new Rectangle(p.getX(), p.getY(), Obstacle.WIDTH, Obstacle.HEIGHT);
        Rectangle player = new Rectangle(pos.getX(), pos.getY(), WIDTH, HEIGHT);
        return player.intersects(o);

    }

    /*
     * EFFECTS: returns true if the position passed lies within the position of the player
     */
    public boolean hasCollided(Position p) {

        return (pos.getX() - WIDTH <= p.getX() && (pos.getX() + WIDTH)  >= p.getX()
                &&
             pos.getY() <= p.getY() && (pos.getY() + HEIGHT) >= p.getY());

    }

    /*
     * MODIFIES: json
     * EFFECTS: adds x and y position of player to json object
     */
    public JSONObject toJson(JSONObject json) {
        json.put("X-pos", getPos().getX());
        json.put("Y-pos", getPos().getY());
        return json;
    }

    public void setMaxJumpsToZero() {
        maxJumps = 0;
    }



    public void setMaxJumpsToOne() {
        maxJumps = 1;
    }

    public Position getPos() {
        return pos;
    }

    public int getMaxJumps() {
        return maxJumps;
    }

    public int getPlayerGravSpeed() {
        return playerGravSpeed;
    }

    public int getPlayerMovementSpeed() {
        return playerMovementSpeed;
    }

    public int getPlayerJumpHeight() {
        return playerJumpHeight;
    }

}

