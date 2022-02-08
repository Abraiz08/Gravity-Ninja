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
            System.out.println(gravDirection);
        } else if (gravDirection == 1) {
            gravDirection = -1;
            System.out.println(gravDirection);

        }


    }

    public void isGravitating() {
        gravitating = true;
    }

    public void noLongerGravitating() {
        gravitating = false;
    }

    public int getGravDirection() {
        return gravDirection;
    }
}
