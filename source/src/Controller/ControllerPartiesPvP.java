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


    private PartiePvP partieActuel;

    public ControllerPartiesPvP(){
        super();
        partieActuel = new PartiePvP();
    }

    @FXML
    public void chargementPlateau() {
        Plateau echiquier = partieActuel.getEchiquier();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                grille.getChildren().get((8 * (y + 1) - (8 - x))).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        switch (NumeroClique((Parties) partieActuel,mouseEvent.getSource())) {
                            case 1:
                                System.out.println("1111111111111");
                                if (!listeDeplacements.isEmpty()) { // Si le clique 1 avait déjà été enclenché
                                    retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
                                    restaurationImageDeplacementPossible((Parties) partieActuel); // Les cases qui contenaient des pièces les retrouves
                                }
                                TraitementCliqueUn(mouseEvent.getSource(), (Parties) partieActuel);
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
        caseArriveeGrille = partieActuel.getNumCaseGrille(decompositionIdBouton(source));

        if (listeDeplacements.containsKey(caseArriveeGrille)) {
            caseArriveePlateau = decompositionIdBouton(source);
            if (roiSelectionne(partieActuel) && (caseArriveePlateau[0] == caseDepartPlateau[0]+2 || caseArriveePlateau[0] == caseDepartPlateau[0]-2))
                roque(partieActuel);
            else
                finDeDeplacement();

            List<Position> menace = partieActuel.echec();
            if (menace.size()>0){
                System.out.println("ECHEEEC");
                this.echec = true;
                if (partieActuel.echecEtMat(menace))
                    System.out.println("EchecEtMAAAAAAAAAAAAAAAT");
            }
            else
                this.echec = false;

            partieActuel.stockerCoup(caseDepartPlateau, caseArriveePlateau, pieceMangee, partieActuel.getJoueurCourant(), partieActuel.getJoueurNonCourant());
            partieActuel.ChangementJoueurCourant();
        }
    }



    public void finDeDeplacement(){
         // Pour arriver sur la case d'arrivée
        pieceMangee=changerBackgroundCase(partieActuel);

    }
}
