package model.coups;


import java.util.LinkedList;

public class StockageCoup {

    //Atribut
    private LinkedList<PlateauEtat> etatsPlateau = new LinkedList<>();

    //Constructeur

    //Methode

    /**
     * permet d'ajouter un etat
     * @param plateau : le plateau à ajouter
     */
    public void addEtatPlateau(PlateauEtat plateau){
        this.etatsPlateau.add(plateau);
    }

    /**
     * permet d'enlever le dernier coup
     */
    public void enleverDernierCoup(){
        etatsPlateau.removeLast();
    }

    /**
     * permet de retourner le nombre total d'etat
     * @return
     */
    public int getNbEtat(){
        return this.etatsPlateau.size();
    }

    /**
     * permet de retourner l'etat d'un plateau par rapport à un index
     * @param index : est l'index
     * @return
     */
    public PlateauEtat getPosition(int index){ return this.etatsPlateau.get(index); }

    /**
     * Retrourne la liste de tout les coup
     * @return
     */
    public LinkedList<PlateauEtat> getCoups(){
        return this.etatsPlateau;
    }
}
