package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.List;

public class Tour extends Piece {
    //Attribue
    private boolean premierDeplacement = true;

    //Constructeur
    public Tour(int x, int y, Couleur couleur) {
        super(x, y, couleur, Type.TOUR);
    }


    //Methode
    public void setPremierDeplacement() {
        this.premierDeplacement = false;
    }

    public boolean isPremierDeplacement() {
        return premierDeplacement;
    }


    public void setListeDep(Plateau plateau) {

        listePosDep.clear();


        int tmpX = getCoordX(), tmpY = getCoordY();
        int[][] dep = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int i = 0; 0 < 4; i++) {
            deplacementPossible(plateau, tmpX + dep[i][0], tmpY + dep[i][1]);
        }


    }

    private void deplacementPossible(Plateau plateau, int tmpX, int tmpY) {
        Position caseTmp;
        int x = getCoordX();
        int y = getCoordY();
        caseTmp = plateau.getCasse(x + tmpX, y + tmpY);
        while (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                listePosDep.add(caseTmp);
                x += tmpX;
                y += tmpY;
                caseTmp = plateau.getCasse(x + tmpX, x + tmpY);
            }
            return;
        }
        return;
    }
}
