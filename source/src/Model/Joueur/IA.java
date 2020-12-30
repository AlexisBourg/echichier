package Model.Joueur;

import Model.Piece.*;

public class IA implements InterfaceJoueur{
    private Couleur couleur=Couleur.NOIR;
    private Piece[] pieces;
    private Piece[] piecesMortes;
    private int nbPiecesMortes=0;

    public IA(){
        pieces = new Piece[16];
        piecesMortes = new Piece[16];
        initPieces();
    }

    public void initPieces(){
        int yTetes, yPions, x=0;

        yPions = 1;
        yTetes = 0;

        for (int i=0; i<8; i++) {
            pieces[i] = new Pion(x, yPions, couleur);
            x+=1;
        }

        pieces[8] = new Tour(0, yTetes, couleur);
        pieces[9] = new Cavalier(1, yTetes, couleur);
        pieces[10] = new Fou(2, yTetes, couleur);
        pieces[11] = new Reine(3, yTetes, couleur);
        pieces[12] = new Roi(4, yTetes, couleur);
        pieces[13] = new Fou(5, yTetes, couleur);
        pieces[14] = new Cavalier(6, yTetes, couleur);
        pieces[15] = new Tour(7, yTetes, couleur);
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

    public void enleverPiece(Piece piece){
        for (int i=0; i<16; i++){
            if (pieces[i].equals(piece))
                pieces[i] = null;
        }
    }

    public Couleur getCouleur() { return couleur;}

}
