/*
 *  Author: Alex Thomas
 *  Creation Date: 10/5/2020
 *  Last Modified: 10/14/2020
 *  Purpose: This class defines an interface for what's necessary in a turn based game
 * 
 */

import java.util.Collection;

public interface isWinnable<T extends Player, A extends Object> {
    
    abstract Collection<T> playersWon(A arg);    // Returns Collection of players who won

}
