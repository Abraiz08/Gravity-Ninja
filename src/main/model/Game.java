package model;




import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;

import java.util.concurrent.ThreadLocalRandom;

import persistence.Writable;

/**
 * Represents the Game as a whole
 */

//some variable names are taken from the SnakeGame example provided to us
public class Game implements Writable {

    public static final int TICKS_PER_SECOND = 60;

    private int ticker;
    private int secondsPassed;
    private PlayerCharacter player;
    private final Gravity gravity;

    private List<Position> points = new ArrayList<>();
    private List<Obstacle> obstacles = new ArrayList<>();

    private int score = 0;

    private boolean ended = false;
    private boolean canSpawnObstacle = true;

    private final int maxX;
    private final int maxY;

    private double decider;

    /*
     * MODIFIES: this
     * EFFECTS: maxX and maxY are set to the biggest values of x and y on the terminal
     * An object of type Gravity, gravity is created and the gravDirection is set to be -1
     * An object of type PlayerCharacter, player is created and its position is set to be (0, maxY)
     * ticker is given a value of 0
     * A point is added to a random valid position on the game screen
     * secondsPassed is given a value of 0
     */
    public Game(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;

        gravity = new Gravity(-1);
        player = new PlayerCharacter(0, maxY);

        ticker = 0;

        points.add(generateRandomPosition());

        secondsPassed = 0;
    }


    /*
     * MODIFIES: this
     * EFFECTS: Performs a number of actions for each tick in the game and progresses the game state
     * ticker is incremented by 1 each tick
     * gravity is applied to the player to pull them up or down
     * Detects if the player has collided with any obstacles on the screen
     * Every obstacle in obstacles is moved depending on its speed and direction
     * Handles the points collected, and adds the points to the score
     * If there are no points on the screen, a new point is spawned on the game screen
     * Counts how many seconds have passed since the game started
     * Spawns obstacles for the player to dodge on the game screen.
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
     * MODIFIES: this
     * EFFECTS: Moves every obstacle in the list obstacles once every 5 ticks
     */
    public void moveAllObstacles() {
        if (ticker % 5 == 0) {
            for (Obstacle obstacle : obstacles) {
                obstacle.move(TICKS_PER_SECOND);
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Applies the effect of gravity on the player once every 3 ticks
     */
    public void applyGravity() {
        if ((ticker % 3) == 0) {
            player.getPulledByGravity(gravity.getGravDirection(), maxY);

        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Counts the number of seconds passed since the game started
     * Resets the ticker to 0 every second
     */
    public void countSeconds() {
        if (ticker > TICKS_PER_SECOND) {
            secondsPassed++;
            ticker = 0;
            obstacleGate();
        }
    }


    /*
     * MODIFIES: this
     * EFFECTS: Prevents obstacles from being spawned in the arena continuously by setting canSpawnObstacle to false,
     * instead limiting the spawning to approximately once every 1.5 seconds
     */
    public void obstacleGate() {
        if ((int)(secondsPassed % 1.5) == 0) {
            canSpawnObstacle = true;
        } else {
            canSpawnObstacle = false;
        }

    }

    /*
     * MODIFIES: this
     * EFFECTS: Checks if the player has collided with (has the same position as) any obstacle
     * in the list obstacles. If true, the game is ended.
     */
    public void detectObstacleCollision() {
        for (Obstacle obstacle : obstacles) {
            if (player.hasCollided(obstacle.getPos())) {
                ended = true;
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Spawns a point into a valid position in the game
     */
    //found in SnakeConsole-Lanterna Project
    public void spawnPoints() {

        Position pos = generateRandomPosition();

        while (! isValidPosition(pos)) {
            pos = generateRandomPosition();
        }

        points.add(pos);
    }

    /*
     * EFFECTS: Returns whether a given position is
     * out of the game frame
     */
    //found in SnakeConsole-Lanterna Project
    public boolean isOutOfBounds(Position pos) {
        return pos.getX() < 0
                || pos.getY() < 0
                || pos.getX() > maxX
                || pos.getY() > maxY;
    }


    /*
     * EFFECTS: Returns whether a given position is in bounds
     * and not already occupied
     */
    //found in SnakeConsole-Lanterna Project
    public boolean isValidPosition(Position pos) {
        return  !isOutOfBounds(pos)
                && !points.contains(pos)
                && !player.hasCollided(pos);
    }

    /*
     * EFFECTS: Returns whether a given position is in bounds, not already occupied,
     * and valid for an obstacle to spawn in. A position is valid for an obstacle to spawn in if
     * it's near the edges of the game frame.
     */
    public boolean isValidPositionForObstacle(Position pos) {
        return  isValidPosition(pos)
              && ((pos.getY() < 5)
              || (pos.getY() > (maxY - 5)
              || (pos.getX() < 5)
              || (pos.getX() > maxX - 5)));
    }

    /*
     * MODIFIES: this
     * EFFECTS: Checks for points that the player has collected,
     * increases score if point is collected
     */
    //found in SnakeConsole-Lanterna Project
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
     * MODIFIES: this
     * EFFECTS: Spawns a random number of obstacles based on the formula
     * 1 + ((Math.random() * 10) / 1.5)
     */
    public void spawnObstacles() {
        decider = 1 + ((Math.random() * 10) / 1.5);
        for (int i = 0; i < decider; i++) {
            spawnObstacle();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Spawns an obstacle in a valid position on the game screen, if ticker == TICKS_PER_SECOND and
     * canSpawnObstacles is true
     */
    public void spawnObstacle() {
        Position pos = generateRandomPosition();

        while (! isValidPositionForObstacle(pos)) {
            pos = generateRandomPosition();
        }

        if (ticker == TICKS_PER_SECOND && canSpawnObstacle) {
            obstacles.add(new Obstacle(pos, maxX, maxY));

        }

    }

    /*
     * EFFECTS: Generates a random position.
     * Guaranteed to be in bounds but not necessarily valid
     */
    //found in SnakeConsole-Lanterna Project
    private Position generateRandomPosition() {
        return new Position(
                ThreadLocalRandom.current().nextInt(maxX),
                ThreadLocalRandom.current().nextInt(maxY)
        );
    }

    // MODIFIES: this
    // EFFECTS: saves the game state as a JSON file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ticker", ticker);
        json.put("Seconds passed", secondsPassed);
        json.put("PlayerCharacter", playerToJson());
        json.put("Gravity", gravityToJson());
        json.put("Points", points);
        json.put("Obstacles", obstaclesToJson());
        json.put("Score", score);
        json.put("Ended", ended);
        json.put("CanSpawnObstacles", canSpawnObstacle);
        json.put("MaxX", maxX);
        json.put("MaxY", maxY);
        json.put("Decider", decider);
        return json;
    }

    // MODIFIES: this
    // EFFECTS: returns player as a JSON object
    public JSONObject playerToJson() {
        JSONObject json = new JSONObject();
        json = player.toJson(json);
        return json;
    }

    // MODIFIES: this
    // EFFECTS: returns gravity as a JSON object
    public JSONObject gravityToJson() {
        JSONObject json = new JSONObject();
        json = gravity.toJson(json);
        return json;
    }

    // MODIFIES: this
    // EFFECTS: returns obstacles as a JSON array
    private JSONArray obstaclesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Obstacle obstacle : obstacles) {
            jsonArray.put(obstacle.toJson());
        }

        return jsonArray;
    }

    public PlayerCharacter getPlayer() {
        return player;
    }

    public List<Position> getPoints() {
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

    public double getDecider() {
        return decider;
    }

    public int getTicker() {
        return ticker;
    }

    public int getSecondsPassed() {
        return secondsPassed;
    }

    public boolean isCanSpawnObstacle() {
        return canSpawnObstacle;
    }

    public void setTicker(int ticker) {
        this.ticker = ticker;
    }

    public void setPlayerPosition(int x, int y) {
        player = new PlayerCharacter(x, y);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setSecondsPassed(int secondsPassed) {
        this.secondsPassed = secondsPassed;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public void setCanSpawnObstacle(boolean canSpawnObstacle) {
        this.canSpawnObstacle = canSpawnObstacle;
    }

    public void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public void setPoints(List<Position> points) {
        this.points = points;
    }

}



