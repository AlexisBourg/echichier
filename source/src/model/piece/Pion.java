package model.piece;

import model.plateau.Plateau;
import model.plateau.Position;

public class Pion extends Piece {

    //Atttribue
    private boolean premierDeplacement=true;

    //Constructeur
    public Pion(Couleur couleur){
        super(couleur, Type.PION);
    }

    public Pion(Couleur couleur, Type type, String image){
        super(couleur, type, image);
    }

    //Methode
    public boolean getPremierDeplacement(){
        return this.premierDeplacement;
    }

    public void setPremierDeplacement(){this.premierDeplacement = false;}

    public boolean isPremierDeplacement() {
        return premierDeplacement;
    }


    public void setListeDep( Plateau plateau, int x, int y) {
        getListeDep().clear();
        getListeProtecDep().clear();

        y = this.getCouleur() == Couleur.BLANC ? y+UNE_CASE_DERRIRE : y+UNE_CASE_DEVANT;
        deplacementPossible(plateau, x, y);
    }

    public void deplacementPossible(Plateau plateau, int tmpX, int tmpY) {
        //déplacement basique
        Position caseTmp;

        if(tmpX > LIMIT_SUP || tmpX < LIMIT_INF || tmpY > LIMIT_SUP || tmpY < LIMIT_INF)
            return;

        caseTmp = plateau.getCase(tmpX, tmpY);

        if (plateau.isCaseNull(caseTmp) && !caseTmp.isOccupe()) {
            getListeDep().add(plateau.getCase(tmpX, tmpY));
            if (isPremierDeplacement() && !plateau.getCase(tmpX, tmpY).isOccupe()) {
                if (this.getCouleur()==Couleur.BLANC && !plateau.getCase(tmpX, tmpY-1).isOccupe())
                    getListeDep().add(plateau.getCase(tmpX, tmpY+UNE_CASE_DERRIRE));
                else {
                    if (!plateau.getCase(tmpX, tmpY+UNE_CASE_DEVANT).isOccupe())
                        getListeDep().add(plateau.getCase(tmpX, tmpY+UNE_CASE_DEVANT));
                }
            }
        }
        attaque(plateau, tmpX+UNE_CASE_DERRIRE, tmpY);
        attaque(plateau, tmpX+UNE_CASE_DEVANT, tmpY);
    }

    private void attaque(Plateau plateau, int tmpX, int tmpY) {
        Position caseTmp;

        if(tmpX<LIMIT_INF || tmpX>LIMIT_SUP)
            return;

        caseTmp= plateau.getCase(tmpX, tmpY);
        if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur()!=this.getCouleur()){
            getListeDep().add(caseTmp);
        }
        if (!caseTmp.isOccupe() || caseTmp.getPiece().getCouleur() == this.getCouleur()){
            getListeProtecDep().add(caseTmp);
        }
    }

}
