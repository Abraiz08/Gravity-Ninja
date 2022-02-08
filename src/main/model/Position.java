package model;

import java.util.Objects;

public class Position {

    private int x1;
    private int y1;

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
