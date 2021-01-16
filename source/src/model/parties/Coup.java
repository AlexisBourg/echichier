package model.parties;

import model.joueur.Joueur;
import model.piece.Piece;

public class Coup {
    private final int[] depart;
    private final int[] arrivee;
    private final Piece pieceMangee;
    private final Joueur joueurCourant;
    private final Joueur joueurNonCourant;
    // trait représenté par la couleur du joueur courant.

    public Coup(int[] depart, int[] arrivee, Piece pieceMangee, Joueur joueurCourant, Joueur joueurNonCourant){
        this.depart = depart;
        this.arrivee = arrivee;
        this.pieceMangee = pieceMangee;
        this.joueurCourant = joueurCourant;
        this.joueurNonCourant = joueurNonCourant;
    }

    public int[] getDepart(){
        return this.depart;
    }

    public int[] getArrivee(){
        return this.arrivee;
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public Joueur getJoueurNonCourant(){
        return this.joueurNonCourant;
    }

    public Piece getPieceMangee() {
        return pieceMangee;
    }

    public boolean isPieceMangeeNull(){
        return pieceMangee==null;
    }
}
