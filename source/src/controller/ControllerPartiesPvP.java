package controller;

import model.parties.PartiePvP;
import javafx.event.EventHandler;
import model.plateau.Plateau;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import res.interfaceGraphique.CssModifier;


public class ControllerPartiesPvP extends ControllerPartie{

    private PartiePvP partieActuel;

    public ControllerPartiesPvP(){
        super();
        partieActuel = new PartiePvP();
    }

    @FXML
    public void chargementPlateau() {
        coups.setItems(listeCoups);
        Plateau echiquier = partieActuel.getEchiquier();
        editeurCoup.ajoutCoup(partieActuel.creerEtatPlateau());

        arriere.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (editeurCoup.getIndexCourant()>0) {
                    partieActuel.recupEtat(editeurCoup.coupPrecedent());
                    actualiserEtatPlateau(partieActuel);
                    partieActuel.changementJoueurCourant();
                }
            }
        });
        suivant.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (editeurCoup.getIndexCourant()!=(editeurCoup.getNbEtat()-1)){
                    partieActuel.recupEtat(editeurCoup.coupSuivant());
                    actualiserEtatPlateau(partieActuel);
                    partieActuel.changementJoueurCourant();
                }
            }
        });
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                grille.getChildren().get((8 * (y + 1) - (8 - x))).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        switch (NumeroClique(partieActuel, mouseEvent.getSource())) {
                            case 1:
                                if (editeurCoup.getIndexCourant() == editeurCoup.getNbEtat()-1) {
                                    if (!listeDeplacements.isEmpty()) { // Si le clique 1 avait déjà été enclenché
                                        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
                                        restaurationImageDeplacementPossible(partieActuel); // Les cases qui contenaient des pièces les retrouves
                                    }
                                    TraitementCliqueUn(mouseEvent.getSource(), partieActuel);
                                    cliqueUnPasse = true;
                                }
                                break;

                            case 2:
                                if (cliqueUnPasse) {
                                    TraitementCliqueDeux(mouseEvent.getSource(), partieActuel);
                                    editeurCoup.ajoutCoup(partieActuel.creerEtatPlateau());
                                }
                                cliqueUnPasse = false;
                                break;
                        }
                    }
                });
                if (echiquier.getCase(x, y).isOccupe()){
                    CssModifier.ChangeBackgroundImage(grille.getChildren().get((8 * (y + 1) - (8 - x))), echiquier.getCase(x, y).getPiece().getImage());
                }
            }
        }
    }
}
