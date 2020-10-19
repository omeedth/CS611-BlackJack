/*
 *  Author: Alex Thomas
 *  Creation Date: 10/17/2020
 *  Last Modified: 10/17/2020
 *  Purpose: Manages all the games you might want to play and helps define rulesets
 * 
 */

import java.util.function.Function;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class GameManager {
    
    // Attribute    
    private HashMap<String,Game> games;
    private Scanner input;

    // Constructors

    public <T extends Game & RuleLoadable> GameManager(Collection<T> games) {
        this.games = new HashMap<String,Game>(games.stream().collect(Collectors.toMap(Game::getName, Function.identity())));
        input = new Scanner(System.in);
    }

    @SafeVarargs // Possible Heap Pollution! - As long as we don't allow the user to add objects of different types we are fine
    public <T extends Game & RuleLoadable> GameManager(T... objects) {
        this(Arrays.asList(objects));
    }

    public <T extends Game & RuleLoadable> GameManager() {
        this(new ArrayList<T>());        
    }

    // Getter Methods

    // Setter Methods

    // GameManager Logic Methods

    // Starts the GameManager
    // 1. User chooses a game
    // 2. Define the rules for that game
    // 3. Start the game
    // 4. User decides whether or not they want to keep playing
    public void start() {

        boolean wantToPlay = true;
        do {

            Game game = chooseGame();
        
            if (game != null) {
                System.out.println("Current Rules:\n" + ((RuleLoadable)game).getMutableRules());
                String response = ScannerUtility.promptTillValid(input, "Would you like to adjust the rules(y/n): ", "^(y|n)$"); 
                if (response.equals("y")) {
                    defineRuleSet((RuleLoadable)game);
                }                
                game.start();
            } else {
                System.out.println("Exiting...");
            }

            // Prompts user if they would like to play again
            String response = ScannerUtility.promptTillValid(input, "Would you like to play another round(y/n): ", "^(y|n)$");

            if (response.equals("n")) {
                wantToPlay = false;
            }

        } while(wantToPlay);           

    }

    // TODO: this is hard coded right now because it is in DEVELOPMENT! Make this modular
    // Defines the rules for a game that is configured to accept rules
    private void defineRuleSet(RuleLoadable ruleLoadableObject) {
        RuleSet rules = new RuleSet(ruleLoadableObject);    // The current rules for this RuleLoadable Object

        // TODO: Depending on the Object we are trying to define the user will be given choices to edit it
        //      EX: List<Modifiable>: 
        //              1. "Modify" -> chooses index and modifies the object according to the rules defines in the interface Modifiable
        //              2. "Add" -> Constructs an empty Modifiable Object and then allows you to edit it before adding it into the List
        //              3. "Remove" -> chooses an index and delets the Modifiable Object at that index
        //              4. "Clear" -> Removes all Modifiable Objects from the List (Consider having a validRules() method defined for each game)
        //      EX: Number:
        //              1. "Modify" -> allows the user to specify a new number of the same type passed in
        //      EX: Modifiable:
        //              1. "Modify" -> Custom logic built into the Modifiable Interface

        /* Modularized Attempt */

        boolean editing = true;
        String yesNoRegex = "^(y|n)$";
        List<String> ruleNames = new ArrayList<>(rules.getMutableRules());

        do {

            // Choose the rule you would like to edit
            int choice = ScannerUtility.chooseFromList(this.input, ruleNames);
            String ruleName = ruleNames.get(choice);

            // Get the current value for the rule
            Rule<?> rule = rules.getRule(ruleName);

            // Edit the rule
            rule.modify(input);            

            // Choose whether or not you would like to continue editing
            editing = ScannerUtility.promptTillValid(this.input, "Would you like to continue editing(y/n):", yesNoRegex).equals("y");

        } while(editing);        

        /* ------------------- */

        // for (String ruleName : rules.getMutableRules()) {                        

        //     // TODO: Make this modular (This is hard coded for now)
        //     if (ruleLoadableObject instanceof BlackJack) {                
        //         Object rule = rules.getRule(ruleName);

        //         // List of players
        //         if (ruleName.equals("Players") && rule instanceof List) {
        //             final String[] options = new String[] {"Add","Remove","Clear","Quit"};                    

        //             boolean running = true;

        //             while(running) {

        //                 System.out.println("Rule: " + ruleName + ", Value: " + rules.getRule(ruleName).toString());

        //                 // List out options
        //                 int START_INDEX = 1;
        //                 for (int i = 0; i < options.length; i++) {
        //                     String option = options[i];
        //                     System.out.printf("%d. %s\n",i + START_INDEX,option);
        //                 }

        //                 // Have user choose an option
        //                 boolean valid = false;
        //                 String numRegex = "^[0-9]+$";
        //                 int chosenOption = 0;

        //                 do {
        //                     String response = ScannerUtility.promptTillValid(this.input, "Please input a choice: ", numRegex);
        //                     chosenOption = Integer.parseInt(response);

        //                     if (chosenOption >= START_INDEX && chosenOption <= options.length) {
        //                         valid = true;
        //                     }

        //                 } while(!valid);

        //                 String choice = options[chosenOption - START_INDEX];

        //                 switch(choice) {
        //                     case "Add":
        //                         BlackJackPlayer newPlayer = new BlackJackPlayer();
        //                         ((List<BlackJackPlayer>) rule).add(newPlayer);
        //                         break;
        //                     case "Remove":

        //                         List<BlackJackPlayer> players = ((List<BlackJackPlayer>) rule);
                                
        //                         System.out.println("Player List");

        //                         // List out options
        //                         for (int i = 0; i < players.size(); i++) {
        //                             BlackJackPlayer option = players.get(i);
        //                             System.out.printf("%d. %s\n",i + START_INDEX,option.toString());
        //                         }

        //                         // Choose index to delete
        //                         boolean isValid = false;
        //                         int index = 0;
        //                         do {
        //                             String response = ScannerUtility.promptTillValid(this.input, "Please input an index of a player to remove: ", numRegex);
        //                             index = Integer.parseInt(response);
        
        //                             if (index >= START_INDEX && index <= players.size()) {
        //                                 isValid = true;
        //                             }
        
        //                         } while(!isValid);

        //                         players.remove(index - START_INDEX);
        //                         break;
        //                     case "Clear":
        //                         ((List<BlackJackPlayer>) rule).clear();
        //                         break;
        //                     case "Quit":
        //                         running = false;
        //                         break;
        //                 }

        //             }
          
        //         }

        //         // Bust Value
        //         else if(ruleName.equals("Bust") && rule instanceof Integer) {
        //             final String[] options = new String[] {"Modify","Quit"};                    

        //             boolean running = true;

        //             while(running) {

        //                 System.out.println("Rule: " + ruleName + ", Value: " + rules.getRule(ruleName).toString());

        //                 // List out options
        //                 int START_INDEX = 1;
        //                 for (int i = 0; i < options.length; i++) {
        //                     String option = options[i];
        //                     System.out.printf("%d. %s\n",i + START_INDEX,option);
        //                 }

        //                 // Have user choose an option
        //                 boolean valid = false;
        //                 String numRegex = "^[0-9]+$";
        //                 int chosenOption = 0;

        //                 do {
        //                     String response = ScannerUtility.promptTillValid(this.input, "Please input a choice: ", numRegex);
        //                     chosenOption = Integer.parseInt(response);

        //                     if (chosenOption >= START_INDEX && chosenOption <= options.length) {
        //                         valid = true;
        //                     }

        //                 } while(!valid);

        //                 String choice = options[chosenOption - START_INDEX];

        //                 switch(choice) {
        //                     case "Modify":
        //                         Integer newRule = Integer.parseInt(ScannerUtility.promptTillValid(this.input, "Please input the new bust value: ", numRegex));
        //                         rules.setRule(ruleName, newRule);
        //                         break;
        //                     case "Quit":
        //                         running = false;
        //                         break;
        //                 }

        //             }

        //         }

        //         // Dealer Threshold Value
        //         else if(ruleName.equals("Dealer Threshold") && rule instanceof Integer) {
        //             final String[] options = new String[] {"Modify","Quit"};                    

        //             boolean running = true;

        //             while(running) {

        //                 System.out.println("Rule: " + ruleName + ", Value: " + rules.getRule(ruleName).toString());

        //                 // List out options
        //                 int START_INDEX = 1;
        //                 for (int i = 0; i < options.length; i++) {
        //                     String option = options[i];
        //                     System.out.printf("%d. %s\n",i + START_INDEX,option);
        //                 }

        //                 // Have user choose an option
        //                 boolean valid = false;
        //                 String numRegex = "^[0-9]+$";
        //                 int chosenOption = 0;

        //                 do {
        //                     String response = ScannerUtility.promptTillValid(this.input, "Please input a choice: ", numRegex);
        //                     chosenOption = Integer.parseInt(response);

        //                     if (chosenOption >= START_INDEX && chosenOption <= options.length) {
        //                         valid = true;
        //                     }

        //                 } while(!valid);

        //                 String choice = options[chosenOption - START_INDEX];

        //                 switch(choice) {
        //                     case "Modify":
        //                         Integer newRule = Integer.parseInt(ScannerUtility.promptTillValid(this.input, "Please input the new dealer threshold value: ", numRegex));
        //                         rules.setRule(ruleName, newRule);
        //                         break;
        //                     case "Quit":
        //                         running = false;
        //                         break;
        //                 }

        //             }

        //         }

        //     }
        // }

        ruleLoadableObject.loadRules(rules);
    }

    // Have the user choose a game to play out of all playable games loaded in the GameManager
    private Game chooseGame() {

        Set<String> gameNames = this.games.keySet();
        Iterator<String> gameNameIter = gameNames.iterator();
        
        if (gameNames.isEmpty()) {
            System.out.println("No Playable Games!");
            return null;
        }

        System.out.println("Playable Games");

        // Cycle through all the playable games
        int START_INDEX = 1;
        int index = START_INDEX;
        while (gameNameIter.hasNext()) {
            System.out.printf("%d. %s\n",index++,gameNameIter.next());            
        }

        // String regex = "^[" + START_INDEX + "-" + --index + "]$";
        boolean valid = false;
        String response = "";
        do {

            response = ScannerUtility.prompt(this.input, "Please input the name of the game you would like to play: ");
            if (gameNames.contains(response)) {
                valid = true;
            }

        } while(!valid);
        
        return this.games.getOrDefault(response,null);

    }

}
