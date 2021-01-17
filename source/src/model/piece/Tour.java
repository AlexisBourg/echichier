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

    /**
     *
     * @param plateau : plateau du jeu
     * @param x : colonne de la pièce dans le plateau
     * @param y : ligne de la pièce dans le plateau
     */
    @Override
    public void setListeDep(Plateau plateau, int x, int y) {

        getListeDep().clear();
        getListeProtecDep().clear();

        int[][] dep = {{SUR_PLACE, UNE_CASE_DEVANT}, {SUR_PLACE, UNE_CASE_DERRIRE}, {UNE_CASE_DEVANT, SUR_PLACE}, {UNE_CASE_DERRIRE, SUR_PLACE}};
        for (int i = 0; i < 4; i++){
            deplacementPossible(plateau, dep[i][0], dep[i][1], x, y);
        }
    }

    /**
     *
     * @param plateau : plateau du jeu
     * @param tmpX : direction que prend la piece
     * @param tmpY : direction que prend la piece
     * @param x : colonne de la pièce dans le plateau
     * @param y : ligne de la pièce dans le plateau
     */
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
