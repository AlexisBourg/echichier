package model.parties;

import model.joueur.IA;

public class PartiePvE extends Parties{

    //Atribut

    //Constructeur
    public PartiePvE() {
        super();
        super.setJoueurs(1,new IA());
    }

    //Methode
    public IA getIA(){ return (IA) joueurs[1]; }
}