package jade;

public class Main_jade {
    public static void main(String[] args) {
        //probably gonna change it, we dont need the jade GUI
        String[] jadearg= new String[2];
        StringBuffer SbAgent=new StringBuffer();
        //create formulaire then send it like below

        Formulaire form= new Formulaire(5,1,2,0,
                "Alger","France",false);

        SbAgent.append("AC:jade.Agent_central("+form.nbBillets+","+form.nbPetits+
                ","+form.nbEnfants+","+form.nbVieux+","+form.depart+","+form.destination+"," +
                form.escale+");");
        SbAgent.append("AN1:jade.Annexe;");
        SbAgent.append("AN2:jade.Annexe;");
        SbAgent.append("AN3:jade.Annexe;");
        SbAgent.append("AN4:jade.Annexe;");
        jadearg[0]="-gui";
        jadearg[1]=SbAgent.toString();
        jade.Boot.main(jadearg);
    }
}
