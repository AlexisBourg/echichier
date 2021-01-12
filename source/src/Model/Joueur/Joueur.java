package Model.Joueur;

import Model.Piece.*;

public class Joueur implements InterfaceJoueur{
    private Couleur couleur;
    private String nom;
    private final String prenom;
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
        int yTetes, yPions, x=0;

        if(couleur==Couleur.NOIR) {
            yPions = 1;
            yTetes = 0;
        }
        else {
            yPions = 6;
            yTetes = 7;
        }

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

    public void removePieceMorte(){
        piecesMortes[nbPiecesMortes] = null;
    }

    public Couleur getCouleur() { return couleur;}

    public String getPrenom(){return prenom;}

    public String getNom(){return nom;}
}
