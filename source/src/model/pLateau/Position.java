package model.plateau;

import model.coups.PieceEtat;
import model.piece.*;

public class Position {
    private int x;
    private int y;
    private boolean occupee=false;
    private Piece piecePresente;

    public Position(int y,int x, Piece piece){
        this.x=x;
        this.y=y;
        this.piecePresente=piece;
    }

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

    public void setOccupee(boolean occupe){
        this.occupee = occupe;
    }

    public void setPiece(Piece piece) {
        this.piecePresente = piece;
        if (piece!=null)
            occupee = true;
    }

    public void setEtatPiece(PieceEtat piece){
        if (piece==null){ // Si la nouvelle version du plateau n'a pas de pièce à cet endroit
            this.piecePresente=null;
            System.out.println("NULLLLLLLLLLLL");
        }
        else{ // Sinon
            if (this.piecePresente==null) { // Si cette version ne possède pas de pièce à cet endroit et que la nouvelle version si
                System.out.println(piece.toString() + "dddddddddddddddddddddddddd");
                instanciationType(piece);
            }
            else { // Sinon
                this.piecePresente.setCoordX(piece.getX());
                this.piecePresente.setCoordY(piece.getY());
                this.piecePresente.setCouleur(piece.getCouleur());
                this.piecePresente.setImage(piece.getImage());
                this.piecePresente.setType(piece.getType());
            }
        }
    }

    public void unsetPiece(){
        this.piecePresente = null;
        occupee = false;
    }

    public Piece getPiece() {
        return piecePresente;
    }

    public void instanciationType(PieceEtat piece){
        switch(piece.getType()){
            case FOU:
                this.piecePresente = new Fou(piece.getX(), piece.getY(), piece.getCouleur(), piece.getType(), piece.getImage());
                break;

            case ROI:
                this.piecePresente = new Roi(piece.getX(), piece.getY(), piece.getCouleur(), piece.getType(), piece.getImage());
                break;

            case PION:
                this.piecePresente = new Pion(piece.getX(), piece.getY(), piece.getCouleur(), piece.getType(), piece.getImage());
                break;

            case TOUR:
                this.piecePresente = new Tour(piece.getX(), piece.getY(), piece.getCouleur(), piece.getType(), piece.getImage());
                break;

            case REINE:
                this.piecePresente = new Reine(piece.getX(), piece.getY(), piece.getCouleur(), piece.getType(), piece.getImage());
                break;

            case CAVALIER:
                this.piecePresente = new Cavalier(piece.getX(), piece.getY(), piece.getCouleur(), piece.getType(), piece.getImage());
                break;
        }
    }

    @Override
    public String toString(){
        return "X: "+getX()+"  Y: "+getY()+"  Piece : "+getPiece()+"\n";
    }
}
