package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.List;

public class Pion extends Pièce {
    //Atttribue
    private boolean premierDeplacement=true;

    //Construccteur
    public Pion(int x, int y, Couleur couleur){
        super(x,y,Image.PION, couleur, "Pion");
    }

    //Methode
    public void setPremierDeplacement(){this.premierDeplacement = false;}

    public boolean isPremierDeplacement() {
        return premierDeplacement;
    }


    public void setListeDep(List<Position> listePosDep, Plateau plateau){
        int tmpX=getCoordX();
        int tmpY=getCoordY();
        Position caseTmp;
        if (getCouleur().equals(Couleur.BLANC)){
            //déplacement basique
            caseTmp=plateau.getCasse(tmpX,tmpY+1);
            if (plateau.isCaseNull(caseTmp)  && !caseTmp.isOccupé()){
                listePosDep.add(plateau.getCasse(tmpX,tmpY+1));
                if (isPremierDeplacement() && !plateau.getCasse(tmpX,tmpY+2).isOccupé()) {
                    listePosDep.add(plateau.getCasse(tmpX,tmpY+2));
                }
            }
            //attaque
            caseTmp=plateau.getCasse(tmpX+1,tmpY+1);
            if (caseTmp.isOccupé() && caseTmp.getPiece().getCouleur()!=this.getCouleur()){
                listePosDep.add(caseTmp);
            }
            caseTmp=plateau.getCasse(tmpX+1,tmpY-1);
            if (caseTmp.isOccupé() && caseTmp.getPiece().getCouleur()!=this.getCouleur()){
                listePosDep.add(caseTmp);
            }
        }
        else{
            //déplacement basique
            caseTmp=plateau.getCasse(tmpX,tmpY-1);
            if (plateau.isCaseNull(caseTmp)  && !caseTmp.isOccupé()){
                listePosDep.add(plateau.getCasse(tmpX,tmpY-1));
                if (isPremierDeplacement() && !plateau.getCasse(tmpX,tmpY-2).isOccupé()) {
                    listePosDep.add(plateau.getCasse(tmpX,tmpY+2));
                }
            }
            //attaque
            caseTmp=plateau.getCasse(tmpX-1,tmpY-1);
            if (plateau.isCaseNull(caseTmp)  && caseTmp.isOccupé() && caseTmp.getPiece().getCouleur()!=this.getCouleur()){
                listePosDep.add(caseTmp);
            }
            caseTmp=plateau.getCasse(tmpX-1,tmpY+1);
            if (plateau.isCaseNull(caseTmp)  && caseTmp.isOccupé() && caseTmp.getPiece().getCouleur()!=this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

    }

}
