package Model.Parties;

import Model.Joueur.IA;
import Model.Joueur.InterfaceJoueur;
import Model.Joueur.Joueur;
import Model.PLateau.Plateau;

import java.util.LinkedList;

public interface PartiesInterface {
    public Plateau getEchiquier();

    public Joueur getJoueur(int num);

    public IA getIA();

    public LinkedList<Coup> getListeCoup();

    public void setJoueurs(int i, InterfaceJoueur joueurs);
}
