/*
 *  Author: Alex Thomas
 *  Creation Date: 10/5/2020
 *  Last Modified: 10/5/2020
 *  Purpose: This is an abstract class defining what a deck of Cards are 
 * 
 */

import java.util.Collections;
import java.util.Stack;

public class Deck<T extends Card> { // extends Stack<Card> - Would I ever want to make this extend Stack?
    
    // Attributes
    private Stack<T> cards;       // Consider finding a better data type

    // Constructors

    public Deck() {
        cards = new Stack<T>();
    }

    // Getter Methods

    public Stack<T> getCards() {
        return this.cards;
    }

    // Setter Methods

    // Deck Logic

    /**
     * Shuffles the deck
     */
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    /**
     * Draw's a card face-up
     */
    public T drawCard(boolean faceUp) {
        // Check to see if there are cards still remaining
        if (this.cards.isEmpty()) {
            return null;
        }
        T card = this.cards.pop();
        if (faceUp) {
            card.setFaceUp();
        } else {
            card.setFaceDown();
        }
        return card;
    }

    // Draw's a card face-down
    public Card drawCard() {
        return drawCard(false);
    }

}
