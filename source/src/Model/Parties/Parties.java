package Model.Parties;

import Controller.ControllerPartiesPvP;
import Model.Joueur.IA;
import Model.Joueur.Joueur;
import Model.Joueur.InterfaceJoueur;
import Model.PLateau.Plateau;
import Model.PLateau.Position;
import Model.Piece.Couleur;
import Model.Piece.Piece;

import java.util.LinkedList;
import java.util.List;

public class Parties {
    private final int ROI = 11;
    private  InterfaceJoueur[] joueurs;
    private Plateau echiquier;

    public Parties(){
        joueurs = new Joueur[2];
        joueurs[0] = new Joueur(1); // BLANC
        joueurs[1] = new Joueur(2); // NOIR
        echiquier = new Plateau(joueurs[0].getPieces(), joueurs[1].getPieces());
    }



    public Plateau getEchiquier(){
        return echiquier;
    }

    public Joueur getJoueur(int num){return (Joueur) joueurs[num];}

    public void setJoueurs(int i,InterfaceJoueur joueurs) {
        this.joueurs[i] = joueurs;
    }
}
