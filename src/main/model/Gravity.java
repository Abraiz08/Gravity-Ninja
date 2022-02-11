package model;

/**
 * Class representing the gravity of the playing arena
 */
public class Gravity {

    private int gravDirection;
    private boolean gravitating;

    /*
     * MODIFIES: this
     * EFFECTS: Assigns a gravDirection to this.gravDirection and sets gravitating to false
     */
    public Gravity(int gravDirection) {
        this.gravDirection = gravDirection;
        gravitating = false;
    }

    /*
     * REQUIRES: gravDirection is either 1 or -1
     * MODIFIES: this
     * EFFECTS: Flips the gravity of the game arena, and sets gravitating to true
     */
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
