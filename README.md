# CS611 - Object Oriented BlackJack Overview

We are creating a BlackJack game using Object Oriented Design Principles.
We are decomposing BlackJack into it's sub parts and thinking about the game in a physical sense as if we are physically building it.

# Installation

Download all of the .java files into it's own folder and continue to usage

# Usage

When Running the program you can compile all of the java source code into runnable byte code by running the following command

```bash
javac App.java
```

You can run the program by using this command

```bash
java App
```

You can remove all of the class files in the current folder with this command. (Be VERY CAREFUL. This will remove all class files in the folder)

```bash
rm *.class
```

# Game Setup

If you initialize BlackJack with an empty constructor it will create a default Game with 1 player with a default name, 1 dealer, and a standard 52 card deck. You may choose to manually tweak the BlackJack variables by using the accessor methods and adding/removing players, or passing in the variables straight into the constructor during initialization.

***NOTE:*** You may also instantiate a GameManager object with the list of Game objects you would like to manage! This is under development currently but I have hardcoded a solution for BlackJack at the very least for this project

```java
public static void main(String args[]) {

    /* 1. Using the Game Manager */
    GameManager gm = new GameManager(new BlackJack());
    gm.start();

    /* 2. Default BlackJack Game */
    BlackJack game = new BlackJack();
    game.start();

    /* 3. Adjusting BlackJack Variables */
    BlackJack game2 = new BlackJack();

    game2.getPlayers().get(0).setPlayerName("Alex");
    game2.getPlayers().add(new BlackJackPlayer("Jack"));

    game2.start();

    /* 4. Passing in rulesets inside the constructor */

    // Defining Players
    ArrayList<BlackJackPlayer> players = new ArrayList<BlackJackPlayer>();
    players.add(new BlackJackPlayer("Alex"));
    players.add(new BlackJackPlayer("Jack"));

    // Defining Bust Value
    int bustValue = 50;

    BlackJack game3 = new BlackJack(players,bustValue);
    game3.start();

}
```

# Classes

## Launcher Classes

---

***App.java*** - The Launcher for the code

## Player Classes

---

***PlayerManager.java*** - ***(IN DEVELOPMENT)*** Defines a class that manages all Player objects

***Player.java*** - Defines an abstract Player object and allows basic functionality in accessing player name, and Unique ID

***CardGamePlayer.java*** - Defines an abstract Player for a CardGame that has a collection of Hands

***BlackJackPlayer.java*** - Defines the BlackJackPlayer which is able to cast bets on specific Hands

***BlackJackDealer.java*** - Defines the Dealer for the BlackJack Game which extends functionality from the CardGamePlayer

## Move Classes

---

***BlackJackMove.java*** - An enum class that defines each possible move in BlackJack

***BlackJackMoves.java*** - A class that groups up the the BlackJackMove enum objects together and offers basic functionality

## Card Classes

---

***Card.java*** - Defines an abstract class for Card and gives basic functionality such as whether or not the Card is faceUp or faceDown

***Usable.java*** - ***(Not Used)*** Defines an interface declaring functionality for 'using' a card (This is for card games that have cards with more logic)

***UsableCard.java*** - ***(Not Used)*** Defines a Card that is able to be 'used' (This is for card games that have cards with more logic)

***Suit.java*** - an enum class that defines the Suit for a StandardCard 

***StandardCard.java*** - Defines a class for a defining a Standard Card which would generate a standard 52 Deck

***Deck.java*** - Defines a class for holding a stack of cards and basic Deck logic

***Hand.java*** - Defines a class for the Hands of Card objects CardGamePlayers will be using

## Game Interfaces

---

***isTurnable.java*** - Defines an interface for Games to define them as turn based

***isWinnable*** - Defines an interface for Games in which players are able to win

## Utility Classes

---

***ScannerUtility.java*** - Defines a class that gives functionality for prompting and validating responses from the Command Line

***StandardCardComparator.java*** - Defines a class that stores all of the Comparator objects for the StandardCard class

***TypeChecking.java*** - Defines a class that stores methods that check the types of objects and does comparisons

## Rule Loading Classes

---

***Modifiable.java*** - ***(IN DEVELOPMENT)*** Defines an interface for modifiable objects

***Rule.java*** - ***(IN DEVELOPMENT)*** Defines an abstract class for holding rules

***NumberRule.java*** - ***(IN DEVELOPMENT)*** Defines a class that handles rules for Number objects

***ListRule.java*** - ***(IN DEVELOPMENT)*** Defines a class that handles rules for List objects

***RuleSet.java*** - ***(IN DEVELOPMENT)*** Defines a class that is able to store mappings of rules to pass into RuleLoadableObjects AKA Games

***RuleLoadable.java*** - ***(IN DEVELOPMENT)*** An interface meant to define methods for loadingRules into a Game and retrieving the rules from a Game

***GameManager.java*** - ***(IN DEVELOPMENT)*** A class that manages multiple games, the rulesets for each game, etc.

## Game Classes

---

***Game.java*** - Defines an abstract class for a Game

***CardGame.java*** - Defines an abstract CardGame that extends Game

***BlackJack.java*** - Defines a BlackJack Game that extends CardGame

# Improvements

1. Create more classes for ease of use of users and readability for developers. For example: creating a Money and Currency class that can define the amount of money a person has instead of holding it in an int primitive

2. Create a Player Manager class so that for games we don't have to manually manage the list of players (I had this for TicTacToe and wanted to see how it is like without it to compare)

3. Use Generics and Wildcards more appropriately... I took the liberty of adding Generics and Wildcards into my code after lecture to see if I can learn how to use them better

4. Think more deeply of a modular way to load rule sets into the game from user input! My method currently is under development and I did not have enough time to think it through all the way

5. Consider adding a ScoreBoard Class that has mappings of Player's to win statistics

6. Separate some of the logic out of the BlackJack Class. For Example: The Dealer can be allocated the job of overseeing bets and who won after the round is over, the PlayerManager Class can iterate over all players for the BlackJack game instead of the game having to write the for loops each time to choose players

7. To make my code more extendable and abstract, I can make the RuleLoadable Interface not specify that it needs to be a Game and change around my logic when constructing RuleSets


