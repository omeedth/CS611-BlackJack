/*
 *  Author: Alex Thomas
 *  Creation Date: 10/5/2020
 *  Last Modified: 10/7/2020
 * 
 * Class Definition: Defines an BlackJackPlayer
 * 
*/

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BlackJackPlayer extends CardGamePlayer<StandardCard> implements Modifiable {

    // Attributes
    private HashMap<Hand<StandardCard>, Integer> bets; // Bets placed on each hand
    private int money; // Dollar amount

    // Constructors

    public BlackJackPlayer(String name) {
        super(name);

        // Initialize bets (Consider having this map to 'Money' class)
        this.bets = new HashMap<Hand<StandardCard>, Integer>();
        this.bets.put(this.getHands().get(0), 0);

        this.money = 100; // Let's say you must pay $100 upfront to play the game (Consider making this a
                          // 'Money' class)
    }

    public BlackJackPlayer() {
        this("Player");
    }

    // Getter Methods

    // Returns the HashMap pairings of Hands and Bets
    public HashMap<Hand<StandardCard>, Integer> getBets() {
        return this.bets;
    }

    // Returns this player's total remaining balance
    public int getMoney() {
        return this.money;
    }

    // Setter Methods

    // Set's the bet for this current Hand
    public boolean setBet(int betValue, Hand<StandardCard> hand) {

        // If pre-existing bet for hand exists AND the bet is higher than the previous
        // bet increaseBet()
        if (betValue > this.bets.getOrDefault(hand, 0)) {
            int prevBet = this.bets.getOrDefault(hand, 0);
            increaseBet(betValue - prevBet, hand);
            return true;
        }

        // Making sure we have the required money to bet
        if (betValue < 0 || betValue > this.money) {
            return false;
        }

        // Placing the bet
        this.bets.put(hand, betValue);
        this.removeMoney(betValue);
        return true;
    }

    // Increases a pre-existing bet on a hand, if no bet on the hand exists it
    // simply adds the bet in
    public boolean increaseBet(int additionalBet, Hand<StandardCard> hand) {
        if (additionalBet < 0 || additionalBet > this.money) {
            return false;
        }
        this.bets.put(hand, this.bets.getOrDefault(hand, 0) + additionalBet);
        this.removeMoney(additionalBet);
        return true;
    }

    // BlackJackPlayer Logic Methods

    // NOTE: this may not be where this function should be placed
    // Adds the money the player won on their hand
    public int retrieveBetMoney(Hand<StandardCard> winningHand, float betMultiplier) {
        if (betMultiplier <= 0) {
            throw new IllegalArgumentException();
        }
        int winnings = (int) (this.bets.getOrDefault(winningHand, 0) * betMultiplier);
        boolean moneyTransferred = this.addMoney(winnings);
        return (moneyTransferred ? winnings : 0);
    }

    // Adds money into their account
    private boolean addMoney(int moneyToAdd) {
        if (moneyToAdd < 0) {
            return false;
        }
        this.money += moneyToAdd;
        return true;
    }

    // Removes money from their account
    private boolean removeMoney(int moneyToRemove) {
        if (moneyToRemove < 0 || moneyToRemove > this.money) {
            return false;
        }
        this.money -= moneyToRemove;
        return true;
    }

    /**
     * Sets all of the Card objects to face-up
     */
    public void revealHands() {
        for (Hand<? extends Card> hand : this.getHands()) {
            for (Card card : hand.getCards()) {
                card.setFaceUp();
            }
        }
    }

    /**
     * Sets all of the Card objects to face-down
     */
    public void hideHands() {
        for (Hand<? extends Card> hand : this.getHands()) {
            for (Card card : hand.getCards()) {
                card.setFaceDown();
            }
        }
    }

    @Override
    public void modify(Scanner input) {
        
        boolean editing = true;
        String yesNoRegex = "^(y|n)$";
        List<String> options = Arrays.asList(new String[] {"Name", "Quit"});

        // Keeps prompting the user what they would like to edit until they are finished editing
        do {

            int optionIndex = ScannerUtility.chooseFromList(input, options);
            String choice = options.get(optionIndex);

            if (choice.equals("Quit")) {
                editing = false;
                continue;
            } else if (choice.equals("Name")) {
                this.setPlayerName(ScannerUtility.prompt(input, "Input new Player Name: "));
            }
            
            String response = ScannerUtility.promptTillValid(input, "Would you like to keep editing this rule(y/n): ", yesNoRegex);
            editing = response.equals("y");
            
            System.out.println("The element is now: " + this);

        } while(editing);

    }

}
