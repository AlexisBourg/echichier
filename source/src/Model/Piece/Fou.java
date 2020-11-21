package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.List;

public class Fou extends Pièce{


    //Attribue

    //Constructeur
    public Fou(int x, int y, Couleur couleur) {
        super(x, y, Image.FOU,couleur,"Fou");
    }


    //Methode

    public void setListeDep(List<Position> listePosDep, Plateau plateau) {

    }


}
