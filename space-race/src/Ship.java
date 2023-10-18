import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * This is the Ship class. In it, there are boolean values to determine which
 * direction a ship should be
 * moving in, if any. Collision detection with asteroids is also handled here.
 * The ship is understood to be
 * a triangle shape (see spaceship_green.png) and collision has occured if an
 * asteroid is within this triangle.
 */
public class Ship {

    private Race app; // reference to main Race object
    private PImage shipImg; // reference to image of a ship
    private int x; // reference to x position of object on screen
    private int y; // reference to y position of object on screen
    private int score = 0; // score of object
    private boolean isUp; // movement determiner
    private boolean isDown; // movement determiner

    /**
     * Constructor for a Ship object
     * 
     * @param app reference to the Race object that created this object
     * @param x   the x coordinate of this object on the screen
     * @param y   the x coordinate of this object on the screen
     */
    public Ship(Race app, int x, int y) {
        this.app = app; // store a reference to the Race object
        this.shipImg = app.loadImage("images/spaceship_green.png"); // load this specific image

        // store x and y coordinates of object on screen
        this.x = x;
        this.y = y;

        // set movement in both directions to false
        this.isUp = false;
        this.isDown = false;
    }

    /**
     * @return score of ship
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the scores based on the position of the ship, if ship at the top of the
     * screen,
     * score is incremented and ship is respawned at the bottom, otherwise it stays
     * the same
     * 
     * @param score score of ship object
     */
    public void setScore(int score) {
        if (this.y <= 0) {
            this.score++;
            this.respawn();
        } else {
            this.score = score;
        }
    }

    /**
     * @param isUp boolean to determine whether up movement key pressed
     */
    public void setUp(boolean isUp) {
        this.isUp = isUp;
    }

    /**
     * @param isDown boolean to determine whether down movement key pressed
     */
    public void setDown(boolean isDown) {
        this.isDown = isDown;
    }

    /**
     * Draws the ship object to a specified x and y location, checks what movement
     * mode ship is in and
     * whether it is within the bounds of the window and moves the ship up or down
     * accordingly
     */
    public void draw() {
        this.app.imageMode(PApplet.CENTER); // setting so the image is drawn centered on the specified x and y coordinates
        this.app.image(this.shipImg, this.x, this.y);

        // allows ship to move up if "W" or UP has been pressed and ship isn't at top of
        // window
        if (this.isUp && this.y > 0) {
            this.moveUp();

            // allows ship to move down if "S" or DOWN has been pressed and ship isn't at
            // spawnpoint
        } else if (this.isDown && this.y < this.app.height - 45) {
            this.moveDown();
        }
        this.setScore(score);
    }

    /**
     * increments ship movement up by two pixels
     */
    public void moveUp() {
        this.y -= 2;
    }

    /**
     * increments ship movement down by 2 pixels
     */
    public void moveDown() {
        this.y += 2;
    }

    /**
     * Resets ship's y coordinate to spawnpoint and resets movement determiners
     */
    public void respawn() {
        this.y = this.app.height - 45;
        this.isUp = false;
        this.isDown = false;
    }

    /**
     * Calculates the area of a trianle given three points, stored as (x, y)
     * coordinates. The cross product method
     * for calculating area is used in which the cross product of two of a
     * triangle's vectors is calculated.
     * Half the magnitude of this cross product gives the area of the triangle. This
     * method makes use of
     * Processing's inbuilt PVector class, which includes magnitude and cross
     * product methods
     * 
     * @param a an object of type Point which has an x and y value
     * @param b an object of type Point which has an x and y value
     * @param c an object of type Point which has an x and y value
     * @return the area of a triangle given three points
     */
    public float area(Point a, Point b, Point c) {

        // creating vectors by subtracting x1 from x2 and y1 from y2
        PVector ab = new PVector(b.x - a.x, b.y - a.y);
        PVector ac = new PVector(c.x - a.x, c.y - a.y);
        PVector crossProduct = ab.cross(ac);

        return crossProduct.mag() / 2;
    }

    /**
     * Determines whether a point is within a triangle. It can be understood that if
     * a point is in
     * a triangle, the areas of 3 new triangles formed by separate pairs of the
     * original triangle's
     * vertices and the point must add up to the area of the original triangle. if
     * these areas don't add
     * up, the point is not in the triangle.
     * 
     * Ex: Triangle ABC and point P
     * Triangles formed: ABP, BCP, CAP
     * If areas of ABP + BCP + CAP = ABC --> point P is within ABC, otherwise it is
     * not
     * 
     * @param pt a point to be checked against a triangle
     * @param a  a point in a triangle
     * @param b  a point in a triangle
     * @param c  a point in a triangle
     * @return boolean for whether the three new triangles' area is equal to the
     *         area of the original triangle
     */
    public boolean pointInTriangle(Point pt, Point a, Point b, Point c) {
        float shipArea = area(a, b, c);
        float tri1 = area(pt, a, b);
        float tri2 = area(pt, b, c);
        float tri3 = area(pt, c, a);

        return tri1 + tri2 + tri3 == shipArea;
    }

    /**
     * Sets up the triangle representing the ship to be checked for collision
     * against an asteroid by defining
     * its three vertices as x and y Points certain distances away from the center
     * of the shipImg
     * 
     * @param asteroid a point to be checked for collision with the ship triangle
     * @return whether an asteroid has collided with the ship
     */
    public boolean collision(Point asteroid) {
        Point leftVertex = new Point(this.x - 30, this.y + 22);
        Point rightVertex = new Point(this.x + 30, this.y + 22);
        Point topVertex = new Point(this.x, this.y - 35);

        return pointInTriangle(asteroid, leftVertex, rightVertex, topVertex);
    }

}
