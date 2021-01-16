package model.plateau;

import model.piece.*;

public class Plateau {

    private Position[][] plateauDeJeu = new Position[8][8];

    /**
     * un plateau est composé de position (casse) et initialisé avec les piece blanche en bas et les noire en haut
     */
    public Plateau(Piece[] piecesBlanc, Piece[] piecesNoir){
        int yb=7, yn=0, i=0;

        for (int x=0;x<8;x++) {
            for (int y=0;y<8;y++) {
                plateauDeJeu[y][x] = new Position(y,x, null);
            }
        }
        for (int x=0;x<8;x++){
            plateauDeJeu[1][x].setPiece(piecesNoir[i]);
            plateauDeJeu[6][x].setPiece(piecesBlanc[i]);
            i+=1;

        }

        for (int x=0; x<8; x++){
            plateauDeJeu[yn][x].setPiece(piecesNoir[i]);
            plateauDeJeu[yb][x].setPiece(piecesBlanc[i]);
            i+=1;
        }
    }

    public void setPlateauDejeu(Position[][] plateauDejeu) {
        this.plateauDeJeu = plateauDejeu;
    }

    public Position[][] getPlateauDejeu() {
        return plateauDeJeu;
    }

    public boolean isCaseNull(Position p){
        return p.getX() >= 0 && p.getX() <= 7 && p.getY() >= 0 && p.getY() <= 7;
    }

    public boolean isCaseSansPiece(Position p){
        return p.getPiece() == null;
    }

    //  /!\ on doit verifier en amont que x et y ne correspondent pas à une casse null -> isCaseNull() doit rendre false
    public Position getCase(int x, int y){
        return plateauDeJeu[y][x];
    }

    @Override
    public String toString(){
        StringBuilder message= new StringBuilder("");

        for(int i=0; i<8; i++){
            for (int j=0; j<8; j++)
                message.append(plateauDeJeu[i][j].toString());
        }

        return message.toString();
    }
}
