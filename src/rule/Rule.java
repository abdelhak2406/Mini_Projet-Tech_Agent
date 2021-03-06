package rule;
//La ligne complete
/*
* definir une seul régle, exemple :"si voirure=btw et nbr roue=17 alors type_voirure=inexistant";
* contient aussi des methodes qui aide a faire l'inférence?TODO: where are they?
 */
import java.util.*;


public class Rule {
    RuleBase rb ;
    String name ;
    Clause antecedents[] ;  // allow up to 3 antecedents for now (ce sont les si(lhs)
    Clause consequent ;     //only 1 consequent clause allowed (we say consequent or rhs)
    Boolean truth;       // states = (null=unknown, true, or false)
    Boolean fired=false;

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
        /*
            TODO: what this method do?
         */
        Enumeration enum87 = clauseRefs.elements();
        while(enum87.hasMoreElements()) {
            Clause temp = (Clause)enum87.nextElement();
            Enumeration enum2 = temp.ruleRefs.elements() ;
            while(enum2.hasMoreElements()) {
                ((Rule)enum2.nextElement()).check() ; // retest the rule
            }
        }
    }


    public String getName() {
        return name;
    }


    Boolean check() {
        /*
            used by forward chaining only !

            if antecedent is true and rule has not fired
                check if all the antecedents are correct so we can say that the
                rule is true!!!

             Keep in mind that, every time a variable is set (we add new knowledge (BF) )
             the value of each Clause that contains this variablee is updated
             (see Rulevariable.SetValue() )

         */
        for (int i=0 ; i < antecedents.length ; i++ ) {
            if (antecedents[i].truth == null) return null ;
            if (antecedents[i].truth == true) {
                continue ;
            } else {
                return truth = false ; //don't fire this rule
            }
        } // endfor
        return truth = true ;  // could fire this rule
    }




    void fire() throws Exception {
        /*
            * fires the rule *
        used by forward chaining only !
        fire this rule -- perform the consequent clause
        if a variable is changes, update all clauses where
        it is references, and then all rules which contain
        those clauses
        */
        System.out.println("\nFiring rule " + name );
        truth = true ;
        fired = true ;
        // set the variable value and update clauses

        //TODO: we need to act here if we need to change and do a a= b+c i think

        //check if a variable exists in the rule Base

        if(consequent.rhsIsComplex()){
            // parse and update the value
            String value= consequent.parseComplexRhs(this.rb);

            consequent.lhs.setValue(value) ;
        }else{
            consequent.lhs.setValue(consequent.rhs) ;
        }

        // now retest any rules whose clauses just changed
        checkRules(consequent.lhs.clauseRefs) ;
    }


    String display() {
        /*
             display the rule in text format
             returns a string that contanins the rule
         */
        String displayStr = "";
        System.out.println(name +": IF ");
        displayStr = displayStr.concat( name + ":\n\nIF (\n");
        for(int i=0 ; i < antecedents.length ; i++) {
            Clause nextClause = antecedents[i] ;

            System.out.println(nextClause.lhs.name +
                    nextClause.cond.asString() +
                    nextClause.rhs + "  )") ;
            displayStr = displayStr.concat("   ( "+nextClause.lhs.name +
                    nextClause.cond.asString() +
                    nextClause.rhs+ " )");
            if ((i+1) < antecedents.length) {
                System.out.println(" AND\n") ;
                displayStr  = displayStr.concat(" AND\n");
            }
        }
        System.out.println("\tTHEN ") ;
        displayStr = displayStr.concat("\n   )\t THEN ");
        System.out.println(consequent.lhs.name +
                consequent.cond.asString() +
                consequent.rhs + "\n") ;
        displayStr = displayStr.concat(consequent.lhs.name +
                consequent.cond.asString() +
                consequent.rhs + "\n\n");

        return displayStr;

    }

/*    Boolean backChain()
    {
        *//*
            Will not be used in our project

            determine is a rule is true or false
            by recursively trying to prove its antecedent clauses are true
            if any are false, the rule is false
         *//*

        RuleBase.appendText("\nEvaluating rule " + name) ;
        for (int i=0 ; i < antecedents.length ; i++) { // test each clause
            if (antecedents[i].truth == null) rb.backwardChain(antecedents[i].lhs.name);
            if (antecedents[i].truth == null) { // we couldn't prove true or false
                truth = antecedents[i].check() ; // redundant?
            } // endif
            if (antecedents[i].truth.booleanValue() == true) {
                continue ;    // test the next antecedent (if any)
            } else {
                return truth = false ; // exit, if any are false
            }
        } // endfor
        return truth = true ; // all antecedents are true
    }*/

}
