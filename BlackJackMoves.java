/*
 *  Author: Alex Thomas
 *  Creation Date: 10/7/2020
 *  Last Modified: 10/7/2020
 *  Purpose: This class defines what a BlackJackMove is and methods to check if a move is a BlackJackMove
 * 
 */

import java.util.HashMap;

final class BlackJackMoves {

    // Attributes
    private HashMap<String,BlackJackMove> moves;

    // Constructor

    BlackJackMoves() {
        moves = new HashMap<String,BlackJackMove>();
        for(BlackJackMove move : BlackJackMove.values()) {
            moves.put(move.toString(),move);
        }
    }

    // Getter Methods

    // Setter Methods

    // BlackJackMoves Logic Methods

    public BlackJackMove getMoveFromString(String s) {
        return moves.getOrDefault(s, null);
    }

    public String[] getAllMovesAsStrings() {
        BlackJackMove[] possibleMoves = BlackJackMove.values();
        String[] stringPossibleMoves = new String[possibleMoves.length];

        for (int i = 0; i < possibleMoves.length; i++) {
            BlackJackMove move = possibleMoves[i];
            stringPossibleMoves[i] = move.toString();
        }

        return stringPossibleMoves;
    }

}
