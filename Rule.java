/*
 *  Author: Alex Thomas
 *  Creation Date: 10/18/2020
 *  Last Modified: 10/18/2020
 *  Purpose: Defines what a Rule is to be used in RuleSet
 * 
 */

import java.util.List;
import java.util.Scanner;

public abstract class Rule<T> {
    
    // Attributes
    private T value;
    private final String name;

    // Constructors

    public Rule(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public Rule(String name) {
        this(name,null);
    }

    public Rule() {
        this("DEFAULT");
    }

    // Getter Methods 

    public T getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    // Setter Methods

    public void setValue(T newValue) {
        this.value = newValue;
    }

    // Rule Logic Methods

    @Override
    public String toString() {
        return "<Rule: " + this.name + ", Value: " + this.value.toString() + ">";
    }

    public abstract void modify(Scanner input);

}
