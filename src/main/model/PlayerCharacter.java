package model;

import com.googlecode.lanterna.input.KeyType;

/**
 * Class representing the player character
 */
public class PlayerCharacter {

    private final int playerJumpHeight = 10;
    private final int playerMovementSpeed = 2;
    private final int maxJumps = 2;

    private Position pos;



    public PlayerCharacter(int x, int y) {
        pos = new Position(x, y);
    }


    public void move(KeyType key, Game game) {

        int x = game.getPlayer().getPos().getX();
        int y = game.getPlayer().getPos().getY();

//for x
        if (key == KeyType.ArrowLeft) {
            if (x > 0) {
                pos = new Position(
                        updateX(x, game, -1),
                        y
                );
            } else {
                pos = game.getPlayer().getPos();
            }
//for y
        } else if (key == KeyType.ArrowRight) {
            if (x < game.getMaxX()) {
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


    public int updateX(int x, Game game, int direction) {
        switch (direction) {
            case 1:
                if ((x + (direction * playerMovementSpeed)) < game.getMaxX()) {
                    return x + (direction * playerMovementSpeed);

                } else {
                    return x;
                }

            case -1:
                if ((x + (direction * playerMovementSpeed)) > 0) {
                    return x + (direction * playerMovementSpeed);
                } else {
                    return 0;
                }
            default:
                return x;
        }

    }



    public void jump(Game game) {

        if (pos.getY() == 0) {
            pos = new Position(
                    pos.getX(),
                    pos.getY() + 5
            );
        } else if (pos.getY() == game.getMaxY()) {
            pos = new Position(
                    pos.getX(),
                    pos.getY() - 5
            );
        }

    }

    public void pull(int gravDirection, int maxY) {

        switch (gravDirection) {

            case 1:

                if (pos.getY() != 0) {
                    pos = new Position(
                            pos.getX(),
                            pos.getY() - 1
                    );
                }

            case -1:


                if (pos.getY() != maxY) {
                    pos = new Position(
                           pos.getX(),
                            pos.getY() + 1
                    );
                }



        }
    }

    public boolean hasCollided(Position p) {
        return pos.equals(p);
    }



    public Position getPos() {
        return pos;
    }

}

