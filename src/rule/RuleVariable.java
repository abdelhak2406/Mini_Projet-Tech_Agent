package rule;

import java.util.*;

public class RuleVariable extends Variable {

    Vector clauseRefs ;   // clauses which refer to this var [list of clause objects]
    //TODO: think again about deleting them because they are useless
    String promptText ;  // used to prompt user for value
    String ruleName ;      // if value is inferred, null = user provided

    public RuleVariable(String Name) {
        super(Name);
        clauseRefs = new Vector();
    }

    public void setValue(String val) {
        /*
            when we set the value we also update the clauses's truth value
         */
        this.value = val;
        updateClauses(); }


    void addClauseRef(Clause ref) { clauseRefs.addElement(ref) ; }

    void updateClauses() {
        /*
            for every clause in the system, we need to rechecck his if it's true of not
         */
        Enumeration enum87 = clauseRefs.elements() ;
        while(enum87.hasMoreElements()) {
            ((Clause)enum87.nextElement()).check() ; // retest the truth condition
        }
    }


    void   setRuleName(String rname) { ruleName = rname; }
    void   setPromptText(String text) { promptText = text; }

    // these two methods are not used in rule variables
    public void computeStatistics(String inValue){} ;
    public int normalize(String inValue, float[] outArray, int inx) {return inx;}

    @Override
    public String toString() {
        return "RuleVariable{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
};


