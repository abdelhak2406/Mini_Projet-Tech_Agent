package rule;

import java.util.*;
import java.io.*;
import java.awt.* ;

public class RuleBase {

    String name ;
    Hashtable variableList ;    // all variables in the rulebase
    Vector ruleList ;           // list of all rules
    Stack goalClauseStack;      // for goals (cons clauses) and subgoals

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public Vector getRuleList() {
        return ruleList;
    }

    public void setRuleList(Vector ruleList) {
        this.ruleList = ruleList;
    }

    public Hashtable getVariableList() {
        return this.variableList;
    }

    public void setVariableList(Hashtable variableList) {
        this.variableList = variableList;
    }

    public Stack getGoalClauseStack() {
        return goalClauseStack;
    }

    public void setGoalClauseStack(Stack goalClauseStack) {
        this.goalClauseStack = goalClauseStack;
    }
//static TextArea textArea1 ;
    //public void setDisplay(TextArea txtArea) { textArea1 = txtArea; }

    public RuleBase(String Name) { this.name = Name; }

    public static void appendText(String text) {
        System.out.println(text); }

    // for trace purposes - display all variables and their value
    public String displayVariables() {
        String strVariable = "";
        Enumeration enum87 = variableList.elements() ;
        while(enum87.hasMoreElements()) {
            RuleVariable temp = (RuleVariable)enum87.nextElement() ;
            strVariable = strVariable.concat("\n" + temp.name + " value = " + temp.value);
            System.out.println("\n" + temp.name + " value = " + temp.value) ;
        }
        return strVariable;
    }
    public String displayVarValue(String varName){
        /*
            Display the value of a specific variable
         */
        RuleVariable temp = (RuleVariable)  variableList.get(varName) ;

        String strVariable = "";//In order to print nothing!
        if (temp.value != null){
            strVariable = strVariable.concat(temp.value);
        }
        else {
            strVariable = strVariable.concat("Nothing to sell :/");
        }
        System.out.println("----------------\n\n\n--------------");
        System.out.println("value of "+varName+" = "+temp.value);
        return strVariable;
    }

    // for trace purposes - display all rules in text format
    public String displayRules() {
        String rulesDisplayString ="";
        //need to return a string to print it in the Gui
        System.out.println("\n" + name + " Rule Base: " + "\n");
        rulesDisplayString = rulesDisplayString.concat(name + " Rule Base: " + "\n\n") ;
        Enumeration enum87 = ruleList.elements() ;
        while(enum87.hasMoreElements()) {
            Rule temp = (Rule)enum87.nextElement() ;
            String tmp = temp.display() ;
            rulesDisplayString = rulesDisplayString.concat(tmp);

        }
        return  rulesDisplayString;
    }

    // for trace purposes - display all rules in the conflict set
    public String displayConflictSet(Vector ruleSet) {
        /*
            we just loop through the ruleSet and print all the rules
         */
        //textArea1.appendText("\n" + " -- Rules in conflict set:\n");
        String strConflictSet= "";
        System.out.println("\n" + " -- Rules in conflict set:\n");
        strConflictSet = strConflictSet.concat("-- Rules in conflict set:\n");
        Enumeration enum87 = ruleSet.elements() ;
        while(enum87.hasMoreElements()) {
            Rule temp = (Rule)enum87.nextElement() ;
            System.out.println(temp.name + "(" + temp.numAntecedents()+ "), " ) ;
            strConflictSet = strConflictSet.concat(temp.name + "(" + temp.numAntecedents()+ "), " );
        }
        return strConflictSet;
    }


    // reset the rule base for another round of inferencing
    // by setting all variable values to null
    public void reset() {
        System.out.println("\n --- Setting all " + name + " variables to null");
        Enumeration enum87 = variableList.elements() ;
        while(enum87.hasMoreElements()) {
            RuleVariable temp = (RuleVariable)enum87.nextElement() ;
            temp.setValue(null) ;
        }
    }

    // for all consequent clauses which refer to this goalVar
    // try to find goalVar value via a rule being true
    //     if rule is true then pop, assign value, re-eval rule
    //     if rule is false then pop, continue
    //     if rule is null then we couldnt find a value (same as false?)
    //
    public void backwardChain(String goalVarName)
    {

        RuleVariable goalVar = (RuleVariable)variableList.get(goalVarName);
        Enumeration goalClauses = goalVar.clauseRefs.elements() ;

        while (goalClauses.hasMoreElements()) {
            Clause goalClause = (Clause)goalClauses.nextElement() ;
            if (goalClause.consequent.booleanValue() == false) continue ;

            goalClauseStack.push(goalClause) ;

            Rule goalRule = goalClause.getRule();
            Boolean ruleTruth = goalRule.backChain() ; // find rule truth value
            if (ruleTruth == null) {
                System.out.println("\nRule " + goalRule.name +
                        " is null, can't determine truth value.");
            } else if (ruleTruth.booleanValue() == true) {
                // rule is OK, assign consequent value to variable
                goalVar.setValue(goalClause.rhs) ;
                goalVar.setRuleName(goalRule.name) ;
                goalClauseStack.pop() ;  // clear item from subgoal stack
                System.out.println("\nRule " + goalRule.name + " is true, setting " + goalVar.name + ": = " + goalVar.value);
                if (goalClauseStack.empty() == true) {
                    System.out.println("\n +++ Found Solution for goal: " + goalVar.name);
                    break ; // for now, only find first solution, then stop
                }
            } else {
                goalClauseStack.pop() ; // clear item from subgoal stack
                System.out.println("\nRule " + goalRule.name + " is false, can't set " + goalVar.name);
            }

            // displayVariables("Backward Chaining") ;  // display variable bindings
        } // endwhile

        if (goalVar.value == null) {
            System.out.println("\n +++ Could Not Find Solution for goal: " + goalVar.name);
        }
    }

    public Vector match(boolean test) {
        /*
             used for forward chaining only
             determine which rules can fire,
                 return a Vector of rules that can fire

             TODO:find what test means
         */
        Vector matchList = new Vector() ;
        Enumeration enum87 = ruleList.elements() ;
        while (enum87.hasMoreElements()) {
            Rule testRule = (Rule)enum87.nextElement() ;
            if (test) testRule.check() ; // test the rule antecedents
            if (testRule.truth == null) continue ;
            // fire the rule only once for now
            if ( (testRule.truth.booleanValue() == true ) &&
                    (testRule.fired == false) ){
                matchList.addElement(testRule) ;
            }
        }
        String ruleSet = displayConflictSet(matchList) ;
        return matchList ;
    }

    public Rule selectRule(Vector ruleSet) {
        /*
            * ruleSet: vector of the ConflictSet

            Used for forward chaining only

            This method decides which rule to fire
            select a rule to fire based on the number of antecedent clauses
            It means that, the Rule that have more antecedents will be fired
            * return the fired  rule

          */

        Enumeration enum87 = ruleSet.elements() ;
        long numClauses ;
        Rule nextRule ;

        Rule bestRule = (Rule)enum87.nextElement() ;
        long max = bestRule.numAntecedents() ;
        while (enum87.hasMoreElements()) {
            nextRule = (Rule)enum87.nextElement() ;
            if ((numClauses = nextRule.numAntecedents()) > max) {
                max = numClauses ;
                bestRule = nextRule ;
            }
        }
        return bestRule ;
    }

    public void forwardChain() {
        Vector conflictRuleSet = new Vector() ;

        // first test all rules, based on initial data
        conflictRuleSet = match(true); // see which rules can fire

        while(conflictRuleSet.size() > 0) {

            Rule selected = selectRule(conflictRuleSet); // select the "best" rule
            selected.fire() ; // fire the rule
            // do the consequent action/assignment
            // update all clauses and rules

            conflictRuleSet = match(false); // see which rules can fire

            // displayVariables("Forward Chaining") ; // display variable bindings
        }
    }


}
