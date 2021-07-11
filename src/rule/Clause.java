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


public class Clause {
    Vector ruleRefs ;//vercteur contenant l'ensemble des regles ayant fait appel a cette clause
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
            System.out.println(toString()+", truth :"+truth);
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

    Boolean isArithmeticOperator(String oper){
        if (oper.equals("-") || oper.equals("+") || oper.equals("/") || oper.equals("*")){
            return true;
        }
        return false;
    }


   public String parseComplexRhs(RuleBase rb) throws Exception {
        /*
            parse the lhs and update his  value
         */
        //here we will parse the rhs part
       System.out.println("IN COMPLEX");
        String [] resultArray = rhs.split("\\s*(\\+|\\-|\\/|\\*)\\s*");
        //TODO: a quoi ressemble ce tableau
        for(String var : resultArray){
            System.out.println(var);
           if(rb.getVariable(var).getValue()==null){
               throw new Exception("Operation impossible, the variable "+var+ " has a null value");
           }
        }
        // !WARNING: No space allowed in the arithmetic expression
        //We are assuming that the operation will look like a*c+i
        // !WARNING2 :the arithmetic operation needs to be ordered THERE IS NO TAKING CARE OF ORDER!!!

        String[] operationArith =  rhs.split("(?<=(\\+|\\-|\\/|\\*)|(?=(\\+|\\-|\\/|\\*)))");
        int result=  Integer.parseInt(rb.getVariable(operationArith[0]).getValue()) ;
        int tmp;
        for (int i=1 ; i<operationArith.length;i=i+2){
            if (isArithmeticOperator(operationArith[i])){
                tmp=  Integer.parseInt(rb.getVariable(operationArith[i+1]).getValue());
                switch (operationArith[i]){
                    //TODO: si on a des resultat float il faut gerer sa normalement

                    case "+":
                        result = result + tmp ;
                        break;
                    case "-":
                        result = result - tmp;
                        break;
                    case "/":
                        result = result / tmp;
                        break;
                    case "*":
                        result = result* tmp;
                        break;
                }
            }else{
                throw new Exception("Bad Arithmetic expression "+lhs);
            }
        }
          return String.valueOf(result);

   }

       Boolean rhsIsComplex() {
        /*
            checks if the lhs is complex, i.e has some arithmetic operation on it
            - example :
                  "consequence": {
                    "ruleVar": "laptop_price",
                    "condition": "=",
                    "rhs": "laptop_range * 5 + tva:"
                  }
             the rhs is an arithmetic operation therefore it's complex
           we will look at the precense of one of the arithmetic operators
         */
        if (rhs.contains("+") || rhs.contains("-") || rhs.contains("/") || rhs.contains("*")){
            return true;
        }
        return false;

   }
};






