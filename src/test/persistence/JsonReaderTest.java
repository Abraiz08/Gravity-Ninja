package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Game game = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGame.json");
        try {
            Game game = reader.read();
            assertEquals(Game.WIDTH, game.getMaxX());
            assertEquals(0, game.getObstacles().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGame.json");
        try {
            Game game = reader.read();
            Position pos = new Position(1, 1);
            Position pos2 = new Position(2, 10);
            assertEquals(Game.WIDTH, game.getMaxX());
            assertEquals(Game.HEIGHT, game.getMaxY());

            List<Obstacle> obstacles = game.getObstacles();
            assertEquals(2, obstacles.size());
            assertEquals(1, obstacles.get(0).getPos().getX());
            assertEquals(1, obstacles.get(0).getPos().getY());
            assertEquals(2, obstacles.get(1).getPos().getX());
            assertEquals(10, obstacles.get(1).getPos().getY());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}