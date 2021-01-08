package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

public class Pion extends Piece {
    //Atttribue
    private boolean premierDeplacement=true;

    //Constructeur

    public Pion(int x, int y, Couleur couleur){
        super(x, y, couleur, Type.PION);
    }

    //Methode

    public boolean getPremierDeplacement(){
        return this.premierDeplacement;
    }

    public void setPremierDeplacement(){this.premierDeplacement = false;}

    public boolean isPremierDeplacement() {
        return premierDeplacement;
    }


    public void setListeDep( Plateau plateau) {
        getListeDep().clear();
        int tmpX = getCoordX();
        int tmpY = getCoordY();

        if (getCouleur().equals(Couleur.BLANC)) {
            tmpY--;
        } else {
            tmpY++;
        }
        deplacementPossible(plateau, tmpX, tmpY);
    }

    @Override
    public void deplacementPossible(Plateau plateau, int tmpX, int tmpY) {
        //déplacement basique
        Position caseTmp;
        int tmpY2;

        if(tmpX > LIMIT_SUP || tmpX < LIMIT_INF || tmpY > LIMIT_SUP || tmpY < LIMIT_INF)
            return;

        caseTmp = plateau.getCase(tmpX, tmpY);

        if (plateau.isCaseNull(caseTmp) && !caseTmp.isOccupe()) {
            getListeDep().add(plateau.getCase(tmpX, tmpY));
            if (isPremierDeplacement() && !plateau.getCase(tmpX, tmpY).isOccupe()) {
                tmpY2 = this.getCouleur() == Couleur.BLANC ? tmpY-1 : tmpY+1;
                if(!plateau.getCase(tmpX, tmpY2).isOccupe())
                    getListeDep().add(plateau.getCase(tmpX, tmpY2));
            }
        }
        //attaque
        /*if(this.getCouleur()==Couleur.NOIR){
            attaque(plateau, tmpX-1, tmpY);
            attaque(plateau, tmpX+1, tmpY);
        }else{*/
            attaque(plateau, tmpX-1, tmpY);
            attaque(plateau, tmpX+1, tmpY);
        //}
    }

    private void attaque(Plateau plateau, int tmpX, int tmpY) {
        Position caseTmp;

        if(tmpX==-1 || tmpX==8)
            return;

        caseTmp= plateau.getCase(tmpX, tmpY);
        if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur()!=this.getCouleur()){
            getListeDep().add(caseTmp);
        }
        if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur()==this.getCouleur()){
            getListeProtecDep().add(caseTmp);
        }
    }

}
