package model;

import java.util.Objects;

/**
 * Class representing the x, y coordinates of an object
 * on the playing arena
 */
public class Position {

    private final int x1;
    private final int y1;

    /*
     * REQUIRES:
     * MODIFIES:
     * EFFECTS:
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

    @Override
    public int hashCode() {
        return Objects.hash(x1,y1);
    }


}
