package Model.Parties;

import Controller.ControllerPlateau;
import Model.Joueur.Joueur;
import Model.PLateau.Plateau;
import Model.PLateau.Position;
import Model.Piece.Couleur;
import Model.Piece.Piece;

import java.util.LinkedList;
import java.util.List;

public class Parties {
    private final int ROI = 11;
    private  Joueur[] joueurs;
    private Plateau echiquier;

    public Parties(){
        joueurs = new Joueur[2];
        joueurs[0] = new Joueur(1);
        joueurs[1] = new Joueur(2);
        echiquier = new Plateau(joueurs[0].getPieces(), joueurs[1].getPieces());
    }

    public Plateau getEchiquier(){
        return echiquier;
    }

    public Joueur getJoueur(int num){return joueurs[num];}




}
