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
        this.setCouleur(position.getPiece().getCouleur());
        this.setImage(position.getPiece().getImage());
        this.setType(position.getPiece().getType());
    }

    //Methode
    /**
     * permet de setter une image
     * @param image : est l'url le l'image
     */
    public void setImage(String image){
        this.image = image;
    }

    /**
     *  permet de renvoyer l'url de l'image
     *  @return l'url de l'image
     */
    public String getImage(){
        return this.image;
    }

    /**
     * permet de setter une couleur
     * @param couleur : est la couleur de l'image
     */
    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * permet de renvoyer une couleur
     * @return : renvoi la couleur
     */
    public Couleur getCouleur(){
        return this.couleur;
    }

    /**
     * permet de setter un type
     * @param type : est le type de la pièce
     */
    public void setType(Type type){
        this.type = type;
    }

    /**
     * permet de renvoyer le type de la pièce
     * @return  le type de la pièce
     */
    public Type getType(){
        return this.type;
    }

    /**
     * permet de renvoyer une description de la piece
     * @return le nom et la couleur de la piece
     */
    @Override
    public String toString(){
        return "Type: "+getType().name()+"  Couleur: "+getCouleur();
    }
}
