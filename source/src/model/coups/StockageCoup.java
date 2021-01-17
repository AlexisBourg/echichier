package model.coups;


import java.util.LinkedList;

public class StockageCoup {

    //Atribut
    private LinkedList<PlateauEtat> etatsPlateau = new LinkedList<>();

    //Constructeur

    //Methode
    public void addEtatPlateau(PlateauEtat plateau){
        this.etatsPlateau.add(plateau);
    }

    public void enleverDernierCoup(){
        etatsPlateau.removeLast();
    }

    public int getNbEtat(){
        return this.etatsPlateau.size();
    }

    public PlateauEtat getPosition(int index){ return this.etatsPlateau.get(index); }

    public LinkedList<PlateauEtat> getCoups(){
        return this.etatsPlateau;
    }
}
