package Model.Piece;

import Model.PLateau.Position;

import java.util.List;

public class Fou extends Pièce{


    //Attribue

    //Constructeur
    public Fou(int x, int y, Couleur couleur) {
        super(x, y, Image.FOU,couleur,"Fou");
    }


    //Methode
    @Override
    public void setListePosDep(List<Position> listePosDep) {

    }


}
