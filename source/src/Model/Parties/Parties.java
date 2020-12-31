package Model.Parties;

import Model.Joueur.IA;
import Model.Joueur.Joueur;
import Model.Joueur.InterfaceJoueur;
import Model.PLateau.Plateau;

public class Parties {
    private final int ROI = 11;
    private  InterfaceJoueur[] joueurs;
    private Plateau echiquier;

    public Parties(){
        joueurs = new InterfaceJoueur[2];
        joueurs[0] = new Joueur(1); // BLANC
        joueurs[1] = new Joueur(2); // NOIR
        echiquier = new Plateau(joueurs[0].getPieces(), joueurs[1].getPieces());
    }

    public Plateau getEchiquier(){
        return echiquier;
    }

    public Joueur getJoueur(int num){return (Joueur) joueurs[num];}

    public IA getIA(){return (IA) joueurs[1];}

    public void setJoueurs(int i,InterfaceJoueur joueurs) {
        this.joueurs[i] = joueurs;
    }
}
