/*
 *  Author: Alex Thomas
 *  Creation Date: 10/18/2020
 *  Last Modified: 10/18/2020
 *  Purpose: Defines what a Rule is to be used in RuleSet
 * 
 */

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class ListRule<T extends Modifiable> extends Rule<ArrayList<T>> {

    // Attributes
    private T templateModifiableObject; // A template object used in order to add additional elements into the list

    // Constructors

    public ListRule(String name, ArrayList<T> value, T templateModifiableObject) {
        super(name, value);
        this.templateModifiableObject = templateModifiableObject;
    }

    public ListRule(String name, T templateModifiableObject) {
        this(name, null, templateModifiableObject);
    }

    // Getter Methods

    // Setter Methods

    // Rule Logic Methods

    @Override
    public void modify(Scanner input) {

        boolean editing = true;
        String yesNoRegex = "^(y|n)$";
        List<String> options = Arrays.asList(new String[] { "Modify", "Add", "Remove", "Clear", "Quit" });
        List<T> list = getValue();

        // Create Mapping of option to procedure
        // TODO: Consider making Procedure class that will execute given no arguments
        HashMap<String, Function<T, T>> procedures = new HashMap<>();

        // Add all mappings of options to procedures in the map
        procedures.put("Modify", (argument) -> {
            int elemIndex = ScannerUtility.chooseFromList(input, list);
            list.get(elemIndex).modify(input);
            return list.get(elemIndex);
        });
        procedures.put("Add", (newElem) -> {
            newElem.modify(input);
            list.add(newElem);
            return newElem;
        });
        procedures.put("Remove", (argument) -> {
            int elemIndex = ScannerUtility.chooseFromList(input, list);
            return list.remove(elemIndex);
        });
        procedures.put("Clear", (argument) -> {
            list.clear();
            return null;
        });

        // Keeps prompting the user what they would like to edit until they are finished
        // editing
        do {

            int optionIndex = ScannerUtility.chooseFromList(input, options);
            String choice = options.get(optionIndex);

            if (choice.equals("Quit")) {
                editing = false;
            } else {                
                T newElem = null;
                try {                                        
                    newElem = (T) this.templateModifiableObject.getClass().getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException | SecurityException | ClassCastException e) {
                    e.printStackTrace();
                }
                procedures.get(choice).apply(newElem);
                String response = ScannerUtility.promptTillValid(input, "Would you like to keep editing this rule(y/n): ", yesNoRegex);
                editing = response.equals("y");
            }   
            
            System.out.println("The element is now: " + getValue());

        } while(editing);  

    }

}
