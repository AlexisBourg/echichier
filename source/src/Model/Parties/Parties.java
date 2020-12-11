package Model.Parties;

import Model.Joueur.Joueur;
import Model.PLateau.Plateau;
import Model.Piece.Couleur;

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
        int i=0;

        while(!echecEtMat()){
            Joueur joueurCourant=joueurs[i];

            System.out.println("Tour du joueur "+joueurCourant.getCouleur()+":\n");

            //selectionne une case de depart

            //selectionne une case d'arrivé

            //doDeplacement(echiquier,controller,joueur);


            i=(i+1)%2;
        }
    }

    public Plateau getEchiquier(){
        return echiquier;
    }

    public Boolean echecEtMat(){
        return false;
    }
}
