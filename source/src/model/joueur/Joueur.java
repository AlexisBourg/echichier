package model.joueur;

import model.piece.*;

public class Joueur implements InterfaceJoueur{
<<<<<<< HEAD
    public static final int NB_PIECES = 16;
    public static final int PREMIERE_PIECE_DERNIERE_RANGEE = 8;
    public static final int Y_ROI_NOIR = 0;
    public static final int Y_ROI_BLANC = 7;
=======

    //Atribut
>>>>>>> 2946f13a9d740fa9a4f0f4f5e965d439eb623709
    private Couleur couleur;
    private final Piece[] pieces;
    private final Piece[] piecesMortes;
    private int nbPiecesMortes=0;

    //Constructeur
    public Joueur(int numJoueur){ // Constructeur pour partie local
        pieces = new Piece[NB_PIECES];
        piecesMortes = new Piece[NB_PIECES];
        couleur = (numJoueur==1) ? Couleur.BLANC : Couleur.NOIR ;
        initPieces();
    }

    //Methode

    /**
     * permet d'initaliser les piece du joueur
     */
    public void initPieces(){
        int xRoi = 4, yRoi = (couleur==Couleur.NOIR) ? Y_ROI_NOIR : Y_ROI_BLANC;


        for (int i=0; i<8; i++) {
            pieces[i] = new Pion(couleur);
        }

        pieces[PREMIERE_PIECE_DERNIERE_RANGEE] = new Tour(couleur);
        pieces[PREMIERE_PIECE_DERNIERE_RANGEE+1] = new Cavalier(couleur);
        pieces[PREMIERE_PIECE_DERNIERE_RANGEE+2] = new Fou(couleur);
        pieces[PREMIERE_PIECE_DERNIERE_RANGEE+3] = new Reine(couleur);
        pieces[PREMIERE_PIECE_DERNIERE_RANGEE+4] = new Roi(couleur, xRoi, yRoi);
        pieces[PREMIERE_PIECE_DERNIERE_RANGEE+5] = new Fou(couleur);
        pieces[PREMIERE_PIECE_DERNIERE_RANGEE+6] = new Cavalier(couleur);
        pieces[PREMIERE_PIECE_DERNIERE_RANGEE+7] = new Tour(couleur);
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
