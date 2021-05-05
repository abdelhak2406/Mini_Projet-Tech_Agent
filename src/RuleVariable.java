

import java.util.*;
import java.awt.* ;

public class RuleVariable extends Variable {

    public RuleVariable(String Name) {
        super(Name);
        clauseRefs = new Vector();
    }

    void setValue(String val) { value = val;
        updateClauses(); }


    Vector clauseRefs ;   // clauses which refer to this var
    void addClauseRef(Clause ref) { clauseRefs.addElement(ref) ; }

    void updateClauses() {
        Enumeration enum87 = clauseRefs.elements() ;
        while(enum87.hasMoreElements()) {
            ((Clause)enum87.nextElement()).check() ; // retest the truth condition
        }
    }

    String promptText ;  // used to prompt user for value
    String ruleName ;      // if value is inferred, null = user provided
    void   setRuleName(String rname) { ruleName = rname; }
    void   setPromptText(String text) { promptText = text; }

    // these methods are not used in rule variables
    public void computeStatistics(String inValue){} ;
    public int normalize(String inValue, float[] outArray, int inx) {return inx;}

};


