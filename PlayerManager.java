/*
 *  Author: Alex Thomas
 *  Creation Date: 10/18/2020
 *  Last Modified: 10/18/2020
 *  Purpose: Defines a class for managing Playerss
 * 
 */

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PlayerManager implements Modifiable {

    // Attributes
    private List<Player> players;

    // Constructors

    public PlayerManager(List<Player> players) {
        this.players = players;
    }

    @SafeVarargs
    public PlayerManager(Player... players) {
        this(Arrays.asList(players));
    }

    public PlayerManager() {
        this(List.of());
    }

    // Getter Methods

    // Setter Methods

    // PlayerManager Logic Methods

    @Override
    public void modify(Scanner input) {

    }

}
