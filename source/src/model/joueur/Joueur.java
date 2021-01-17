package model.joueur;

import model.piece.*;

public class Joueur implements InterfaceJoueur{

    //Atribut
    private Couleur couleur;
    private final Piece[] pieces;
    private final Piece[] piecesMortes;
    private int nbPiecesMortes=0;

    //Constructeur
    public Joueur(int numJoueur){ // Constructeur pour partie local
        pieces = new Piece[16];
        piecesMortes = new Piece[16];
        couleur = (numJoueur==1) ? Couleur.BLANC : Couleur.NOIR ;
        initPieces();
    }

    //Methode

    /**
     * permet d'initaliser les piece du joueur
     */
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

    /**
     * retpurne la liste de piece
     * @return
     */
    public Piece[] getPieces(){
        return pieces;
    }

    /**
     * Permet d'ajouter une piece à la liste des pièces mortes
     * @param pieceMorte : est la pièce à ajouter
     */
    public void addPieceMorte(Piece pieceMorte){
        piecesMortes[nbPiecesMortes] = pieceMorte;
        nbPiecesMortes+=1;
    }

    /**
     * retourne la couleur du joueur
     * @return
     */
    public Couleur getCouleur() { return couleur;}
}
