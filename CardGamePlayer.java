/*
 *  Author: Alex Thomas
 *  Creation Date: 10/14/2020
 *  Last Modified: 10/14/2020
 * 
 * Class Definition: Defines an abstract Player for Card Games
 * 
*/

import java.util.ArrayList;
import java.util.HashSet;

public abstract class CardGamePlayer<T extends Card> extends Player {
    
    // Attributes
    private ArrayList<Hand<T>> hands;        // Some games may involve multiple Hands
    private HashSet<Hand<T>> activeHands;    // A list of the active hands

    // Constructors

    public CardGamePlayer(String name) {
        super(name);

        // Initialize Hands
        this.hands = new ArrayList<Hand<T>>();
        this.hands.add(new Hand<T>());

        // Initialize active hands
        this.activeHands = new HashSet<>(hands);        
    }

    public CardGamePlayer() {
        this(new String("Player"));
    }

    // Getter Methods

    // Returns the list of all hands the player has
    public ArrayList<Hand<T>> getHands() {
        return this.hands;
    }

    // Returns the hands currently in play for the game
    public HashSet<Hand<T>> getActiveHands() {
        return this.activeHands;
    }

    // Setter Methods

    // CardGamePlayer Logic Methods

}
