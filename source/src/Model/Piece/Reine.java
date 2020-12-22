package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

public class Reine extends Piece {


    //Attribue

    //Constructeur
    public Reine(int x, int y, Couleur couleur) {
        super(x, y, couleur, Type.REINE);
    }

    //Methode

    public void setListeDep(Plateau plateau) {
        int[][] dep = {{1, 1}, {-1, 1}, {1, -1}, {-1, -1}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        getListeDep().clear();

        for (int i = 0; i< 8; i++) {
            deplacementPossible(plateau, dep[i][0], dep[i][1]);
        }
    }

    @Override
    public void deplacementPossible(Plateau plateau, int tmpX, int tmpY){
        Position caseTmp;
        int x = getCoordX();
        int y = getCoordY();
        System.out.println("x ::  "+x+"   y ::  "+y);
        System.out.println("tmpX ::  "+tmpX+"   tmpY ::   "+tmpY);
        if(x+tmpX > LIMIT_SUP || x+tmpX < LIMIT_INF || y+tmpY > LIMIT_SUP || y+tmpY < LIMIT_INF)
            return;

        caseTmp = plateau.getCasse(x + tmpX, y + tmpY);
        while (plateau.isCaseNull(caseTmp)) {
            if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() == this.getCouleur()) {
                getListeProtecDep().add(caseTmp);
                break;
            }
            if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                getListeDep().add(caseTmp);
                break;
            }
            if(!caseTmp.isOccupe()) {
                getListeDep().add(caseTmp);
                x += tmpX;
                y += tmpY;
                if(x+tmpX > LIMIT_SUP || x+tmpX < LIMIT_INF || y+tmpY > LIMIT_SUP || y+tmpY < LIMIT_INF)
                    return;
                caseTmp = plateau.getCasse(x + tmpX, y + tmpY);
            }
        }
    }
}
