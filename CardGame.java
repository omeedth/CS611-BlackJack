/*
 *  Author: Alex Thomas
 *  Creation Date: 10/5/2020
 *  Last Modified: 10/10/2020
 *  Purpose: This is the an abstract class defining what a CardGame needs in order to be played
 * 
 */

import java.util.ArrayList;

public abstract class CardGame<C extends Card> extends Game {
    
    // Attributes
    private Deck<C> deck;                  // The deck of cards corresponding to the CardGame
    private ArrayList<C> usedCards;        // The cards that have been used in the game

    // Constructors

    public CardGame(String name, Deck<C> deck) {
        super(name);
        this.deck = deck;
        this.usedCards = new ArrayList<C>();
    }

    public CardGame(Deck<C> deck) {
        this("Card Game",deck);
    }

    public CardGame(String name) {
        this(name,new Deck<C>());
    }

    public CardGame() {
        // Do Nothing (Card game with no defined Deck)
        this(new Deck<C>());
    }

    // Getter Methods

    // Returns the Deck
    public Deck<C> getDeck() {
        return this.deck;
    }    

    // Returns the List of used cards (Unused variable in BlackJack)
    public ArrayList<C> getUsedCards() {
        return this.usedCards;
    }

    // Setter Methods

    // Set's the deck equal to a deck that you pass in
    public void setDeck(Deck<C> deck) {
        this.deck = deck;
    }

    // CardGame Logic

    // Cleanup by getting all of the used cards and putting them back into the deck then re-shuffling
    @Override
    protected void cleanUp() {
        for (C c : usedCards) {
            deck.getCards().push(c);
        }
        usedCards.clear();
        deck.shuffle();
    }

}
