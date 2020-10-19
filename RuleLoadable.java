/*
 *  Author: Alex Thomas
 *  Creation Date: 10/17/2020
 *  Last Modified: 10/17/2020
 *  Purpose: Defines interface for objects able to load rules for Games
 * 
 */

import java.util.List;
import java.util.Map;

// NOTE: DEVELOPMENT
public interface RuleLoadable {
    public <T> List<Rule<T>> getMutableRules();                            // Get's the RuleSet with only the mutable rules
    public boolean areRulesValid(RuleSet rules);                                  // Tests whether or not the rules are valid
    public void loadRules(RuleSet rules);                                         // Loads the RuleSet into the Game
}
