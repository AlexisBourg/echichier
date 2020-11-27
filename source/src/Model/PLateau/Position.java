package Model.PLateau;

import Model.Piece.Piece;

public class Position {
    private int x;
    private int y;
    private boolean Occupé=false;
    private Piece piecePresente;

    public Position(int x,int y, Piece piece){
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

    public void setEstOccupe(boolean estOccupé) {
        this.Occupé = estOccupé;
    }

    public boolean isOccupe() {
        return Occupé;
    }

    public void setPiece(Piece piece) {
        this.piecePresente = piece;
        piece.setCoordX(x);
        piece.setCoordY(y);
    }

    public Piece getPiece() {
        return piecePresente;
    }
}
