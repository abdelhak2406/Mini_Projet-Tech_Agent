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
    Object[] obj=null;      //to receive serialisable Objects
    Object simpleObj=null;  //to receive strings
    Object[] arguments;     //infos li dkhelnahom
    protected void setup(){
        try{
            arguments= this.getArguments();
            DFAgentDescription dfd= new DFAgentDescription();
            dfd.setName(this.getAID());
            DFService.register(this,dfd);

            //Creating the object  to send to annexes
            obj=new Object[arguments.length];
            for(int i=0;i<arguments.length;i++){
                obj[i]=arguments[i];
            }
            //We send messages like this
            for (int i = 0; i < 4; i++) {
                ACLMessage message= new ACLMessage(ACLMessage.INFORM);
                message.setContentObject(obj);
                String s="AN"+(i+1);
                message.addReceiver(new AID(s,AID.ISLOCALNAME));
                send(message);
            }


        }catch (Exception e){
            System.out.println(e);
        }

        addBehaviour(new CyclicBehaviour(this) {
            public void action(){
                //we wait for annexes to return their result
                ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                if (msg != null) {
                    //we print the data given by les annexes, treat it and
                    //then resend another message to the annex that the user has chosen

                    try {
                        System.out.println("printing in AC : ");
                        simpleObj=msg.getContent();
                        System.out.println(simpleObj);

                        //new message to the annexe we chose, probably better if its a function
                        //doesnt work yet :(
                        ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
                        msg1.setContent(simpleObj.toString());
                        //msg1.addReceiver(new AID("AN11", AID.ISLOCALNAME));
                        //send(msg1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Do delete stops the cyclic behaviour, kills the agent.
                    //doDelete();
                }
                else {
                    //this blocks the agent until it gets a message
                    block();
                }
            }
        });
    }
}
