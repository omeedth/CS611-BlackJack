/*
 *  Author: Alex Thomas
 *  Creation Date: 10/5/2020
 *  Last Modified: 10/14/2020
 *  Purpose: This class defines everything you need in order to play the CardGame BlackJack
 * 
 */

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BlackJack extends CardGame<StandardCard> implements isTurnable<BlackJackPlayer,Hand<StandardCard>>, isWinnable<BlackJackPlayer,Object>, RuleLoadable {
    
    // Attributes
    private final BlackJackDealer dealer;                          // Someone who manages the cards and their allocation
    private final BlackJackMoves BJMoves;                          // A defined set of BlackJackMoves
    private ArrayList<BlackJackPlayer> players;                    // The players playing the game
    // private PlayerManager<BlackJackPlayer> playerManager;          // 
    private int bustValue;                                         // sets the value at which the player's will bust
    private int dealerValueThreshold;                              // defines the value the Dealer strives toward when drawing cards

    private Scanner input = new Scanner(System.in);                                               // Input for the players
    private HashMap<String, Integer[]> cardFaceToValue = new HashMap<String, Integer[]>();        // Mapping of cards to values

    private static final String name = "BlackJack";
    private static final Deck<StandardCard> DEFAULT_DECK = StandardCard.createStandard52Deck();
    private static final int DEFAULT_BUST = 21;
    private static final int DEFAULT_DEALER_THRESHOLD = 17;
    private static final ArrayList<BlackJackPlayer> DEFAULT_PLAYERS = new ArrayList<BlackJackPlayer>(Arrays.asList(new BlackJackPlayer[] {new BlackJackPlayer()}));

    // Constructors

    public BlackJack(Deck<StandardCard> deck, Collection<BlackJackPlayer> players, int bustValue, int dealerValueThreshold) {
        super(name,deck);

        // Create the Dealer (Consider having Dealer extend BlackJackPlayer OR creating a CardPlayer Class and having it extend that)
        this.dealer = new BlackJackDealer();        

        // By Default there is one player (Assigned a DEFAULT name)
        this.players = new ArrayList<BlackJackPlayer>(players);

        // Defines all of the Moves in BlackJack
        this.BJMoves = new BlackJackMoves();
        
        this.bustValue = bustValue;

        this.dealerValueThreshold = dealerValueThreshold;

        // Default Values Assigned To Cards
        cardFaceToValue.put("A", new Integer[] {11, 1});
        cardFaceToValue.put("2", new Integer[] {2});
        cardFaceToValue.put("3", new Integer[] {3});
        cardFaceToValue.put("4", new Integer[] {4});
        cardFaceToValue.put("5", new Integer[] {5});
        cardFaceToValue.put("6", new Integer[] {6});
        cardFaceToValue.put("7", new Integer[] {7});
        cardFaceToValue.put("8", new Integer[] {8});
        cardFaceToValue.put("9", new Integer[] {9});
        cardFaceToValue.put("10", new Integer[] {10});
        cardFaceToValue.put("J", new Integer[] {10});
        cardFaceToValue.put("Q", new Integer[] {10});
        cardFaceToValue.put("K", new Integer[] {10});
    }

    public BlackJack(Collection<BlackJackPlayer> players, int bustValue, int dealerValueThreshold) {
        this(DEFAULT_DECK,players,bustValue,dealerValueThreshold);
    }

    public BlackJack(Collection<BlackJackPlayer> players, int bustValue) {
        this(DEFAULT_DECK,players,bustValue,DEFAULT_DEALER_THRESHOLD);
    }

    public BlackJack(Collection<BlackJackPlayer> players) {
        this(DEFAULT_DECK,players,DEFAULT_BUST,DEFAULT_DEALER_THRESHOLD);
    }

    public BlackJack(int bustValue, int dealerValueThreshold) {
        this(DEFAULT_DECK,DEFAULT_PLAYERS,bustValue,dealerValueThreshold);
    }

    public BlackJack(int bustValue) {
        this(DEFAULT_DECK,DEFAULT_PLAYERS,bustValue,DEFAULT_DEALER_THRESHOLD);
    }

    public BlackJack() {
        this(DEFAULT_DECK,DEFAULT_PLAYERS,DEFAULT_BUST,DEFAULT_DEALER_THRESHOLD);        
    }

    // Getter Methods

    // Returns the dealer
    public BlackJackDealer getDealer() {
        return this.dealer;
    }

    // Returns the list of players
    public ArrayList<BlackJackPlayer> getPlayers() {
        return this.players;
    }

    // Setter Methods

    // BlackJack Logic

    /**
     * Initializes everything necessary to play BlackJack - (this will be run automatically before you start the game, DONT CALL IT MANUALLY)
     */
    @Override
    protected void init() {
        this.getDeck().shuffle();
    }   

    /**
     * Runs the game logic for the game BlackJack - (this method will run over and over again until the game is finished, DONT CALL IT MANUALLY)
     */
    @Override
    protected void gameLogic() {

        // Player bets an amount
        bet();

        // Play a round of BlackJack
        playRound();        

        // Finish Game
        this.stopRunning();

    }   

    /**
     * TODO: make this neater and potentially add cleanup methods in other classes to make this shorter
     * Cleans up everything from the game (this will be run automatically after the game ends, DONT CALL IT MANUALLY)
     */
    @Override
    protected void cleanUp() {

        // Debugging //
        // System.out.println("Deck Size (Before Returned Cards): " + this.getDeck().getCards().size());

        // All players return their cards to the Deck
        for(BlackJackPlayer p : this.players) {
            for(Hand<StandardCard> hand : p.getHands()) {
                for(StandardCard c : hand.getCards()) {
                    this.getDeck().getCards().push(c);
                }                            
            }

            // Clear all hands of cards
            p.getHands().clear();
            p.getHands().add(new Hand<StandardCard>());

            // Reset which hands are active
            p.getActiveHands().clear();
            p.getActiveHands().add(p.getHands().get(0));

            // Reset bets to 0
            p.getBets().clear();
            p.setBet(0, p.getHands().get(0));
        }

        // Reset Dealer's Hand as well
        for(StandardCard c : dealer.getHand().getCards()) {
            this.getDeck().getCards().push(c);
        }
        dealer.getHand().getCards().clear();

        // Reshuffle
        this.getDeck().shuffle();

        // Debugging //
        // System.out.println("Deck Size (After Returned Cards): " + this.getDeck().getCards().size());
    }   

    /**
     * Plays the turn for the current player or team specified by the TeamManager
     */
    @Override
    public Object playTurn(BlackJackPlayer player, Hand<StandardCard> hand) {
        // Define the resulting BlackJackMove from the Player's input (Not initialized yet)
        BlackJackMove res;        

        // Format the prompt with the possible moves
        String promptString = "Player " + player + "\'s turn for Hand: " + hand + "! Please do one of the following actions (\"%s\",\"%s\",\"%s\",\"%s\"): ";
        promptString = String.format(promptString, (Object[]) this.BJMoves.getAllMovesAsStrings());                                     // Slow O(n) - We should predefine this at the constructor

        // Keep prompting for input until valid input
        boolean valid = false;
        StandardCardComparators standardCardComparators = new StandardCardComparators();
        do {

            // Prompts user for what action they would like to take
            String result = ScannerUtility.prompt(this.input, promptString);
            res = BJMoves.getMoveFromString(result);

            // For the 'Split' move, the Player must have duplicate cards AND enough money to bet the same amount for that Hand
            // For the 'Double Up' move, the Player must have enough money to bet the additional amount            
            boolean noDuplicateCards = hand.getDuplicates(standardCardComparators.getCardFaceComparator()).size() == 0;
            boolean notEnoughMoney = player.getMoney() < player.getBets().getOrDefault(hand, 0);
            if (res == null) {
                System.out.println("Invalid Move!");
            } else if (res == BlackJackMove.SPLIT && (noDuplicateCards || notEnoughMoney)) {
                System.out.println("Unable to split your hand! You must have duplicate cards, and enough money to bet $" + player.getBets().get(hand));
            } else if (res == BlackJackMove.DOUBLE_UP && notEnoughMoney) {
                System.out.println("Unable to double up! You must have enough money to bet $" + player.getBets().get(hand) + " more money!");
            } else {
                valid = true;
            }

        } while(!valid);                

        return res;
    }

    /**
     * @param arg - the argument to take in when evaluating which player won
     * @return a collection of players who have won
     */
    @Override
    public Collection<BlackJackPlayer> playersWon(Object arg) {

        // Initialize a Collection of all winners to be empty
        ArrayList<BlackJackPlayer> winners = new ArrayList<BlackJackPlayer>(this.players.size());

        // Define the value the players are trying to beat from the Dealer's Hand
        int valToBeat = calculateHandValue(dealer.getHand().getCards());

        for (BlackJackPlayer p : this.players) {

            boolean playerWon = false;

            for (Hand<StandardCard> hand : p.getHands()) {

                int playerHandVal = calculateHandValue(hand.getCards());

                // TODO: make this more concise!
                // Checking to see if this Hand beat the Dealer's Hand
                if (bust(playerHandVal)) {
                    // Player automatically lost - Allocating Bet Money to Dealer
                    System.out.println("Player " + p + "\'s Hand: " + hand + " lost to the Dealer\'s!");
                    dealer.increaseMoneyTaken(p.getBets().get(hand));
                } else if (bust(valToBeat) || playerHandVal > valToBeat) {
                    playerWon = true;

                    // Allocating Bet Money to Player
                    System.out.println("Player " + p + "\'s Hand: " + hand + " beat the Dealer\'s! Allocating Bet Money...");
                    dealer.increaseMoneyGiven(p.getBets().get(hand)); 
                    p.retrieveBetMoney(hand, 2); 
                } else {
                    // Dealer Won because their hand was greater than or equal to the players - Allocating Bet Money to Dealer
                    System.out.println("Player " + p + "\'s Hand: " + hand + " lost to the Dealer\'s!");
                    dealer.increaseMoneyTaken(p.getBets().get(hand));
                }

            }

            System.out.println("Player " + p + " has $" + p.getMoney() + " remaining!");
            System.out.println("Dealer " + dealer + " has given $" + dealer.getMoneyGiven() + ", and taken $" + dealer.getMoneyTaken() + " thus far!");
            
            if (playerWon) {
                winners.add(p);
            }

        }

        return winners;
    }   

    /* BlackJack specific logic */
    // Plays a round of BlackJack
    private Collection<BlackJackPlayer> playRound() {

        // At the start of the round Dealer deals two cards to each player(I consider Teams to be players)
        int cardsToDeal = 2;
        for (BlackJackPlayer p : this.getPlayers()) {
            for (int dealtCards = 0; dealtCards < cardsToDeal; dealtCards++) {
                p.getHands().get(0).getCards().add((StandardCard) this.getDeck().drawCard(true));                        
            }       
            System.out.println("Player " + p + "\'s Hand: " + p.getHands().get(0));
        }        

        // Deal cards to the dealer
        dealer.addCardToHand((StandardCard) this.getDeck().drawCard(true));  // face-up AKA known to everyone
        dealer.addCardToHand((StandardCard) this.getDeck().drawCard());  // face-down AKA only known by the dealer

        System.out.println("Dealer\'s Hand: " + dealer.getHand());

        // Create a list of current playing teams AKA teams who have NOT bust and have NOT done 'stand'
        HashSet<BlackJackPlayer> activePlayers = new HashSet<BlackJackPlayer>(this.players);      

        // While there are active players (active - a player who has a hand that is not BUST or played the move 'STAND')    
        while(!activePlayers.isEmpty()) {   // Infinite loop here when restarting the game - somehow Player objects no longer match what's in the set

            // Debugging //
            // System.out.println("Active Players: " + activePlayers);

            // Ask each of the Players still playing to 'Stand', 'Hit', etc. per active player
            for (BlackJackPlayer p : this.players) {     
                
                if(!activePlayers.contains(p)) {
                    continue;
                }

                // Go through the player's active hands 
                int i = 0;
                int numOfHands = p.getHands().size();
                while (i < numOfHands) {

                    Hand<StandardCard> hand = p.getHands().get(i);

                    if(!p.getActiveHands().contains(hand)) {
                        i++;
                        continue;
                    }

                    // Debugging //
                    // System.out.println("Active Hands: " + p.getActiveHands());
                    // System.out.println("This Hand: " + hand);

                    // Gets the move corresponding to this player and this specific hand
                    BlackJackMove move = (BlackJackMove) playTurn(p,hand);     // Consider reworking BlackJackMove and BlackJackMoves

                    // Run the logic for the move for the player  
                    switch(move) {
                        case HIT: 
                            hit(p,hand,activePlayers);
                            break;
                        case STAND:
                            stand(p,hand,activePlayers);
                            break;
                        case SPLIT:
                            split(p,hand,activePlayers);
                            break;
                        case DOUBLE_UP:
                            doubleUp(p, hand, activePlayers);
                            break;
                    }

                    i++;

                }                

            }
        }

        // Dealer reveals their face down card
        dealer.getHand().reveal();
        System.out.println("Dealer\'s Hand Revealed: " + dealer.getHand() + ", Value: " + calculateHandValue(dealer.getHand().getCards()));

        // Dealer continually 'hit's until their hand value reaches or exceeds 17
        int dealerHandValue = calculateHandValue(dealer.getHand().getCards());
        while (dealerHandValue < this.dealerValueThreshold) {
            // Draw card and add to value
            dealer.addCardToHand(this.getDeck().drawCard(true));
            dealerHandValue = calculateHandValue(dealer.getHand().getCards());

            System.out.println("Dealer\'s Hand: " + dealer.getHand() + ", Value: " + dealerHandValue);
        }        

        for(BlackJackPlayer p : this.players) {
            p.revealHands();
            for(Hand<StandardCard> hand : p.getHands()) {
                System.out.println("Player " + p + "\'s Hand: " + hand + ", Value: " + calculateHandValue(hand.getCards()));
            }
        }

        return playersWon(null);

    }

    /**
     * @param value The value of the hand to check
     * @return (true/false) whether or not the value is a bust
     */
    public boolean bust(int value) {
        return value > this.bustValue;
    }

    /**
     * TODO: Clean this later
     * ASSUMPTIONS: this bet will always be on the main hand
     * This is where each player casts their initial bets
     */
    public void bet() {
        String numberRegex = "^[0-9]+$"; // Looking for a number n WHERE (0 <= n < min(total_money, betting_limit))

        // Cast bet for each Player
        for (BlackJackPlayer p : this.players) {
            boolean valid = false;
            do {
                String betString = ScannerUtility.promptTillValid(this.input, "Player " + p + "! Please input the amount you would like to bet: ", numberRegex);
                int betAmount = Integer.parseInt(betString);
                if (betAmount <= p.getMoney()) {
                    valid = true;
                    p.setBet(betAmount, p.getHands().get(0));   // Hard Coded for now                    
                }
            } while(!valid); 

            System.out.println("Player " + p + " has $" + p.getMoney() + " remaining!");

        }        
    }

    // Calculates the value of the players hand
    public int calculateHandValue(Collection<StandardCard> hand) {
        int res = 0;
        int aceCount = 0;
        String ACE = "A";

        for (StandardCard card : hand) {
            String cardFace = card.getCardFace();
            if (cardFace.equals(ACE)) {
                aceCount++;
            }
            res += this.cardFaceToValue.get(cardFace)[0];
            if (bust(res) && aceCount > 0) {
                res -= this.cardFaceToValue.get(ACE)[0]; 
                res += this.cardFaceToValue.get(ACE)[1];
                aceCount--;
            }
        }

        return res;
    }

    //--- BlackJack Move Logic --------------------------------------------------------------------------//

    // Does the logic for hit in BlackJack
    // 1. gives the player a new card in the assigned Hand object
    // 2. Checks the value to see if they bust
    // 3. Prints out the new hand
    private void hit(BlackJackPlayer p, Hand<StandardCard> hand, HashSet<BlackJackPlayer> activePlayers) {

        // Draw a card and add it to 'hand' for this Player 'p'
        StandardCard card = (StandardCard) this.getDeck().drawCard(true);   // Draw the card face-up

        // If we were unable to draw a card -> Add an additional Standard 52 card deck to this one
        if (card == null) {
            System.out.println("Ran out of cards! Adding another deck...");
            this.getDeck().getCards().addAll(StandardCard.createStandard52Deck().getCards());
        }

        hand.getCards().add(card);
        int handValue = calculateHandValue(hand.getCards());                            

        // Calculate hand value and if this hand is bust, then remove it from hands in play!
        // If Player 'p' has no more active hands remove this Player from 'activePlayer'
        if (bust(handValue)) {
            hand.reveal();
            System.out.print("Bust! ");
            p.getActiveHands().remove(hand);
            if (p.getActiveHands().isEmpty()) {
                activePlayers.remove(p);
            }
        }

        // Show on command line
        System.out.println("Player " + p + "\'s Hand: " + hand);

    }

    // Does the logic for stand in Blackjack
    // 1. Removes this hand from the active hands
    private void stand(BlackJackPlayer p, Hand<StandardCard> hand, HashSet<BlackJackPlayer> activePlayers) {

        // Remove this hand from play for the current player
        p.getActiveHands().remove(hand);

        // If Player 'p' has no more active hands remove this Player from 'activePlayer'
        if (p.getActiveHands().isEmpty()) {
            activePlayers.remove(p);
        }

    }

    // Does the logic for split in BlackJack
    // ASSUMPTION: player must have atleast two cards of the same kind (Card Face)
    // 1. Splits hand into two separate hands (put duplicate cards into other hand)
    // 2. Places Bet equal to their original bet
    // 3. HIT for both hands
    private void split(BlackJackPlayer p, Hand<StandardCard> hand, HashSet<BlackJackPlayer> activePlayers) {

        // ASSUMPTION: player must have atleast two cards of the same kind (Card Face)
        // 1. Splits hand into two separate hands (put duplicate cards into other hand)
        // 2. Places Bet equal to their original bet
        // 3. HIT for both hands

        // Define the Set that will tell us whether or not we have seen this card before AND the list of duplicate cards
        HashSet<String> distinctCardFaces = new HashSet<>();
        ArrayList<StandardCard> duplicateCards = new ArrayList<StandardCard>(hand.getCards().size() / 2);

        // Cycle through the Cards in the hand and add the duplicates into a separate list
        for (StandardCard c : hand.getCards()) {
            if (distinctCardFaces.contains(c.getCardFace())) {
                duplicateCards.add(c);                                    
            } else {
                distinctCardFaces.add(c.getCardFace());
            }
        }

        // Remove all duplicate cards from Hand
        hand.getCards().removeAll(duplicateCards);

        // Create a new Hand AND add the duplicates to that hand for this Player
        Hand<StandardCard> newHand = new Hand<StandardCard>(duplicateCards);
        p.getHands().add(newHand);
        p.getActiveHands().add(newHand);

        // Sets the same bet for this new Hand
        p.setBet(p.getBets().get(hand), newHand);

        System.out.println("Player " + p + " has $" + p.getMoney() + " remaining!");

        // HIT for both the current Hand and the new Hand
        hit(p,hand,activePlayers);
        hit(p,newHand,activePlayers);

    }

    // Does the logic for double-up in BlackJack
    // ASSUMPTION: player has enough money to bet to DOUBLE UP
    // 1. Doubles Bet
    // 2. does HIT
    // 3. does STAND
    private void doubleUp(BlackJackPlayer p, Hand<StandardCard> hand, HashSet<BlackJackPlayer> activePlayers) {

        // ASSUMPTION: player has enough money to bet to DOUBLE UP
        // 1. Doubles Bet
        // 2. does HIT
        // 3. does STAND

        // Double the bet for this hand
        int upFactor = 1;
        p.increaseBet(p.getBets().get(hand) * upFactor, hand);

        System.out.println("Player " + p + " has $" + p.getMoney() + " remaining!");

        // Hit
        hit(p,hand,activePlayers);

        // Stand
        stand(p,hand,activePlayers);

    }

    //--- DEVELOPMENTAL Load Rule Logic --------------------------------------------------------------------------//

    // DEVELOPMENT - returns the mapping of all of the existing rules for this game
    @Override
    public List<Rule<?>> getMutableRules() {
        List<Rule<?>> mutableRules = new ArrayList<>(3); 
        // Map<String, Rule<? extends Object>> mutableRules = new HashMap<>();
        // mutableRules.put("Deck",DEFAULT_DECK);
        mutableRules.add(new ListRule<BlackJackPlayer>("Players",this.players,new BlackJackPlayer()));
        mutableRules.add(new NumberRule("Bust",this.bustValue));
        mutableRules.add(new NumberRule("Dealer Threshold", this.dealerValueThreshold));
        // mutableRules.put("Players",new Rule<ArrayList<BlackJackPlayer>>("Players",this.players));
        // mutableRules.put("Bust",new Rule<Integer>("Bust",this.bustValue));
        // mutableRules.put("Dealer Threshold",new Rule<Integer>("Dealer Threshold", this.dealerValueThreshold));
        return mutableRules;
    }

    // DEVELOPMENT - loads the rules into the game
    @Override    
    public boolean areRulesValid(RuleSet rules) {
        boolean hasAllRules = rules.getMutableRules().containsAll(Arrays.asList("Players","Bust","Dealer Threshold"));
        // System.out.println("Has All Rules: " + hasAllRules);
        boolean rulesAreCorrectType = true;
        // System.out.println("Rules Are Correct Type: " + rulesAreCorrectType);
        return hasAllRules && rulesAreCorrectType;
    }

    // DEVELOPMENT - loads the rules into the game
    @Override
    public void loadRules(RuleSet rules) {
        if (areRulesValid(rules)) {
            this.players = ((ListRule<BlackJackPlayer>) rules.getRule("Players")).getValue();
            this.bustValue = ((NumberRule) rules.getRule("Bust")).getValue().intValue();
            this.dealerValueThreshold = ((NumberRule) rules.getRule("Dealer Threshold")).getValue().intValue();
            System.out.println(rules);
        } else {
            System.out.println("Invalid Rules!");
        }
    }

}
