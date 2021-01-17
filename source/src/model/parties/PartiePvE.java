package model.parties;

import model.joueur.IA;
import model.joueur.InterfaceJoueur;
import model.piece.Piece;

public class PartiePvE extends Parties{

    public PartiePvE() {
        super();
        super.setJoueurs(1,new IA());
    }
<<<<<<< HEAD
    
=======

>>>>>>> d8dc896257ce996fa311fad39acc65bfbe68f685
    public IA getIA(){ return (IA) joueurs[1]; }

}