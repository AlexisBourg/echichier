package Model.Piece;

import Model.PLateau.Position;

import java.util.List;

public class Reine extends Pièce{


    //Attribue

    //Constructeur
    public Reine(int x, int y, Couleur couleur) {
        super(x, y, Image.REINE, couleur,"Reine");
    }

    //Methode
    @Override
    public void setListePosDep(List<Position> listePosDep) {

    }
}
