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
        listePosDep.clear();
        int[][] dep ={{1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2}};
        for (int i=0; i<8; i++) {
            updateDeplacementValide(plateau, dep[i][0], dep[i][1]);
        }
    }

    private void updateDeplacementValide(Plateau plateau, int dx, int dy) {
        int tmpX=getCoordX();
        int tmpY=getCoordY();
        Position caseTmp;
        caseTmp=plateau.getCasse(tmpX+dx,tmpY+dy);
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
            if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() == this.getCouleur()) {
                listeProtecDep.add(caseTmp);
            }
        }
    }

}
