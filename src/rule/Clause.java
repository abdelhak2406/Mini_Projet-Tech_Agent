package rule;

//une partie de la ligne (ex : budget>300)
/*  Clause class
** utiliser aussi bien qu'entécedant que (condition) que conséquence
** Une clause est composé de 3 elem
*   1. ruleVariable(exemple:  num_wheels)
*   2. Condition
*   3. String(right hand side)
 */

import java.util.*;
import java.io.*;


public class Clause {
    Vector ruleRefs ;//vercteur contenant l'esnemble des regles ayant fait appel a cette clause
    RuleVariable lhs ;//left hand side
    String rhs ;
    Condition  cond ;
    Boolean consequent ;  // true or false
    Boolean truth ;   // states = null(unknown), true or false (car sometimes rule cannot be determiend)
    Clause(RuleVariable Lhs, Condition Cond, String Rhs)
    {
        this.lhs = Lhs ;
        this.cond = Cond ;
        this.rhs = Rhs ;
        this.lhs.addClauseRef(this) ;//referencer toute les clause utilisant la variable
        ruleRefs = new Vector() ;
        truth = null ;
        consequent = false ;
    }

    void addRuleRef(Rule ref) { ruleRefs.addElement(ref) ; }

    Boolean check() {
        /*
            check if a clause is correct
            and update the value of truth
            is executed every time a new value is set
            to a variable
         */
        if (consequent) return truth= null ;//doesn't make sence to check the value of the variable if it's a consequence

        if (lhs.value == null) {
            return truth = null ;  // can't check if variable value is undefined
        }
        if(ifInt(rhs)){

            switch(cond.index) {
                case 1:
                    truth = (Integer.parseInt(lhs.value)==(Integer.parseInt(rhs))) ;
                    break ;
                case 2:
                    truth = (Integer.parseInt(lhs.value)>(Integer.parseInt(rhs))) ;
                    break ;
                case 3:
                    truth = (Integer.parseInt(lhs.value)<(Integer.parseInt(rhs))) ;
                    break ;
                case 4:
                    truth = (Integer.parseInt(lhs.value)!=(Integer.parseInt(rhs))) ;
                    break ;
                case 5:
                    truth = (Integer.parseInt(lhs.value)>=(Integer.parseInt(rhs))) ;
                    break ;
                case 6:
                    truth = (Integer.parseInt(lhs.value)<=(Integer.parseInt(rhs))) ;
                    break ;
            }
            return truth ;
        }
        else {
            switch(cond.index) {
                case 1: truth = (lhs.value.equals(rhs)) ;
                    break ;
                case 4: truth = (lhs.value.compareTo(rhs) != 0) ;
                    break ;
            }

            return truth ;
        }

    }

    void isConsequent() {
        consequent =  true;
    }
    Rule getRule() {
        /*
        returns a reference to the owning rule instance
         */
        if (consequent == true)
            return (Rule)ruleRefs.firstElement() ;
        else
            return null;
    }

    public Boolean ifInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public String toString() {
        return "Clause{" +
                "rhs='" + rhs + '\'' +
                '}';
    }
};






