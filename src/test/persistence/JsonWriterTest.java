package persistence;

import model.Game;
import model.Obstacle;
import model.Position;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Game game = new Game(20, 20);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Game game = new Game(20, 20);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGame.json");
            game = reader.read();
            assertEquals(20, game.getMaxX());
            assertEquals(20, game.getMaxY());
            assertEquals(0, game.getTicker());
            assertEquals(0, game.getSecondsPassed());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Game game = new Game(20,20);
            Position pos = new Position(1, 1);
            Position pos2 = new Position(2, 10);
            game.getObstacles().add(new Obstacle(pos, 20,20));
            game.getObstacles().add(new Obstacle(pos2, 20,20));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
            game = reader.read();

            assertEquals(20, game.getMaxX());
            assertEquals(20, game.getMaxY());

            List<Obstacle> obstacles = game.getObstacles();
            assertEquals(2, obstacles.size());

            assertEquals(1, obstacles.get(0).getPos().getX());
            assertEquals(1, obstacles.get(0).getPos().getY());
            assertEquals(2, obstacles.get(1).getPos().getX());
            assertEquals(10, obstacles.get(1).getPos().getY());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
