package model.piece;

import model.plateau.Plateau;
import model.plateau.Position;

public class Cavalier extends Piece {

    //Atribut

    //Constructeur
    public Cavalier(Couleur couleur){
        super(couleur, Type.CAVALIER);
    }

    public Cavalier(Couleur couleur, Type type, String image){
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
        int[][] dep ={{UNE_CASE_DEVANT,DEUX_CASES_DEVANT},{DEUX_CASES_DEVANT,UNE_CASE_DEVANT},{DEUX_CASES_DEVANT,UNE_CASE_DERRIRE},{UNE_CASE_DEVANT, DEUX_CASES_DERRIRE},{UNE_CASE_DERRIRE, DEUX_CASES_DERRIRE},{DEUX_CASES_DERRIRE, UNE_CASE_DERRIRE},{DEUX_CASES_DERRIRE, UNE_CASE_DEVANT},{UNE_CASE_DERRIRE, DEUX_CASES_DEVANT}};

        getListeDep().clear();
        getListeProtecDep().clear();
        for (int i=0; i<8; i++) {
            deplacementPossible(plateau, dep[i][0], dep[i][1], x, y);
        }
    }

    /**
     *
     * @param plateau : plateau du jeu
     * @param depX : direction que prend la piece
     * @param depY : direction que prend la piece
     * @param x : colonne de la pièce dans le plateau
     * @param y : ligne de la pièce dans le plateau
     */
    @Override
    public void deplacementPossible(Plateau plateau, int depX, int depY, int x, int y) {
        Position caseTmp;

        if(depX+x > LIMIT_SUP || depX+x < LIMIT_INF || depY+y > LIMIT_SUP || depY+y < LIMIT_INF)
            return;

        caseTmp=plateau.getCase(x+depX,y+depY);

        if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
            getListeDep().add(caseTmp);
        }
        if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() == this.getCouleur()) {
            getListeProtecDep().add(caseTmp);
        }

    }

}
