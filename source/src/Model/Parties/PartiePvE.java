package Model.Parties;

import Model.Joueur.IA;
import Model.Joueur.Joueur;

public class PartiePvE extends PartieGraph {

    public PartiePvE() {
        super();
        super.setJoueurs(1,new IA() );
    }

    public Joueur getJoueurCourant(){
        return getJoueur(indexJoueurCourant);
    }
}
