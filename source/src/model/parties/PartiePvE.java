package model.parties;

import model.joueur.IA;
import model.joueur.InterfaceJoueur;
import model.piece.Piece;

public class PartiePvE extends Parties{

    //Atribut

    //Constructeur

    /**
     * Créé une partie joueur contre IA
     */
    public PartiePvE() {
        super();
        super.setJoueurs(1,new IA());
    }

    //Methode

    /**
     *
     * @return : l'IA de la partie courante
     */
    public IA getIA(){ return (IA) joueurs[1]; }
}