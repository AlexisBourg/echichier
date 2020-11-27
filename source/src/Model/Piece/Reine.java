package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.List;

public class Reine extends Piece {


    //Attribue

    //Constructeur
    public Reine(int x, int y, Couleur couleur) {
        super(x, y, couleur,Type.REINE);
    }

    //Methode

    public void setListeDep( Plateau plateau){

        listePosDep.clear();

        Position caseTmp;
        int tmpX, tmpY;
        for (int i = 0; 0 < 7; i++) {
            tmpX = getCoordX();
            tmpY = getCoordY();
            switch (i){
                case 0:                                             //Position possible horizontale droite
                    caseTmp = plateau.getCasse(tmpX, tmpY + 1);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpY++;
                            caseTmp = plateau.getCasse(tmpX, tmpY + 1);
                        }
                        break;
                    }
                case 1:                                             //Position possible diagonale bas/droit
                    caseTmp = plateau.getCasse(tmpX++, tmpY--);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpX++;
                            tmpY--;
                            caseTmp = plateau.getCasse(tmpX++, tmpX++);
                        }
                        break;
                    }
                    break;
                case 2:                                             //Position possible verticale bas
                    caseTmp = plateau.getCasse(tmpX-1, tmpY);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpX--;
                            caseTmp = plateau.getCasse(tmpX-1, tmpY);
                        }
                        break;
                    }
                    break;
                case 3:                                             //Position possible bas/gauche
                    caseTmp = plateau.getCasse(tmpX--, tmpY--);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpX--;
                            tmpY--;
                            caseTmp = plateau.getCasse(tmpX--, tmpY--);
                        }
                        break;
                    }
                    break;
                case 4:                                             //Position possible horizontale gauche
                    caseTmp = plateau.getCasse(tmpX, tmpY - 1);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpY--;
                            caseTmp = plateau.getCasse(tmpX, tmpY - 1);
                        }
                        break;
                    }
                    break;
                case 5:                                             //Position possible diagonale haut/gauche
                    caseTmp = plateau.getCasse(tmpX--, tmpY++);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpX--;
                            tmpY++;
                            caseTmp = plateau.getCasse(tmpX--, tmpY++);
                        }
                        break;
                    }
                    break;
                case 6:                                             //Position possible verticale haut
                    caseTmp = plateau.getCasse(tmpX+1, tmpY);
                    while (!plateau.isCaseNull(caseTmp)){
                        if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpX++;
                            caseTmp = plateau.getCasse(tmpX, tmpX + 1);
                        }
                        break;
                    }
                    break;

                case 7:                                             //Position possible diagonale haut/droit
                    caseTmp = plateau.getCasse(tmpX++, tmpY++);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpX++;
                            tmpY++;
                            caseTmp = plateau.getCasse(tmpX++, tmpY++);
                        }
                        break;
                    }
                    break;
            }
        }
    }
}
