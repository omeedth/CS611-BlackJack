/*
 *  Author: Alex Thomas
 *  Creation Date: 10/5/2020
 *  Last Modified: 10/5/2020
 *  Purpose: This is the an abstract class defining what a game needs to be a game
 * 
 */

import java.util.Map;

public abstract class Game {
    
    // Attributes
    private String name;
    private boolean isRunning = false;

    // Constructors

    public Game(String name) {
        this.name = name;
    }

    public Game() {
        this.name = "NO_NAME";
    }

    // Getter Methods

    // Get the name of the game
    public String getName() {
        return this.name;
    }

    // Get whether or not the game is running or not
    public boolean getIsRunning() {
        return this.isRunning;
    }

    // Setter Methods

    // Run the game if not running
    public void startRunning() {
        if (isRunning) {
            System.out.println("Game is already running!");
        } else {
            this.isRunning = true;
        }        
    }

    // Stop the game if running
    public void stopRunning() {
        if (!isRunning) {
            System.out.println("Game is already not running!");
        } else {
            this.isRunning = false;
        }
    }

    // Game Logic

    protected abstract void init();                                 // Sets up any game variables
    protected abstract void gameLogic();                            // Runs the game logic
    protected abstract void cleanUp();                              // Cleans up the game (closes variables, free's memory, etc.)    

    // Starts the game
    // 1. Initialization
    // 2. Starts running - Game Loop
    // 3. Runs the logic for the game
    // 4. Cleanup
    public void start() {
        init();
        startRunning();
        while(this.isRunning) {
            gameLogic();
        }
        cleanUp();
    }

}
