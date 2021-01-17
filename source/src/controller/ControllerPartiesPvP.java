package controller;

import model.parties.PartiePvP;
import javafx.event.EventHandler;
import model.plateau.Plateau;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import res.interfaceGraphique.CssModifier;

public class ControllerPartiesPvP extends ControllerPartie{

    //Atribut
    public static final int LONGUEUR_EN_CASE=8;
    private PartiePvP partieActuel;

    //Constructeur
    public ControllerPartiesPvP(){
        super();
        partieActuel = new PartiePvP();
    }

    //Methode
    /**
     * permet de charger une partie contre un autre joueur
     */
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
        for (int y = 0; y <LONGUEUR_EN_CASE; y++) {
            for (int x = 0; x <LONGUEUR_EN_CASE; x++) {
                grille.getChildren().get((LONGUEUR_EN_CASE * (y + 1) - (LONGUEUR_EN_CASE - x))).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        switch (numeroClique(partieActuel, mouseEvent.getSource())) {
                            case 1:
                                if (editeurCoup.getIndexCourant() == editeurCoup.getNbEtat()-1) {
                                    if (!listeDeplacements.isEmpty()) { // Si le clique 1 avait déjà été enclenché
                                        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
                                        restaurationImageDeplacementPossible(partieActuel); // Les cases qui contenaient des pièces les retrouves
                                    }
                                    traitementCliqueUn(mouseEvent.getSource(), partieActuel);
                                    cliqueUnPasse = true;
                                }
                                break;

                            case 2:
                                if (cliqueUnPasse) {
                                    traitementCliqueDeux(mouseEvent.getSource(), partieActuel);
                                    editeurCoup.ajoutCoup(partieActuel.creerEtatPlateau());
                                }
                                cliqueUnPasse = false;
                                break;
                        }
                    }
                });
                if (echiquier.getCase(x, y).isOccupe()){
                    CssModifier.changeBackgroundImage(grille.getChildren().get((LONGUEUR_EN_CASE * (y + 1) - (LONGUEUR_EN_CASE - x))), echiquier.getCase(x, y).getPiece().getImage());
                }
            }
        }
    }
}
