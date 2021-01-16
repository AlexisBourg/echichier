package controller;


import model.coups.EditeurCoup;
import model.plateau.Position;
import model.parties.Parties;
import model.piece.Piece;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import res.interfaceGraphique.LettrePlateau;
import res.son.Son;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import res.interfaceGraphique.ChessGrid;
import res.interfaceGraphique.CssModifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ControllerPartie extends ControllerAffichage {


    protected boolean cliqueUnPasse = false;
    protected int caseDepartGrille;
    protected int[] caseDepartPlateau;
    protected int caseArriveeGrille;
    protected int[] caseArriveePlateau;
    protected Piece pieceMangee;
    protected boolean echec =false;
    protected List<Position> menace;

    protected ControllerSon son= new ControllerSon();
    protected EditeurCoup editeurCoup = new EditeurCoup();







    public ControllerPartie(){
        listeDeplacements = new HashMap<>();
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

    //--------------------------------------------------------------------
    /**
     * Cette méthode traite le cas du second clique, c'est à dire, de faire déplacer la pièce dans le plateau et d'actualiser l'interface en conséquence
     *
     * @param source : bouton cliqué
     */
    public void TraitementCliqueDeux(Object source, Parties partieActuel) {

        caseArriveeGrille = partieActuel.getNumCaseGrille(decompositionIdBouton(source));

        if (listeDeplacements.containsKey(caseArriveeGrille)) {
            caseArriveePlateau = decompositionIdBouton(source);
            if (roiSelectionne(partieActuel) && (caseArriveePlateau[0] == caseDepartPlateau[0]+2 || caseArriveePlateau[0] == caseDepartPlateau[0]-2))
                roque(partieActuel);
            else
                finDeDeplacement(partieActuel);

            //if (EchecEtMat.echecEtMat(partiesPvP.getJoueurNonCourant(), partiesPvP.getEchiquier(), menace))
            //  System.out.println("Echec et mat");

            menace = partieActuel.echec();
            if (menace.size()>0){
                System.out.println("ECHEEEC");
                this.echec = true;
                if (partieActuel.echecEtMat(menace))
                    System.out.println("EchecEtMAAAAAAAAAAAAAAAT");
            }
            else
                this.echec = false;

            ajoutCoupListe(caseDepartPlateau, caseArriveePlateau);
            partieActuel.stockerCoup(caseDepartPlateau, caseArriveePlateau, pieceMangee, partieActuel.getJoueurCourant(), partieActuel.getJoueurNonCourant());
            partieActuel.ChangementJoueurCourant();
        }
    }

    public void finDeDeplacement(Parties partieActuel){
        // Pour arriver sur la case d'arrivée
        this.pieceMangee=changerBackgroundCase(partieActuel);
    }

    /**
     * Cette méthode indique si la case sélectionnée contient une pièce appartenant au joueur et par conséquent, si l'on peut ou non la déplacer
     *
     * @param source : bouton cliqué
     * @return : le fait que la case fasse (Parties) partieActuel du jeu du joueur
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
            //CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), (Parties) partieActuel.getEchiquier().getCase(coordPlateau[0], coordPlateau[1]).getPiece().getImage());
        }
    }

    public Piece changementsPlateau(Parties parties) {
        return parties.actualiserPlateau(caseDepartPlateau, caseArriveePlateau);
    }

    public void changementsPlateauRoque(Parties parties) { // Déplace la bonne tour par rapport au déplacement fait par le Roi
        parties.roqueTour(caseArriveePlateau);
    }

    public Piece changerBackgroundCase(Parties partie){
        son.jouerSon(Son.MOVEPIECE.getBruit());
        Piece pieceMangee=null;
        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
        restaurationImageDeplacementPossible(partie); // Les cases qui contenaient des pièces les retrouves
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseDepartGrille), ""); // La pièce de la case de départ disparaît..
        pieceMangee = changementsPlateau(partie); // Le plateau effectue les changements de position
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partie.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
        // Pour arriver sur la case d'arrivée
        return pieceMangee;
    }

    public boolean roiSelectionne(Parties partieActuel){
        return  partieActuel.isRoiSelectionne(caseDepartPlateau);
    }

    public void roque(Parties partieActuel){
        int xTour;
        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
        restaurationImageDeplacementPossible(partieActuel); // Les cases qui contenaient des pièces les retrouves
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseDepartGrille), ""); // La pièce de la case de départ disparaît..
        pieceMangee = changementsPlateau(partieActuel); // Le plateau effectue les changements de position
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partieActuel.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
        // Pour arriver sur la case d'arrivée

        xTour = (caseArriveePlateau[0]==2) ? 0 : 7;

        CssModifier.ChangeBackgroundImage(grille.getChildren().get(partieActuel.getNumCaseGrille(new int[]{xTour, caseArriveePlateau[1]})), ""); // La pièce de la case de départ disparaît..
        changementsPlateauRoque(partieActuel); // Le plateau effectue les changements de position
        if (caseArriveePlateau[0]==2)
            CssModifier.ChangeBackgroundImage(grille.getChildren().get(partieActuel.getNumCaseGrille(new int[]{caseArriveePlateau[0]+1, caseArriveePlateau[1]})), partieActuel.getEchiquier().getCase(caseArriveePlateau[0]+1, caseArriveePlateau[1]).getPiece().getImage());
        else
            CssModifier.ChangeBackgroundImage(grille.getChildren().get(partieActuel.getNumCaseGrille(new int[]{caseArriveePlateau[0]-1, caseArriveePlateau[1]})), partieActuel.getEchiquier().getCase(caseArriveePlateau[0]-1,  caseArriveePlateau[1]).getPiece().getImage());

        // Pour arriver sur la case d'arrivée
    }

}
