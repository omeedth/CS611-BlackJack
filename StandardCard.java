/*
 *  Author: Alex Thomas
 *  Creation Date: 10/7/2020
 *  Last Modified: 10/14/2020
 *  Purpose: This is an extension of Card where it defines the standard cards used for games in poker and such
 * 
 */

public class StandardCard extends Card {
    
    // Attributes
    Suit suit;
    String cardFace;
    String name;

    // Static Attributes
    public static final String[] standardCardFaces = new String[] {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

    // Constructors

    public StandardCard(Suit suit, String cardFace, String name) {
        this.suit = suit;
        this.cardFace = cardFace;
        this.name = name;
    }

    public StandardCard() {
        // No Suit nor CardFace 
    }

    // Getter Methods

    // Returns the Suit of the Card
    public Suit getSuit() {
        return this.suit;
    }

    // Returns the CardFace
    public String getCardFace() {
        return this.cardFace;
    }

    // Returns the name of the card (Usually 1-to-1 mapping of the cardFace)
    public String getName() {
        return this.name;
    }

    // Setter Methods


    // Card Logic

    @Override
    public String toString() {
        String res;

        if(this.faceUp) {
            res = "" + this.name + " of " + this.suit;
        } else {
            res = "Unknown Card";
        }

        return res;
    }

    // Static Methods

    // Creates a Standard 52 card deck
    public static Deck<StandardCard> createStandard52Deck() {
        Deck<StandardCard> deck = new Deck<StandardCard>();
        for (Suit suit : Suit.values()) {
            for (String cardFace : standardCardFaces) {
                String name = cardFace;
                switch(cardFace) {
                    case "A":
                        name = "Ace";
                        break;
                    case "J":
                        name = "Jack";
                        break;
                    case "Q":
                        name = "Queen";
                        break;
                    case "K":
                        name = "King";
                        break;
                }
                deck.getCards().push(new StandardCard(suit, cardFace, name));
            }
        }
        return deck;
    }

}
