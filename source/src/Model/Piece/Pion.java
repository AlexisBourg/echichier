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
        Position casseTmp;
        if (getCouleur().equals(Couleur.BLANC)){
            //déplacement basique
            if (!plateau.getCasse(tmpX,tmpY+1).isOccupé()){
                listePosDep.add(plateau.getCasse(tmpX,tmpY+1));
                if (isPremierDeplacement() && !plateau.getCasse(tmpX,tmpY+2).isOccupé()) {
                    listePosDep.add(plateau.getCasse(tmpX,tmpY+2));
                }
            }
            //attaque
            casseTmp=plateau.getCasse(tmpX+1,tmpY+1);
            if (casseTmp.isOccupé() && casseTmp.getPiece().getCouleur()!=this.getCouleur()){
                listePosDep.add(casseTmp);
            }
        }
        else{
            //déplacement basique
            if (!plateau.getCasse(tmpX,tmpY-1).isOccupé()){
                listePosDep.add(plateau.getCasse(tmpX,tmpY-1));
                if (isPremierDeplacement() && !plateau.getCasse(tmpX,tmpY-2).isOccupé()) {
                    listePosDep.add(plateau.getCasse(tmpX,tmpY+2));
                }
            }
            //attaque
            casseTmp=plateau.getCasse(tmpX-1,tmpY-1);
            if (casseTmp.isOccupé() && casseTmp.getPiece().getCouleur()!=this.getCouleur()){
                listePosDep.add(casseTmp);
            }
        }

    }


/*    @Override
    public int[][] deplacementsPoss(int x, int y){
        int i=0, j=0, k=1;                              // j représente la case du tab contenant le x de destination, k représente la case du tab contenant le y de destination
        int[][] destinations = new int[2][2];

        if(getCouleur().equals(Couleur.NOIR)){
            if(getPremierDeplacement()){
                destinations[i][j]= x;
                destinations[i][k]= y+2;
                i+=1;
                setPremierDeplacement();
            }
            destinations[i][j]= x;
            destinations[i][k]= y+1;
        }
        else{
            if(getPremierDeplacement()){
                destinations[i][j]= x;
                destinations[i][k]= y-2;
                i+=1;
            }
            destinations[i][j]= x;
            destinations[i][k]= y-1;
        }

        return destinations;
    }

 */
}
