package model.joueur;

import model.piece.*;

public class Joueur implements InterfaceJoueur{
    private Couleur couleur;
    private final Piece[] pieces;
    private final Piece[] piecesMortes;
    private int nbPiecesMortes=0;

    public Joueur(int numJoueur){ // Constructeur pour partie local
        prenom = "Joueur";
        pieces = new Piece[16];
        piecesMortes = new Piece[16];

        switch (numJoueur) {
            case 1 :
                couleur = Couleur.BLANC;
                nom = " 1";
            break;
            case 2 :
                couleur = Couleur.NOIR;
                nom = " 2";
            break;
        }
        initPieces();
    }

    public void initPieces(){
        int xRoi = 4, yRoi = (couleur==Couleur.NOIR) ? 0 : 7;


        for (int i=0; i<8; i++) {
            pieces[i] = new Pion(couleur);
        }

        pieces[8] = new Tour(couleur);
        pieces[9] = new Cavalier(couleur);
        pieces[10] = new Fou(couleur);
        pieces[11] = new Reine(couleur);
        pieces[12] = new Roi(couleur, xRoi, yRoi);
        pieces[13] = new Fou(couleur);
        pieces[14] = new Cavalier(couleur);
        pieces[15] = new Tour(couleur);
    }

    public Piece[] getPieces(){
        return pieces;
    }

    public Piece[] getPiecesMortes(){
        return piecesMortes;
    }

    public void addPieceMorte(Piece pieceMorte){
        piecesMortes[nbPiecesMortes] = pieceMorte;
        nbPiecesMortes+=1;
    }

    public void removePieceMorte(){
        piecesMortes[nbPiecesMortes] = null;
    }

    public Couleur getCouleur() { return couleur;}
}
