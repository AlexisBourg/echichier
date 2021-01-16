package model.coups;

import model.plateau.Plateau;
import model.plateau.Position;

import java.util.LinkedList;

public class StockageCoup {

    private LinkedList<PlateauEtat> etatsPlateau = new LinkedList<>();

    public void addEtatPlateau(PlateauEtat plateau){
        this.etatsPlateau.add(plateau);
    }

    public PlateauEtat getLastEtat(){
        return this.etatsPlateau.getLast();
    }

    public int getNbEtat(){
        return this.etatsPlateau.size();
    }

    public PlateauEtat getPosition(int index){
        return this.etatsPlateau.get(index);

    }

    public LinkedList<PlateauEtat> getCoups(){
        return this.etatsPlateau;
    }
}
