package model.joueur;

import model.piece.*;

public class IA implements InterfaceJoueur{

    //Atribut
    private final Couleur couleur = Couleur.NOIR;
    private final Piece[] pieces;
    private final Piece[] piecesMortes;
    private int nbPiecesMortes=0;

    //Constructeur
    public IA(){
        pieces = new Piece[16];
        piecesMortes = new Piece[16];
        initPieces();
    }

    //Methode
    /**
     * permet d'initaliser les piece de l'IA
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


    public Couleur getCouleur() { return couleur;}

}
