package Model.Joueur;

import Model.Piece.Couleur;
import Model.Piece.Piece;

public interface InterfaceJoueur {
    public void initPieces();

    public Piece[] getPieces();

    public Couleur getCouleur();

    public void addPieceMorte(Piece pieceMorte);

    //public void enleverPiece(Piece piece);
}