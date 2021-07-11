package jade;

import java.util.Date;

public class Offre {

    String nomCompany,durée_vol;
    int prix;
    //Date date;
    Boolean escale;

    public Offre(String nomCompany, String durée_vol, int prix, Boolean escale) {
        this.nomCompany = nomCompany;
        this.durée_vol = durée_vol;
        this.prix = prix;
        this.escale = escale;
    }

    public Offre() {
    }

    @Override
    public String toString() {
        return "Offre{" +
                "nomCompany='" + nomCompany + '\'' +
                ", durée_vol='" + durée_vol + '\'' +
                ", prix=" + prix +
                ", escale=" + escale +
                '}';
    }
}
