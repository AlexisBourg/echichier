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

        getListeDep().clear();
        int tmpX = getCoordX(), tmpY = getCoordY();
        int[][] dep = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int i = 0; i < 4; i++) {
            deplacementPossible(plateau, dep[i][0], dep[i][1]);
        }


    }

    @Override
    public void deplacementPossible(Plateau plateau, int tmpX, int tmpY) {
        Position caseTmp;
        int x = getCoordX();
        int y = getCoordY();

        if(x+tmpX > LIMIT_SUP || x+tmpX < LIMIT_INF || y+tmpY > LIMIT_SUP || y+tmpY < LIMIT_INF)
            return;

        caseTmp = plateau.getCasse(x + tmpX, y + tmpY);
        while (!plateau.isCaseNull(caseTmp)) {
            if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() == this.getCouleur()) {
                getListeProtecDep().add(caseTmp);
                break;
            }
            if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                getListeDep().add(caseTmp);
                break;
            }
            if (!caseTmp.isOccupe()) {
                getListeDep().add(caseTmp);
                x += tmpX;
                y += tmpY;
                if(x+tmpX > LIMIT_SUP || x+tmpX < LIMIT_INF || y+tmpY > LIMIT_SUP || y+tmpY < LIMIT_INF)
                    break;
                caseTmp = plateau.getCasse(x + tmpX, y + tmpY);
            }
        }
    }
}
