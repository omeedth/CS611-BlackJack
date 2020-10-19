/*
 *  Author: Alex Thomas
 *  Creation Date: 10/10/2020
 *  Last Modified: 10/10/2020
 *  Purpose: This is a class defining what a hand of cards are (Also helps to store active hands in the HashSet)
 * 
 */

import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Comparator;

public class Hand<C extends Card> {

    // Attributes
    private ArrayList<C> cards;

    // Constructors

    public Hand(Collection<C> cards) {
        this.cards = new ArrayList<C>(cards);
    }

    public Hand() {
        this(new ArrayList<C>());
    }

    // Getter Methods

    // Gets the cards from the hand as a list - CONSIDER encapsulating the functions I can do on the Hand within this class
    public ArrayList<C> getCards() {
        return this.cards;
    }

    // Setter Methods

    // Hand Logic Methods
    
    @Override
    public String toString() {
        return this.cards.toString();
    }

    // Sets all of the cards in this Hand to face-up
    public void reveal() {
        for (Card card : this.cards) {
            card.setFaceUp();
        }
    }

    /**
     * 
     * @param comparator
     * @return A list of all the duplicates (leaves one distinct element left in the list)
     */
    public Collection<C> getDuplicates(Comparator<C> comparator) {
        if(this.cards.size() == 0) {
            return new ArrayList<C>(0);
        }

        ArrayList<C> duplicates = new ArrayList<C>(this.cards.size());
        for (int i = 0; i < this.cards.size() - 1; i++) {
            C card1 = this.cards.get(i);
            C card2 = this.cards.get(i + 1);
            boolean equal = comparator.compare(card1, card2) == 0;
            if (equal) duplicates.add(card1);                           
        }

        return duplicates;
    }

}
