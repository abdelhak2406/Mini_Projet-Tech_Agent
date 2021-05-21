import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

public class CarRules {

    private Clause[] clauseArray(Clause ... args) {
        Clause[] clauses= new Clause[1];
        for (Clause arg : args) {
            clauses[clauses.length-1]=arg;
        }
        System.out.println(clauses);
        return clauses;
    }

    public void rules(){

        RuleBase rb = new RuleBase("Vehicules") ;
        rb.goalClauseStack = new Stack() ;
        rb.variableList = new Hashtable() ;


        //Creating the variables

        RuleVariable vehiculeType = new RuleVariable("vehiculeType") ;
        //vehiculeType.setLabels("");
        rb.variableList.put(vehiculeType.name,vehiculeType) ;


        RuleVariable numWheels = new RuleVariable("numWheels") ;
        rb.variableList.put(numWheels.name,numWheels) ;


        RuleVariable motor = new RuleVariable("motor") ;
        rb.variableList.put(motor.name,motor) ;


        RuleVariable vehicule = new RuleVariable("vehicule") ;
        //vehicule.setLabels("");
        rb.variableList.put(vehicule.name,vehicule) ;


        RuleVariable size = new RuleVariable("size") ;
        //size.setLabels("");
        rb.variableList.put(size.name,size) ;


        RuleVariable numDoors = new RuleVariable("numDoors") ;
        //numDoors.setLabels("");
        rb.variableList.put(numDoors.name,numDoors) ;



        // Note: at this point all variables values are NULL
        //conditions
        Condition cEquals = new Condition("=") ;
        Condition cNotEquals = new Condition("!=") ;
        Condition cLessThan = new Condition("<") ;
        Condition cMoreThan = new Condition(">") ;
        Condition cLessEqualThan= new Condition("<=");
        Condition cMoreEqualThan= new Condition(">=");
        rb.ruleList = new Vector() ;


        // creating rules and closes
        //TODO: fix the clauseArray thing!

        Rule bicycleRule = new Rule(rb, "bicycle_rule" ,
            clauseArray(
                new Clause(vehiculeType,cEquals,"cycle"),
                new Clause (numWheels, cEquals,"2"),
                new Clause(motor,cEquals,"no")),
            new Clause(vehicule, cEquals, "bicycle"));


        Rule   tricycleRule = new Rule(rb, "tricycle_rule" ,
                clauseArray(
                        new Clause(vehiculeType,cEquals,"cycle"),
                        new Clause (numWheels, cEquals,"3"),
                        new Clause(motor,cEquals,"no")),
                new Clause(vehicule, cEquals, "tricycle"));


        Rule   motorcycleRule = new Rule(rb, "motorcycle_rule" ,
                clauseArray(
                        new Clause(vehiculeType,cEquals,"cycle"),
                        new Clause (numWheels, cEquals,"2"),
                        new Clause(motor,cEquals,"yes")),
                new Clause(vehicule, cEquals, "motorcycle"));

        Rule   sportCarRule = new Rule(rb, "sportCar_rule" ,
                clauseArray(
                        new Clause(vehiculeType,cEquals,"automobile"),
                        new Clause (size, cEquals,"small"),
                        new Clause(numDoors,cEquals,"2")),
                new Clause(vehicule, cEquals, "sportCar"));


        Rule   sedanRule = new Rule(rb, "sedan_rule" ,
                clauseArray(
                        new Clause(vehiculeType,cEquals,"automobile"),
                        new Clause (size, cEquals,"medium"),
                        new Clause(numDoors,cEquals,"4")),
                new Clause(vehicule, cEquals, "sedan"));


        Rule   miniVanRule = new Rule(rb, "miniVan_rule" ,
                clauseArray(
                        new Clause(vehiculeType,cEquals,"automobile"),
                        new Clause (size, cEquals,"medium"),
                        new Clause(numDoors,cEquals,"3")),
                new Clause(vehicule, cEquals, "miniVan"));

        Rule   suvRule = new Rule(rb, "suv_rule" ,
                clauseArray(
                        new Clause(vehiculeType,cEquals,"automobile"),
                        new Clause (size, cEquals,"large"),
                        new Clause(numDoors,cEquals,"4")),
                new Clause(vehicule, cEquals, "sportUtilityVehicule"));


        Rule   cycleRule = new Rule(rb, "cycle_rule" ,
                clauseArray(
                    new Clause(numWheels,cLessEqualThan,"4")),
                new Clause(vehiculeType, cEquals, "cycle"));


        Rule   automobileRule = new Rule(rb, "cycle_rule" ,
                clauseArray(
                        new Clause(numWheels,cLessEqualThan,"4")),
                new Clause(vehiculeType, cEquals, "cycle"));


    }






}



