package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    private Position testPosition;

    @BeforeEach
    void runBefore() {
        testPosition = new Position(0,0);
    }

    @Test
    void testConstructor(){
        assertEquals(0, testPosition.getX());
        assertEquals(0, testPosition.getY());
    }
}

