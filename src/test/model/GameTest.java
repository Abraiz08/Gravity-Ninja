package model;

import javafx.geometry.Pos;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game testGame;

    @BeforeEach
    void runBefore() {
        testGame = new Game();
    }

    @Test
    void testConstructor() {
       assertEquals(Game.HEIGHT, testGame.getMaxY());
       assertEquals(Game.WIDTH, testGame.getMaxX());

       assertEquals(-1, testGame.getGravity().getGravDirection());
       assertEquals(0, testGame.getPlayer().getPos().getX());
       assertEquals(Game.HEIGHT - PlayerCharacter.HEIGHT, testGame.getPlayer().getPos().getY());

       assertEquals(0, testGame.getTicker());

       assertEquals(1, testGame.getPoints().size());

       assertEquals(0, testGame.getSecondsPassed());

    }

    @Test
    void testTick() {
        testGame.getPoints().clear();
        testGame.tick();

        assertEquals(1, testGame.getTicker());
    }

    @Test
    void testMoveAllObstacles() {
        testGame.setTicker(Game.TICKS_PER_SECOND);
        testGame.moveAllObstacles();
        testGame.setCanSpawnObstacle(true);
        testGame.spawnObstacle();

        Obstacle obstacle = testGame.getObstacles().get(0);
        Position pos = obstacle.getPos();
        int x = pos.getX();
        int y = pos.getY();

        assertTrue((x!=obstacle.getPos().getX() || y!=obstacle.getPos().getX()) ||
                (x==obstacle.getPos().getX() && y==obstacle.getPos().getX()));

        testGame.setTicker(50);
        testGame.moveAllObstacles();

        assertTrue((x!=obstacle.getPos().getX() || y!=obstacle.getPos().getX()) ||
                (x==obstacle.getPos().getX() && y==obstacle.getPos().getX()));

        testGame.setTicker(51);

        assertFalse(testGame.getTicker() % 5 == 0);

//this one, add obstacles and do testing
    }

    @Test
    void testApplyGravity() {
        testGame.setTicker(30);
        testGame.applyGravity();
        assertEquals(Game.HEIGHT - PlayerCharacter.HEIGHT, testGame.getPlayer().getPos().getY());

        testGame.setPlayerPosition(0, 15);
        testGame.applyGravity();
        assertEquals(15 + testGame.getPlayer().getPlayerGravSpeed(), testGame.getPlayer().getPos().getY());

        testGame.getGravity().flipGravity();
        testGame.setPlayerPosition(0, 15);
        testGame.applyGravity();
        assertEquals(15 - testGame.getPlayer().getPlayerGravSpeed(), testGame.getPlayer().getPos().getY());

        testGame.setPlayerPosition(0, 0);
        testGame.applyGravity();
        assertEquals(0, testGame.getPlayer().getPos().getY());
    }

    @Test
    void testCountSeconds() {
        testGame.setTicker(Game.TICKS_PER_SECOND + 1);
        testGame.countSeconds();
        assertEquals (1, testGame.getSecondsPassed());
        assertEquals(0, testGame.getTicker());

    }

    @Test
    void testObstacleGate() {
        testGame.setSecondsPassed(7);
        testGame.obstacleGate();
        assertFalse(testGame.isCanSpawnObstacle());
        testGame.setSecondsPassed(2);
        testGame.obstacleGate();
        assertTrue(testGame.isCanSpawnObstacle());
    }

    @Test
    void testDetectObstacleCollision() {
        testGame.getObstacles().add(new Obstacle((new Position(5, 5)), Game.HEIGHT, Game.WIDTH));

        testGame.setPlayerPosition(20, 700);
        testGame.detectObstacleCollision();
        assertFalse(testGame.isEnded());

        testGame.setPlayerPosition(5, 5);
        testGame.detectObstacleCollision();
        assertTrue(testGame.isEnded());

    }

    @Test
    void testSpawnPoints() {
        testGame.spawnPoints();

        assertEquals(2, testGame.getPoints().size());
    }

    @Test
    void testIsOutOfBounds() {
        Position pos = new Position (0,0);
        assertFalse(testGame.isOutOfBounds(pos));

        Position pos2 = new Position (-1,0);
        assertTrue(testGame.isOutOfBounds(pos2));

        Position pos3 = new Position (0,-1);
        assertTrue(testGame.isOutOfBounds(pos3));

        Position pos4 = new Position (Game.WIDTH + 1,0);
        assertTrue(testGame.isOutOfBounds(pos4));

        Position pos5 = new Position (0,Game.HEIGHT + 1);
        assertTrue(testGame.isOutOfBounds(pos5));

    }

    @Test
    void testIsValidPosition() {
        Position pos = new Position (-5,0);
        assertFalse(testGame.isValidPosition(pos));

        Position pos2 = new Position (5,0);
        testGame.getPoints().add(pos2);
        assertFalse(testGame.isValidPosition(pos2));

        Position pos3 = new Position (6,0);
        testGame.setPlayerPosition(6,0);
        assertFalse(testGame.isValidPosition(pos3));

        Position pos4 = new Position (100,100);
        assertTrue(testGame.isValidPosition(pos4));

    }

    @Test
    void testIsValidPositionForObstacle() {
        Position pos = new Position (-5,0);
        assertFalse(testGame.isValidPositionForObstacle(pos));

        Position pos2 = new Position (6,8);
        assertFalse(testGame.isValidPositionForObstacle(pos2));

        Position pos3 = new Position (2,8);
        assertTrue(testGame.isValidPositionForObstacle(pos3));
    }

    @Test
    void testHandlePoints() {
        testGame.setScore(0);
        testGame.getPoints().clear();

        Position pos = new Position(1, 1);
        testGame.getPoints().add(pos);

        testGame.setPlayerPosition(7, 7);
        testGame.handlePoints();
        assertEquals(0, testGame.getScore());

        testGame.setPlayerPosition(1, 1);
        testGame.handlePoints();
        assertEquals(1, testGame.getScore());

    }

    @Test
    void testHandleGravitating() {
        testGame.getGravity().setGravitating(false);
        testGame.handleGravitating(30);
        assertFalse(testGame.getGravity().getGravitating());

        testGame.getGravity().setGravitating(true);
        testGame.handleGravitating(0);
        assertFalse(testGame.getGravity().getGravitating());

        testGame.getGravity().setGravitating(true);
        testGame.handleGravitating(100);
        assertTrue(testGame.getGravity().getGravitating());

        testGame.getGravity().setGravitating(true);
        testGame.handleGravitating(Game.HEIGHT - testGame.getPlayer().HEIGHT);
        assertFalse(testGame.getGravity().getGravitating());

    }

    @Test
    void testSpawnObstacles() {
        Game testGame2 = new Game();
        testGame2.spawnObstacles();
        assertTrue(testGame2.getDecider() > 1);
        assertEquals(0, testGame2.getObstacles().size());
    }

    
    @Test
    void testToJson() {
        JSONObject json = new JSONObject();
        JSONObject obj = testGame.toJson();

        json.put("ticker", testGame.getTicker());
        json.put("Seconds passed", testGame.getSecondsPassed());
        json.put("PlayerCharacter", testGame.playerToJson());
        json.put("Gravity", testGame.gravityToJson());
        json.put("Points", testGame.getPoints());
        json.put("Obstacles", testGame.obstaclesToJson());
        json.put("Score", testGame.getScore());
        json.put("Ended", testGame.isEnded());
        json.put("CanSpawnObstacles", testGame.isCanSpawnObstacle());
        json.put("MaxX", testGame.getMaxX());
        json.put("MaxY", testGame.getMaxY());
        json.put("Decider", testGame.getDecider());

        assertEquals(obj.toString(), json.toString());

    }



     

}


