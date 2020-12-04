package Model.PLateau;

import Model.Piece.*;

public class Plateau {


    private Position plateauDejeu[][];

    /**
     * un plateau est composé de position (casse) et initialisé avec les piece blanche en bas et les noire en haut
     */
    public Plateau(Piece[] piecesBlanc, Piece[] piecesNoir){
        plateauDejeu = new Position[8][8];
        int yb=7, yn=0, i=0;

        for (int x=0;x<8;x++) {
            for (int y=0;y<8;y++) {
                plateauDejeu[y][x] = new Position(y,x, null);
            }
        }
        for (int x=0;x<8;x++){
            plateauDejeu[1][x].setPiece(piecesNoir[i]);
            plateauDejeu[6][x].setPiece(piecesBlanc[i]);
            i+=1;

        }

        for (int x=0; x<8; x++){
            plateauDejeu[yn][x].setPiece(piecesNoir[i]);
            plateauDejeu[yb][x].setPiece(piecesBlanc[i]);
            i+=1;
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
