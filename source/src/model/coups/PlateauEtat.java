package model.coups;

import model.plateau.Plateau;

public class PlateauEtat {
<<<<<<< HEAD
    public static final int LIMIT_INF=0;
    public static final int LIMIT_SUP=7;
=======

    //Atribut
>>>>>>> 2946f13a9d740fa9a4f0f4f5e965d439eb623709
    private PositionEtat[][] plateau = new PositionEtat[8][8];

    //Constructeur
    public PlateauEtat(Plateau echiquier){
        for (int y=LIMIT_INF; y<=LIMIT_SUP; y++){
            for (int x=LIMIT_INF; x<=LIMIT_SUP; x++){
                this.plateau[y][x] = new PositionEtat(echiquier.getCase(x, y));
            }
        }
    }

    //Methode
    /**
     *  retourn l'état du plateau à un moment donné
     * @return l'état du plateau
     */
    public PositionEtat[][] getPlateau(){
        return this.plateau;
    }

    /**
     * permet de recuperer l'etat d'une case
     * @param x : est l'abscisse de la case
     * @param y : est l'ordonnée de la case
     * @return retourne la case en question
     */
    public PositionEtat getCaseEtat(int x, int y){
        return this.plateau[y][x];
    }

    /**
     * @return retourn tout les toString de toutes les positionsEtat qui le compose
     */
    @Override
    public String toString(){
        StringBuilder message= new StringBuilder();

        for(int i=LIMIT_INF; i<=LIMIT_SUP; i++){
            for (int j=LIMIT_INF; j<=LIMIT_SUP; j++)
                message.append(plateau[i][j].toString());
        }

        return message.toString();
    }
}
