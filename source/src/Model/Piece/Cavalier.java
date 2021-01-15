package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

public class Cavalier extends Piece {


    //Attribue

    //Constructeur
    public Cavalier(int x, int y, Couleur couleur){
        super(x, y, couleur, Type.CAVALIER);
    }


    //Methode

    @Override
    public void setListeDep(Plateau plateau) {
        int[][] dep ={{1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2}};

        getListeDep().clear();
        getListeProtecDep().clear();
        for (int i=0; i<8; i++) {
            deplacementPossible(plateau, dep[i][0], dep[i][1]);
        }
    }

    @Override
    public void deplacementPossible(Plateau plateau, int x, int y) {
        int tmpX=getCoordX();
        int tmpY=getCoordY();
        Position caseTmp;

        if(x+tmpX > LIMIT_SUP || x+tmpX < LIMIT_INF || y+tmpY > LIMIT_SUP || y+tmpY < LIMIT_INF)
            return;

        caseTmp=plateau.getCase(tmpX+x,tmpY+y);

        if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
            getListeDep().add(caseTmp);
        }
        if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() == this.getCouleur()) {
            getListeProtecDep().add(caseTmp);
        }

    }

}
