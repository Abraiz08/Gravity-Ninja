package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

/**
 * Class representing the x, y coordinates of an object
 * on the playing arena
 */
//found in SnakeConsole-Lanterna Project
public class Position {

    private final int x1;
    private final int y1;

    /*
     * MODIFIES: this
     * EFFECTS: Assigns the value of x and y to x1 and y1, describing a position on the game screen
     */
    public Position(int x, int y) {
        this.x1 = x;
        this.y1 = y;
    }

    public int getX() {
        return x1;
    }

    public int getY() {
        return y1;
    }


    //MODIFIES: json
    //EFFECTS: returns pos as a json object
    public JSONObject toJson(JSONObject json) {
        json.put("X-pos", x1);
        json.put("Y-pos", y1);
        return json;
    }


}
