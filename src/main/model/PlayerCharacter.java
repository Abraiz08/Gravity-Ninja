package model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.awt.event.KeyEvent;

/**
 * Class representing the player character
 */
public class PlayerCharacter {

    private final int PLAYER_JUMP_HEIGHT = 10;
    private final int PLAYER_MOVEMENT_SPEED = 2;
    private final int MAX_JUMPS = 2;

    private Position pos;
    private Game game;
    private Gravity gravity;


    public Position move(KeyType key) {

//for x
        if (key == KeyType.ArrowLeft) {
            if (pos.getX() < game.getMaxX()) {

                return new Position(
                        updateX(pos.getX()),
                        pos.getY()
                );
            } else {
                return pos;
            }
//for y
        } else if (key == KeyType.ArrowRight) {
            if (pos.getY() < game.getMaxY()) {
                return new Position(
                        pos.getX(),
                        updateY(pos.getY())
                );
            } else {
                return pos;
            }
        } else {
            return pos;
        }

    }


    public int updateX(int x) {
        if ((x + PLAYER_MOVEMENT_SPEED) < game.getMaxX()) {
            return x + PLAYER_MOVEMENT_SPEED;
        } else {
            return x;
        }
    }

    public int updateY(int y) {
        if ((y + PLAYER_MOVEMENT_SPEED) < game.getMaxY()) {
            return y + PLAYER_MOVEMENT_SPEED;
        } else {
            return y;
        }
    }


    public void jump(){

    }



    public Position getPos() {
            return pos;
    }

}

