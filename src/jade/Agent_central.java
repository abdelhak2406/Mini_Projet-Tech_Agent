package jade;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

public class Agent_central extends Agent {
    Object[] obj=null;
    Object[] arguments; //infos li dkhelnahom
    protected void setup(){
        try{
            arguments= this.getArguments();
            DFAgentDescription dfd= new DFAgentDescription();
            dfd.setName(this.getAID());
            DFService.register(this,dfd);
        }catch (Exception e){
            System.out.println(e);
        }

        for(int i=0;i<arguments.length;i++){
            System.out.println(arguments[i]);
        }
        addBehaviour(new CyclicBehaviour(this) {
            public void action(){
                //we wait for annexes to return
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                if (msg != null) {
                    //we print the data given by les annexes, treat it and
                    //then resend another message
                    ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
                    try {
                        obj=(Object[]) msg.getContentObject();
                        msg1.setContentObject(obj);
                        msg1.addReceiver(new AID("Annexei", AID.ISLOCALNAME));
                        send(msg1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    doDelete();
                }
                else {
                    block();
                }
                //blabla whathever
            }
        });
    }
}
