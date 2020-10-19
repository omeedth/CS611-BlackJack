/*
 *  Author: Alex Thomas
 *  Creation Date: 10/18/2020
 *  Last Modified: 10/18/2020
 *  Purpose: Defines what a Rule is to be used in RuleSet
 * 
 */

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ModifiableRule extends Rule<Modifiable> {
    
    // Attributes

    // Constructors

    public ModifiableRule(String name, Modifiable value) {
        super(name, value);
    }

    public ModifiableRule(String name) {
        this(name,null);
    }

    // Getter Methods 

    // Setter Methods

    // Rule Logic Methods

    @Override
    public void modify(Scanner input) {

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
                getValue().modify(input);
                String response = ScannerUtility.promptTillValid(input, "Would you like to keep editing this rule(y/n): ", yesNoRegex);
                editing = response.equals("y");
            }  
            
            System.out.println("The element is now: " + getValue());

        } while(editing); 

    }

}
