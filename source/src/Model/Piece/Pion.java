package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.List;

public class Pion extends Piece {
    //Atttribue
    private boolean premierDeplacement=true;

    //Constructeur

    public Pion(int x, int y, Couleur couleur){
        super(x, y, couleur, Type.PION);

    }

    //Methode
    public void setPremierDeplacement(){this.premierDeplacement = false;}

    public boolean isPremierDeplacement() {
        return premierDeplacement;
    }


    public void setListeDep( Plateau plateau) {

        listePosDep.clear();
        int tmpX = getCoordX();
        int tmpY = getCoordY();
        Position caseTmp;
        if (getCouleur().equals(Couleur.BLANC)) {
            tmpY++;
        } else {
            tmpY--;
        }
        deplacementDisponible(plateau, tmpX, tmpY);
    }

    /*        //déplacement basique
            caseTmp=plateau.getCasse(tmpX,tmpY-1);
            if (plateau.isCaseNull(caseTmp)  && !caseTmp.isOccupe()){
                listePosDep.add(plateau.getCasse(tmpX,tmpY-1));
                if (isPremierDeplacement() && !plateau.getCasse(tmpX,tmpY-2).isOccupe()) {
                    listePosDep.add(plateau.getCasse(tmpX,tmpY-2));
                }
            }
            //attaque
            caseTmp=plateau.getCasse(tmpX-1,tmpY-1);
            if (plateau.isCaseNull(caseTmp)  && caseTmp.isOccupe() && caseTmp.getPiece().getCouleur()!=this.getCouleur()){
                listePosDep.add(caseTmp);
            }
            caseTmp=plateau.getCasse(tmpX-1,tmpY+1);
            if (plateau.isCaseNull(caseTmp)  && caseTmp.isOccupe() && caseTmp.getPiece().getCouleur()!=this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

    }*/

    private void deplacementDisponible(Plateau plateau, int tmpX, int tmpY) {
        //déplacement basique
        Position caseTmp;
        caseTmp = plateau.getCasse(tmpX, tmpY);
        if (plateau.isCaseNull(caseTmp) && !caseTmp.isOccupe()) {
            listePosDep.add(plateau.getCasse(tmpX, tmpY));
            if (isPremierDeplacement() && !plateau.getCasse(tmpX, tmpY).isOccupe()) {
                int tmpY2 = tmpY;
                if (getCouleur().equals(Couleur.BLANC)) {
                    tmpY2++;
                } else {
                    tmpY2--;
                }
                listePosDep.add(plateau.getCasse(tmpX, tmpY2));
            }
        }
        //attaque
        for (int i = -1; i < 1; i = i + 2){
            attaque(plateau, tmpX, i);
        }
    }

    private void attaque(Plateau plateau, int tmpX, int tmpY) {
        Position caseTmp;
        caseTmp= plateau.getCasse(tmpX, tmpY );
        if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur()!=this.getCouleur()){
            listePosDep.add(caseTmp);
        }
    }

}
