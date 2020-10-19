/*
 *  Author: Alex Thomas
 *  Creation Date: 10/5/2020
 *  Last Modified: 10/14/2020
 *  Purpose: This is an abstract class defining what a Card is
 * 
 */

public abstract class Card {
    
    // Attributes
    boolean faceUp;     // Card will show it's values if face-up, but won't if face-down

    // Constructors

    public Card() {
        faceUp = false;
    }

    // Getter Methods

    // Returns true if the card is face-up and false if the card is face-down
    public boolean getIsFaceUp() {
        return this.faceUp;
    }

    // Setter Methods

    // Sets the card to face-up
    public void setFaceUp() {
        this.faceUp = true;
    }

    // Sets the card to face-down
    public void setFaceDown() {
        this.faceUp = false;
    }

    // Card Logic

    public String toString() {
        String res;

        if(this.faceUp) {
            res = "Known Card";
        } else {
            res = "Unknown Card";
        }

        return res;
    }

}
