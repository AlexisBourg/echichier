package Controller;


import Model.PLateau.Position;
import Model.Parties.Parties;
import Model.Piece.Piece;
import javafx.fxml.FXML;
import res.BoxCoups;
import res.ChessGrid;
import res.CssModifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ControllerPartie {

    protected HashMap<Integer, int[]> listeDeplacements;
    protected boolean cliqueUnPasse = false;
    protected int caseDepartGrille;
    protected int[] caseDepartPlateau;
    protected int caseArriveeGrille;
    protected int[] caseArriveePlateau;
    protected Piece pieceMangee;
    protected boolean echec =false;
    protected List<Position> menace;


    @FXML
    protected ChessGrid grille;

    @FXML
    protected BoxCoups listeCoups;

    public ControllerPartie(){
        listeDeplacements = new HashMap<>();
    }


    /**
     * Ré-affecte le style de base des cases de déplacement possible
     */
    public void retablissementCouleurCaseDeplacementPossibles() {
        int coordGrille;
        int[] coordPlateau;

        for (Map.Entry coord : listeDeplacements.entrySet()) {
            coordGrille = (int) coord.getKey();
            coordPlateau = (int[]) coord.getValue();

            if (coordPlateau[1] % 2 == 0) {
                if (coordPlateau[0] % 2 == 0)
                    CssModifier.ChangeBackgroundColor(grille.getChildren().get(coordGrille), "white;");
                else
                    CssModifier.ChangeBackgroundColor(grille.getChildren().get(coordGrille), "black;");
            } else {
                if (coordPlateau[0] % 2 == 1)
                    CssModifier.ChangeBackgroundColor(grille.getChildren().get(coordGrille), "white;");
                else
                    CssModifier.ChangeBackgroundColor(grille.getChildren().get(coordGrille), "black;");
            }

        }
    }


    /**
     * @param source : bouton cliqué
     * @return : retourne les coordonnées du PLATEAU correspondant à l'endroit cliqué
     */
    public int[] decompositionIdBouton(Object source) {
        int[] tabCoord = new int[2];
        String id = source.toString();
        id = id.substring(10, 12);
        int x, y;
        tabCoord[0] = Integer.parseInt(id.substring(0, 1));
        tabCoord[1] = Integer.parseInt(id.substring(1));
        return tabCoord;
    }

    /**
     * @param source bouton qui a reçcu le clique
     * @return : le numéro du clique (1 si le joueur a sélectionné une pièce de son jeu qu'il veut déplacer, 2 s'il indique où déplacer la pièce de son jeu)
     */
    public int NumeroClique(Parties parties,Object source) {
        int[] coord = decompositionIdBouton(source);
        return numClique(coord[0], coord[1], parties);
    }

    /**
     * @param x : coordonnée x du bouton cliqué (coordonnée plateau)
     * @param y : coordonnée y du bouton cliqué (coordonnée plateau)
     * @return : le numéro du clic en fonction des propriétés du bouton cliqué
     */
    public int numClique(int x, int y, Parties parties) {
        if (!parties.isCaseSansPiece(x, y) && parties.isPieceSelecAppartientAuJoueurCourant(x, y, parties.getJoueur(parties.getIndexJoueurCourant()))){
            return 1;
        }
        return 2;
    }

    public void restaurationImageDeplacementPossible(Parties parties) {
        int x, y, coordGrille;
        for (Map.Entry coord : listeDeplacements.entrySet()) {
            coordGrille = (int) coord.getKey();
            x = ((int[]) coord.getValue())[0];
            y = ((int[]) coord.getValue())[1];

            if (!parties.isCaseSansPiece(x, y))
                CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), urlImageDeplacementPossible(coordGrille, x, y, parties));
        }
    }



    public String urlImageDeplacementPossible(int i, int x, int y, Parties parties) {
        return parties.getEchiquier().getCase(x, y).getPiece().getImage();
    }

    /**
     * Cette méthode s'occupe de traiter le cas du premier clique, c'est à dire, afficher les cases sur lesquelles la pièce peut se déplacer
     *
     * @param source : bouton cliqué
     */
    public void TraitementCliqueUn(Object source, Parties parties) {
        caseDepartPlateau = decompositionIdBouton(source);
        if (verificationClique(source, parties)) {
            montrerDeplacementDispo(parties);
            caseDepartGrille = parties.getNumCaseGrille(decompositionIdBouton(source));
        }
    }

    /**
     * Cette méthode indique si la case sélectionnée contient une pièce appartenant au joueur et par conséquent, si l'on peut ou non la déplacer
     *
     * @param source : bouton cliqué
     * @return : le fait que la case fasse (Parties) partiesActuel du jeu du joueur
     */
    public boolean verificationClique(Object source, Parties parties) {
        return (parties.verifCase(caseDepartPlateau[0], caseDepartPlateau[1], parties.getJoueurCourant()));
    }

    /**
     * Cette méthode récupère les liste des déplacements possibles avec la pièce sélectionnée.
     *
     */

    public void montrerDeplacementDispo(Parties parties) {
        HashMap<Integer, int[]> listeDeplacements = (!this.echec) ? parties.getDeplacements(caseDepartPlateau[0], caseDepartPlateau[1]) : parties.getDeplacementsEchec(caseDepartPlateau[0], caseDepartPlateau[1], menace);
        int coordGrille;
        int[] coordPlateau;

        for (Map.Entry coord : listeDeplacements.entrySet()) { // Pour chaque case dans la liste de déplacements possibles
            coordGrille = (int) coord.getKey();
            coordPlateau = (int[]) coord.getValue();

            declarationDeplacementPossible(coordGrille, coordPlateau, parties); // On les met en surbrillance
        }

        this.listeDeplacements = listeDeplacements;
    }

    public void declarationDeplacementPossible(int coordGrille, int[] coordPlateau, Parties parties) {
        if (!parties.isCaseSansPiece(coordPlateau[0], coordPlateau[1])) {
            //CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), "");
        }

        CssModifier.ChangeBackgroundColor(grille.getChildren().get(coordGrille), "red");

        if (!parties.isCaseSansPiece(coordPlateau[0], coordPlateau[1])) {
            //CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), (Parties) partiesActuel.getEchiquier().getCase(coordPlateau[0], coordPlateau[1]).getPiece().getImage());
        }
    }

    public Piece changementsPlateau(Parties parties) {
        return parties.actualiserPlateau(caseDepartPlateau, caseArriveePlateau);
    }

    public void changementsPlateauRoque(Parties parties) { // Déplace la bonne tour par rapport au déplacement fait par le Roi
        parties.roqueTour(caseArriveePlateau);
    }

    public void changerBackgroundCase(Parties parties){
        Piece pieceMangee=null;
        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
        restaurationImageDeplacementPossible(parties); // Les cases qui contenaient des pièces les retrouves
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseDepartGrille), ""); // La pièce de la case de départ disparaît..
        pieceMangee = changementsPlateau(parties); // Le plateau effectue les changements de position
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), parties.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());

    }

}
