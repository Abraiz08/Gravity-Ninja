package model;

public class Gravity {

    private int gravDirection;

    public Gravity(int gravDirection) {
        this.gravDirection = gravDirection;
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

    public int getGravDirection() {
        return gravDirection;
    }
}
