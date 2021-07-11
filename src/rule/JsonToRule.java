package rule;

import java.io.FileReader;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class JsonToRule {
    //Conditions
    Condition cEquals ;
    Condition cNotEquals;
    Condition cLessThan ;
    Condition cMoreThan ;
    Condition cLessEqualThan ;
    Condition cMoreEqualThan ;
    //to acess the objects
    Map variableObjects = new HashMap<String,Object>();//{"variable_name": variable Object}
    Map ruleObjects = new HashMap<String,Object>();//{"ruleName":rule Object}
    //json object
    private JSONObject jsonObj;
    RuleBase rb  ;

    public JsonToRule(String fileName) throws Exception{
        getJsonRuleObject(fileName);
        createConditions();
        createRuleBase(this.jsonObj);
        createVars(this.jsonObj);
        createRules(this.jsonObj);
        System.out.println("here we go!");
    }

    public Map getVariableObjects() {
        return variableObjects;
    }

    public Map getRuleObjects() {
        return ruleObjects;
    }

    public RuleBase getRb() {
        return rb;
    }

    private void getJsonRuleObject(String jsonFile) throws Exception {

        // parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader(jsonFile));

        // typecasting obj to JSONObject
         this.jsonObj = (JSONObject) obj;
    }

    public void createRuleBase(JSONObject jo){
        String ruleBaseName = (String) jo.get("ruleBaseName");
        this.rb = new RuleBase(ruleBaseName) ;
        this.rb.setGoalClauseStack(new Stack())  ;
        this.rb.setVariableList (new Hashtable())  ;

        this.rb.setRuleList(new Vector() );
    }

    public void createVars(JSONObject jo) {

        this.variableObjects = new HashMap<String,Object>();
        JSONArray variables = (JSONArray) jo.get("variables");

        variables.forEach(entry -> {
            JSONObject variable = (JSONObject) entry;
            String varName = (String) variable.get("name");


            RuleVariable ruleVari = new RuleVariable(varName) ;
            this.rb.getVariableList().put(ruleVari.name,ruleVari) ;

            JSONArray label = (JSONArray) variable.get("labels");

            if(label.size() > 0) {
                String labelValues = new String("");
                for (int i = 0; i < label.size(); i++) {
                    String valueStr = (String) label.get(i);
                    labelValues = labelValues.concat(" " + valueStr);
                }//we add the labels now
                ruleVari.setLabels(labelValues);
            }
            //we add the object created
            this.variableObjects.put(varName,ruleVari);
        });

    }

    public void createRules(JSONObject jo){

        JSONArray rules = (JSONArray) jo.get("rules");
//        System.out.println("la taille du tabl "+variables.size());

        rules.forEach(entry -> {
            JSONObject rule = (JSONObject) entry;
            String ruleName = (String) rule.get("name");

            JSONArray antecedents = (JSONArray) rule.get("antecedents");
            Clause[] clauseArray= new Clause[antecedents.size()];
            //adding antecedent clauses
            for (int i = 0; i < antecedents.size(); i++) {
                JSONObject   clausei = (JSONObject) antecedents.get(i);

                String temp= (String) clausei.get("ruleVar");
                System.out.println("ruleName= "+ruleName+"\ntemp = "+temp);

                RuleVariable var =getRuleVarObject(clausei);
                String condStr = (String) clausei.get("condition") ;
                Condition cond = getCondition(condStr) ;
                String rhs = (String)  clausei.get("rhs");
                Clause clauseTemp = new Clause(var,cond,rhs);
                clauseArray[i] = clauseTemp;
                System.out.println("HELLO 1");
            }

            JSONObject consequence =(JSONObject) rule.get("consequence");
            System.out.println("HELLO 2");
            RuleVariable var =getRuleVarObject(consequence);
            System.out.println("HELLO 3");
            String condStr = (String) consequence.get("condition") ;
            System.out.println("HELLO 4");
            Condition cond = getCondition(condStr) ;
            System.out.println("HELLO 5");
            String rhs = (String)  consequence.get("rhs");
            System.out.println("HELLO 6");
            Clause  clauseCons = new Clause(var,cond,rhs);
            Rule rulei = new Rule(rb, ruleName,clauseArray,clauseCons );

            this.ruleObjects.put(ruleName,rulei);

        });
    }

    RuleVariable getRuleVarObject(JSONObject clausei){
        System.out.println("excuse me 0");
        String varName =  (String) clausei.get("ruleVar");
        System.out.println(varName);
        System.out.println("excuse me 1");
        RuleVariable  var = (RuleVariable) this.variableObjects.get(varName) ;
        System.out.println("excuse me 2");
        return var;
    }

    void createConditions(){
        //TODO: ajouter les condition au variable de la classe
        this.cEquals = new Condition("=") ;
        this.cNotEquals = new Condition("!=") ;
        this.cLessThan = new Condition("<") ;
        this.cMoreThan = new Condition(">") ;
        this.cLessEqualThan= new Condition("<=");
        this.cMoreEqualThan= new Condition(">=");
    }

    public Condition getCondition(String cond)  {

        switch(cond){
            case "=": return cEquals ;
            case "!=": return  cNotEquals ;
            case "<": return cLessThan ;
            case ">": return  cMoreThan ;
            case "<=": return cLessEqualThan  ;
            case ">=": return cMoreEqualThan ;
        }
        return null;

    }


}
