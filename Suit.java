/*
 *  Author: Alex Thomas
 *  Creation Date: 10/7/2020
 *  Last Modified: 10/14/2020
 *  Purpose: This categorizes the suits there are on a StandardCard
 * 
 */

enum Suit {
    CLUBS("black"),
    DIAMONDS("red"),
    HEARTS("red"),
    SPADES("black");

    private String color;

    private Suit(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public String getColor() {
        return this.color;
    }

}