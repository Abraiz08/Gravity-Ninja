package persistence;

import com.googlecode.lanterna.TerminalSize;
import model.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    // EFFECTS: reads game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

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
        //setPoints(game, jsonObject);
        setObstacles(game, jsonObject);
        setScore(game, jsonObject);
        setEnded(game, jsonObject);
        setCanSpawnObstacles(game, jsonObject);
       // setDecider(game, jsonObject);

    }

    public void setTicker(Game game, JSONObject jsonObject) {
        int ticker = jsonObject.getInt("ticker");
        game.setTicker(ticker);
    }

    public void setSecondsPassed(Game game, JSONObject jsonObject) {
        int secondsPassed = jsonObject.getInt("Seconds passed");
        game.setSecondsPassed(secondsPassed);
    }

    public void setPlayerCharacter(Game game, JSONObject jsonObject) {
        JSONObject player =  jsonObject.getJSONObject("PlayerCharacter");

        int xpos = player.getInt("X-pos");
        int ypos = player.getInt("Y-pos");
        game.setPlayerPosition(xpos, ypos);

    }

    public void setGravity(Game game, JSONObject jsonObject) {
        JSONObject gravity = jsonObject.getJSONObject("Gravity");

        int gravDirection = gravity.getInt("Grav Direction");
        boolean gravitating = gravity.getBoolean("Gravitating");

        game.getGravity().setGravDirection(gravDirection);
        game.getGravity().setGravitating(gravitating);
    }
/*
    public void setPoints(Game game, JSONObject jsonObject) {
        game.getPoints().clear();
        JSONArray points = jsonObject.getJSONArray("Points");

        while (points.iterator().hasNext()) {

            JSONObject point = (JSONObject)points.iterator().next();
            game.getPoints().add((Position)points.iterator().next());
        }

    }

 */

    public void setScore(Game game, JSONObject jsonObject) {
        int score =  jsonObject.getInt("Score");
        game.setScore(score);
    }

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
    /*
    {
            "Position": {
                "X-pos": 76,
                "Y-pos": 5
            },
            "Obstacle Ticker": 5,
            "Obstacle Speed": 1,
            "Obstacle Direction": "down",
            "Decider": 0.5145588457292194
        }
     */

    public void setEnded(Game game, JSONObject jsonObject) {
        boolean ended = jsonObject.getBoolean("Ended");
        game.setEnded(ended);
    }

    public void setCanSpawnObstacles(Game game, JSONObject jsonObject) {
        boolean canSpawnObstacles = jsonObject.getBoolean("CanSpawnObstacles");
        game.setCanSpawnObstacle(canSpawnObstacles);
    }

    /*
    public void setDecider(Game game, JSONObject jsonObject) {
        double decider = jsonObject.getDouble("decider");
        game.setDecider(decider);
    }

     */

    /*
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ticker", ticker);
        json.put("Seconds passed", secondsPassed);
        json.put("PlayerCharacter", playerToJson());
        json.put("Gravity", gravityToJson());
        json.put("Points", points);
        json.put("Obstacles", obstaclesToJson());
        json.put("Score", score);
        json.put("Ended", ended);
        json.put("CanSpawnObstacles", canSpawnObstacle);
        json.put("MaxX", maxX);
        json.put("MaxY", maxY);
        json.put("Decider", decider);
        return json;
    }

     */

}
