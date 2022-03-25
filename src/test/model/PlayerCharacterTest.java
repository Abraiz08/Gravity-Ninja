package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerCharacterTest {
   /*
    private PlayerCharacter testPlayer;
    private Game testGame = new Game();

    @BeforeEach
    void runBefore() {
        testPlayer = new PlayerCharacter(0, 20);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testPlayer.getPos().getX());
        assertEquals(20, testPlayer.getPos().getY());
    }

    @Test
    void testMove() {
        testPlayer.move(KeyEvent.VK_LEFT, testGame);
        assertEquals(0, testPlayer.getPos().getX());

        testPlayer.move(KeyEvent.VK_RIGHT, testGame);
        assertEquals(1, testPlayer.getPos().getX());

        testGame.setPlayerPosition(3, 20);
        testPlayer.move(KeyEvent.VK_LEFT, testGame);
        assertEquals(2, testPlayer.getPos().getX());

        testGame.setPlayerPosition(20, 20);
        testPlayer.move(KeyEvent.VK_RIGHT, testGame);
        assertEquals(20, testPlayer.getPos().getX());

    }

    @Test
    void testUpdateX() {
        assertEquals(6, testPlayer.updateX(5, testGame, 1));
        assertEquals(20, testPlayer.updateX(20, testGame, 1));
        assertEquals(4, testPlayer.updateX(5, testGame, -1));
        assertEquals(0, testPlayer.updateX(0, testGame, -1));

    }

    @Test
    void testJump() {
        testGame.setPlayerPosition(20, 0);
        testGame.getPlayer().jump(testGame);
        assertEquals( testGame.getPlayer().getPlayerJumpHeight(), testGame.getPlayer().getPos().getY());

        testGame.setPlayerPosition(20, 20);
        testGame.getPlayer().jump(testGame);
        assertEquals(20 - testGame.getPlayer().getPlayerJumpHeight(), testGame.getPlayer().getPos().getY());

        testGame.setPlayerPosition(20, 15);
        testGame.getPlayer().jump(testGame);
        assertEquals(15 - testGame.getPlayer().getPlayerJumpHeight(), testGame.getPlayer().getPos().getY());

        testGame.setPlayerPosition(20, 10);
        testGame.getGravity().flipGravity();
        testGame.getPlayer().jump(testGame);
        assertEquals(10 + testGame.getPlayer().getPlayerJumpHeight(), testGame.getPlayer().getPos().getY());


    }

    @Test
    void testResetDoubleJump() {

        testGame.getPlayer().setMaxJumpsToZero();
        testGame.setPlayerPosition(0,0);
        testGame.getGravity().isGravitating();
        testGame.getPlayer().resetDoubleJump(testGame);

        assertEquals(1, testGame.getPlayer().getMaxJumps());

        testGame.getPlayer().setMaxJumpsToZero();
        testGame.setPlayerPosition(0,0);
        testGame.getGravity().noLongerGravitating();
        testGame.getPlayer().resetDoubleJump(testGame);

        assertEquals(1, testGame.getPlayer().getMaxJumps());

        testGame.getPlayer().setMaxJumpsToZero();
        testGame.setPlayerPosition(0,testGame.getMaxY());
        testGame.getGravity().isGravitating();
        testGame.getPlayer().resetDoubleJump(testGame);

        assertEquals(1, testGame.getPlayer().getMaxJumps());

        testGame.getPlayer().setMaxJumpsToZero();
        testGame.setPlayerPosition(0,testGame.getMaxY());
        testGame.getGravity().noLongerGravitating();
        testGame.getPlayer().resetDoubleJump(testGame);

        assertEquals(1, testGame.getPlayer().getMaxJumps());

        testGame.getPlayer().setMaxJumpsToZero();
        testGame.setPlayerPosition(0,5);
        testGame.getGravity().isGravitating();
        testGame.getPlayer().resetDoubleJump(testGame);

        assertEquals(1, testGame.getPlayer().getMaxJumps());
    }

    @Test
    void testGetPulledByGravity() {

        testPlayer.getPulledByGravity(1, 20);
        assertEquals(19, testPlayer.getPos().getY());


        testPlayer.getPulledByGravity(-1, 20);
        assertEquals(20, testPlayer.getPos().getY());

        testGame.setPlayerPosition(0, 0);
        testGame.getPlayer().getPulledByGravity(1, 20);
        assertEquals(0, testGame.getPlayer().getPos().getY());

        testGame.setPlayerPosition(0, 0);
        testGame.getPlayer().getPulledByGravity(-1, 20);
        assertEquals(1, testGame.getPlayer().getPos().getY());


    }

    @Test
    void testHasCollided() {
        Position p = new Position(0,20);
        assertTrue(testPlayer.hasCollided(p));

        Position p2 = new Position (3, 4);
        assertFalse(testPlayer.hasCollided(p2));
    }

    */
}
