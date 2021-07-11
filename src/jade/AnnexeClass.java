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

    int nb_billets,nb_age7,nb_age11,prix=0,nb_age75;
    String annex_name,customer_depart,customer_dest;

    Boolean escale=false,bonus_estivale=false,proposer_vol=false;

    float bonus,escaleR,reduction_nb_billet,
            reduction_petit,reduction_enfants,reduction_vieux;
    JsonToRule rules;

    ArrayList<Vol> vols;

    //Used when creating the annex, we get it from json or smthin
    public AnnexeClass(String annex_name,ArrayList<Vol> vols,
                       float bonus, float escaleR, float reduction_nb_billet,
                       float reduction_petit, float reduction_enfants, float reduction_vieux,JsonToRule rules) {
        this.annex_name=annex_name;
        this.bonus = bonus;
        this.escaleR = escaleR;
        this.reduction_nb_billet = reduction_nb_billet;
        this.reduction_petit = reduction_petit;
        this.reduction_enfants = reduction_enfants;
        this.reduction_vieux = reduction_vieux;
        this.rules=rules;

        this.vols=vols;

        transformReductions();
    }

    //user enter these infos from UI
    public void getFormulaire(String depart,String dest,int nb_billets,int nb_age7,
                              int nb_age11,int nb_age75,Boolean escale){

        customer_depart=depart;customer_dest=dest;
        this.nb_billets=nb_billets;this.nb_age7=nb_age7;
        this.nb_age11=nb_age11;this.nb_age75=nb_age75;
        this.escale=escale;
    }

    public void transformReductions(){
        bonus=(int)(1/bonus);
        escaleR=(int)(1/escaleR);
        reduction_nb_billet=(int)(1/reduction_nb_billet);
        reduction_petit=(int)(1/reduction_petit);
        reduction_enfants=(int)(1/reduction_enfants);
        reduction_vieux=(int)(1/reduction_vieux);
    }

    public Offre forwardChaining() throws Exception{

        Vol vol= getVol(customer_depart,customer_dest);
        if(vol!=null){
            prix=vol.prix;
            escale=vol.escale;
        }

        System.out.println("pls1");

        Map variableObjects  = rules.getVariableObjects() ;
        Map rulesObjects  = rules.getRuleObjects() ;
        RuleBase rb=rules.getRb();
        System.out.println("pls2");

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
        RuleVariable prixVar = (RuleVariable) variableObjects.get("prix_init");
        prixVar.setValue(String.valueOf(prix));

        RuleVariable escaleVar = (RuleVariable) variableObjects.get("escale");
        escaleVar.setValue(String.valueOf(escale));

        RuleVariable escaleRVar = (RuleVariable) variableObjects.get("escaleR");
        escaleRVar.setValue(String.valueOf((int)escaleR));

        RuleVariable bonusEVar = (RuleVariable) variableObjects.get("bonus_estivale");
        bonusEVar.setValue(String.valueOf(bonus_estivale));

        RuleVariable bonusVar = (RuleVariable) variableObjects.get("bonus");
        bonusVar.setValue(String.valueOf(bonus));

        RuleVariable reductionBilletVar = (RuleVariable) variableObjects.get("reduction_nbbillet");
        reductionBilletVar.setValue(String.valueOf((int)reduction_nb_billet));

        RuleVariable rpVar = (RuleVariable) variableObjects.get("reduction_petit");
        rpVar.setValue(String.valueOf((int)reduction_petit));


        RuleVariable reVar = (RuleVariable) variableObjects.get("reduction_enfants");
        reVar.setValue(String.valueOf((int)reduction_enfants));

        RuleVariable rvVar = (RuleVariable) variableObjects.get("reduction_vieux");
        rvVar.setValue(String.valueOf((int)reduction_vieux));


        RuleVariable prix7Var = (RuleVariable) variableObjects.get("prix_7");
        prix7Var.setValue(String.valueOf(0));


        RuleVariable prix11Var = (RuleVariable) variableObjects.get("prix_11");
        prix11Var.setValue(String.valueOf(0));

        RuleVariable prix75Var = (RuleVariable) variableObjects.get("prix_75");
        prix75Var.setValue(String.valueOf(0));

        RuleVariable prixnbbVar = (RuleVariable) variableObjects.get("prix_billets");
        prixnbbVar.setValue(String.valueOf(0));

        RuleVariable prixEscaleVar = (RuleVariable) variableObjects.get("prix_escale");
        prixEscaleVar.setValue(String.valueOf(0));

        RuleVariable prixbonusVar = (RuleVariable) variableObjects.get("prix_bonus");
        prixbonusVar.setValue(String.valueOf(0));

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


        if(Boolean.valueOf(rb.getVariable("proposer_vol").getValue()) ==true){
            Offre offre= new Offre(annex_name,getVol(customer_depart,customer_dest).durée,
                    Integer.valueOf(rb.getVariable("prix_final").getValue()),
                    getVol(customer_depart,customer_dest).escale);
            return  offre;
        }
        return null;

    }

    public Vol getVol(String depart, String dest){
        for (Vol i : vols){
            if(i.depart.equals(depart) && i.destination.equals(dest)){
                return i;
            }
        }
        return null;
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
                ", Vols list=" + vols+
                '}';
    }
}
