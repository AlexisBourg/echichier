package Model.Piece;

import Model.PLateau.Plateau;
import Model.PLateau.Position;

public class Cavalier extends Piece {


    //Attribue

    //Constructeur
    public Cavalier(int x, int y, Couleur couleur){
        super(x, y, couleur, Type.CAVALIER);
    }


    //Methode

    @Override
    public void setListeDep(Plateau plateau) {

        Position caseTmp;

        listePosDep.clear();
        //TODO faire un tableau de deplacement
        updateDeplacementValide(plateau, 1,2 );                 //1case à droite, 2casses en haut
        updateDeplacementValide(plateau, 2,1 );                 //1case à droite, 2casses en haut
        updateDeplacementValide(plateau, 2,-1 );                 //1case à droite, 2casses en haut
        updateDeplacementValide(plateau, 1,-2 );                 //1case à droite, 2casses en haut
        updateDeplacementValide(plateau, -1,-2 );                 //1case à droite, 2casses en haut
        updateDeplacementValide(plateau, -2,-1 );                 //1case à droite, 2casses en haut
        updateDeplacementValide(plateau, -2,1 );                 //1case à droite, 2casses en haut
        updateDeplacementValide(plateau, -1,2 );                 //1case à droite, 2casses en haut

    }

    private void updateDeplacementValide(Plateau plateau, int dx, int dy) {
        int tmpX=getCoordX();
        int tmpY=getCoordY();
        Position caseTmp;
        caseTmp=plateau.getCasse(tmpX+dx,tmpY+dy);                        //1case à droite, 2casses en haut
        if (!plateau.isCaseNull(caseTmp)) {
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                listePosDep.add(caseTmp);
            }
        }
    }

}
