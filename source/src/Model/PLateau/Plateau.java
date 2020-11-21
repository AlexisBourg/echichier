package Model.PLateau;

import Model.Piece.*;

public class Plateau {


    private Position plateauDejeu[][];

    /**
     * un plateau est composé de position (casse) et initialisé avec les piece blanche en bas et les noire en haut
     */
    public Plateau(){
        plateauDejeu = new Position[8][8];
        for (int x=0;x<8;x++) {
            for (int y=0;y<8;y++) {
                plateauDejeu[x][y] = new Position(x,y, null);
            }
        }
        for (int x=0;x<8;x++){
            plateauDejeu[x][1].setPiece(new Pion(x,2, Couleur.BLANC));
            plateauDejeu[x][1].setEstOccupé(true);
            plateauDejeu[x][6].setPiece(new Pion(x,6, Couleur.NOIR));
            plateauDejeu[x][6].setEstOccupé(true);
        }
        for (int x=0;x<8;x++){
            int yb=0, yn=7;

            switch (x){
                case 0,7:
                        plateauDejeu[x][yn].setPiece(new Tour(x,yn,Couleur.NOIR));
                        plateauDejeu[x][yb].setPiece(new Tour(x,yb,Couleur.BLANC));
                        plateauDejeu[x][yn].setEstOccupé(true);
                        plateauDejeu[x][yb].setEstOccupé(true);
                    break;
                case 1,6:
                        plateauDejeu[x][yn].setPiece(new Cavalier(x,yn,Couleur.NOIR));
                        plateauDejeu[x][yb].setPiece(new Cavalier(x,yb,Couleur.BLANC));
                        plateauDejeu[x][yn].setEstOccupé(true);
                        plateauDejeu[x][yb].setEstOccupé(true);
                    break;
                case 2,5:
                        plateauDejeu[x][yn].setPiece(new Fou(x,yn,Couleur.NOIR));
                        plateauDejeu[x][yb].setPiece(new Fou(x,yb,Couleur.BLANC));
                        plateauDejeu[x][yn].setEstOccupé(true);
                        plateauDejeu[x][yb].setEstOccupé(true);
                    break;
                case 3:
                        plateauDejeu[x][yn].setPiece(new Reine(x,yn,Couleur.NOIR));
                        plateauDejeu[x][yb].setPiece(new Reine(x,yb,Couleur.BLANC));
                        plateauDejeu[x][yn].setEstOccupé(true);
                        plateauDejeu[x][yb].setEstOccupé(true);
                    break;
                case 4:
                        plateauDejeu[x][yn].setPiece(new Roi(x,yn,Couleur.NOIR));
                        plateauDejeu[x][yb].setPiece(new Roi(x,yb,Couleur.BLANC));
                        plateauDejeu[x][yn].setEstOccupé(true);
                        plateauDejeu[x][yb].setEstOccupé(true);
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
