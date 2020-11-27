package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class Piece {

    //Atribut
    private int coordX;
    private int coordY;
    private static String image;
    private static Couleur couleur;
    protected List<Position> listePosDep;
    private Type type;

    //Constructeur
    public Piece(int x, int y, Couleur couleur, Type type){
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




    public String getImage() {
        return image;
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

    public abstract void setListeDep( Plateau plateau);

    public List<Position> getListeDep() {
        return listePosDep;
    }



}
