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
        if (indexCourant>0){
            System.out.println(listeCoups.getPosition(indexCourant-1));
            return listeCoups.getPosition(--indexCourant);
        }

        return null;
    }

    public PlateauEtat coupSuivant(){
        if (indexCourant==listeCoups.getNbEtat())
            return listeCoups.getPosition(++indexCourant);
        return null;
    }

    public LinkedList<PlateauEtat> getCoups(){
        return listeCoups.getCoups();
    }

    public int getIndexCourant(){
        return this.indexCourant;
    }
}
