
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        RuleBase rb = new RuleBase("Achat laptop") ;
        rb.goalClauseStack = new Stack() ;
        rb.variableList = new Hashtable() ;


        //Creating the variables
        RuleVariable software_needs = new RuleVariable("software_needs") ;
        software_needs.setLabels("user_friendly finalCutPro gaming niche_user_interface");
        rb.variableList.put(software_needs.name,software_needs) ;


        RuleVariable os = new RuleVariable("OS") ;
        os.setLabels("linux windows iOS ");
        rb.variableList.put(os.name,os) ;

        RuleVariable linux_distro = new RuleVariable("linux_distro") ;
        linux_distro.setLabels("Manjaro linuxMint Kubuntu");
        rb.variableList.put(linux_distro.name,linux_distro) ;


        RuleVariable ramVar = new RuleVariable("RAM") ;
        ramVar.setLabels("2 4 8 12 16");
        rb.variableList.put(ramVar.name,ramVar) ;


        RuleVariable desktop_environement = new RuleVariable("desktop_environement") ;
        desktop_environement.setLabels("kde_plasma xfce cinamone");
        rb.variableList.put(desktop_environement.name,desktop_environement) ;


        RuleVariable user_budget = new RuleVariable("user_budget") ;
        user_budget.setLabels("200 300 400 500 600 700 800 900 1000");
        rb.variableList.put(user_budget.name,user_budget) ;

        RuleVariable laptop_range = new RuleVariable("laptop_range") ;
        laptop_range.setLabels("low medium high");
        rb.variableList.put(laptop_range.name,laptop_range) ;

        RuleVariable laptop = new RuleVariable("laptop") ;
        laptop.setLabels("Macbook_air macbook_Pro KDE_slimbook Lenovo_thinkpad_X240 asus_rog microsoft_Surface");
        rb.variableList.put(laptop.name,laptop) ;






        // Note: at this point all variables values are NULL
        //conditions
        Condition cEquals = new Condition("=") ;
        Condition cNotEquals = new Condition("!=") ;
        Condition cLessThan = new Condition("<") ;
        Condition cMoreThan = new Condition(">") ;
        rb.ruleList = new Vector() ;




        // Creating rules and closes
        //Budget
        Rule lowbudget = new Rule(rb, "regle1:si budget<300" ,

                new Clause(user_budget,cLessThan,"300")  ,
                new Clause(laptop_range, cEquals, "low"));

        Rule mediumbudget = new Rule(rb, "si budget medium",

                new Clause(user_budget,cLessThan,"1000")  ,
                new Clause(user_budget,cMoreThan,"500"),
                new Clause(laptop_range, cEquals, "medium"));

        Rule highbudget = new Rule(rb, "si budget high",

                new Clause(user_budget,cMoreThan,"1000")  ,
                new Clause(laptop_range, cEquals, "high"));



        //desktop_environement rules
        Rule os_cinamon_rule = new Rule(rb, "cinnamon rule" ,


                new Clause(os,cEquals,"linux")  ,

                new Clause(laptop_range, cEquals, "low"),
                new Clause(ramVar, cLessThan, "4"),
                new Clause(desktop_environement, cEquals, "cinnamon"));


        Rule os_kde_rule = new Rule(rb, "kde_plasma rule",

                new Clause(os,cEquals,"linux")  ,

                new Clause(laptop_range, cEquals, "medium"),
                new Clause(ramVar, cMoreThan, "4"),
                new Clause(desktop_environement, cEquals, "kde_plasma"));


        Rule os_xfce_rule = new Rule(rb, "xfce rule",

                new Clause(os,cEquals,"linux")  ,

                new Clause(laptop_range, cEquals, "low"),
                new Clause(ramVar, cLessThan, "4"),
                new Clause(software_needs, cEquals, "niche_user_interface"),
                new Clause(desktop_environement,cEquals,"xfce"));



        //linux distribution rules
        Rule kubuntu_rule = new Rule(rb, "kubuntu_rule" ,

                new Clause(desktop_environement,cEquals,"kde_plasma")  ,
                new Clause(linux_distro, cEquals, "Kubuntu"));


        Rule linuxMint_rule = new Rule(rb, "linuxMint rule" ,
                new Clause(desktop_environement,cEquals,"cinnamon")  ,
                new Clause(linux_distro, cEquals, "LinuxMint"));

        Rule Manjaro_rule = new Rule(rb, "Manjaro rule" ,

                new Clause(desktop_environement,cEquals,"xfce")  ,
                new Clause(linux_distro, cEquals, "Manjaro"));



        //Laptop rules
        Rule slimbook_rule = new Rule(rb, "slimBook rule" ,

                new Clause(linux_distro,cEquals,"Kubuntu")  ,
                new Clause(laptop, cEquals, "KDE_Slimbook"));

        Rule lenovo_rule1 = new Rule(rb, "lenovo rule 1",

                new Clause(linux_distro,cEquals,"LinuxMint"),
                new Clause(laptop, cEquals, "lenovo ThinkPad X240"));

        Rule lenovo_rule2 = new Rule(rb, "lenovo rule 2",

                new Clause(linux_distro,cEquals,"Manjaro")  ,
                new Clause(laptop, cEquals, "lenovo ThinkPad X240"));



        Rule macBookAir_rule = new Rule(rb, "MacBookAir_rule" ,

                new Clause(os,cEquals,"iOS")  ,
                new Clause(laptop_range,cEquals,"medium")  ,
                new Clause(software_needs,cEquals,"finalCutPro")  ,
                new Clause(ramVar,cLessThan,"8")  ,
                new Clause(laptop, cEquals, "MacBookAir"));

        Rule macBookPro_rule = new Rule(rb, "macBookPro rule",

                new Clause(os,cEquals,"iOS")  ,
                new Clause(laptop_range,cEquals,"high")  ,
                new Clause(software_needs,cEquals,"finalCutPro")  ,
                new Clause(ramVar,cMoreThan,"8")  ,
                new Clause(laptop, cEquals, "MacBookPro"));



        Rule asus_rog_rule = new Rule(rb, "Asus rog rule",

                new Clause(os,cEquals,"windows")  ,
                new Clause(software_needs,cEquals,"gaming")  ,
                new Clause(laptop_range,cEquals,"high"),
                new Clause(laptop, cEquals, "Asus rog"));

        Rule microsoft_rule = new Rule(rb, "microsoft surface rule" ,

                new Clause(os,cEquals,"windows")  ,
                new Clause(laptop_range,cEquals,"medium")  ,
                new Clause(laptop, cEquals, "microsoft Surface"));


        user_budget.setValue("1200");
        os.setValue("windows");
        software_needs.setValue("gaming");
        ramVar.setValue("16");
        rb.displayRules();
        rb.forwardChain();
        rb.displayVariables();
    }


}
