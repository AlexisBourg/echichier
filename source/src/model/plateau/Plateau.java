package model.plateau;

import model.coups.PlateauEtat;
import model.piece.*;

public class Plateau {

<<<<<<< HEAD
    public static final int LIMIT_INF=0;
    public static final int LIMIT_SUP=7;
=======
    //Atribut
>>>>>>> 2946f13a9d740fa9a4f0f4f5e965d439eb623709
    private Position[][] plateauDeJeu = new Position[8][8];

    //Constructeur
    /**
     * un plateau est composé de position (casse) et initialisé avec les piece blanche en bas et les noire en haut
     */
    public Plateau(Piece[] piecesBlanc, Piece[] piecesNoir){
        int yb=7, yn=0, i=0;

        for (int x=LIMIT_INF;x<=LIMIT_SUP;x++) {
            for (int y=LIMIT_INF;y<=LIMIT_SUP;y++) {
                plateauDeJeu[y][x] = new Position(y,x, null);
            }
        }
        for (int x=LIMIT_INF;x<=LIMIT_SUP;x++){
            plateauDeJeu[1][x].setPiece(piecesNoir[i]);
            plateauDeJeu[6][x].setPiece(piecesBlanc[i]);
            i+=1;

        }

        for (int x=LIMIT_INF; x<=LIMIT_SUP; x++){
            plateauDeJeu[yn][x].setPiece(piecesNoir[i]);
            plateauDeJeu[yb][x].setPiece(piecesBlanc[i]);
            i+=1;
        }
    }

    //Methode
    public boolean isCaseNull(Position p){
        return p.getX() >= LIMIT_INF && p.getX() <= LIMIT_SUP && p.getY() >= LIMIT_INF && p.getY() <= LIMIT_SUP;
    }

    public boolean isCaseSansPiece(Position p){
        return p.getPiece() == null;
    }

    public Position getCase(int x, int y){
        return plateauDeJeu[y][x];
    }

    public void setEtat(PlateauEtat plateau){
<<<<<<< HEAD
        //System.out.println(plateau.toString());
        for (int y=LIMIT_INF; y<=LIMIT_SUP; y++){
            for (int x=LIMIT_INF; x<=LIMIT_SUP; x++){
=======
        for (int y=0; y<8; y++){
            for (int x=0; x<8; x++){
>>>>>>> 2946f13a9d740fa9a4f0f4f5e965d439eb623709
                this.plateauDeJeu[y][x].setY(plateau.getCaseEtat(x, y).getY());
                this.plateauDeJeu[y][x].setX(plateau.getCaseEtat(x, y).getX());
                this.plateauDeJeu[y][x].setEtatPiece(plateau.getCaseEtat(x, y).getPiece());
                this.plateauDeJeu[y][x].setOccupee(plateau.getCaseEtat(x, y).getOccupe());
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder message= new StringBuilder();

        for(int i=LIMIT_INF; i<=LIMIT_SUP; i++){
            for (int j=LIMIT_INF; j<=LIMIT_SUP; j++)
                message.append(plateauDeJeu[i][j].toString());
        }
        return message.toString();
    }
}
