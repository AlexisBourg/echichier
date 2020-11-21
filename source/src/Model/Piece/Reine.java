package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.List;

public class Reine extends Pièce{


    //Attribue

    //Constructeur
    public Reine(int x, int y, Couleur couleur) {
        super(x, y, Image.REINE, couleur,"Reine");
    }

    //Methode

    public void setListeDep(List<Position> listePosDep, Plateau plateau){

    }
}
