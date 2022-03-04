package model;

import javafx.geometry.Pos;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ObstacleTest {
    private Obstacle testObstacle;
    private Position testPos;

    @BeforeEach
    void runBefore() {
        testPos = new Position(1, 1);
        testObstacle = new Obstacle(testPos, 20, 20);
    }

    @Test
    void testConstructor() {
        assertTrue(testPos.getX() == testObstacle.getPos().getX());
        assertTrue(testPos.getY() == testObstacle.getPos().getY());
        testRandomizeObstacleSpeed();
        testRandomizeDecider();
        testDecideObstacleDirection();

    }

    @Test
    void testRandomizeDecider(){
        testObstacle.randomizeDecider();
        assertTrue(0 <= testObstacle.getDecider()
                && testObstacle.getDecider() < 1);
    }

    @Test
    void testRandomizeObstacleSpeed() {
        assertTrue(0 < testObstacle.getObstacleTicker()
        && testObstacle.getObstacleTicker() <= 6);
    }

    @Test
    void testDecideObstacleDirection() {
        while (testObstacle.getDecider() >= 0.5) {
            testObstacle.randomizeDecider();
        }
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        assertEquals("right", testObstacle.getObstacleDirection());

        testPos = new Position(16, 1);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        assertEquals("left", testObstacle.getObstacleDirection());

        testPos = new Position(16, 16);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        assertEquals("left", testObstacle.getObstacleDirection());

        testPos = new Position(1, 16);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        assertEquals("right", testObstacle.getObstacleDirection());

        while (testObstacle.getDecider() < 0.5) {
            testObstacle.randomizeDecider();
        }
        testPos = new Position(16, 1);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        assertEquals("down", testObstacle.getObstacleDirection());

        testPos = new Position(16, 1);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        assertEquals("down", testObstacle.getObstacleDirection());

        testPos = new Position(16, 16);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        assertEquals("up", testObstacle.getObstacleDirection());

        testPos = new Position(1, 16);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        assertEquals("up", testObstacle.getObstacleDirection());

        testPos = new Position(8, 16);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        assertEquals("up", testObstacle.getObstacleDirection());

        testPos = new Position(8, 1);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        assertEquals("down", testObstacle.getObstacleDirection());

        testPos = new Position(1, 8);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        assertEquals("right", testObstacle.getObstacleDirection());

        testPos = new Position(16, 8);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        assertEquals("left", testObstacle.getObstacleDirection());

    }

    @Test
    void testMove() {
        testObstacle.setObstacleTicker(20);
//right
        testPos = new Position(1, 8);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        testObstacle.move(60);

        assertEquals(2, testObstacle.getPos().getX());
//left
        Position testPos2 = new Position(16, 8);
        Obstacle testObstacle2 = new Obstacle (testPos2, 20, 20);
        testObstacle2.decideObstacleDirection(testPos2, 20, 20, testObstacle2.getDecider());
        testObstacle2.move(60);

        assertEquals(15, testObstacle2.getPos().getX());
//up
        Position testPos3 = new Position(8, 16);
        Obstacle testObstacle3 = new Obstacle (testPos3, 20, 20);
        testObstacle3.decideObstacleDirection(testPos3, 20, 20, testObstacle3.getDecider());
        testObstacle3.move(60);

        assertEquals(15, testObstacle3.getPos().getY());
//down
        testPos = new Position(8, 1);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        testObstacle.move(60);

        assertEquals(2, testObstacle.getPos().getY());

    }

}
