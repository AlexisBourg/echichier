package Model.Piece;

import Model.PLateau.Position;

import java.util.List;

public class Tour extends Pièce{
    //Attribue
    private boolean premierDeplacement=true;
    //Constructeur
    public Tour(int x, int y, Couleur couleur){
        super(x,y,Image.TOUR,couleur, "Tour");
    }


    //Methode
    public void setPremierDeplacement(){this.premierDeplacement = false;}

    public boolean getPremierDeplacement() {
        return premierDeplacement;
    }

    @Override
    public void setListePosDep(List<Position> listePosDep) {

    }





}
