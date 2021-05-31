package jade;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Annexe2 extends Agent {
    Object[] obj=null;
    protected void setup(){
        try{
            DFAgentDescription dfd= new DFAgentDescription();
            dfd.setName(this.getAID());
            DFService.register(this,dfd);
        }catch (Exception e){
            System.out.println(e);
        }

        addBehaviour(new CyclicBehaviour(this) {
            public void action(){
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                if (msg != null) {
                    try {
                        obj=(Object[]) msg.getContentObject();
                        ACLMessage reply = msg.createReply();
                        //systeme experts comes here
                        //reply.setContent(data);
                        myAgent.send(reply);

                    }catch (Exception e){
                        System.out.println(e);
                    }
                    doDelete();
                }
                else {
                    block();
                }
                //whathever
            }
        });
    }
}


