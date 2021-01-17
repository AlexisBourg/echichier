package model.joueur;

import model.piece.Couleur;
import model.piece.Piece;

public interface InterfaceJoueur {
    public void initPieces();

    public Piece[] getPieces();

    public Couleur getCouleur();

    public void addPieceMorte(Piece pieceMorte);

}