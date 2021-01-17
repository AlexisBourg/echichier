package model.parties;

import model.joueur.IA;
import model.joueur.InterfaceJoueur;
import model.piece.Piece;

public class PartiePvE extends Parties{

    //Atribut

    //Constructeur
    public PartiePvE() {
        super();
        super.setJoueurs(1,new IA());
    }

    //Methode
    public IA getIA(){ return (IA) joueurs[1]; }
}