/*
 *  Author: Alex Thomas
 *  Creation Date: 10/5/2020
 *  Last Modified: 10/14/2020
 *  Purpose: This class defines an interface for what's necessary in a turn based game
 * 
 */

public interface isTurnable<P extends Player, A extends Object> {
    
    abstract Object playTurn(P p, A arg);     // Plays a turn of a game with given player and a given argument, and returns anything

}
