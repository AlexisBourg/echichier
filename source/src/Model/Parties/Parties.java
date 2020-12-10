package Model.Parties;

import Model.Joueur.Joueur;
import Model.PLateau.Plateau;

public class Parties {
    private Plateau echiquier;
    private Joueur[] joueurs;

    public Parties(){
        joueurs = new Joueur[2];
        joueurs[0] = new Joueur(1);
        joueurs[1] = new Joueur(2);
        echiquier = new Plateau(joueurs[0].getPieces(), joueurs[1].getPieces());
    }

    public void partieLocal(){
        int JoueurCourant=1;

        while(!echecEtMat()){
            //System.out.println("Tour du joueur n°"+JoueurCourant+":\n");

        }
    }

    public Plateau getEchiquier(){
        return echiquier;
    }

    public Boolean echecEtMat(){
        return false;
    }
}
