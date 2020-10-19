/*
 *  Author: Alex Thomas
 *  Creation Date: 10/17/2020
 *  Last Modified: 10/17/2020
 *  Purpose: Manages all the games you might want to play and helps define rulesets
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RuleSet {
    
    // Attributes
    private final HashMap<String,Rule<?>> rules;

    // Constructor

    public <T extends RuleLoadable> RuleSet(T ruleLoadableObject) {        
        this.rules = new HashMap<>(ruleLoadableObject.getMutableRules().stream().collect(Collectors.toMap(Rule::getName, Function.identity())));
    }

    // Getter Methods

    // Setter Methods

    // RuleSet Logic Methods

    // Returns a Set of the names of the mutable rules
    public Set<String> getMutableRules() {
        return this.rules.keySet();
    }

    // Possible change can occur here to the objects (UNEXPECTED CHANGES)
    // -    Consider: returning deep copy, or doing all changes internally if possible
    // Returns the Rule mapped to the particular rule name
    public Rule<?> getRule(String ruleName) {
        return this.rules.getOrDefault(ruleName, null);
    }

    // Set's the rule given the rule name if the previous rule and the new rule match Types and Parameterized Types 
    public void setRule(String ruleName, Rule<?> rule) {
        if (!this.rules.containsKey(ruleName)) {
            return;
        }

        Object prevRule = this.rules.get(ruleName);
        if (!(TypeChecking.sameClassTypes(prevRule, rule) && TypeChecking.sameParameterizedTypes(prevRule, rule))) {
            return;
        }

        this.rules.put(ruleName, rule);
    }

    // List<Modifiable>: 
    //  1. "Modify" -> chooses index and modifies the object according to the rules defines in the interface Modifiable
    //  2. "Add" -> Constructs an empty Modifiable Object and then allows you to edit it before adding it into the List
    //  3. "Remove" -> chooses an index and delets the Modifiable Object at that index
    //  4. "Clear" -> Removes all Modifiable Objects from the List (Consider having a validRules() method defined for each game)
    //  5. "Quit" -> Quits out of this method
    // ------------------------------------------------
    // Inputs:
    //  1. Scanner - for requesting info from the user
    //  2. List<T extends Modifiable> - which is the list we will editing
    //  3. T elem - An element of the same type of the list<T> which we might be adding if the user selects "Add" procedure
    public static <T extends Modifiable> List<T> modifyList(Scanner input, List<T> list) { // , T elem
        boolean editing = true;
        String yesNoRegex = "^(y|n)$";
        List<String> options = Arrays.asList(new String[] {"Modify", "Add", "Remove", "Clear", "Quit"});

        // Create Mapping of option to procedure
        // TODO: Consider making Procedure class that will execute given no arguments
        HashMap<String,Function<Object,Object>> procedures = new HashMap<>(); 

        // Add all mappings of options to procedures in the map
        procedures.put("Modify", (argument) -> {
            int elemIndex = ScannerUtility.chooseFromList(input, list);
            list.get(elemIndex).modify(input);
            return null;
        });
        procedures.put("Add", (argument) -> {
            // elem.modify(input);
            // list.add(elem);
            return null;
        });
        procedures.put("Remove", (argument) -> {
            int elemIndex = ScannerUtility.chooseFromList(input, list);
            list.remove(elemIndex);
            return null;
        });
        procedures.put("Clear", (argument) -> {            
            list.clear();
            return null;
        });

        // Keeps prompting the user what they would like to edit until they are finished editing
        do {

            int optionIndex = ScannerUtility.chooseFromList(input, options);
            String choice = options.get(optionIndex);

            if (choice.equals("Quit")) {
                editing = false;
            } else {
                procedures.get(choice).apply(null);
                String response = ScannerUtility.promptTillValid(input, "Would you like to keep editing this rule(y/n): ", yesNoRegex);
                editing = response.equals("y");
            }   
            
            System.out.println("The element is now: " + list);

        } while(editing);        



        return list;
    }

    // Modifiable: 
    //  1. "Modify" -> runs the custom modify() logic built into the Modifiable object
    // ------------------------------------------------
    // Inputs:
    //  1. Scanner - for requesting info from the user
    //  2. T elemToModify - the element we will be modifying
    public static <T extends Modifiable> T modifyModifiableObject(Scanner input, T elemToModify) {
        boolean editing = true;
        String yesNoRegex = "^(y|n)$";
        List<String> options = Arrays.asList(new String[] {"Modify", "Quit"});

        // Keeps prompting the user what they would like to edit until they are finished editing
        do {

            int optionIndex = ScannerUtility.chooseFromList(input, options);
            String choice = options.get(optionIndex);

            if (choice.equals("Quit")) {
                editing = false;
            } else {
                elemToModify.modify(input);
                String response = ScannerUtility.promptTillValid(input, "Would you like to keep editing this rule(y/n): ", yesNoRegex);
                editing = response.equals("y");
            }  
            
            System.out.println("The element is now: " + elemToModify);

        } while(editing);        

        return elemToModify;
    }

    // Number: 
    //  1. "Modify" -> allows the user to select a new number
    // ------------------------------------------------
    // Inputs:
    //  1. Scanner - for requesting info from the user
    //  2. T elemToModify - the element we will be modifying
    //  3. Function<Number,Boolean> isValid - a function that will test whether or not the modified number is valid
    public static Number modifyNumber(Scanner input, Number elemToModify) {
        boolean editing = true;
        String numRegex = "^(\\+|-)?([0-9]+(\\.[0-9]*)?)$";
        String yesNoRegex = "^(y|n)$";
        List<String> options = Arrays.asList(new String[] {"Modify", "Quit"});

        // Keeps prompting the user what they would like to edit until they are finished editing
        do {

            int optionIndex = ScannerUtility.chooseFromList(input, options);
            String choice = options.get(optionIndex);

            if (choice.equals("Quit")) {
                editing = false;
            } else {
                elemToModify = Float.parseFloat(ScannerUtility.promptTillValid(input, "Please input a new number: ", numRegex));
                String response = ScannerUtility.promptTillValid(input, "Would you like to keep editing this rule(y/n): ", yesNoRegex);
                editing = response.equals("y");
            } 
            
            System.out.println("The element is now: " + elemToModify);

        } while(editing);        

        return elemToModify;
    }


    // Choose the rule you would like to edit (Think more carefully whether or not this should be in this class)
    protected String chooseRule(Scanner input) {        
        List<String> options = new ArrayList<>(getMutableRules());

        // Select from options
        int choice = ScannerUtility.chooseFromList(input, options);

        return options.get(choice);
    }

    @Override
    public String toString() {
        return this.rules.values().toString();
    }

}
