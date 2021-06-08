package jade;

import java.util.Date;

public class Formulaire {

    int nbBillets,nbPetits,nbEnfants,nbVieux;
    //Date dateVol;
    String depart,destination;
    Boolean escale;


    public Formulaire(int nbBillets, int nbPetits, int nbEnfants, int nbVieux,
                      String depart, String destination, Boolean escale) {
        this.nbBillets = nbBillets;
        this.nbPetits = nbPetits;
        this.nbEnfants = nbEnfants;
        this.nbVieux = nbVieux;
        //this.dateVol = dateVol;
        this.depart = depart;
        this.destination = destination;
        this.escale = escale;
    }

    @Override
    public String toString() {
        return "Formulaire{" +
                "nbBillets=" + nbBillets +
                ", nbPetits=" + nbPetits +
                ", nbEnfants=" + nbEnfants +
                ", nbVieux=" + nbVieux +
                ", depart='" + depart + '\'' +
                ", destination='" + destination + '\'' +
                ", escale=" + escale +
                '}';
    }
}
