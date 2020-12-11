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


    public void setListeDep( Plateau plateau){

        listePosDep.clear();

        int tmpX=getCoordX();
        int tmpY=getCoordY();
        Position caseTmp;
        if (getCouleur().equals(Couleur.BLANC)){
            //déplacement basique
            caseTmp=plateau.getCasse(tmpX,tmpY-1);
            if (plateau.isCaseNull(caseTmp)  && !caseTmp.isOccupe()){
                listePosDep.add(plateau.getCasse(tmpX,tmpY-1));
                if (isPremierDeplacement() && !plateau.getCasse(tmpX,tmpY-2).isOccupe()) {
                    listePosDep.add(plateau.getCasse(tmpX,tmpY-2));
                }
            }
            //attaque
            caseTmp=plateau.getCasse(tmpX+1,tmpY-1);
            if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur()!=this.getCouleur()){
                listePosDep.add(caseTmp);
            }
            caseTmp=plateau.getCasse(tmpX-1,tmpY-1);
            if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur()!=this.getCouleur()){
                listePosDep.add(caseTmp);
            }
        }
        else{
            //déplacement basique
            caseTmp=plateau.getCasse(tmpX,tmpY+1);
            if (plateau.isCaseNull(caseTmp)  && !caseTmp.isOccupe()){
                listePosDep.add(plateau.getCasse(tmpX,tmpY+1));
                if (isPremierDeplacement() && !plateau.getCasse(tmpX,tmpY+2).isOccupe()) {
                    listePosDep.add(plateau.getCasse(tmpX,tmpY+2));
                }
            }
            //attaque
            caseTmp=plateau.getCasse(tmpX-1,tmpY+1);
            if (plateau.isCaseNull(caseTmp)  && caseTmp.isOccupe() && caseTmp.getPiece().getCouleur()!=this.getCouleur()){
                listePosDep.add(caseTmp);
            }
            caseTmp=plateau.getCasse(tmpX+1,tmpY+1);
            if (plateau.isCaseNull(caseTmp)  && caseTmp.isOccupe() && caseTmp.getPiece().getCouleur()!=this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

    }

}
