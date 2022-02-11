package model;

/**
 * Class representing the obstacles the player has to dodge
 */
public class Obstacle {

    private Position pos;
    private final int obstacleSpeed = 1;
    private int obstacleTicker;
    private String obstacleDirection;

    /*
     * REQUIRES: x and y coordinates should be greater than 0 and less
     * than maxX and maxY respectively,as defined in the Game class.
     * 0 <= pos.getX <= maxX
     * 0 <= pos.getY <= maxY
     * MODIFIES: this
     * EFFECTS: Constructs an obstacle with the position pos
     * Gives obstacleTicker a random value through randomizeObstacleSpeed()
     * Gives decider a random value through Math.random()
     * Calls decideObstacleDirection, which gives the obstacle a movement direction
     */
    public Obstacle(Position pos, int maxX, int maxY) {

        this.pos = new Position(pos.getX(), pos.getY());

        obstacleTicker = randomizeObstacleSpeed();

        double decider = Math.random();
        decideObstacleDirection(pos, maxX, maxY, decider);
    }

    /*
     * MODIFIES: this
     * EFFECTS: randomizes ot (obstacle ticker) using the formula "1 + ((int) (Math.random() * 10))",
     * but does not allow the ot to exceed 6
     */
    private int randomizeObstacleSpeed() {
        int ot = 1 + ((int) (Math.random() * 10));
        while (ot > 6) {
            ot = 1 + ((int) (Math.random() * 10));
        }
        return ot;
    }

    /*
     * REQUIRES: 0 <= pos.getX <= maxX
     * 0 <= pos.getY  <= maxY
     * MODIFIES: this
     * EFFECTS: Uses decider, along with the position in which the obstacle was constructed to decide
     * the direction in which the obstacle will move
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
     * REQUIRES: * 0 <= pos.getX <= maxX
     * 0 <= pos.getY  <= maxY
     * obstacleSpeed > 0
     * MODIFIES: this, game
     * EFFECTS: Moves the obstacle in the direction it is supposed to move, with the magnitude obstacleSpeed,
     * once every time (tps % obstacleTicker == 0), where tps is TICKS_PER_SECOND
     */
    public void move(int tps) {
        if (tps % obstacleTicker == 0) {

            if (obstacleDirection.equals("right")) {
                pos = new Position(
                        pos.getX() + obstacleSpeed,
                        pos.getY()
                );
            } else if (obstacleDirection.equals("left")) {
                pos = new Position(
                        pos.getX() - obstacleSpeed,
                        pos.getY()
                );
            } else if (obstacleDirection.equals("up")) {
                pos = new Position(
                        pos.getX(),
                        pos.getY() - obstacleSpeed
                );
            } else if (obstacleDirection.equals("down")) {
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
