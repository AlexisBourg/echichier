package model.pLateau;

import model.piece.Piece;

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

    public void setPiece(Piece piece) {
        this.piecePresente = piece;
        occupee = true;
    }

    public void unsetPiece(){
        this.piecePresente = null;
        occupee = false;
    }

    public Piece getPiece() {
        return piecePresente;
    }

    public String toString(){
        return "X: "+getX()+"  Y: "+getY()+"  Piece : "+getPiece()+"\n";
    }
}
