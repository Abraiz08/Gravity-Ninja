package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Class representing the obstacles the player has to dodge
 */
public class Obstacle implements Writable {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 20;

    private Position pos;
    private final int obstacleSpeed;
    private double decider;
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

        obstacleSpeed = randomizeObstacleSpeed();

        randomizeDecider();
        decideObstacleDirection(pos, maxX, maxY, decider);
    }

    /*
     * MODIFIES: this
     * EFFECTS: randomizes decider using Math.random()
     */
    public void randomizeDecider() {
        decider = Math.random();
    }

    /*
     * MODIFIES: this
     * EFFECTS: randomizes os (obstacle speed) using the formula "1 + ((int) (Math.random() * 10))",
     * but does not allow the os to exceed 6
     */
    public int randomizeObstacleSpeed() {
        int os = 1 + ((int) (Math.random() * 10));
        while (os > 6) {
            os = 1 + ((int) (Math.random() * 10));
        }
        return os;
    }

    /*
     * REQUIRES: 0 <= pos.getX <= maxX
     * 0 <= pos.getY  <= maxY
     * MODIFIES: this
     * EFFECTS: Uses decider, along with the position in which the obstacle was constructed to decide
     * the direction in which the obstacle will move
     */
    @SuppressWarnings("methodlength")
    public void decideObstacleDirection(Position pos, int maxX, int maxY, double decider) {
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
    public void move() {

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

    /*
     *EFFECTS: returns obstacle as a json object
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Position", positionToJson());
        json.put("Obstacle Speed", obstacleSpeed);
        json.put("Decider", decider);
        json.put("Obstacle Direction", obstacleDirection);

        return json;
    }

    /*
     * EFFECTS: returns obstacle position as a json object
     */
    public JSONObject positionToJson() {
        JSONObject json = new JSONObject();
        json = pos.toJson(json);
        return json;
    }


    public Position getPos() {
        return pos;
    }

    public String getObstacleDirection() {
        return obstacleDirection;
    }

    public double getDecider() {
        return decider;
    }

    public void setDecider(double decider) {
        this.decider = decider;
    }

    public int getObStacleSpeed() {
        return obstacleSpeed;
    }

    public void setObstacleDirection(String obstacleDirection) {
        this.obstacleDirection = obstacleDirection;
    }
}
