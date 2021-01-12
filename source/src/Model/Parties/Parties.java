package Model.Parties;


import Controller.ControllerPartiesPvP;

import Model.Joueur.IA;
import Model.Joueur.Joueur;
import Model.Joueur.InterfaceJoueur;
import Model.PLateau.Plateau;

import java.util.LinkedList;

public class Parties {
    private final int ROI = 12;
    private final InterfaceJoueur[] joueurs;
    private Plateau echiquier;
    private final LinkedList<Coup> listeCoup;

    public Parties(){
        joueurs = new InterfaceJoueur[2];
        joueurs[0] = new Joueur(1); // BLANC
        joueurs[1] = new Joueur(2); // NOIR
        echiquier = new Plateau(joueurs[0].getPieces(), joueurs[1].getPieces());
        listeCoup = new LinkedList<>();
    }

    public Plateau getEchiquier(){
        return echiquier;
    }

    public Joueur getJoueur(int num){return (Joueur) joueurs[num];}

    public IA getIA(){return (IA) joueurs[1];}

    public LinkedList<Coup> getListeCoup(){
        return listeCoup;
    }

    public void setJoueurs(int i,InterfaceJoueur joueurs) {
        this.joueurs[i] = joueurs;
    }
}
