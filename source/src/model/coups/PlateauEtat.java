package model.coups;

import model.plateau.Plateau;

public class PlateauEtat {
    private PositionEtat[][] plateau = new PositionEtat[8][8];

    public PlateauEtat(Plateau echiquier){
        for (int y=0; y<8; y++){
            for (int x=0; x<8; x++){
                this.plateau[y][x] = new PositionEtat(echiquier.getCase(x, y));
            }
        }
    }

    public PositionEtat[][] getPlateau(){
        return this.plateau;
    }

    public PositionEtat getCaseEtat(int x, int y){
        return this.plateau[y][x];
    }

    @Override
    public String toString(){
        StringBuilder message= new StringBuilder();

        for(int i=0; i<8; i++){
            for (int j=0; j<8; j++)
                message.append(plateau[i][j].toString());
        }

        return message.toString();
    }

}
