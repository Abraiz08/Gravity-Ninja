package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game testGame;

    @BeforeEach
    void runBefore() {
        testGame = new Game(20, 20);
    }

    @Test
    void testConstructor() {
       assertEquals(20, testGame.getMaxY());
       assertEquals(20, testGame.getMaxX());

       assertEquals(-1, testGame.getGravity().getGravDirection());
       assertEquals(0, testGame.getPlayer().getPos().getX());
       assertEquals(20, testGame.getPlayer().getPos().getY());

       assertEquals(0, testGame.getTicker());

       assertEquals(1, testGame.getPoints().size());

       assertEquals(0, testGame.getSecondsPassed());

    }

    @Test
    void testTick() {
        testGame.tick();
        assertEquals(1, testGame.getTicker());
    }

    @Test
    void testMoveAllObstacles() {
        testGame.setTicker(50);
        assertTrue(testGame.getTicker() % 5 == 0);

        testGame.setTicker(51);
        assertFalse(testGame.getTicker() % 5 == 0);

    }

    @Test
    void testApplyGravity() {
        testGame.setTicker(30);
        testGame.applyGravity();
        assertEquals(20, testGame.getPlayer().getPos().getY());

        testGame.setPlayerPosition(0, 15);
        testGame.applyGravity();
        assertEquals(16, testGame.getPlayer().getPos().getY());

        testGame.getGravity().flipGravity();
        testGame.setPlayerPosition(0, 15);
        testGame.applyGravity();
        assertEquals(14, testGame.getPlayer().getPos().getY());

        testGame.setPlayerPosition(0, 0);
        testGame.applyGravity();
        assertEquals(0, testGame.getPlayer().getPos().getY());
    }

    @Test
    void testCountSeconds() {
        testGame.setTicker(61);
        testGame.countSeconds();
        assertEquals (1, testGame.getSecondsPassed());
        assertEquals(0, testGame.getTicker());

    }

    @Test
    void testObstacleGate() {
        testGame.setSecondsPassed(2);
        assertTrue(testGame.isCanSpawnObstacle());


        assertTrue(testGame.isCanSpawnObstacle());
    }




}


