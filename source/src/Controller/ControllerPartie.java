package Controller;


import Model.Parties.PartiePvE;
import Model.Parties.Parties;
import Model.Parties.PartiesInterface;
import Model.Piece.Piece;
import javafx.fxml.FXML;
import res.BoxCoups;
import res.ChessGrid;
import res.CssModifier;

import java.util.HashMap;
import java.util.Map;

public abstract class ControllerPartie {

    protected Parties partie;
    protected HashMap<Integer, int[]> listeDeplacements;
    protected boolean cliqueUnPasse = false;
    protected int caseDepartGrille;
    protected int[] caseDepartPlateau;
    protected int caseArriveeGrille;
    protected int[] caseArriveePlateau;
    protected Piece pieceMangee;
    protected boolean echec =false;

    @FXML
    protected ChessGrid grille;

    @FXML
    protected BoxCoups listeCoups;

    public ControllerPartie(Parties partie){
        this.partie=partie;
        listeDeplacements = new HashMap<>();
    }

    /**
     * @param source bouton qui a reçcu le clique
     * @return : le numéro du clique (1 si le joueur a sélectionné une pièce de son jeu qu'il veut déplacer, 2 s'il indique où déplacer la pièce de son jeu)
     */
    public int NumeroClique(Object source) {
        int[] coord = decompositionIdBouton(source);
        return partie.numClique(coord[0], coord[1]);
    }

    /**
     * Cette méthode s'occupe de traiter le cas du premier clique, c'est à dire, afficher les cases sur lesquelles la pièce peut se déplacer
     *
     * @param source : bouton cliqué
     */
    public void TraitementCliqueUn(Object source) {
        caseDepartPlateau = decompositionIdBouton(source);
        if (verificationClique(source)) {
            montrerDeplacementDispo();
            caseDepartGrille = partie.getNumCaseGrille(decompositionIdBouton(source));
        }
    }

    /**
     * Cette méthode indique si la case sélectionnée contient une pièce appartenant au joueur et par conséquent, si l'on peut ou non la déplacer
     *
     * @param source : bouton cliqué
     * @return : le fait que la case fasse partie du jeu du joueur
     */
    public boolean verificationClique(Object source) {
        return (partie.verifCase(caseDepartPlateau[0], caseDepartPlateau[1], partie.getJoueurCourant()));
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

    public Piece changementsPlateau() {
        return partie.actualiserPlateau(caseDepartPlateau, caseArriveePlateau);
    }

    public Piece changementsPlateauIA(){
        return partie.actualiserPlateauIA(caseDepartPlateau, caseArriveePlateau);
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

    public String urlImageDeplacementPossible(int i, int x, int y) {
        return partie.getEchiquier().getCase(x, y).getPiece().getImage();
    }

    public void finDeDeplacement() {
        Piece pieceMangee;
        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
        restaurationImageDeplacementPossible(); // Les cases qui contenaient des pièces les retrouves
        pieceMangee = changementsPlateau();
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseDepartGrille), ""); // La pièce de la case de départ disparaît..
        if(partie.getIndexJoueurCourant()==1){
            changementsPlateau(); // Le plateau effectue les changements de position pour l'ia
        }
        else {
            changementsPlateauIA(); // Le plateau effectue les changements de position
        }
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partie.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
        // Pour arriver sur la case d'arrivée
        this.pieceMangee = pieceMangee;
    }

    public void restaurationImageDeplacementPossible() {
        int x, y, coordGrille;
        for (Map.Entry coord : listeDeplacements.entrySet()) {
            coordGrille = (int) coord.getKey();
            x = ((int[]) coord.getValue())[0];
            y = ((int[]) coord.getValue())[1];

            if (!partie.isCaseSansPiece(x, y))
                CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), urlImageDeplacementPossible(coordGrille, x, y));
        }
    }

    public void declarationDeplacementPossible(int coordGrille, int[] coordPlateau) {
        if (!partie.isCaseSansPiece(coordPlateau[0], coordPlateau[1])) {
            //CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), "");
        }

        CssModifier.ChangeBackgroundColor(grille.getChildren().get(coordGrille), "red");

        if (!partie.isCaseSansPiece(coordPlateau[0], coordPlateau[1])) {
            //CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), partie.getEchiquier().getCase(coordPlateau[0], coordPlateau[1]).getPiece().getImage());
        }
    }

    /**
     * Cette méthode récupère les liste des déplacements possibles avec la pièce sélectionnée.
     *
     *
     */

    public void montrerDeplacementDispo() {
        HashMap<Integer, int[]> listeDeplacements = (!this.echec) ? partie.getDeplacements(caseDepartPlateau[0], caseDepartPlateau[1]) : partie.getDeplacementsEchec(caseDepartPlateau[0], caseDepartPlateau[1], menace);
        int coordGrille;
        int[] coordPlateau;

        for (Map.Entry coord : listeDeplacements.entrySet()) { // Pour chaque case dans la liste de déplacements possibles
            coordGrille = (int) coord.getKey();
            coordPlateau = (int[]) coord.getValue();

            declarationDeplacementPossible(coordGrille, coordPlateau); // On les met en surbrillance
        }

        this.listeDeplacements = listeDeplacements;
    }
}
