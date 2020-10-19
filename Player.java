/*
 *  Author: Alex Thomas
 *  Creation Date: 10/5/2020
 *  Last Modified: 10/7/2020
 * 
 * Class Definition: Defines an abstract Player with a name and an id
 * 
*/

public abstract class Player {
    
    // Attributes
    private String playerName;
    private int playerId;

    private static int currPlayerId = 0;

    // Constructors

    public Player(String name) {
        this.setPlayerId(currPlayerId);
        this.setPlayerName(name);
        currPlayerId++;
    }

    public Player() {
        this(new String("Player"));
    }

    // Getter Methods

    // Returns the Player's name
    public String getPlayerName() {
        return this.playerName;
    }

    // Returns the Player's ID
    public int getPlayerId() {
        return this.playerId;
    }

    // Setter Methods

    // Sets the Player's Name
    public void setPlayerName(String name) {
        if(name == null) {
            throw new IllegalArgumentException();
        }
        this.playerName = name;
    }

    // Set's the Player's ID
    private void setPlayerId(int Id) {
        this.playerId = Id;
    }

    // Player Logic Methods

    @Override
    public String toString() {
        return this.getPlayerName();
    }

}   
