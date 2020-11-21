package Model.PLateau;

import Model.Piece.Pièce;

public class Position {
    private int x;
    private int y;
    private boolean estOccupé=false;

    private Pièce piecePresente;

    public Position(int x,int y, Pièce piece){
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

    public void setEstOccupé(boolean estOccupé) {
        this.estOccupé = estOccupé;
    }

    public boolean isEstOccupé() {
        return estOccupé;
    }

    public void setPiece(Pièce piece) {
        this.piecePresente = piece;
    }

    public Pièce getPiece() {
        return piecePresente;
    }
}
