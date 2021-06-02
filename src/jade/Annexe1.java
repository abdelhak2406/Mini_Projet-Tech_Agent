package jade;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Annexe1 extends Agent {
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
                ACLMessage msg = receive();
                if (msg != null) {
                    if(msg.getPerformative()==ACLMessage.INFORM){
                        //We will make an if here li tdifferenci between when we send the data
                        //Or when we choose the annexe pour le vol
                        try {
                            System.out.println("printing in AN1 : ");
                            obj=(Object[]) msg.getContentObject();
                            for (int i = 0; i < obj.length; i++) {
                                System.out.println(obj[i]);
                            }

                            ACLMessage reply = msg.createReply();
                            //systeme experts comes here probably
                            reply.setContent("AN1 IS RESPONDING TO AGENT_CENTRAL");
                            myAgent.send(reply);

                        }catch (Exception e){
                            System.out.println(e);
                        }

                    }
                    else if(msg.getPerformative()==ACLMessage.CONFIRM){
                        //we enter here to confirm l'achat des billets
                        System.out.println("AN1 CONFIRMATION");
                        ACLMessage reply=msg.createReply();
                        reply.setContent("WELCOME ABROAD :D");
                        myAgent.send(reply);
                        //this deletes the agent
                        doDelete();
                    }

                }
                else {
                    block();
                }
                //whathever
            }
        });
    }
}

