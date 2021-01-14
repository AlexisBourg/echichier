package Controller;

import Model.PLateau.Position;
import Model.Parties.PartiePvP;
import Model.Parties.Parties;
import Model.Piece.Piece;
import javafx.event.EventHandler;
import Model.PLateau.Plateau;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import res.CssModifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerPartiesPvP extends ControllerPartie{


    private PartiePvP partiesActuel;

    public ControllerPartiesPvP(){
        super();
        partiesActuel = new PartiePvP();
    }

    @FXML
    public void chargementPlateau() {
        Plateau echiquier = partiesActuel.getEchiquier();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                grille.getChildren().get((8 * (y + 1) - (8 - x))).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        switch (NumeroClique((Parties) partiesActuel,mouseEvent.getSource())) {
                            case 1:
                                System.out.println("1111111111111");
                                if (!listeDeplacements.isEmpty()) { // Si le clique 1 avait déjà été enclenché
                                    retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
                                    restaurationImageDeplacementPossible((Parties) partiesActuel); // Les cases qui contenaient des pièces les retrouves
                                }
                                TraitementCliqueUn(mouseEvent.getSource(), (Parties) partiesActuel);
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
     * Cette méthode traite le cas du second clique, c'est à dire, de faire déplacer la pièce dans le plateau et d'actualiser l'interface en conséquence
     *
     * @param source : bouton cliqué
     */
    public void TraitementCliqueDeux(Object source) {
        caseArriveeGrille = partiesActuel.getNumCaseGrille(decompositionIdBouton(source));

        if (listeDeplacements.containsKey(caseArriveeGrille)) {
            caseArriveePlateau = decompositionIdBouton(source);
            if (roiSelectionne() && (caseArriveePlateau[0] == caseDepartPlateau[0]+2 || caseArriveePlateau[0] == caseDepartPlateau[0]-2))
                roque();
            else
                finDeDeplacement();

            List<Position> menace = partiesActuel.echec();
            if (menace.size()>0){
                System.out.println("ECHEEEC");
                this.echec = true;
                if (partiesActuel.echecEtMat(menace))
                    System.out.println("EchecEtMAAAAAAAAAAAAAAAT");
            }
            else
                this.echec = false;

            partiesActuel.stockerCoup(caseDepartPlateau, caseArriveePlateau, pieceMangee, partiesActuel.getJoueurCourant(), partiesActuel.getJoueurNonCourant());
            partiesActuel.ChangementJoueurCourant();
        }
    }



    public void finDeDeplacement(){
         // Pour arriver sur la case d'arrivée
        changerBackgroundCase(partiesActuel);
        this.pieceMangee = pieceMangee;
    }

    public boolean roiSelectionne(){
        return  partiesActuel.isRoiSelectionne(caseDepartPlateau);
    }

    public void roque(){
        int xTour;
        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
        restaurationImageDeplacementPossible(partiesActuel); // Les cases qui contenaient des pièces les retrouves
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseDepartGrille), ""); // La pièce de la case de départ disparaît..
        pieceMangee = changementsPlateau(partiesActuel); // Le plateau effectue les changements de position
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partiesActuel.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
        // Pour arriver sur la case d'arrivée

        xTour = (caseArriveePlateau[0]==2) ? 0 : 7;

        CssModifier.ChangeBackgroundImage(grille.getChildren().get(partiesActuel.getNumCaseGrille(new int[]{xTour, caseArriveePlateau[1]})), ""); // La pièce de la case de départ disparaît..
        changementsPlateauRoque(partiesActuel); // Le plateau effectue les changements de position
        if (caseArriveePlateau[0]==2)
            CssModifier.ChangeBackgroundImage(grille.getChildren().get(partiesActuel.getNumCaseGrille(new int[]{caseArriveePlateau[0]+1, caseArriveePlateau[1]})), partiesActuel.getEchiquier().getCase(caseArriveePlateau[0]+1, caseArriveePlateau[1]).getPiece().getImage());
        else
            CssModifier.ChangeBackgroundImage(grille.getChildren().get(partiesActuel.getNumCaseGrille(new int[]{caseArriveePlateau[0]-1, caseArriveePlateau[1]})), partiesActuel.getEchiquier().getCase(caseArriveePlateau[0]-1,  caseArriveePlateau[1]).getPiece().getImage());

        // Pour arriver sur la case d'arrivée
    }
}
