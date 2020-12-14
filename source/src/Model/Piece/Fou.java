package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.List;

public class Fou extends Piece {


    //Attribue

    //Constructeur
    public Fou(int x, int y, Couleur couleur) {
        super(x, y,couleur,Type.FOU);
    }


    //Methode

    public void setListeDep(Plateau plateau) {

        listePosDep.clear();

        int tmpX = getCoordX(), tmpY = getCoordY();
        int[][] dep ={{1,1},{-1,1},{1,-1},{-1,-1}};
        for (int i = 0; i < 4; i++) {
            deplacementPossible(plateau, tmpX+dep[i][0], tmpY+dep[i][1]);
        }
    }

    private void deplacementPossible(Plateau plateau, int tmpX, int tmpY) {
        Position caseTmp;
        int x = getCoordX();
        int y = getCoordY();
        caseTmp = plateau.getCasse(x+tmpX, y+tmpY);
        while (!plateau.isCaseNull(caseTmp)) {
            if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() == this.getCouleur()) {
                listeProtecDep.add(caseTmp);
            }
            if (!caseTmp.isOccupe()) { //
                listePosDep.add(caseTmp);
                x+=tmpX;
                y+=tmpY;
                caseTmp = plateau.getCasse(x+tmpX, x+tmpY);
            }
            else if(caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()){
                listePosDep.add(caseTmp);
                break;
            }
        }
    }
}
