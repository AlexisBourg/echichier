package model.parties;

import model.joueur.IA;
import model.joueur.Joueur;
import model.piece.Piece;

public class PartiePvE extends Parties{

    public PartiePvE() {
        super();
        super.setJoueurs(1,new IA());
    }

    public Joueur getJoueurCourant(){
        return getJoueur(indexJoueurCourant);
    }

    public IA getIA(){ return (IA) joueurs[1]; }

    public Piece actualiserPlateauIA(int[] depart, int[] arrivee){
        Piece pieceMorte = deplacerPiece(depart, arrivee);
        if(pieceMorte!=null) {
            getIA().addPieceMorte(pieceMorte);
        }
        return pieceMorte;
    }
}