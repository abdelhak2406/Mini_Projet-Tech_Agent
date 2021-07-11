package jade;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.tools.sniffer.Message;
import rule.JsonToRule;
import rule.RuleBase;

import java.util.ArrayList;
import java.util.Map;

public class Annexe extends Agent {
    Object[] obj=null;
    Boolean acceptable=false;

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

                            //Now that we have data we treat it with systeme experts here

                            String company;
                            Offre offre= new Offre();
                            if(getLocalName().equals("AN1")){
                                //SE1
                                company="Air algerie";
                                ArrayList<Vol> vols= new ArrayList<>();
                                Vol vol1= new Vol("Algerie","France","2H",false,200);
                                Vol vol2= new Vol("France","Algerie","1H30",true,250);
                                vols.add(vol1);vols.add(vol2);

                                AnnexeClass anex1=new AnnexeClass(company,vols,0.8F,0.1F,
                                        0.4F,0.3F,0.25F,0.15F,
                                        new JsonToRule("resources/vente_billets_1.json"));

                                anex1.getFormulaire(form.depart,form.destination,form.nbBillets,form.nbPetits,
                                        form.nbEnfants, form.nbVieux, form.escale);

                                offre=anex1.forwardChaining();

                            }
                            else if (getLocalName().equals("AN2")){
                                //SE2
                                company="Air France";
                                ArrayList<Vol> vols= new ArrayList<>();
                                Vol vol1= new Vol("France","Usa","9H",true,550);
                                Vol vol2= new Vol("France","Algerie","1H30",false,300);
                                vols.add(vol1);vols.add(vol2);

                                AnnexeClass anex1=new AnnexeClass(company,vols,0.5F,0.15F,
                                        0.5F,0.3F,0.2F,0.25F,
                                        new JsonToRule("resources/vente_billets_2.json"));

                                anex1.getFormulaire(form.depart,form.destination,form.nbBillets,form.nbPetits,
                                        form.nbEnfants, form.nbVieux, form.escale);

                                offre=anex1.forwardChaining();
                            }
                            else if (getLocalName().equals("AN3")){
                                //SE3
                                company="Qatar Airways";
                                ArrayList<Vol> vols= new ArrayList<>();
                                Vol vol1= new Vol("Usa","France","8H",true,500);
                                Vol vol2= new Vol("Qatar","Algerie","5H",false,600);
                                vols.add(vol1);vols.add(vol2);

                                AnnexeClass anex1=new AnnexeClass(company,vols,0.4F,0.3F,
                                        0.1F,0.15F,0.2F,0.25F,
                                        new JsonToRule("resources/vente_billets_3.json"));

                                anex1.getFormulaire(form.depart,form.destination,form.nbBillets,form.nbPetits,
                                        form.nbEnfants, form.nbVieux, form.escale);

                                offre=anex1.forwardChaining();
                            }
                            else {
                                //SE4
                                company="Turkish Airlines";
                                ArrayList<Vol> vols= new ArrayList<>();
                                Vol vol1= new Vol("Turkey","France","4H",true,400);
                                Vol vol2= new Vol("France","Algerie","3H",false,200);
                                vols.add(vol1);vols.add(vol2);

                                AnnexeClass anex1=new AnnexeClass(company,vols,0.5F,0.1F,
                                        0.4F,0.3F,0.2F,0.4F,
                                        new JsonToRule("resources/vente_billets_4.json"));

                                anex1.getFormulaire(form.depart,form.destination,form.nbBillets,form.nbPetits,
                                        form.nbEnfants, form.nbVieux, form.escale);

                                System.out.println("chainin");
                                offre=anex1.forwardChaining();
                            }
                            //modify acceptable when finished

                            //when we finish we get an offre
                            if(offre!=null){
                                acceptable=true;
                            }

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

