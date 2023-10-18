import java.lang.Math;

/**
 * This is the Asteroid class. Asteroids start on either side of the window, offscreen at a random x and y position
 * so that all together, they create a "field" of asteroids moving at many different places for the ships to navigate through
 */
public class Asteroid {

    private Race app; // reference to main Race object
    private int x; // reference to x position of object on screen
    private int y; // reference to y position of object on screen
    private boolean goingLeft; // reference for what direction the object is moving onscreen

    /**
     * Constructor to create an Asteroid object at a randomized position off screen, as seen in setAsteroid()
     * @param app a reference to the Race object that created this object
     */
    public Asteroid(Race app) {
        this.app = app; // store a reference to the main Race object 
        this.setAsteroid(); // draw the asteroid at a random position
    }

    /**
     * @return x position of asteroid
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x position of the asteroid by checking whether it has crossed the screen from the
     * side it started on using goingLeft. If asteroid has reached the side opposite to the one
     * it started on, the asteroid gets replaced at the edge of the side it started on
     * @param x x position to be set
     */
    public void setX(int x) {

        // replaces left moving asteroids to the right if they reach left side
        if (this.goingLeft && x == 0) { 
            this.x = this.app.width;

        // replaces right moving asteroids to the left if they reach right side    
        } else if (!this.goingLeft && x == this.app.width) { 
            this.x = 0;
        } else {
            this.x = x;
        }
    }

    /**
     * @return y position of the asteroid
     */
    public int getY() {
        return y;
    }

    /**
     * Sets a random y position for the asteroid (no lower than 100 pixels from the bottom of the window) then 
     * randomizes what side the asteroid will start on. Finally it randomizes the x position in the off-screen
     * space the asteroid starts on, so there is buffer between each asteroid drawn and they aren't in a line
     */
    public void setAsteroid() {

        // y set to a random position somewhere between the top of the window and 100 pixels above the bottom
        this.y = (int) (Math.random() * (this.app.height - 100));
        double spawnPossiblity = Math.random();

        // x set to somewhere between the left edge of the window and one window's width to the left of that edge
        if (spawnPossiblity < 0.5) {
            this.x = (int) (Math.random() * (-this.app.width));
            this.goingLeft = false;
         
        // x set to somewhere between the right edge of the window and one window's width to the right of that edge
        } else {
            this.x = (int) (Math.random() * (2 * this.app.width - this.app.width)); 
            this.goingLeft = true;
        }
    }

    /**
     * Draws the asteroid as a yellow, 5x5 cube at a random x and y location determined by setAsteroid()
     * and increments the movement of the asteroid in the direction set by goingLeft
     */
    public void draw() {
        this.app.fill(255, 255, 0);
        this.app.rect(this.x, this.y, 5, 5);
        if (this.goingLeft) {
            this.setX(this.x - 1);
        } else {
            this.setX(this.x + 1);
        }
    }

}
