package model;

public class Gravity {

    private int gravDirection;
    private boolean gravitating;


    public Gravity(int gravDirection) {
        this.gravDirection = gravDirection;
        gravitating = false;
    }


    public void flipGravity() {
        if (gravDirection == -1) {
            gravDirection = 1;
            isGravitating();

        } else if (gravDirection == 1) {
            gravDirection = -1;
            isGravitating();

        }


    }

    public void isGravitating() {
        gravitating = true;
    }

    public void noLongerGravitating() {
        gravitating = false;
    }

    public boolean getGravitating() {
        return gravitating;
    }

    public int getGravDirection() {
        return gravDirection;
    }
}
