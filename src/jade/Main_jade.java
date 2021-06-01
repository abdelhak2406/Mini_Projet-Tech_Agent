package jade;

public class Main_jade {
    public static void main(String[] args) {
        String[] jadearg= new String[2];
        StringBuffer SbAgent=new StringBuffer();
        SbAgent.append("AC:jade.Agent_central(arguments,META,drias);");
        SbAgent.append("AN1:jade.Annexe1;");
        SbAgent.append("AN2:jade.Annexe2;");
        SbAgent.append("AN3:jade.Annexe3;");
        SbAgent.append("AN4:jade.Annexe4;");
        jadearg[0]="-gui";
        jadearg[1]=SbAgent.toString();
        jade.Boot.main(jadearg);
    }
}
