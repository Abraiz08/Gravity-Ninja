package persistence;


import javafx.geometry.Pos;
import model.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads game from JSON data stored in file
public class JsonReader {
    private String source;


    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // Method taken from JsonSerializationDemo
    // EFFECTS: reads game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // Method taken from JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // Method taken from JsonSerializationDemo
    // EFFECTS: parses game from JSON object and returns it
    private Game parseGame(JSONObject jsonObject) {
        int maxX = jsonObject.getInt("MaxX");
        int maxY = jsonObject.getInt("MaxY");
        Game game = new Game(maxX, maxY);
        addGame(game, jsonObject);
        return game;
    }

    // MODIFIES: game
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addGame(Game game, JSONObject jsonObject) {

        setTicker(game, jsonObject);
        setSecondsPassed(game, jsonObject);
        setPlayerCharacter(game, jsonObject);
        setGravity(game, jsonObject);
        setPoints(game, jsonObject);
        setObstacles(game, jsonObject);
        setScore(game, jsonObject);
        setEnded(game, jsonObject);
        setCanSpawnObstacles(game, jsonObject);
    }

    // MODIFIES: game
    // EFFECTS: parses ticker from JSON object and sets its value
    public void setTicker(Game game, JSONObject jsonObject) {

        int ticker = jsonObject.getInt("ticker");
        game.setTicker(ticker);
    }

    // MODIFIES: game
    // EFFECTS: parses secondsPassed from JSON object and sets its value
    public void setSecondsPassed(Game game, JSONObject jsonObject) {

        int secondsPassed = jsonObject.getInt("Seconds passed");
        game.setSecondsPassed(secondsPassed);
    }

    // MODIFIES: game
    // EFFECTS: parses PlayerCharacter from JSON object and sets its value
    public void setPlayerCharacter(Game game, JSONObject jsonObject) {

        JSONObject player =  jsonObject.getJSONObject("PlayerCharacter");

        int xpos = player.getInt("X-pos");
        int ypos = player.getInt("Y-pos");

        game.setPlayerPosition(xpos, ypos);
    }

    // MODIFIES: game
    // EFFECTS: parses Gravity from JSON object and sets its value
    public void setGravity(Game game, JSONObject jsonObject) {

        JSONObject gravity = jsonObject.getJSONObject("Gravity");

        int gravDirection = gravity.getInt("Grav Direction");
        boolean gravitating = gravity.getBoolean("Gravitating");

        game.getGravity().setGravDirection(gravDirection);
        game.getGravity().setGravitating(gravitating);
    }

    // MODIFIES: game
    // EFFECTS: parses points from JSON object and sets its value
    public void setPoints(Game game, JSONObject jsonObject) {

        game.getPoints().clear();
        JSONArray jsonPoints = jsonObject.getJSONArray("Points");
        List<Position> points = new ArrayList<>();

        for (Object json : jsonPoints) {
            JSONObject nextPoint = (JSONObject) json;
            int x = nextPoint.getInt("x");
            int y = nextPoint.getInt("y");

            Position p = new Position(x, y);
            points.add(p);
        }
        game.setPoints(points);
    }

    // MODIFIES: game
    // EFFECTS: parses score from JSON object and sets its value
    public void setScore(Game game, JSONObject jsonObject) {

        int score =  jsonObject.getInt("Score");
        game.setScore(score);
    }

    // MODIFIES: game
    // EFFECTS: parses obstacles from JSON object and sets its value
    public void setObstacles(Game game, JSONObject jsonObject) {

        game.getObstacles().clear();
        JSONArray jsonObstacles = jsonObject.getJSONArray("Obstacles");
        List<Obstacle> obstacles = new ArrayList<>();

        for (Object json : jsonObstacles) {
            JSONObject nextObstacle = (JSONObject) json;
            JSONObject position = nextObstacle.getJSONObject("Position");


            int x = position.getInt("X-pos");
            int y = position.getInt("Y-pos");
            Position pos = new Position(x, y);

            int maxX = jsonObject.getInt("MaxX");
            int maxY = jsonObject.getInt("MaxY");

            Obstacle o = new Obstacle(pos, maxX, maxY);

            int ticker = ((JSONObject) json).getInt("Obstacle Ticker");
            String obstacleDirection = ((JSONObject) json).getString("Obstacle Direction");
            double decider = ((JSONObject) json).getDouble("Decider");

            o.setObstacleTicker(ticker);
            o.setObstacleDirection(obstacleDirection);
            o.setDecider(decider);
            obstacles.add(o);
        }
        game.setObstacles(obstacles);
    }

    // MODIFIES: game
    // EFFECTS: parses setEnded from JSON object and sets its value
    public void setEnded(Game game, JSONObject jsonObject) {

        boolean ended = jsonObject.getBoolean("Ended");
        game.setEnded(ended);
    }

    // MODIFIES: game
    // EFFECTS: parses canSpawnObstacles from JSON object and sets its value
    public void setCanSpawnObstacles(Game game, JSONObject jsonObject) {

        boolean canSpawnObstacles = jsonObject.getBoolean("CanSpawnObstacles");
        game.setCanSpawnObstacle(canSpawnObstacles);
    }
}
