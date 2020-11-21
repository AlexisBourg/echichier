package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.List;

public class Fou extends Pièce{


    //Attribue

    //Constructeur
    public Fou(int x, int y, Couleur couleur) {
        super(x, y, Image.FOU,couleur,"Fou");
    }


    //Methode

    public void setListeDep(List<Position> listePosDep, Plateau plateau) {
        Position caseTmp;
        int tmpX, tmpY;
        for (int i = 0; 0 < 4; i++) {
            tmpX = getCoordX();
            tmpY = getCoordY();
            switch (i) {
                case 0:                                             //Position possible diagonale haut/droit
                    caseTmp = plateau.getCasse(tmpX++, tmpY++);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupé() || caseTmp.isOccupé() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpX++;
                            tmpY++;
                            caseTmp = plateau.getCasse(tmpX++, tmpY++);
                        }
                        break;
                    }
                    break;
                case 1:                                             //Position possible diagonale haut/gauche
                    caseTmp = plateau.getCasse(tmpX--, tmpY++);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupé() || caseTmp.isOccupé() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpX--;
                            tmpY++;
                            caseTmp = plateau.getCasse(tmpX--, tmpY++);
                        }
                        break;
                    }
                    break;
                case 2:                                             //Position possible diagonale bas/droit
                    caseTmp = plateau.getCasse(tmpX++, tmpY--);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupé() || caseTmp.isOccupé() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpX++;
                            tmpY--;
                            caseTmp = plateau.getCasse(tmpX++, tmpX++);
                        }
                        break;
                    }
                    break;
                case 3:                                             //Position possible bas/gauche
                    caseTmp = plateau.getCasse(tmpX--, tmpY--);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupé() || caseTmp.isOccupé() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpX--;
                            tmpY--;
                            caseTmp = plateau.getCasse(tmpX--, tmpY--);
                        }
                        break;
                    }
                    break;

            }
        }
    }


}
