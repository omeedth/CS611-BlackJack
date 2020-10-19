/*
 *  Author: Alex Thomas
 *  Creation Date: 10/9/2020
 *  Last Modified: 10/14/2020
 *  Purpose: Enum that categorizes each one of the BlackJack Moves
 * 
 */

enum BlackJackMove {
    HIT("hit"),
    STAND("stand"),
    SPLIT("split"),
    DOUBLE_UP("double up");

    private String str;

    private BlackJackMove(String s) {
        str = s;
    }

    @Override
    public String toString() {
        return str;
    }

}