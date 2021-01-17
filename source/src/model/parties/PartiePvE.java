package model.parties;

import model.joueur.IA;
import model.joueur.InterfaceJoueur;
import model.joueur.Joueur;
import model.piece.Piece;

public class PartiePvE extends Parties{

    public PartiePvE() {
        super();
        super.setJoueurs(1,new IA());
    }

    public IA getIA(){ return (IA) joueurs[1]; }

}