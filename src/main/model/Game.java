package model;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents the Game as a whole
 */

//some variable names are taken from the SnakeGame example provided to us
public class Game {

    public static final int TICKS_PER_SECOND = 60;

    private int ticker;
    private final PlayerCharacter player;
    private final Gravity gravity;

    private final Set<Position> points = new HashSet<>();
    private int score = 0;

    private boolean ended = false;

    private final int maxX;
    private final int maxY;

    public Game(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;

        gravity = new Gravity(-1);
        player = new PlayerCharacter(0, maxY);

        ticker = 0;

        points.add(generateRandomPosition());
    }

    /**
     * Progresses the game state and handles points
     */

    public void tick() {
        ticker++;
        /*
        if (player.hasCollidedWithObstacle()) {
            ended = true;
        }
         */


        if ((ticker % (TICKS_PER_SECOND / 20)) == 0) {
            player.pull(gravity.getGravDirection(), maxY);
            ticker = 0;
        }

        handlePoints();

        if (points.isEmpty()) {
            spawnPoints();

        }

        if (ticker > TICKS_PER_SECOND) {
            ticker = 0;
        }
    }



    /**
     * Spawns a point into a valid position in the game
     */

    public void spawnPoints() {

        Position pos = generateRandomPosition();

        while (! isValidPosition(pos)) {
            pos = generateRandomPosition();
        }

        points.add(pos);
    }


    /**
     * Returns whether a given position is
     * out of the game frame
     */

    public boolean isOutOfBounds(Position pos) {
        return pos.getX() < 0
                || pos.getY() < 0
                || pos.getX() > maxX
                || pos.getY() > maxY;
    }

    /**
     * Returns whether a given position is in bounds
     * and not already occupied
     */
    public boolean isValidPosition(Position pos) {
        return  !isOutOfBounds(pos)
                && !points.contains(pos)
                && !player.hasCollided(pos);
    }

    /**
     * Checks for points that the player has collected,
     * increases score if point is collected
     */
    private void handlePoints() {
        Position collectedPoints = points.stream()
                .filter(player::hasCollided)
                .findFirst()
                .orElse(null);

        if (collectedPoints == null) {
            return;
        }

        points.remove(collectedPoints);
        score++;

    }

    /**
     * Generates a random position.
     * Guaranteed to be in bounds but not necessarily valid
     */
    private Position generateRandomPosition() {
        return new Position(
                ThreadLocalRandom.current().nextInt(maxX),
                ThreadLocalRandom.current().nextInt(maxY)
        );
    }

    public PlayerCharacter getPlayer() {
        return player;
    }


    public Set<Position> getPoints() {
        return points;
    }

    public int getScore() {
        return score;
    }

    public Gravity getGravity() {
        return gravity;
    }

    public boolean isEnded() {
        return ended;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
}



