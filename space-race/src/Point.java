/**
 * This is the Point class. It simply stores two floats in one grouping. I created this class to make
 * my collision detection code in the Ship class easier to write and read as it allowed me to store the 
 * x and y location of any object in one variable, resembling coordinates on a plane.
 */
public class Point {
    public float x;
    public float y;
    
    /**
     * @param x a value to store
     * @param y a value to store
     */
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

}
