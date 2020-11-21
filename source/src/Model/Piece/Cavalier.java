package Model.Piece;

import Model.PLateau.Position;

import java.util.List;

public class Cavalier extends Pièce{


    //Attribue

    //Constructeur
    public Cavalier(int x, int y, Couleur couleur){
        super(x, y, Image.CAVALIER,couleur,"Cavalier");
    }


    //Methode

    public void setListePosDep(List<Position> listePosDep) {

    }

}
