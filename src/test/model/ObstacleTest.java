package model;

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
        System.out.println(testObstacle.getObstacleDirection());

        assertEquals(2, testObstacle.getPos().getX());
//left
        testPos = new Position(16, 8);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        testObstacle.move(60);

        assertEquals(15, testObstacle.getPos().getX());
//up
        testPos = new Position(8, 16);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        testObstacle.move(60);

        assertEquals(15, testObstacle.getPos().getY());
//down
        testPos = new Position(8, 1);
        testObstacle.decideObstacleDirection(testPos, 20, 20, testObstacle.getDecider());
        testObstacle.move(60);

        assertEquals(2, testObstacle.getPos().getY());



    }

}
