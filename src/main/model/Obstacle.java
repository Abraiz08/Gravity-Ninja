package model;

import javafx.geometry.Pos;

/**
 * Class representing the obstacles the player has to dodge
 */
public class Obstacle {

    private Position pos;
    private int obstacleSpeed = 1;
    private int obstacleTicker;
    private String obstacleDirection;

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    public Obstacle(Position pos, int maxX, int maxY) {

        this.pos = new Position(pos.getX(), pos.getY());

        obstacleTicker = randomizeObstacleSpeed();

        double decider = Math.random();
        decideObstacleDirection(pos, maxX, maxY, decider);
    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    private int randomizeObstacleSpeed() {
        int speed = 1 + ((int) (Math.random() * 10));
        while (speed > 6) {
            speed = 1 + ((int) (Math.random() * 10));
        }
        return speed;
    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    @SuppressWarnings("methodlength")
    private void decideObstacleDirection(Position pos, int maxX, int maxY, double decider) {
        if ((pos.getY() < 5) && (pos.getX() < 5)) {
            if (decider < 0.5) {
                obstacleDirection = "right";
            } else {
                obstacleDirection = "down";
            }
        } else if ((pos.getY() > (maxY - 5)) && (pos.getX() < 5)) {
            if (decider < 0.5) {
                obstacleDirection = "right";
            } else {
                obstacleDirection = "up";
            }
        } else if ((pos.getY() > (maxY - 5)) && (pos.getX() > (maxX - 5))) {
            if (decider < 0.5) {
                obstacleDirection = "left";
            } else {
                obstacleDirection = "up";
            }
        } else if ((pos.getY() < 5) && (pos.getX() > (maxX - 5))) {
            if (decider < 0.5) {
                obstacleDirection = "left";
            } else {
                obstacleDirection = "down";
            }
        } else if (pos.getY() > (maxY - 5)) {
            obstacleDirection = "up";
        } else if (pos.getY() < 5) {
            obstacleDirection = "down";
        } else if (pos.getX() < 5) {
            obstacleDirection = "right";
        } else if (pos.getX() > (maxX - 5)) {
            obstacleDirection = "left";

        }
    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    public void move(int tps) {
        if (tps % obstacleTicker == 0) {

            if (obstacleDirection == "right") {
                pos = new Position(
                        pos.getX() + obstacleSpeed,
                        pos.getY()
                );
            } else if (obstacleDirection == "left") {
                pos = new Position(
                        pos.getX() - obstacleSpeed,
                        pos.getY()
                );
            } else if (obstacleDirection == "up") {
                pos = new Position(
                        pos.getX(),
                        pos.getY() - obstacleSpeed
                );
            } else if (obstacleDirection == "down") {
                pos = new Position(
                        pos.getX(),
                        pos.getY() + obstacleSpeed
                );
            }
        }
    }

    public String getObstacleDirection() {
        return obstacleDirection;
    }

    public Position getPos() {
        return pos;
    }
}
