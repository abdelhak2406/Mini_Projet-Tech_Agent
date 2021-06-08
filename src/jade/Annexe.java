package jade;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.tools.sniffer.Message;

public class Annexe extends Agent {
    Object[] obj=null;
    Boolean acceptable=true;

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
                            System.out.println("printing in "+getLocalName());
                            System.out.println(myAgent.getLocalName());
                            obj=(Object[]) msg.getContentObject();
                            Formulaire form= new Formulaire(Integer.parseInt((String) obj[0]),Integer.parseInt((String) obj[1]),
                                    Integer.parseInt((String) obj[2]), Integer.parseInt((String) obj[3]),(String) obj[4],
                                    (String)obj[5],Boolean.parseBoolean((String) obj[6]));
                            System.out.println(form);

                            //Now that we have data we treat it with systeme experts here

                            String company;
                            if(getLocalName().equals("AN1")){
                                //SE1
                                company="Air algerie";
                            }
                            else if (getLocalName().equals("AN2")){
                                //SE2
                                company="Air France";
                            }
                            else if (getLocalName().equals("AN3")){
                                //SE3
                                company="Aigle Azur";
                            }
                            else {
                                //SE4
                                company="Turkish Airlines";
                            }
                            //modify acceptable when finished

                            //when we finish we get an offre
                            Offre offre= new Offre(company,"3H",100000,false);

                            if(acceptable){
                                Object[] replyO= new Object[4];
                                replyO[0]=offre.nomCompany;replyO[1]=offre.durée_vol;
                                replyO[2]=offre.prix;replyO[3]=offre.escale;
                                ACLMessage reply = msg.createReply();
                                reply.setContentObject(replyO);
                                myAgent.send(reply);
                            }else {
                                ACLMessage refus= new ACLMessage(ACLMessage.REFUSE);
                                refus.setContent(getLocalName()+" a refuser because whathever");
                                send(refus);
                            }


                        }catch (Exception e){
                            System.out.println(e);
                        }

                    }
                    else if(msg.getPerformative()==ACLMessage.CONFIRM){
                        //we enter here to confirm l'achat des billets
                        System.out.println(getLocalName()+" CONFIRMATION");
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

