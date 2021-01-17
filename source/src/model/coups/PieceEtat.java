package model.coups;

import model.piece.Couleur;
import model.piece.Type;
import model.plateau.Position;

public class PieceEtat {

    //Atribut
    private int x;
    private int y;
    private String image;
    private Couleur couleur;
    private Type type;

    //Constructeur
    public PieceEtat(Position position){
        this.setX(position.getX());
        this.setY(position.getY());
        this.setCouleur(position.getPiece().getCouleur());
        this.setImage(position.getPiece().getImage());
        this.setType(position.getPiece().getType());
    }

    //Methode
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

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return this.image;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public Couleur getCouleur(){
        return this.couleur;
    }

    public void setType(Type type){
        this.type = type;
    }

    public Type getType(){
        return this.type;
    }

    @Override
    public String toString(){
        return "Type: "+getType().name()+"  Couleur: "+getCouleur() + "  x : " + getX() + " y: "+getY();
    }
}
