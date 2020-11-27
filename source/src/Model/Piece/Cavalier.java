package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.List;

public class Cavalier extends Piece {


    //Attribue

    //Constructeur
    public Cavalier(int x, int y, Couleur couleur){
        super(x, y, couleur, Type.CAVALIER);
    }


    //Methode

    @Override
    public void setListeDep(Plateau plateau) {
        int tmpX=getCoordX();
        int tmpY=getCoordY();
        Position caseTmp;

        listePosDep.clear();

        caseTmp=plateau.getCasse(tmpX++,tmpY+2);                        //1case à droite, 2casses en haut
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX+2,tmpY++);                        //2cases à droite, 1case en haut
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX+2,tmpY--);                        //2cases à droite, 1 case en bas
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX++,tmpY-2);                        //1case à droite, 2cases en bas
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX--,tmpY+2);                        //1case à gauche, 2cases en bas
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX-2,tmpY--);                        //2cases à gauche, 1case en bas
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX-2,tmpY++);                        //2cases à gauche, 1 case en haut
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX-2,tmpY++);                        //2cases à gauche, 1 case en haut
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }

        caseTmp=plateau.getCasse(tmpX--,tmpY+2);                        //1case à gauche, 2 cases en haut
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }
    }

}
