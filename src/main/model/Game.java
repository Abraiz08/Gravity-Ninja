package model;




import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents the Game as a whole
 */

//some variable names are taken from the SnakeGame example provided to us
public class Game {

    public static final int TICKS_PER_SECOND = 60;

    private int ticker;
    private int secondsPassed;
    private final PlayerCharacter player;
    private final Gravity gravity;

    private final Set<Position> points = new HashSet<>();
    private List<Obstacle> obstacles = new ArrayList<>();

    private int score = 0;

    private boolean ended = false;
    private boolean canSpawnObstacle = true;

    private final int maxX;
    private final int maxY;

    public Game(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;

        gravity = new Gravity(-1);
        player = new PlayerCharacter(0, maxY);

        ticker = 0;

        points.add(generateRandomPosition());

        secondsPassed = 0;
    }

    /**
     * Progresses the game state and handles points
     */
    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    public void tick() {

        ticker++;

        applyGravity();

        detectObstacleCollision();

        moveAllObstacles();

        handlePoints();

        if (points.isEmpty()) {
            spawnPoints();
        }

        countSeconds();

        spawnObstacles();

    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    private void moveAllObstacles() {
        if (ticker % 5 == 0) {
            for (Obstacle obstacle : obstacles) {
                obstacle.move(TICKS_PER_SECOND);
            }
        }
    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    private void applyGravity() {
        if ((ticker % (TICKS_PER_SECOND / 20)) == 0) {
            player.getPulledByGravity(gravity.getGravDirection(), maxY);

        }
    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    private void countSeconds() {
        if (ticker > TICKS_PER_SECOND) {
            secondsPassed++;
            ticker = 0;
            obstacleGate();
        }
    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    public void obstacleGate() {
        if ((int)(secondsPassed % 1.5) == 0) {
            canSpawnObstacle = true;
        } else {
            canSpawnObstacle = false;
        }

    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    private void detectObstacleCollision() {
        for (Obstacle obstacle : obstacles) {
            if (player.hasCollided(obstacle.getPos())) {
                ended = true;
            }
        }
    }


    /**
     * Spawns a point into a valid position in the game
     */
    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
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
    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
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
    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    public boolean isValidPosition(Position pos) {
        return  !isOutOfBounds(pos)
                && !points.contains(pos)
                && !player.hasCollided(pos);
    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    public boolean isValidPositionForObstacle(Position pos) {
        return  isValidPosition(pos)
              && ((pos.getY() < 5)
              || (pos.getY() > (maxY - 5)
              || (pos.getX() < 5)
              || (pos.getX() > maxX - 5)));
    }

    /**
     * Checks for points that the player has collected,
     * increases score if point is collected
     */
    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
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

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    private void spawnObstacles() {
        double decider = 1 + ((Math.random() * 10) / 1.5);
        for (int i = 0; i < decider; i++) {
            spawnObstacle();
        }
    }

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
     */
    private void spawnObstacle() {
        Position pos = generateRandomPosition();

        while (! isValidPositionForObstacle(pos)) {
            pos = generateRandomPosition();
        }

        if (ticker == TICKS_PER_SECOND && canSpawnObstacle) {
            obstacles.add(new Obstacle(pos, maxX, maxY));

        }

    }


    /**
     * Generates a random position.
     * Guaranteed to be in bounds but not necessarily valid
     */
    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
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

    public List<Obstacle> getObstacles() {
        return obstacles;
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



