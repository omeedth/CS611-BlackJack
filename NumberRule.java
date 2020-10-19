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

public class NumberRule extends Rule<Number> {
    
    // Attributes

    // Constructors

    public NumberRule(String name, Number value) {
        super(name, value);
    }

    public NumberRule(String name) {
        this(name,null);
    }

    // Getter Methods 

    // Setter Methods

    // Rule Logic Methods

    @Override
    public void modify(Scanner input) {

        boolean editing = true;
        String numRegex = "^(\\+|-)?([0-9]+(\\.[0-9]*)?)$";
        String yesNoRegex = "^(y|n)$";
        List<String> options = Arrays.asList(new String[] {"Modify", "Quit"});

        // Keeps prompting the user what they would like to edit until they are finished editing
        do {

            int optionIndex = ScannerUtility.chooseFromList(input, options);
            String choice = options.get(optionIndex);
            Number newValue = null;

            if (choice.equals("Quit")) {
                editing = false;
            } else {
                String numberString = ScannerUtility.promptTillValid(input, "Please input a new number: ", numRegex);
                newValue = Double.parseDouble(numberString);                       

                String response = ScannerUtility.promptTillValid(input, "Would you like to keep editing this rule(y/n): ", yesNoRegex);
                editing = response.equals("y");
            } 

            setValue(newValue);
            
            System.out.println("The element is now: " + getValue());

        } while(editing);

    }

}
