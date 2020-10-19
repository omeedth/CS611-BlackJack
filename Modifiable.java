/*
 *  Author: Alex Thomas
 *  Creation Date: 10/18/2020
 *  Last Modified: 10/18/2020
 *  Purpose: An interface that requires functionality to modify an object
 * 
 */

import java.util.Scanner;

public interface Modifiable {
    public abstract void modify(Scanner input);     // Takes in a Scanner and asks the user how they would like to modify the object
    // public abstract List<Rule<?>> getModifiableRules() // Returns the List of Rule<?> Objects that can be modified in the Modifiable Object    
}
