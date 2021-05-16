
//La ligne complete

import java.util.*;
import java.io.*;
import java.awt.* ;

public class Rule {
    RuleBase rb ;
    String name ;
    Clause antecedents[] ;  // allow up to 3 antecedents for now
    Clause consequent ;     //only 1 consequent clause allowed
    Boolean truth;       // states = (null=unknown, true, or false)
    boolean fired=false;

    Rule(RuleBase Rb, String Name, Clause clauses[], Clause rhs ) {
        rb = Rb ;
        name = Name ;
        antecedents = clauses;
        for(int i=0;i<antecedents.length;i++){
            antecedents[i].addRuleRef(this);
        }
        consequent=rhs;
        rhs.addRuleRef(this) ;
        rhs.isConsequent() ;
        rb.ruleList.addElement(this) ;  // add self to rule list
        truth = null ;
    }

    long numAntecedents() { return antecedents.length; }

    public static void checkRules(Vector clauseRefs) {
        Enumeration enum87 = clauseRefs.elements();
        while(enum87.hasMoreElements()) {
            Clause temp = (Clause)enum87.nextElement();
            Enumeration enum2 = temp.ruleRefs.elements() ;
            while(enum2.hasMoreElements()) {
                ((Rule)enum2.nextElement()).check() ; // retest the rule
            }
        }
    }


    // used by forward chaining only !
    Boolean check() {  // if antecedent is true and rule has not fired
        //RuleBase.appendText("\nTesting rule " + name ) ;
        for (int i=0 ; i < antecedents.length ; i++ ) {
            if (antecedents[i].truth == null) return null ;
            if (antecedents[i].truth.booleanValue() == true) {
                continue ;
            } else {
                System.out.println("rule : "+name+" not fired");
                return truth = new Boolean(false) ; //don't fire this rule
            }
        } // endfor
        System.out.println("rule : "+name+" fired.");
        return truth = new Boolean(true) ;  // could fire this rule
    }


    // used by forward chaining only !
    // fire this rule -- perform the consequent clause
    // if a variable is changes, update all clauses where
    //  it is references, and then all rules which contain
    //  those clauses

    void fire() {
        RuleBase.appendText("\nFiring rule " + name ) ;
        truth = new Boolean(true) ;
        fired = true ;
        // set the variable value and update clauses
        consequent.lhs.setValue(consequent.rhs) ;
        // now retest any rules whose clauses just changed
        checkRules(consequent.lhs.clauseRefs) ;
    }

    // determine is a rule is true or false
    // by recursively trying to prove its antecedent clauses are true
    // if any are false, the rule is false

    Boolean backChain()
    {
        RuleBase.appendText("\nEvaluating rule " + name) ;
        for (int i=0 ; i < antecedents.length ; i++) { // test each clause
            if (antecedents[i].truth == null) rb.backwardChain(antecedents[i].lhs.name);
            if (antecedents[i].truth == null) { // we couldn't prove true or false
                truth = antecedents[i].check() ; // redundant?
            } // endif
            if (antecedents[i].truth.booleanValue() == true) {
                continue ;    // test the next antecedent (if any)
            } else {
                return truth = new Boolean(false) ; // exit, if any are false
            }
        } // endfor
        return truth = new Boolean(true) ; // all antecedents are true
    }

    // display the rule in text format
    @SuppressWarnings("deprecation")
    void display() {
        System.out.println(name +": IF ");
        for(int i=0 ; i < antecedents.length ; i++) {
            Clause nextClause = antecedents[i] ;
            System.out.println(nextClause.lhs.name +
                    nextClause.cond.asString() +
                    nextClause.rhs + " ") ;
            if ((i+1) < antecedents.length) System.out.println("\n     AND ") ;
        }
        System.out.println("\n     THEN ") ;
        System.out.println(consequent.lhs.name +
                consequent.cond.asString() +
                consequent.rhs + "\n") ;
    }
};
