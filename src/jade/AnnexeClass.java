package jade;

import rule.JsonToRule;
import rule.Rule;
import rule.RuleBase;
import rule.RuleVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class AnnexeClass {

    int nb_billets,nb_age7,nb_age11,nb_age75;
    String annex_name,customer_depart,customer_dest;

    Boolean escale=false,bonus_estivale=false,proposer_vol=false;

    float prix,bonus,escaleR,reduction_nb_billet,
            reduction_petit,reduction_enfants,reduction_vieux;

    ArrayList<String> depart_list,destination_list;

    //Used when creating the annex, we get it from json or smthin
    public AnnexeClass(String annex_name,ArrayList<String> depart_list,ArrayList<String> destination_list,
                       float prix, float bonus, float escaleR, float reduction_nb_billet,
                       float reduction_petit, float reduction_enfants, float reduction_vieux) {
        this.annex_name=annex_name;
        this.prix = prix;
        this.bonus = bonus;
        this.escaleR = escaleR;
        this.reduction_nb_billet = reduction_nb_billet;
        this.reduction_petit = reduction_petit;
        this.reduction_enfants = reduction_enfants;
        this.reduction_vieux = reduction_vieux;

        this.depart_list=depart_list;
        this.destination_list=destination_list;
    }

    //user enter these infos from UI
    public void getFormulaire(String depart,String dest,int nb_billets,int nb_age7,
                              int nb_age11,int nb_age75,Boolean escale){

        customer_depart=depart;customer_dest=dest;
        this.nb_billets=nb_billets;this.nb_age7=nb_age7;
        this.nb_age11=nb_age11;this.nb_age75=nb_age75;
        this.escale=escale;
    }

    public Boolean forwardChaining() throws Exception{
        JsonToRule rules = new JsonToRule("resources/vente_billets.json") ;
        Map variableObjects  = rules.getVariableObjects() ;
        Map rulesObjects  = rules.getRuleObjects() ;
        RuleBase rb=rules.getRb();


        RuleVariable nbbilletVar = (RuleVariable) variableObjects.get("nb_billets");
        nbbilletVar.setValue(String.valueOf(nb_billets));

        RuleVariable departVar = (RuleVariable) variableObjects.get("depart");
        departVar.setValue(customer_depart);

        RuleVariable destVar = (RuleVariable) variableObjects.get("destination");
        destVar.setValue(customer_dest);

        RuleVariable petitVar = (RuleVariable) variableObjects.get("nb_age7");
        petitVar.setValue(String.valueOf(nb_age7));

        RuleVariable enfantVar = (RuleVariable) variableObjects.get("nb_age11");
        enfantVar.setValue(String.valueOf(nb_age11));

        RuleVariable vieuxVar = (RuleVariable) variableObjects.get("nb_age75");
        vieuxVar.setValue(String.valueOf(nb_age75));

        RuleVariable prixVar = (RuleVariable) variableObjects.get("prix");
        prixVar.setValue(String.valueOf(prix));

        RuleVariable escaleVar = (RuleVariable) variableObjects.get("escale");
        escaleVar.setValue(String.valueOf(escale));

        RuleVariable escaleRVar = (RuleVariable) variableObjects.get("escaleR");
        escaleRVar.setValue(String.valueOf(escaleR));

        RuleVariable bonusEVar = (RuleVariable) variableObjects.get("bonus_estivale");
        bonusEVar.setValue(String.valueOf(bonus_estivale));

        RuleVariable bonusVar = (RuleVariable) variableObjects.get("bonus");
        bonusVar.setValue(String.valueOf(bonus));

        RuleVariable reductionBilletVar = (RuleVariable) variableObjects.get("reduction_nbbillet");
        reductionBilletVar.setValue(String.valueOf(reduction_nb_billet));

        RuleVariable rpVar = (RuleVariable) variableObjects.get("reduction_petit");
        rpVar.setValue(String.valueOf(reduction_petit));

        RuleVariable reVar = (RuleVariable) variableObjects.get("reduction_enfants");
        reVar.setValue(String.valueOf(reduction_enfants));

        RuleVariable rvVar = (RuleVariable) variableObjects.get("reduction_vieux");
        rvVar.setValue(String.valueOf(reduction_vieux));

        System.out.println("STARTING FORWARD CHAINING");

        HashMap<String, ArrayList> outputs = rb.forwardChain();
        ArrayList<Rule> firedRules = outputs.get("fired");
        ArrayList<Vector> conflictSets = outputs.get("conflictSet");

        int lim =firedRules.size();

        for(int i=0; i<lim;i++){
            Rule firedTmp = firedRules.get(i);
            Vector tmpConf = conflictSets.get(i);
            System.out.println("conflict set "+i+"\n\n------------*------------");
            String cnf = rb.displayConflictSet(tmpConf);

            System.out.println("fired "+i+" "+firedTmp.getName());

        }
        return false;
    }


    @Override
    public String toString() {
        return "AnnexeClass{" +
                "nb_billets=" + nb_billets +
                ", nb_age7=" + nb_age7 +
                ", nb_age11=" + nb_age11 +
                ", nb_age75=" + nb_age75 +
                ", customer_depart='" + customer_depart + '\'' +
                ", customer_dest='" + customer_dest + '\'' +
                ", escale=" + escale +
                ", bonus_estivale=" + bonus_estivale +
                ", proposer_vol=" + proposer_vol +
                ", prix=" + prix +
                ", bonus=" + bonus +
                ", escaleR=" + escaleR +
                ", reduction_nb_billet=" + reduction_nb_billet +
                ", reduction_petit=" + reduction_petit +
                ", reduction_enfants=" + reduction_enfants +
                ", reduction_vieux=" + reduction_vieux +
                ", depart_list=" + depart_list +
                ", destination_list=" + destination_list +
                '}';
    }
}
