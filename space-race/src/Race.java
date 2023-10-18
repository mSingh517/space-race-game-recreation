import processing.core.PApplet;
import processing.core.PFont;

import java.util.ArrayList;

/**
 * This is a recreation of the 1973 game Space Race, by Atari. The goal of the game is to navigate a spaceship
 * through a field of moving asteroids to the top of the screen. There are two ships, the left being controlled by the 
 * "W" and "S" keys and the right being controlled by the up and down arrows. Reaching the top of the screen scores a point and
 * respawns the ship at the bottom; hitting an asteroid respawns the ship at the bottom. The time left in the game is indicated by
 * the green bar. When the bar dissapears, the game ends, a short message is displayed and then the program quits. 
 * 
 * @author Milind Singh
 * @version 1.0
 */
public class Race extends PApplet {

    private Ship leftShip; // left-side Ship object
    private Ship rightShip; // right-side Ship object
    private ArrayList<Asteroid> asteroids; // ArrayList of asteroids to create
    private Timer timer; // Timer object to display to screen
    private PFont scoreText; // text object for player scores
    private PFont endText; // text object for end of game message
    private boolean gameOver = false; // check to run Thread.sleep()
    private final int WINDOW_WIDTH = 800; // width of display window
    private final int WINDOW_HEIGHT = 600; // height display window
    private final int NUM_ASTEROIDS = 30; // number of asteroid obstacles to create on screen

    /**
	 * Initializes the two ships, the asteroid field, the score and end texts, and the game timer
	 */
    public void setup() {

        // sets up left ship at 1/4 of the way across the window, 45 pixels above the bottom
        leftShip = new Ship(this, this.width / 4, this.height - 45);

        // sets up the right ship 3/4 of the way across the window, 45 pixels above the bottom
        rightShip = new Ship(this, this.width * 3 / 4, this.height - 45);
        
        // creates the Asteroid objects
        asteroids = new ArrayList<Asteroid>();
        for (int i = 0; i < NUM_ASTEROIDS; i++) {
            Asteroid asteroid = new Asteroid(this);
            this.asteroids.add(asteroid);
        }
        scoreText = createFont("Arial", 40);  // sets font and size of score text to display
        endText = createFont("Arial", 60); // sets font and size of end game message
        timer = new Timer(this); // sets up the timer bar to display to the window
    }

    /**
	 * draws the main game including the ships, asteroids, timer, and end message to the window
	 */
    public void draw() {

        // determines whether game has completed and ending message has been printed so that program can pause for
        // five seconds before quitting on its own. This has to be called away from other draw method components to avoid
        // the game pausing and quitting unexpectedly
        if (gameOver) { 
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.exit(0);
            }
        }

        // displays the game to the window as long as the timer hasn't run out
        if (!timer.isTimeUp()) {
            this.background(0, 0, 0); // setting black background, has to be redrawn everytime to not interfere with timer bar
            leftShip.draw();
            rightShip.draw();
            textFont(scoreText);
            fill(255);
            text(Integer.toString(this.leftShip.getScore()), 100, this.height - 45);
            text(Integer.toString(this.rightShip.getScore()), 700, this.height - 45);
            for (int i = 0; i < this.asteroids.size(); i++) {
                Asteroid asteroid = this.asteroids.get(i);
                asteroid.draw();

                // storing x and y coordinates of asteroid as a single Point for collision check
                Point astPos = new Point(asteroid.getX(), asteroid.getY());
                
                // checking every asteroid with each ship to see if they have collided, if so, reset ship to the bottom of screen
                if (leftShip.collision(astPos)) {
                    leftShip.respawn();
                }
                if (rightShip.collision(astPos)) {
                    rightShip.respawn();
                }
            }
            timer.draw();

        // displays game end message, depending on player scores
        } else {
            gameOver = true; // makes it so that the system can pause, then quit when draw() is called again after end message
            this.background(0, 0, 0); // clears existing background
            textFont(endText);
            fill(255);
            textAlign(CENTER, CENTER);
            text("GAME OVER", this.width / 2, (this.height / 2) - 30);
            textSize(40);
            if (this.leftShip.getScore() > this.rightShip.getScore()) {
                text("Player 1 wins!", this.width / 2, (this.height / 2) + 30);
            } else if (this.rightShip.getScore() > this.leftShip.getScore()) {
                text("Player 2 wins!", this.width / 2, (this.height / 2) + 30);
            } else {
                text("It's a tie!", this.width / 2, (this.height / 2) + 30);
            }
        }
    }

    /**
	 * Allows up and down movement for each ship by changing movement boolean values which affect draw() in the Ship class
     * Left ship uses "W" and "S" keys while right ship uses Up and Down arrows
	 */
    public void keyPressed() {
        if (this.keyCode == 87) { // keyCode 87 is for "W" key
            this.leftShip.setUp(true);
            this.leftShip.setDown(false);
        } else if (this.keyCode == 83) { // keyCode 83 is for "S" key
            this.leftShip.setUp(false);
            this.leftShip.setDown(true);
        }

        if (this.keyCode == UP) {
            this.rightShip.setUp(true);
            this.rightShip.setDown(false);
        } else if (this.keyCode == DOWN) {
            this.rightShip.setUp(false);
            this.rightShip.setDown(true);
        }
    }

    /**
	 * Stops ships from moving indefinitely after a key is pressed by changing boolean values used in Ship class
	 */
    public void keyReleased() {
        if (this.keyCode == 87) {
            this.leftShip.setUp(false);
        } else if (this.keyCode == 83) {
            this.leftShip.setDown(false);
        }

        if (this.keyCode == UP) {
            this.rightShip.setUp(false);
        } else if (this.keyCode == DOWN) {
            this.rightShip.setDown(false);
        }
    }

    /**
	 * Opens a 800 x 600 window
	 */
    public void settings() {
        this.size(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public static void main(String[] args) {
        PApplet.main("Race");
    }

}
