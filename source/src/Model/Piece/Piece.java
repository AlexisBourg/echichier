package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Piece {

    //Atribut
    private int coordX;
    private int coordY;
    private String image;
    private Couleur couleur;
    protected List<Position> listePosDep;
    protected List<Position> listeProtecDep;
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
                    image="res/ImagesPieces/fouB.png";
                }
                else{
                    image="res/ImagesPieces/fouN.png";
                }
                break;
            case ROI:
                if (couleur.equals(Couleur.BLANC)){
                    image="res/ImagesPieces/roiB.png";
                }
                else{
                    image="res/ImagesPieces/roiN.png";
                }
                break;
            case PION:
                if (couleur.equals(Couleur.BLANC)){
                    image="res/ImagesPieces/pionB.png";
                }
                else{
                    image="res/ImagesPieces/pionN.png";
                }
                break;
            case TOUR:
                if (couleur.equals(Couleur.BLANC)){
                    image="res/ImagesPieces/tourB.png";
                }
                else{
                    image="res/ImagesPieces/tourN.png";
                }
                break;
            case REINE:
                if (couleur.equals(Couleur.BLANC)){
                    image="res/ImagesPieces/reineB.png";
                }
                else{
                    image="res/ImagesPieces/reineN.png";
                }
                break;
            case CAVALIER:
                if (couleur.equals(Couleur.BLANC)){
                    image="res/ImagesPieces/cavalierB.png";
                }
                else{
                    image="res/ImagesPieces/cavalierN.png";
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

    public void setListeDep(Plateau plateau) {

    }
    public List<Position> getListeDep() {
        return listePosDep;
    }

    public void setListeProtecDep(Plateau plateau) {

    }
    public List<Position> getListeProtecDep() {
        return listeProtecDep;
    }



}
