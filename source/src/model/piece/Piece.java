package model.piece;

import model.plateau.Plateau;
import model.plateau.Position;


import java.util.LinkedList;
import java.util.List;

public abstract class Piece {

    //Atribut
    public static final int LIMIT_SUP = 7;
    public static final int LIMIT_INF = 0;
    public static final int SUR_PLACE = 0;
    public static final int UNE_CASE_DEVANT = 1;
    public static final int UNE_CASE_DERRIRE = -1;
    public static final int DEUX_CASES_DEVANT = 2;
    public static final int DEUX_CASES_DERRIRE = -2;
    private String image;
    private Couleur couleur;
    private List<Position> listePosDep;
    private List<Position> listeProtecDep;
    private Type type;

    //Constructeur
    public Piece(Couleur couleur, Type type){
        listePosDep = new LinkedList<>();
        listeProtecDep = new LinkedList<>();
        this.couleur=couleur;
        this.type=type;
        switch (type){
            case FOU :
                if (couleur.equals(Couleur.BLANC)){
                    image= "res/imagesPieces/fouB.png";
                }
                else{
                    image= "res/imagesPieces/fouN.png";
                }
                break;
            case ROI:
                if (couleur.equals(Couleur.BLANC)){
                    image= "res/imagesPieces/roiB.png";
                }
                else{
                    image= "res/imagesPieces/roiN.png";
                }
                break;
            case PION:
                if (couleur.equals(Couleur.BLANC)){
                    image= "res/imagesPieces/pionB.png";
                }
                else{
                    image= "res/imagesPieces/pionN.png";
                }
                break;
            case TOUR:
                if (couleur.equals(Couleur.BLANC)){
                    image= "res/imagesPieces/tourB.png";
                }
                else{
                    image= "res/imagesPieces/tourN.png";
                }
                break;
            case REINE:
                if (couleur.equals(Couleur.BLANC)){
                    image= "res/imagesPieces/reineB.png";
                }
                else{
                    image= "res/imagesPieces/reineN.png";
                }
                break;
            case CAVALIER:
                if (couleur.equals(Couleur.BLANC)){
                    image= "res/imagesPieces/cavalierB.png";
                }
                else{
                    image= "res/imagesPieces/cavalierN.png";
                }
                break;
        }
    }

    public Piece(Couleur couleur, Type type, String image){
        this.couleur = couleur;
        this.type = type;
        this.image = image;
        this.listePosDep = new LinkedList<>();
        this.listeProtecDep = new LinkedList<>();
    }

    //Methode

        //Getter et Setter
    public void setImage(String image){
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public  void setCouleur(Couleur couleur){
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

    /**
     *
     * @param plateau : plateau du jeu
     * @param x : ligne de la pièce dans le plateau
     * @param y : colonne de la pièce dans le plateau
     */
    public void setListeDep(Plateau plateau, int x, int y) {
    }

    public void actualiserListeDep(List<Position> liste){
        this.listePosDep = liste;
    }

    public void deplacementPossible(Plateau plateau, int depX, int depY, int x, int y){}

    public List<Position> getListeDep() {
        return listePosDep;
    }

    public void setListeProtecDep(Plateau plateau) {
    }

    public List<Position> getListeProtecDep() {
        return listeProtecDep;
    }

    @Override
    public String toString(){
        return "Type: "+getType().name()+"  Couleur: "+getCouleur();
    }
}
