package rule;

import java.util.*;
import java.io.*;

public abstract class Variable {
    String name ;
    String value ;
    int column ;
    // used by categorical only
    Vector labels ;
    public Variable() {} ;
    public Variable(String Name) {name = Name; value = null; }
    void   setValue(String val) { value = val ; }
    String getValue() { return value; }


    void setLabels(String Labels) {
        /*
            input!:string with " "(space) separated  values

         */
        this.labels = new Vector() ;
        StringTokenizer tok = new StringTokenizer(Labels," ") ;
        while (tok.hasMoreTokens()) {
            this.labels.addElement(new String(tok.nextToken())) ;
        }
    }

    String getLabel(int index) {
        /*
            return the label with the specified index
         */
        return (String)labels.elementAt(index);
    }

    String getLabels() {
        /*
            return a string containing all labels
         */
        String labelList = new String();
        Enumeration enum87 = labels.elements() ;
        while(enum87.hasMoreElements()) {
            labelList += enum87.nextElement() + " " ;
        }
        return labelList ;
    }

    int getIndex(String label) {
        /*
            given a label, return its index
         */
        int i = 0, index = 0  ;
        Enumeration enum87 = labels.elements() ;
        while(enum87.hasMoreElements()) {
            if (label.equals(enum87.nextElement()))
            { index = i ; break ; }
            i++;
        }
        return i;
    }


    boolean categorical() {
        /*
            check if the variable is categorical or not
         */
        if (labels != null) {
            return true ;
        } else {
            return false ;
        }
    }

    // used by the DataSet class
    public void setColumn(int col) { column = col ; }
    public abstract void computeStatistics(String inValue) ;
    public abstract int normalize(String inValue, float[] outArray, int inx);
    public int normalizedSize() { return 1 ; }
    public String getDecodedValue(float[] act, int index) { return String.valueOf(act[index]); }

}