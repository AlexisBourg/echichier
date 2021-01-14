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
import java.util.Map;
import java.util.Map.Entry;


public class ControllerPartiesPvP  extends ControllerPartie{

    private PartieGraph partiesPvP;
    private List<Position> menace;

    public ControllerPartiesPvP(PartieGraph partie){
        super();
        partiesPvP= partie;
    }

    /**
     * pour les parties en reseau
     */
    public ControllerPartiesPvP(){
        super();
    }

    @FXML
    public void chargementPlateau() {
        Plateau echiquier = partiesPvP.getEchiquier();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                grille.getChildren().get((8 * (y + 1) - (8 - x))).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        switch (NumeroClique(partiesPvP,mouseEvent.getSource())) {
                            case 1:
                                System.out.println("1111111111111");
                                if (!listeDeplacements.isEmpty()) { // Si le clique 1 avait déjà été enclenché
                                    retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
                                    restaurationImageDeplacementPossible(partiesPvP); // Les cases qui contenaient des pièces les retrouves
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

    /**
     * Cette méthode s'occupe de traiter le cas du premier clique, c'est à dire, afficher les cases sur lesquelles la pièce peut se déplacer
     *
     * @param source : bouton cliqué
     */
    public void TraitementCliqueUn(Object source) {
        caseDepartPlateau = decompositionIdBouton(source);
        if (verificationClique(source)) {
            montrerDeplacementDispo();
            caseDepartGrille = partiesPvP.getNumCaseGrille(decompositionIdBouton(source));
        }
    }

    /**
     * Cette méthode indique si la case sélectionnée contient une pièce appartenant au joueur et par conséquent, si l'on peut ou non la déplacer
     *
     * @param source : bouton cliqué
     * @return : le fait que la case fasse partiesPvP du jeu du joueur
     */
    public boolean verificationClique(Object source) {
        return (partiesPvP.verifCase(caseDepartPlateau[0], caseDepartPlateau[1], partiesPvP.getJoueurCourant()));
    }

    public Piece changementsPlateau() {
        return partiesPvP.actualiserPlateau(caseDepartPlateau, caseArriveePlateau);
    }

    public void changementsPlateauRoque() { // Déplace la bonne tour par rapport au déplacement fait par le Roi
        partiesPvP.roqueTour(caseArriveePlateau);
    }

    /**
     * Cette méthode traite le cas du second clique, c'est à dire, de faire déplacer la pièce dans le plateau et d'actualiser l'interface en conséquence
     *
     * @param source : bouton cliqué
     */
    public void TraitementCliqueDeux(Object source) {
        caseArriveeGrille = partiesPvP.getNumCaseGrille(decompositionIdBouton(source));

        if (listeDeplacements.containsKey(caseArriveeGrille)) {
            caseArriveePlateau = decompositionIdBouton(source);
            /*if (roiSelectionne() && (caseArriveePlateau[0] == caseDepartPlateau[0]+2 || caseArriveePlateau[0] == caseDepartPlateau[0]-2))
                roque();
            else*/
                finDeDeplacement();

            List<Position> menace = partiesPvP.echec();
            if (menace.size()>0){
                System.out.println("ECHEEEC");
                this.echec = true;
                if (partiesPvP.echecEtMat(menace))
                    System.out.println("EchecEtMAAAAAAAAAAAAAAAT");
            }
            else
                this.echec = false;

            partiesPvP.stockerCoup(caseDepartPlateau, caseArriveePlateau, pieceMangee, partiesPvP.getJoueurCourant(), partiesPvP.getJoueurNonCourant());
            partiesPvP.ChangementJoueurCourant();
        }
    }



    public void finDeDeplacement(){
        Piece pieceMangee=null;
        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
        restaurationImageDeplacementPossible(partiesPvP); // Les cases qui contenaient des pièces les retrouves
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseDepartGrille), ""); // La pièce de la case de départ disparaît..
        pieceMangee = changementsPlateau(); // Le plateau effectue les changements de position
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partiesPvP.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
        // Pour arriver sur la case d'arrivée
        this.pieceMangee = pieceMangee;
    }




    public void declarationDeplacementPossible(int coordGrille, int[] coordPlateau) {
        if (!partiesPvP.isCaseSansPiece(coordPlateau[0], coordPlateau[1])) {
            //CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), "");
        }

        CssModifier.ChangeBackgroundColor(grille.getChildren().get(coordGrille), "red");

        if (!partiesPvP.isCaseSansPiece(coordPlateau[0], coordPlateau[1])) {
            //CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), partiesPvP.getEchiquier().getCase(coordPlateau[0], coordPlateau[1]).getPiece().getImage());
        }
    }

    /**
     * Cette méthode récupère les liste des déplacements possibles avec la pièce sélectionnée.
     *
     *
     */

    public void montrerDeplacementDispo() {
        HashMap<Integer, int[]> listeDeplacements = (!this.echec) ? partiesPvP.getDeplacements(caseDepartPlateau[0], caseDepartPlateau[1]) : partiesPvP.getDeplacementsEchec(caseDepartPlateau[0], caseDepartPlateau[1], menace);
        int coordGrille;
        int[] coordPlateau;

        for (Map.Entry coord : listeDeplacements.entrySet()) { // Pour chaque case dans la liste de déplacements possibles
            coordGrille = (int) coord.getKey();
            coordPlateau = (int[]) coord.getValue();

            declarationDeplacementPossible(coordGrille, coordPlateau); // On les met en surbrillance
        }

        this.listeDeplacements = listeDeplacements;
    }


    public boolean roiSelectionne(){
        return partiesPvP.isRoiSelectionne(caseDepartPlateau);
    }

    public void roque(){
        int xTour;
        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
        restaurationImageDeplacementPossible(partiesPvP); // Les cases qui contenaient des pièces les retrouves
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseDepartGrille), ""); // La pièce de la case de départ disparaît..
        pieceMangee = changementsPlateau(); // Le plateau effectue les changements de position
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partiesPvP.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
        // Pour arriver sur la case d'arrivée

        xTour = (caseArriveePlateau[0]==2) ? 0 : 7;

        CssModifier.ChangeBackgroundImage(grille.getChildren().get(partiesPvP.getNumCaseGrille(new int[]{xTour, caseArriveePlateau[1]})), ""); // La pièce de la case de départ disparaît..
        changementsPlateauRoque(); // Le plateau effectue les changements de position
        if (caseArriveePlateau[0]==2)
            CssModifier.ChangeBackgroundImage(grille.getChildren().get(partiesPvP.getNumCaseGrille(new int[]{caseArriveePlateau[0]+1, caseArriveePlateau[1]})), partiesPvP.getEchiquier().getCase(caseArriveePlateau[0]+1, caseArriveePlateau[1]).getPiece().getImage());
        else
            CssModifier.ChangeBackgroundImage(grille.getChildren().get(partiesPvP.getNumCaseGrille(new int[]{caseArriveePlateau[0]-1, caseArriveePlateau[1]})), partiesPvP.getEchiquier().getCase(caseArriveePlateau[0]-1,  caseArriveePlateau[1]).getPiece().getImage());

        // Pour arriver sur la case d'arrivée
    }
}
