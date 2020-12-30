package Model.Joueur;

import Model.Piece.Couleur;
import Model.Piece.Piece;

public interface JoueurCourant {
    public void initPieces();
    public Piece[] getPieces();
    public void enleverPiece(Piece piece);
    public Couleur getCouleur();
}
