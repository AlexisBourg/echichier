package model.joueur;

import model.piece.*;

public class IA implements InterfaceJoueur{


    //Atribut
    public static final int NB_PIECES = 16;
    public static final int PREMIERE_PIECE_DERNIERE_RANGEE = 8;
    public static final int Y_ROI_NOIR = 0;
    public static final int Y_ROI_BLANC = 7;
    private final Couleur couleur = Couleur.NOIR;
    private final Piece[] pieces;
    private final Piece[] piecesMortes;
    private int nbPiecesMortes=0;

    //Constructeur
    public IA(){
        pieces = new Piece[NB_PIECES];
        piecesMortes = new Piece[NB_PIECES];
        initPieces();
    }

    //Methode
    /**
     * permet d'initaliser les piece de l'IA
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
     * Retourne vrai si la pièce a été tué, sinon retourne faux
     * @param piece : est la piece à verifier
     * @return
     */
    public boolean estPieceMorte(Piece piece){
        for (int i=0; i<piecesMortes.length;i++){
            if (piecesMortes[i].equals(piece)){
                return true;
            }
        }
        return false;
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
     * Retourne la couleur de la piece
     * @return
     */
    public Couleur getCouleur() { return couleur;}

}
