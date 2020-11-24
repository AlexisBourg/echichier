package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.LinkedList;
import java.util.List;

public abstract class Pièce{

    //Atribut
    private int coordX;
    private int coordY;
    private String image;
    private Couleur couleur;
    private List<Position> listePosDep;
    private Type type;

    //Constructeur
    public Pièce(int x,int y, Couleur couleur, Type type){
        coordX=x;
        coordY=y;
        this.couleur=couleur;
        this.type=type;
        switch (type){
            case FOU :
                if (couleur.equals(Couleur.BLANC)){
                    image="/source/src/res/fouB.png";
                }
                else{
                    image="/source/src/res/fouN.png";
                }
                break;
            case ROI:
                if (couleur.equals(Couleur.BLANC)){
                    image="/source/src/res/roiB.png";
                }
                else{
                    image="/source/src/res/roiN.png";
                }
                break;
            case PION:
                if (couleur.equals(Couleur.BLANC)){
                    image="/source/src/res/pionB.png";
                }
                else{
                    image="/source/src/res/pionN.png";
                }
                break;
            case TOUR:
                if (couleur.equals(Couleur.BLANC)){
                    image="/source/src/res/tourB.png";
                }
                else{
                    image="/source/src/res/tourN.png";
                }
                break;
            case REINE:
                if (couleur.equals(Couleur.BLANC)){
                    image="/source/src/res/reineB.png";
                }
                else{
                    image="/source/src/res/reineN.png";
                }
                break;
            case CAVALIER:
                if (couleur.equals(Couleur.BLANC)){
                    image="/source/src/res/cavalierB.png";
                }
                else{
                    image="/source/src/res/cavalierN.png";
                }
                break;
        }
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


    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public abstract void setListeDep(List<Position> listePosDep, Plateau plateau);

    public List<Position> getListeDep() {
        return listePosDep;
    }

}
