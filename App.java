/*
 *  Author: Alex Thomas
 *  Creation Date: 10/5/2020
 *  Last Modified: 10/5/2020
 *  Purpose: This is the game launcher for my game code (CS 611 BlackJack Assignment)
 * 
 */

import java.util.Scanner;

public class App {

    public static void main(String args[]) {

        // IN DEVELOPMENT - VERY MESSY CODE
        GameManager gm = new GameManager(new BlackJack());
        gm.start();

        /*---------*/

        /* Alternate Game Launching Code 
        // Setup Game //

        // Empty Constructor means a DEFAULT game of BlackJack
        BlackJack blackJack = new BlackJack();

        // Code showing how to change player's names and adding additional players        
        blackJack.getPlayers().get(0).setPlayerName("Alex");
        blackJack.getPlayers().add(new BlackJackPlayer("Peter"));
    
        // Start Game //

        // Decided whether or not to play another round 
        Scanner input = new Scanner(System.in);
        boolean wantToPlay = true;
        do {

            // Starts the game
            blackJack.start();

            // Prompts user if they would like to play again
            String response = ScannerUtility.promptTillValid(input, "Would you like to play another round(y/n): ", "^(y|n)$");

            if (response.equals("n")) {
                wantToPlay = false;
            }

        } while(wantToPlay);    
        
        input.close();
        */
    
     }

}