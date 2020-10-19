/*
 *  Author: Alex Thomas
 *  Creation Date: 10/5/2020
 *  Last Modified: 10/14/2020
 *  Purpose: This is a class that defines the dealer for a Card Game
 * 
 */

public class BlackJackDealer extends CardGamePlayer<StandardCard> {
    
    // Attributes
    private final int HAND = 0;     // The index of the one and only hand a BlackJackDealer has
    private int moneyTaken = 0;     // How much money the Dealer won from the players
    private int moneyGiven = 0;     // How much money the Dealer awarded the players

    // Constructors

    public BlackJackDealer() {
        super("Dealer");
        this.moneyTaken = 0;
        this.moneyGiven = 0;
    }

    // Getter Methods

    // Returns the Dealer's Hand
    public Hand<StandardCard> getHand() {
        return this.getHands().get(HAND);
    }

    // Returns the total money taken from players
    public int getMoneyTaken() {
        return this.moneyTaken;
    }

    // Returns the total money given to the players
    public int getMoneyGiven() {
        return this.moneyGiven;
    }

    // Setter Methods

    // Returns the total money taken from players
    public void increaseMoneyTaken(int moneyTaken) {
        this.moneyTaken += moneyTaken;
    }

    // Returns the total money given to the players
    public void increaseMoneyGiven(int moneyGiven) {
        this.moneyGiven += moneyGiven;
    }

    // Dealer Logic

    // Since Dealer has one hand this method is for ease of use to add to their Hand
    public boolean addCardToHand(StandardCard card) {
        return this.getHands().get(HAND).getCards().add(card);
    }

}
