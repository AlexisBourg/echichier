package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;
//import org.jetbrains.annotations.NotNull;

import java.util.List;

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

        listePosDep.clear();

        int tmpX=getCoordX();
        int tmpY=getCoordY();
        Position caseTmp;

        caseTmp=plateau.getCasse(tmpX--,tmpY--);
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX--,tmpY);
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX--,tmpY++);
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX,tmpY--);
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX,tmpY++);
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX++,tmpY--);
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX++,tmpY);
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX++,tmpY++);
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

    }
}
