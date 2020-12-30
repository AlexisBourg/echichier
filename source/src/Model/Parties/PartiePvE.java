package Model.Parties;

import Model.Joueur.IA;
import Model.Joueur.Joueur;
import Model.Joueur.InterfaceJoueur;
import Model.PLateau.Plateau;

public class PartiePvE extends Parties implements FactoryPartie {


    public PartiePvE() {
        super();
        super.setJoueurs(1,new IA() );
    }

    public void partie() {

    }

}
