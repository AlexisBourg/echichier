package model.piece;

import model.plateau.Plateau;
import model.plateau.Position;

public class Tour extends Piece {

    //Atribut

    //Constructeur
    public Tour(Couleur couleur) {
        super(couleur, Type.TOUR);
    }

    public Tour(Couleur couleur, Type type, String image){
        super(couleur, type, image);
    }

    //Methode
    public void setListeDep(Plateau plateau, int x, int y) {

        getListeDep().clear();
        getListeProtecDep().clear();

<<<<<<< HEAD
        int[][] dep = {{SUR_PLACE, UNE_CASE_DEVANT}, {SUR_PLACE, UNE_CASE_DERRIRE}, {UNE_CASE_DEVANT, SUR_PLACE}, {UNE_CASE_DERRIRE, SUR_PLACE}};
        for (int i = 0; i < 4; i++) {
=======
        int[][] dep = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int i = 0; i < 4; i++){
>>>>>>> 2946f13a9d740fa9a4f0f4f5e965d439eb623709
            deplacementPossible(plateau, dep[i][0], dep[i][1], x, y);
        }
    }

    @Override
    public void deplacementPossible(Plateau plateau, int tmpX, int tmpY, int x, int y) {
        Position caseTmp;

        if(x+tmpX > LIMIT_SUP || x+tmpX < LIMIT_INF || y+tmpY > LIMIT_SUP || y+tmpY < LIMIT_INF)
            return;

        caseTmp = plateau.getCase(x + tmpX, y + tmpY);
        while (plateau.isCaseNull(caseTmp)) {
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
                caseTmp = plateau.getCase(x + tmpX, y + tmpY);
            }
        }
    }
}
