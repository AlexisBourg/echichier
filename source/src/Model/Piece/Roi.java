package Model.Piece;

import Model.PLateau.Position;

import java.util.List;

public class Roi extends Pièce {


    //Attribue
    private boolean premierDeplacement=true;

    //Constructeur
    public Roi(int x, int y, Couleur couleur) {
        super(x, y, Image.ROI, couleur,"Roi");
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
