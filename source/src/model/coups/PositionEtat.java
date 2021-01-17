package model.coups;

import model.plateau.Position;

public class PositionEtat {

    //Atribut
    private int x;
    private int y;
    private boolean occupe;
    private PieceEtat piece;

    //Constructeur
    public PositionEtat(Position position){
        this.x = position.getX();
        this.y = position.getY();
        this.occupe = position.isOccupe();
        this.piece = position.isOccupe() ? new PieceEtat(position) : null;
    }

    //Methode

    /**
     * permet de de setter l'abscisse de la position
     * @param x : est la coordonnée de l'abscisse
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * permet de recuperer l'abscisse
     * @return
     */
    public int getX(){
        return this.x;
    }

    /**
     * permet de setter l'ordonnée de la position
     * @param y : est l'ordonnée
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * permet de recuperer l'ordonnée
     * @return
     */
    public int getY(){
        return this.y;
    }

    /**
     * retourne vrai si la position est occupé
     * @return
     */
    public boolean getOccupe(){
        return this.occupe;
    }

    /**
     * retourne la piece présente
     * @return
     */
    public PieceEtat getPiece(){
        return this.piece;
    }

    /**
     * retourne l'abscise, l'ordonnée, et la piece presente
     * @return
     */
    public String toString(){
        return "X: "+getX()+"  Y: "+getY()+"  Piece : "+getPiece()+"\n";
    }
}
