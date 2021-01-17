package model.plateau;

import model.coups.PieceEtat;
import model.piece.*;

public class Position {

    //Atribut
    private int x;
    private int y;
    private boolean occupee=false;
    private Piece piecePresente;

    //Constructeur
    public Position(int y,int x, Piece piece){
        this.x=x;
        this.y=y;
        this.piecePresente=piece;
    }

    //Methode
    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public boolean isOccupe() {
        return occupee;
    }

    /**
     *
     * @param occupe : statut occupe
     */
    public void setOccupee(boolean occupe){
        this.occupee = occupe;
    }

    /**
     *  Cette méthode mets une piece donnée en paramètre sur la position
     * @param piece : piece qui sera mise sur la position
     */
    public void setPiece(Piece piece) {
        this.piecePresente = piece;
        if (piece!=null)
            occupee = true;
    }

    /**
     * Cette méthode mets à jour l'état de piece de la position
     * @param piece : état de la piece de l'état du plateau
     */
    public void setEtatPiece(PieceEtat piece){
        if (piece==null){ // Si la nouvelle version du plateau n'a pas de pièce à cet endroit
            this.piecePresente=null;
        }
        else{ // Sinon
            if (this.piecePresente==null) { // Si cette version ne possède pas de pièce à cet endroit et que la nouvelle version si
                instanciationType(piece);
            }
            else { // Sinon
                this.piecePresente.setCouleur(piece.getCouleur());
                this.piecePresente.setImage(piece.getImage());
                this.piecePresente.setType(piece.getType());
            }
        }
    }

    /**
     * Cette méthode enlève la piece courante de la position
     */
    public void unsetPiece(){
        this.piecePresente = null;
        occupee = false;
    }

    public Piece getPiece() {
        return piecePresente;
    }

    /**
     * Cette méthode instancie le bon type de pièce selon l'état de cette dernière donnée en paramètre
     * @param piece : etat d'un piece
     */
    public void instanciationType(PieceEtat piece){
        switch(piece.getType()){
            case FOU:
                this.piecePresente = new Fou(piece.getCouleur(), piece.getType(), piece.getImage());
                break;

            case ROI:
                this.piecePresente = new Roi(piece.getCouleur(), piece.getType(), piece.getImage(), this.x, this.y);
                break;

            case PION:
                this.piecePresente = new Pion(piece.getCouleur(), piece.getType(), piece.getImage());
                break;

            case TOUR:
                this.piecePresente = new Tour(piece.getCouleur(), piece.getType(), piece.getImage());
                break;

            case REINE:
                this.piecePresente = new Reine(piece.getCouleur(), piece.getType(), piece.getImage());
                break;

            case CAVALIER:
                this.piecePresente = new Cavalier(piece.getCouleur(), piece.getType(), piece.getImage());
                break;
        }
    }

    @Override
    public String toString(){
        return "X: "+getX()+"  Y: "+getY()+"  Piece : "+getPiece()+"\n";
    }
}
