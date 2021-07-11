package jade;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.io.Serializable;
/*
anexe = compagnie
*/

public class Agent_central extends Agent {
    Object[] obj=null;      //to receive serialisable Objects
    Object simpleObj=null;  //to receive strings
    Object[] arguments;     //infos li dkhelnahom
    int compteur=0;

    protected void setup(){
        try{
            arguments= this.getArguments();
            DFAgentDescription dfd= new DFAgentDescription();
            dfd.setName(this.getAID());
            DFService.register(this,dfd);

            //Creating the object  to send to annexes
            obj=arguments;

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
                ACLMessage msg = receive();
                if (msg != null) {
                    compteur++;
                    //when annexes return their results
                    if (msg.getPerformative()==ACLMessage.INFORM){
                        //we print the data given by les annexes, treat it and
                        //then resend another message to the annex that the user has chosen

                        try {

                            System.out.println("printing in AC : ");
                            Object[] annexReply = (Object[]) msg.getContentObject();

                            //HERE ARE THE OFFERS TO PRINT IN UI, LZM TRECUPIRIHOM GE3 SINCE YJO BEL WA7D (multi agent)
                            Offre offre= new Offre((String) annexReply[0],(String) annexReply[1],
                                    (int) annexReply[2],(Boolean) annexReply[3]);

                            System.out.println(offre);


                            //new message to the annexe we choose, probably better if its a function
                            //Le compteur y7seb w9teh on a recu ge3 les messages, so ki ndkhlo l if hedi mli7a
                            //tu call un fct qui va faire attendre hed l'agent jusqu'a ce que le client choisi l'annexe,
                            if(compteur==4){

                                ACLMessage msg1 = new ACLMessage(ACLMessage.CONFIRM);
                                msg1.setContent("We choose YOU");
                                //send to selected annexe
                                //puis 3la 7sab l'annexe bdel l AID to AN1 AN2 AN3 or AN4
                                msg1.addReceiver(new AID("AN2", AID.ISLOCALNAME));
                                send(msg1);
                            }
                            //mli7a every print li rahi f this class t7etha fel UI since il l'a demandé,
                            // after that cbn kemelna!

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    else if (msg.getPerformative()==ACLMessage.REFUSE){
                        //l'annexe n'a pas accepté le formulaire envoyé
                        String wh= msg.getContent();
                        System.out.println(wh);
                    }

                    else {
                        //receive message from selected annexe, that l'achat is accepted
                        simpleObj=msg.getContent();
                        System.out.println(simpleObj);

                    }

                }
                else {
                    //this blocks the agent until it gets a message
                    block();
                }
            }
        });
    }
}
