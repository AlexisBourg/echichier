package model.coups;

import model.plateau.Plateau;

import java.util.LinkedList;

public class EditeurCoup {

    private StockageCoup listeCoups = new StockageCoup();
    private int indexCourant=0;

    public void ajoutCoup(PlateauEtat plateau){
        listeCoups.addEtatPlateau(plateau);
        if (listeCoups.getNbEtat()!=1)
            indexCourant++;
    }

    public PlateauEtat coupPrecedent(){
        listeCoups.enleverDernierCoup();
        return listeCoups.getPosition(--indexCourant);
    }

    public PlateauEtat coupSuivant(){
        return listeCoups.getPosition(++indexCourant);
    }

    public LinkedList<PlateauEtat> getCoups(){
        return listeCoups.getCoups();
    }

    public int getIndexCourant(){
        return this.indexCourant;
    }

    public int getNbEtat(){
        return listeCoups.getNbEtat();
    }
}
