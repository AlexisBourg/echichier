package model.piece;

import model.plateau.Plateau;
import model.plateau.Position;

public class Cavalier extends Piece {


    //Attribue

    //Constructeur
    public Cavalier(Couleur couleur){
        super(couleur, Type.CAVALIER);
    }

    public Cavalier(Couleur couleur, Type type, String image){
        super(couleur, type, image);
    }


    //Methode

    @Override
    public void setListeDep(Plateau plateau, int x, int y) {
        int[][] dep ={{1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2}};

        getListeDep().clear();
        getListeProtecDep().clear();
        for (int i=0; i<8; i++) {
            deplacementPossible(plateau, dep[i][0], dep[i][1], x, y);
        }
    }

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
