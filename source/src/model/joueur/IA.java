package model.joueur;

import model.piece.*;

public class IA implements InterfaceJoueur{
    public static final int NB_PIECES = 16;
    public static final int PREMIERE_PIECE_DERNIERE_RANGEE = 8;
    public static final int Y_ROI_NOIR = 0;
    public static final int Y_ROI_BLANC = 7;
    private final Couleur couleur=Couleur.NOIR;
    private final Piece[] pieces;
    private final Piece[] piecesMortes;
    private int nbPiecesMortes=0;

    public IA(){
        pieces = new Piece[NB_PIECES];
        piecesMortes = new Piece[NB_PIECES];
        initPieces();
    }

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

    public Piece[] getPieces(){
        return pieces;
    }

    public Piece[] getPiecesMortes(){
        return piecesMortes;
    }

    public boolean estPieceMorte(Piece piece){

        for (int i=0; i<nbPiecesMortes;i++){
            if (piecesMortes[i].equals(piece)){
                return true;
            }
        }
        return false;
    }

    public void addPieceMorte(Piece pieceMorte){
        piecesMortes[nbPiecesMortes] = pieceMorte;
        nbPiecesMortes+=1;
    }

    @Override
    public void removePieceMorte() { piecesMortes[nbPiecesMortes] = null; }

    public Couleur getCouleur() { return couleur;}

}
