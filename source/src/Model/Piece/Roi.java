package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;
//import org.jetbrains.annotations.NotNull;


public class Roi extends Piece {


    //Attribue
    private boolean premierDeplacement=true;

    //Constructeur
    public Roi(int x, int y, Couleur couleur) {
        super(x, y, couleur,Type.ROI);
    }

    //Methode
    public void setPremierDeplacement(){this.premierDeplacement = false;}

    public boolean isPremierDeplacement() {
        return premierDeplacement;
    }


    public void setListeDep( Plateau plateau){
        int[][] dep = {{1, 1}, {-1, 1}, {1, -1}, {-1, -1}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        getListeDep().clear();
        for(int i=0; i<8; i++){
            deplacementPossibleRoi(plateau, dep[i][0], dep[i][1]);
        }
    }

    public void deplacementPossibleRoi(Plateau plateau, int tmpX, int tmpY) {
        Position caseTmp;
        int x = getCoordX();
        int y = getCoordY();

        if(x+tmpX > LIMIT_SUP || x+tmpX < LIMIT_INF || y+tmpY > LIMIT_SUP || y+tmpY < LIMIT_INF)
            return;

        caseTmp = plateau.getCase(x + tmpX, y + tmpY);

        if (plateau.isCaseNull(caseTmp)) {
            if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() == this.getCouleur()) {
                getListeProtecDep().add(caseTmp);
            }
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                getListeDep().add(caseTmp);
            }
        }
    }
}
