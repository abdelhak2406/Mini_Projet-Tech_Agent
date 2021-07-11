package jade;

public class LaunchAgents {

    Formulaire form;

    public  void launchAgents(int nbBillets, int nbPetits, int nbEnfants, int nbVieux,
                                    String depart, String destination, Boolean escale){
        //probably gonna change it, we dont need the jade GUI
        String[] jadearg= new String[2];
        StringBuffer SbAgent=new StringBuffer();
        //create formulaire then send it like below
        this.form = new Formulaire(nbBillets,nbPetits,nbEnfants,nbVieux,
                depart,destination,escale);

        SbAgent.append("AC:jade.Agent_central("+this.form.nbBillets+","+this.form.nbPetits+
                ","+this.form.nbEnfants+","+this.form.nbVieux+","+this.form.depart+","+this.form.destination+"," +
                this.form.escale+");");
        SbAgent.append("AN1:jade.Annexe;");
        SbAgent.append("AN2:jade.Annexe;");
        SbAgent.append("AN3:jade.Annexe;");
        SbAgent.append("AN4:jade.Annexe;");
        jadearg[0]="-gui";
        jadearg[1]=SbAgent.toString();
        jade.Boot.main(jadearg);
    }


}
