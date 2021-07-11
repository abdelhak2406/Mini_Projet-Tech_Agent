package jade;

public class Vol {

    String depart, destination,durée;
    Boolean escale;
    int prix;

    public Vol(String depart, String destination, String durée, Boolean escale,int prix) {
        this.depart = depart;
        this.destination = destination;
        this.durée = durée;
        this.escale=escale;
        this.prix=prix;
    }

    @Override
    public String toString() {
        return "Vol{" +
                "depart='" + depart + '\'' +
                ", destination='" + destination + '\'' +
                ", durée='" + durée + '\'' +
                ", escale=" + escale +
                ", prix=" + prix +
                '}';
    }
}
