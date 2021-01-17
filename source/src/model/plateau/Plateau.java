package model.plateau;

import model.coups.PlateauEtat;
import model.piece.*;

public class Plateau {

    //Atribut
    public static final int LIMIT_INF = 0;
    public static final int LIMIT_SUP = 7;
    private Position[][] plateauDeJeu = new Position[8][8];

    //Constructeur

    /**
     * un plateau est composé de position (casse) et initialisé avec les piece blanche en bas et les noire en haut
     */
    public Plateau(Piece[] piecesBlanc, Piece[] piecesNoir) {
        int yb = 7, yn = 0, i = 0;

        for (int x = LIMIT_INF; x <= LIMIT_SUP; x++) {
            for (int y = LIMIT_INF; y <= LIMIT_SUP; y++) {
                plateauDeJeu[y][x] = new Position(y, x, null);
            }
        }
        for (int x = LIMIT_INF; x <= LIMIT_SUP; x++) {
            plateauDeJeu[1][x].setPiece(piecesNoir[i]);
            plateauDeJeu[6][x].setPiece(piecesBlanc[i]);
            i += 1;

        }

        for (int x = LIMIT_INF; x <= LIMIT_SUP; x++) {
            plateauDeJeu[yn][x].setPiece(piecesNoir[i]);
            plateauDeJeu[yb][x].setPiece(piecesBlanc[i]);
            i += 1;
        }
    }

    //Methode
    public boolean isCaseNull(Position p) {
        return p.getX() >= LIMIT_INF && p.getX() <= LIMIT_SUP && p.getY() >= LIMIT_INF && p.getY() <= LIMIT_SUP;
    }

    public boolean isCaseSansPiece(Position p) {
        return p.getPiece() == null;
    }

    public Position getCase(int x, int y) {
        return plateauDeJeu[y][x];
    }

    public void setEtat(PlateauEtat plateau) {

        for (int y = LIMIT_INF; y <= LIMIT_SUP; y++) {
            for (int x = LIMIT_INF; x <= LIMIT_SUP; x++) {

                this.plateauDeJeu[y][x].setY(plateau.getCaseEtat(x, y).getY());
                this.plateauDeJeu[y][x].setX(plateau.getCaseEtat(x, y).getX());
                this.plateauDeJeu[y][x].setEtatPiece(plateau.getCaseEtat(x, y).getPiece());
                this.plateauDeJeu[y][x].setOccupee(plateau.getCaseEtat(x, y).getOccupe());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();

        for (int i = LIMIT_INF; i <= LIMIT_SUP; i++) {
            for (int j = LIMIT_INF; j <= LIMIT_SUP; j++)
                message.append(plateauDeJeu[i][j].toString());
        }
        return message.toString();
    }
}

