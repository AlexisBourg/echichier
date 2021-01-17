package model.coups;

import model.piece.Couleur;
import model.piece.Type;
import model.plateau.Position;

public class PieceEtat {
    private String image;
    private Couleur couleur;
    private Type type;

    public PieceEtat(Position position){
        this.setCouleur(position.getPiece().getCouleur());
        this.setImage(position.getPiece().getImage());
        this.setType(position.getPiece().getType());
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
        return "Type: "+getType().name()+"  Couleur: "+getCouleur();
    }
}
