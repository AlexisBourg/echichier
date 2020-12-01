package Model.PLateau;

import Model.Piece.*;

public class Plateau {


    private Position plateauDejeu[][];

    /**
     * un plateau est composé de position (casse) et initialisé avec les piece blanche en bas et les noire en haut
     */
    public Plateau(){
        plateauDejeu = new Position[8][8];
        int yb=0, yn=7;

        for (int x=0;x<8;x++) {
            for (int y=0;y<8;y++) {
                plateauDejeu[y][x] = new Position(y,x, null);
            }
        }
        for (int x=0;x<8;x++){
            plateauDejeu[1][x].setPiece(new Pion(x,1, Couleur.BLANC));
            plateauDejeu[1][x].setEstOccupe(true);
            plateauDejeu[6][x].setPiece(new Pion(x,6, Couleur.NOIR));
            plateauDejeu[6][x].setEstOccupe(true);
        }
        for (int x=0;x<8;x++){


            switch (x){
                case 0:
                case 7:
                        plateauDejeu[yn][x].setPiece(new Tour(x,yn,Couleur.NOIR));
                        plateauDejeu[yn][x].setEstOccupe(true);
                        plateauDejeu[yb][x].setPiece(new Tour(x,yb,Couleur.BLANC));
                        plateauDejeu[yb][x].setEstOccupe(true);
                        break;
                case 1:
                case 6:
                        plateauDejeu[yn][x].setPiece(new Cavalier(x,yn,Couleur.NOIR));
                        plateauDejeu[yn][x].setEstOccupe(true);
                        plateauDejeu[yb][x].setPiece(new Cavalier(x,yb,Couleur.BLANC));
                        plateauDejeu[yb][x].setEstOccupe(true);
                        break;
                case 2:
                case 5:
                        plateauDejeu[yn][x].setPiece(new Fou(x,yn,Couleur.NOIR));
                        plateauDejeu[yn][x].setEstOccupe(true);
                        plateauDejeu[yb][x].setPiece(new Fou(x,yb,Couleur.BLANC));
                        plateauDejeu[yb][x].setEstOccupe(true);
                        break;
                case 3:
                        plateauDejeu[yn][x].setPiece(new Reine(x,yn,Couleur.NOIR));
                        plateauDejeu[yn][x].setEstOccupe(true);
                        plateauDejeu[yb][x].setPiece(new Reine(x,yb,Couleur.BLANC));
                        plateauDejeu[yb][x].setEstOccupe(true);
                        break;
                case 4:
                        plateauDejeu[yn][x].setPiece(new Roi(x,yn,Couleur.NOIR));
                        plateauDejeu[yn][x].setEstOccupe(true);
                        plateauDejeu[yb][x].setPiece(new Roi(x,yb,Couleur.BLANC));
                        plateauDejeu[yb][x].setEstOccupe(true);
                        break;

            }
        }
    }

    public void setPlateauDejeu(Position[][] plateauDejeu) {
        this.plateauDejeu = plateauDejeu;
    }

    public Position[][] getPlateauDejeu() {
        return plateauDejeu;
    }

    public boolean isCaseNull(Position p){
        if (p.getX()<0 || p.getX()>7 || p.getY()<0 || p.getY()>7 ){
            return true;
        }
        return false;
    }

    //  /!\ on doit verifier que x et y ne correspondent pas à une casse null -> isCaseNull() doit rendre false
    public Position getCasse(int x, int y){
        return plateauDejeu[x][y];
    }


}
