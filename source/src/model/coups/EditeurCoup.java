package model.coups;

import java.util.LinkedList;

public class EditeurCoup {

    //Atribut
    private StockageCoup listeCoups = new StockageCoup();
    private int indexCourant=0;

    //Constructeur

    //Methode

    /**
     * permet d'ajouter un coup à la liste des coup joué
     * @param plateau: est le plateau actuel
     */
    public void ajoutCoup(PlateauEtat plateau){
        listeCoups.addEtatPlateau(plateau);
        if (listeCoups.getNbEtat()!=1)
            indexCourant++;
    }

    /**
     * permet de revenir au coup précedant
     * @return le plateau avec la position des pièce au coup précedant
     */
    public PlateauEtat coupPrecedent(){
        return listeCoups.getPosition(--indexCourant);
    }

    /**
     * Permet de retirer le dernier coup enregistré
     */
    public void enleverCoup(){
        listeCoups.enleverDernierCoup();
    }

    /**
     * permet d'allez au coup suivant (si jouer)
     * @return le plateau avec la position des pièce au coup suivant
     */
    public PlateauEtat coupSuivant(){
        return listeCoups.getPosition(++indexCourant);
    }

    /**
     * Permet de recuperet la liste des coup joué
     * @return la liste des coup jouer
     */
    public LinkedList<PlateauEtat> getCoups(){
        return listeCoups.getCoups();
    }

    /**
     * Permet de récupérer l'index
     * @return l'index courant
     */
    public int getIndexCourant(){
        return this.indexCourant;
    }

    /**
     * Permet de retourne le nombre de coup
     * @return le nombre de coup
     */
    public int getNbEtat(){
        return listeCoups.getNbEtat();
    }
}
