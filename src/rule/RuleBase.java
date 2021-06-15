package rule;

import java.util.*;

public class RuleBase {

    String name ;
    Hashtable variableList ;    // all variables in the rulebase, it's structured like this:{"var_name":RuleVariablbe Object}
    Vector ruleList ;           // list of all rules[contains RULE objects]
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

    public RuleVariable getVariable(String varName) {
        return (RuleVariable)variableList.get(varName);
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


    public RuleBase(String Name) { this.name = Name; }


    public String displayVariables() {
        /*
            for trace purposes - display all variables and their value
            * return the string that contains all the vars and their values
         */
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


    public String displayRules() {
        /*
            for trace purposes - display all rules in text format
            return the string containing all the rules
         */
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
            strConflictSet = strConflictSet.concat(temp.name + "(" + temp.numAntecedents()+ "), "+"\n" );
        }
        return strConflictSet;
    }


    public void reset() {
            /*
                 reset the rule base for another round of inferencing
                 by setting all variable values to null
             */
        System.out.println("\n --- Setting all " + name + " variables to null");
        Enumeration enum87 = variableList.elements() ;
        while(enum87.hasMoreElements()) {
            RuleVariable temp = (RuleVariable)enum87.nextElement() ;
            temp.setValue(null) ;
        }
    }



    public Vector match(boolean test) {

        /*
             used for forward chaining only
             determine which rules can fire,
                 return a Vector of rules that can fire

            * test : true : means we will check the rule's antecedents and determine if we can use it or not (update truth in Rule )
            * test : false : we don't need to update the truth value since we did it already with calling match(true)

             TODO:find what test means
         */
        Vector matchList = new Vector() ;
        Enumeration enum87 = ruleList.elements() ;
        while (enum87.hasMoreElements()) {
            Rule testRule = (Rule)enum87.nextElement() ;
            if (test) testRule.check() ; // test if all the rule's antecedents are true
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

    public HashMap forwardChain() throws Exception {
        /*
            do the forwarrd Chain and output a hashmap
            * {"fired":[list of fired list(by order)],"conflictSet":[listOf conflict sets (by order) ] }
         */
        // hashmap aura 2 true: conflict set et fired rule
        //{"fired":[list of fired list(by order)],"conflictSet":[listOf conflict sets (by order) ] }
        HashMap<String, ArrayList> firedAndConflictHash = new HashMap<>();
        firedAndConflictHash.put("fired",new ArrayList<Rule>()) ;
        firedAndConflictHash.put("conflictSet",new ArrayList<Vector>()) ;



        Vector conflictRuleSet = new Vector() ;

        // first test all rules, based on initial data
        conflictRuleSet = match(true); // see which rules can fire

        while(conflictRuleSet.size() > 0) {
            firedAndConflictHash.get("conflictSet").add(conflictRuleSet);
            Rule selected = selectRule(conflictRuleSet); // select the "best" rule
            selected.fire() ; // fire the rule
            // do the consequent action/assignment
            // update all clauses and rules


            //add fired rule to the list of fired rule
            firedAndConflictHash.get("fired").add(selected);

            conflictRuleSet = match(false); // see which rules can fire

        }
        return firedAndConflictHash;
    }

   /* public void backwardChain(String goalVarName)
    {
        *//*
            Will not be used in our project

            for all consequent clauses which refer to this goalVar
            try to find goalVar value via a rule being true
            if rule is true then pop, assign value, re-eval rule
            //     if rule is false then pop, continue
            //     if rule is null then we couldnt find a value (same as false?)
            //
         *//*

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
    }*/

}
