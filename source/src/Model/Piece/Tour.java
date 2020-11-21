package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.List;

public class Tour extends Pièce{
    //Attribue
    private boolean premierDeplacement=true;
    //Constructeur
    public Tour(int x, int y, Couleur couleur){
        super(x,y,Image.TOUR,couleur, "Tour");
    }


    //Methode
    public void setPremierDeplacement(){this.premierDeplacement = false;}

    public boolean isPremierDeplacement() {
        return premierDeplacement;
    }


    public void setListeDep(List<Position> listePosDep, Plateau plateau) {
        Position caseTmp;
        int tmpX, tmpY;
        for (int i = 0; 0 < 4; i++) {
            tmpX = getCoordX();
            tmpY = getCoordY();
            switch (i) {
                case 0:                                             //Position possible à droite
                    caseTmp = plateau.getCasse(tmpX, tmpY + 1);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupé() || caseTmp.isOccupé() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpY++;
                            caseTmp = plateau.getCasse(tmpX, tmpY + 1);
                        }
                        break;
                    }
                case 1:                                               //Position possible à gauche
                    caseTmp = plateau.getCasse(tmpX, tmpY - 1);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupé() || caseTmp.isOccupé() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpY--;
                            caseTmp = plateau.getCasse(tmpX, tmpY - 1);
                        }
                        break;
                    }
                    break;
                case 2:                                             //Position possible à droite
                    caseTmp = plateau.getCasse(tmpX+1, tmpY);
                    while (!plateau.isCaseNull(caseTmp)){
                        if (!caseTmp.isOccupé() || caseTmp.isOccupé() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpX++;
                            caseTmp = plateau.getCasse(tmpX, tmpX + 1);
                        }
                        break;
                    }
                    break;
                case 3:                                             //Position possible à gauche
                    caseTmp = plateau.getCasse(tmpX-1, tmpY);
                    while (!plateau.isCaseNull(caseTmp)) {
                        if (!caseTmp.isOccupé() || caseTmp.isOccupé() && caseTmp.getPiece().getCouleur() != this.getCouleur()) { //
                            listePosDep.add(caseTmp);
                            tmpX--;
                            caseTmp = plateau.getCasse(tmpX-1, tmpY);
                        }
                        break;
                    }
                    break;
                }
            }
        }
    }


