package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.LinkedList;
import java.util.List;

public abstract class Pièce{

    //Atribut
    private int coordX;
    private int coordY;
    private Image image;
    private Couleur couleur;
    private List<Position> listePosDep;
    private String type;

    //Constructeur
    public Pièce(int x,int y,Image img, Couleur couleur, String type){
        coordX=x;
        coordY=y;
        image=img;
        this.couleur = couleur;
        this.type=type;
    }

    //Methode



        //Getter et Setter
    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getCoordY() {
        return coordY;
    }


    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public abstract void setListeDep(List<Position> listePosDep, Plateau plateau);

    public List<Position> getListeDep() {
        return listePosDep;
    }

}
