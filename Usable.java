/*
 *  Author: Alex Thomas
 *  Creation Date: 10/14/2020
 *  Last Modified: 10/14/2020
 *  Purpose: This defines objects in games that can be used on targets, if no target you can set it to null
 * 
 */

public interface Usable {
    public <T extends Player> void use(T target);   // Use the card on the target Player
}
