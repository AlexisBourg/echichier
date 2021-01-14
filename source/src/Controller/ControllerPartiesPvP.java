package Controller;

import Model.PLateau.Position;
import Model.Parties.PartieGraph;
import Model.Piece.Piece;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import res.BoxCoups;
import res.ChessGrid;
import Model.PLateau.Plateau;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import res.CssModifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class ControllerPartiesPvP  extends ControllerPartie{
    /*
    private PartieGraph partie;
    private HashMap<Integer, int[]> listeDeplacements;
    private boolean cliqueUnPasse = false;
    private int caseDepartGrille;
    private int[] caseDepartPlateau;
    private int caseArriveeGrille;
    private int[] caseArriveePlateau;
    private Piece pieceMangee;
    private boolean echec;
    private List<Position> menace;

    @FXML
    private ChessGrid grille;

    @FXML
    private BoxCoups listeCoups;
    */
    public ControllerPartiesPvP(PartieGraph partie){
        super(partie);
        /*
        super.partie = partie;
        listeDeplacements = new HashMap<>();
        */
    }

    @FXML
    public void chargementPlateau() {
        Plateau echiquier = partie.getEchiquier();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                grille.getChildren().get((8 * (y + 1) - (8 - x))).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        switch (numeroClique(mouseEvent.getSource())) {
                            case 1:
                                System.out.println("1111111111111");
                                if (!listeDeplacements.isEmpty()) { // Si le clique 1 avait déjà été enclenché
                                    retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
                                    restaurationImageDeplacementPossible(); // Les cases qui contenaient des pièces les retrouves
                                }
                                TraitementCliqueUn(mouseEvent.getSource());
                                cliqueUnPasse = true;
                                break;

                            case 2:
                                System.out.println("2222222222222");
                                if (cliqueUnPasse) {
                                    TraitementCliqueDeux(mouseEvent.getSource());
                                }
                                cliqueUnPasse = false;
                                break;
                        }
                    }
                });
                if (echiquier.getCase(x, y).isOccupe()){
                    CssModifier.ChangeBackgroundImage(grille.getChildren().get((8 * (y + 1) - (8 - x))), echiquier.getCase(x, y).getPiece().getImage());
                    //CssModifier.test((Button)grille.getChildren().get((8 * (y + 1) - (8 - x))), echiquier.getCase(x, y).getPiece().getImage());
                }
            }
        }
    }
    //--------------------------------------------------------------
//    /**
//     * @param source bouton qui a reçcu le clique
//     * @return : le numéro du clique (1 si le joueur a sélectionné une pièce de son jeu qu'il veut déplacer, 2 s'il indique où déplacer la pièce de son jeu)
//     */
//    public int numeroClique(Object source) {
//        int[] coord = decompositionIdBouton(source);
//        return partie.numClique(coord[0], coord[1]);
//    }
//
//    /**
//     * Cette méthode s'occupe de traiter le cas du premier clique, c'est à dire, afficher les cases sur lesquelles la pièce peut se déplacer
//     *
//     * @param source : bouton cliqué
//     */
//    public void TraitementCliqueUn(Object source) {
//        caseDepartPlateau = decompositionIdBouton(source);
//        if (verificationClique(source)) {
//            montrerDeplacementDispo();
//            caseDepartGrille = partie.getNumCaseGrille(decompositionIdBouton(source));
//        }
//    }
//
//    /**
//     * Cette méthode indique si la case sélectionnée contient une pièce appartenant au joueur et par conséquent, si l'on peut ou non la déplacer
//     *
//     * @param source : bouton cliqué
//     * @return : le fait que la case fasse partie du jeu du joueur
//     */
//    public boolean verificationClique(Object source) {
//        return (partie.verifCase(caseDepartPlateau[0], caseDepartPlateau[1], partie.getJoueurCourant()));
//    }


    //--------------------------------------------------------------
//    public void declarationDeplacementPossible(int coordGrille, int[] coordPlateau) {
//        if (!partie.isCaseSansPiece(coordPlateau[0], coordPlateau[1])) {
//            //CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), "");
//        }
//
//        CssModifier.ChangeBackgroundColor(grille.getChildren().get(coordGrille), "red");
//
//        if (!partie.isCaseSansPiece(coordPlateau[0], coordPlateau[1])) {
//            //CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), partie.getEchiquier().getCase(coordPlateau[0], coordPlateau[1]).getPiece().getImage());
//        }
//    }

//    public void restaurationImageDeplacementPossible() {
//        int x, y, coordGrille;
//        for (Entry coord : listeDeplacements.entrySet()) {
//            coordGrille = (int) coord.getKey();
//            x = ((int[]) coord.getValue())[0];
//            y = ((int[]) coord.getValue())[1];
//
//            if (!partie.isCaseSansPiece(x, y))
//                CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), urlImageDeplacementPossible(coordGrille, x, y));
//        }
//    }

    /**
     * Cette méthode traite le cas du second clique, c'est à dire, de faire déplacer la pièce dans le plateau et d'actualiser l'interface en conséquence
     *
     * @param source : bouton cliqué
     */
    public void TraitementCliqueDeux(Object source) {
        caseArriveeGrille = partie.getNumCaseGrille(decompositionIdBouton(source));

        if (listeDeplacements.containsKey(caseArriveeGrille)) {
            caseArriveePlateau = decompositionIdBouton(source);
            if (roiSelectionne() && (caseArriveePlateau[0] == caseDepartPlateau[0]+2 || caseArriveePlateau[0] == caseDepartPlateau[0]-2))
                roque();
            else
                finDeDeplacement();

            menace = partie.echec();
            if (menace.size()>0){
                System.out.println("ECHEEEC");
                this.echec = true;
                if (partie.echecEtMat(menace))
                    System.out.println("EchecEtMAAAAAAAAAAAAAAAT");
            }
            else
                this.echec = false;

            partie.stockerCoup(caseDepartPlateau, caseArriveePlateau, pieceMangee, partie.getJoueurCourant(), partie.getJoueurNonCourant());
            partie.ChangementJoueurCourant();
        }
    }


    //----------------------------------------------------------------
//    /**
//     * Ré-affecte le style de base des cases de déplacement possible
//     */
//    public void retablissementCouleurCaseDeplacementPossibles() {
//        int coordGrille;
//        int[] coordPlateau;
//
//        for (Entry coord : listeDeplacements.entrySet()) {
//            coordGrille = (int) coord.getKey();
//            coordPlateau = (int[]) coord.getValue();
//
//            if (coordPlateau[1] % 2 == 0) {
//                if (coordPlateau[0] % 2 == 0)
//                    CssModifier.ChangeBackgroundColor(grille.getChildren().get(coordGrille), "white;");
//                else
//                    CssModifier.ChangeBackgroundColor(grille.getChildren().get(coordGrille), "black;");
//            } else {
//                if (coordPlateau[0] % 2 == 1)
//                    CssModifier.ChangeBackgroundColor(grille.getChildren().get(coordGrille), "white;");
//                else
//                    CssModifier.ChangeBackgroundColor(grille.getChildren().get(coordGrille), "black;");
//            }
//
//        }
//    }
//
//    public Piece changementsPlateau() {
//        return partie.actualiserPlateau(caseDepartPlateau, caseArriveePlateau);
//    }
//
//    public void changementsPlateauRoque() { // Déplace la bonne tour par rapport au déplacement fait par le Roi
//        partie.roqueTour(caseArriveePlateau);
//    }
//
//    /**
//     * @param source : bouton cliqué
//     * @return : retourne les coordonnées du PLATEAU correspondant à l'endroit cliqué
//     */
//    public int[] decompositionIdBouton(Object source) {
//        int[] tabCoord = new int[2];
//        String id = source.toString();
//        id = id.substring(10, 12);
//        tabCoord[0] = Integer.parseInt(id.substring(0, 1));
//        tabCoord[1] = Integer.parseInt(id.substring(1));
//        return tabCoord;
//    }
//
//    public String urlImageDeplacementPossible(int i, int x, int y) {
//        return partie.getEchiquier().getCase(x, y).getPiece().getImage();
//    }
//
//    public void finDeDeplacement(){
//        Piece pieceMangee=null;
//        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
//        restaurationImageDeplacementPossible(); // Les cases qui contenaient des pièces les retrouves
//        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseDepartGrille), ""); // La pièce de la case de départ disparaît..
//        pieceMangee = changementsPlateau(); // Le plateau effectue les changements de position
//        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partie.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
//        // Pour arriver sur la case d'arrivée
//        this.pieceMangee = pieceMangee;
//    }

//    /**
//     * Cette méthode récupère les liste des déplacements possibles avec la pièce sélectionnée.
//     *
//     *
//     */
//
//    public void montrerDeplacementDispo() {
//        HashMap<Integer, int[]> listeDeplacements = (!this.echec) ? partie.getDeplacements(caseDepartPlateau[0], caseDepartPlateau[1]) : partie.getDeplacementsEchec(caseDepartPlateau[0], caseDepartPlateau[1], menace);
//        int coordGrille;
//        int[] coordPlateau;
//
//        for (Entry coord : listeDeplacements.entrySet()) { // Pour chaque case dans la liste de déplacements possibles
//            coordGrille = (int) coord.getKey();
//            coordPlateau = (int[]) coord.getValue();
//
//            declarationDeplacementPossible(coordGrille, coordPlateau); // On les met en surbrillance
//        }
//
//        this.listeDeplacements = listeDeplacements;
//    }

    public boolean roiSelectionne(){
        return partie.isRoiSelectionne(caseDepartPlateau);
    }

    public void roque(){
        int xTour;
        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
        restaurationImageDeplacementPossible(); // Les cases qui contenaient des pièces les retrouves
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseDepartGrille), ""); // La pièce de la case de départ disparaît..
        pieceMangee = changementsPlateau(); // Le plateau effectue les changements de position
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partie.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
        // Pour arriver sur la case d'arrivée

        xTour = (caseArriveePlateau[0]==2) ? 0 : 7;

        CssModifier.ChangeBackgroundImage(grille.getChildren().get(partie.getNumCaseGrille(new int[]{xTour, caseArriveePlateau[1]})), ""); // La pièce de la case de départ disparaît..
        changementsPlateauRoque(); // Le plateau effectue les changements de position
        if (caseArriveePlateau[0]==2)
            CssModifier.ChangeBackgroundImage(grille.getChildren().get(partie.getNumCaseGrille(new int[]{caseArriveePlateau[0]+1, caseArriveePlateau[1]})), partie.getEchiquier().getCase(caseArriveePlateau[0]+1, caseArriveePlateau[1]).getPiece().getImage());
        else
            CssModifier.ChangeBackgroundImage(grille.getChildren().get(partie.getNumCaseGrille(new int[]{caseArriveePlateau[0]-1, caseArriveePlateau[1]})), partie.getEchiquier().getCase(caseArriveePlateau[0]-1,  caseArriveePlateau[1]).getPiece().getImage());

        // Pour arriver sur la case d'arrivée
    }
}
