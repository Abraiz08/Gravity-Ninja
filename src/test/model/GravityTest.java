package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GravityTest {
    private Gravity testGravity;

    @BeforeEach
    void runBefore() {
        testGravity = new Gravity(-1);
    }

    @Test
    void testConstructor() {
        assertEquals(-1, testGravity.getGravDirection());
        assertFalse(testGravity.getGravitating());
    }

    @Test
    void testFlipGravity() {
        testGravity.flipGravity();
        assertEquals(1, testGravity.getGravDirection());
        assertTrue(testGravity.getGravitating());
        testGravity.flipGravity();
        assertEquals(-1, testGravity.getGravDirection());
        assertTrue(testGravity.getGravitating());
    }

    @Test
    void testNoLongerGravitating() {
        testGravity.noLongerGravitating();
        assertFalse(testGravity.getGravitating());
    }

}
