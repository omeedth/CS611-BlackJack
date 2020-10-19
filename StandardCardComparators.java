/*
 *  Author: Alex Thomas
 *  Creation Date: 10/14/2020
 *  Last Modified: 10/14/2020
 *  Purpose: A list of all the comparators for the StandardCard Class
 * 
 */

import java.util.Comparator;
import java.util.HashMap;

public final class StandardCardComparators {
    
    // Attributes
    private HashMap<String, Comparator<StandardCard>> standardCardComparators = new HashMap<String, Comparator<StandardCard>>();

    // Constructors

    public StandardCardComparators() {
        standardCardComparators.put("Suit",new StandardCardComparators.SortBySuit());
        standardCardComparators.put("CardFace",new StandardCardComparators.SortByCardFace());
        standardCardComparators.put("Name",new StandardCardComparators.SortByName());
    }  
    
    // Methods

    // Returns the comparator for comparing StandardCard objects by Suit
    public Comparator<StandardCard> getSuitComparator() {
        return this.standardCardComparators.get("Suit");
    }

    // Returns the comparator for comparing StandardCard objects by Card Face
    public Comparator<StandardCard> getCardFaceComparator() {
        return this.standardCardComparators.get("CardFace");
    }

    // Returns the comparator for comparing StandardCard objects by Name
    public Comparator<StandardCard> getNameComparator() {
        return this.standardCardComparators.get("Name");
    }

    // Comparators
    
    // The comparator for comparing StandardCard objects by Suit
    public class SortBySuit implements Comparator<StandardCard> {

        // Used for sorting in ascending order by Suit
        @Override
        public int compare(StandardCard card1, StandardCard card2) {
            return card1.getSuit().toString().compareTo(card2.getSuit().toString());
        }

    }

    // The comparator for comparing StandardCard objects by Card Face
    public class SortByCardFace implements Comparator<StandardCard> {

        // Used for sorting in ascending order by CardFace
        @Override
        public int compare(StandardCard card1, StandardCard card2) {
            return card1.getCardFace().compareTo(card2.getCardFace());
        }

    }

    // The comparator for comparing StandardCard objects by Name
    public class SortByName implements Comparator<StandardCard> {

        // Used for sorting in ascending order by Name
        @Override
        public int compare(StandardCard card1, StandardCard card2) {
            return card1.getName().compareTo(card2.getName());
        }

    }

}
