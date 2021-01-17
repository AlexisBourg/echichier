package model.coups;

import model.plateau.Position;

public class PositionEtat {
    private int x;
    private int y;
    private boolean occupe;
    private PieceEtat piece;

    public PositionEtat(Position position){
        this.x = position.getX();
        this.y = position.getY();
        this.occupe = position.isOccupe();
        this.piece = position.isOccupe() ? new PieceEtat(position) : null;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getX(){
        return this.x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getY(){
        return this.y;
    }

    public boolean getOccupe(){
        return this.occupe;
    }

    public PieceEtat getPiece(){
        return this.piece;
    }

    public String toString(){
        return "X: "+getX()+"  Y: "+getY()+"  Piece : "+getPiece()+"\n";
    }

}
