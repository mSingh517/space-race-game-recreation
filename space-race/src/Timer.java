import java.time.Duration;
import java.time.Instant;

/**
 * This is the Timer class. In classic Space Race, there is a decreasing white bar in the middle of the screen that
 * represents the time left in the game and can be adjusted to represent time between 45 seconds and three minutes. In 
 * my implementaion, the timer is represented by a rectangle that disappears after one minute, so my game runs for one minute. 
 * The bar is green until it gets to 50 pixels above the bottom of the window, at which point it turns red. 
 * Once the bar disappears, the game ends.
 */
public class Timer {

    private Race app; // reference to main Race object
    private Instant lastUpdate; // a moment to compare elasped time
    private int x; // reference to x position of object on screen
    private int y; // reference to y position of object on screen
    private int width; // reference to width of object on screen
    private int height; // reference to height of object on screen

    /**
     * Constructor for a Timer object halfway across the window, 100 pixels below the top of the window
     * @param app
     */
    public Timer(Race app) {
        this.app = app; // stores a reference to the main Race object 

        // stores specific x and y coordinates to draw the bar at
        this.x = (this.app.width / 2) - 5;
        this.y = 100;

        // stores the specific width and height of the bar
        this.width = 10;
        this.height = 500;
    }

    /**
     * @return whether the timer has disappeared from the window by checking the height of the rectangle that represents the timer
     */
    public boolean isTimeUp() {
        return this.height <= 0;
    }

    /**
     * Draws a rectangle of specific width and height to the x and y coordinates specified in the constructor. In the first call,
     * draw() will check the time twice and store the duration between the two moments. In the rest of the calls, it'll check the time
     * once after the bar is drawn and once again after the interval is at least half a second. If this is the case it will change 
     * the height and the y position of the rectangle by a certain amount, which will make the rectangle look like a decreasing bar.
     */
    public void draw() {

        // changes the rectangle's color to red if its y position reaches 50 pixels above the bottom of the window
        if (this.y >= 550) {
            this.app.fill(255, 0, 0);

        // keeps the color green if not at 50 pixels above the bottom
        } else {
            this.app.fill(0, 255, 0);
        }
        this.app.rect(this.x, this.y, this.width, this.height); // draws rectangle

        // checks time here in first iteration because lastUpdate starts as null
        if (lastUpdate == null) {
            lastUpdate = Instant.now();
        }

        // will always check the time here to compare it to the lastest update
        Instant checkTime = Instant.now();
        long interval = Duration.between(lastUpdate, checkTime).toMillis(); // stores the duration between moments
        
        // if the interval is >= half a second, time will be checked again and bar will get smaller
        if (interval >= 500) {
            lastUpdate = Instant.now();
            this.y += 4;
            this.height -= 4;
        }
    }

}
