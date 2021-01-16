package controller;

import javafx.scene.control.ListCell;
import model.parties.PartiePvP;
import model.parties.Parties;
import javafx.event.EventHandler;
import model.plateau.Plateau;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import res.interfaceGraphique.CssModifier;

import java.util.HashMap;
import java.util.Map;

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
        System.out.println(editeurCoup.getIndexCourant());
        //System.out.println(partieActuel.getEchiquier().toString());

        arriere.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (editeurCoup.getIndexCourant()>0) {
                    partieActuel.recupEtat(editeurCoup.coupPrecedent());
                    actualiserEtatPlateau(partieActuel);
                    //System.out.println(partieActuel.getEchiquier().toString());
                }
            }
        });
        suivant.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (editeurCoup.getIndexCourant()!=(editeurCoup.getNbEtat()-1)){
                    partieActuel.recupEtat(editeurCoup.coupSuivant());
                    actualiserEtatPlateau(partieActuel);
                    //System.out.println(partieActuel.getEchiquier().toString());
                }
            }
        });
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                grille.getChildren().get((8 * (y + 1) - (8 - x))).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        switch (NumeroClique((Parties) partieActuel,mouseEvent.getSource())) {
                            case 1:
                                if (editeurCoup.getIndexCourant() == editeurCoup.getNbEtat()-1) {
                                    if (!listeDeplacements.isEmpty()) { // Si le clique 1 avait déjà été enclenché
                                        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
                                        restaurationImageDeplacementPossible((Parties) partieActuel); // Les cases qui contenaient des pièces les retrouves
                                    }
                                    TraitementCliqueUn(mouseEvent.getSource(), (Parties) partieActuel);
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
                    //CssModifier.test((Button)grille.getChildren().get((8 * (y + 1) - (8 - x))), echiquier.getCase(x, y).getPiece().getImage());
                }
            }
        }
    }

    public void declarationDeplacementPossible(int coordGrille, int[] coordPlateau) {
        if (!partieActuel.isCaseSansPiece(coordPlateau[0], coordPlateau[1])) {
            //CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), "");
        }

        CssModifier.ChangeBackgroundColor(grille.getChildren().get(coordGrille), "red");

        if (!partieActuel.isCaseSansPiece(coordPlateau[0], coordPlateau[1])) {
            //CssModifier.ChangeBackgroundImage(grille.getChildren().get(coordGrille), partiesPvP.getEchiquier().getCase(coordPlateau[0], coordPlateau[1]).getPiece().getImage());
        }
    }

    /**
     * Cette méthode récupère les liste des déplacements possibles avec la pièce sélectionnée.
     *
     *
     */

    public void montrerDeplacementDispo() {
        HashMap<Integer, int[]> listeDeplacements = (!this.echec) ? partieActuel.getDeplacements(caseDepartPlateau[0], caseDepartPlateau[1]) : partieActuel.getDeplacementsEchec(caseDepartPlateau[0], caseDepartPlateau[1], menace);
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
        return  partieActuel.isRoiSelectionne(caseDepartPlateau);
    }

    public void roque() {
        int xTour;
        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
        restaurationImageDeplacementPossible(partieActuel); // Les cases qui contenaient des pièces les retrouves
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseDepartGrille), ""); // La pièce de la case de départ disparaît..
        pieceMangee = changementsPlateau(partieActuel); // Le plateau effectue les changements de position
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partieActuel.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
        // Pour arriver sur la case d'arrivée

        xTour = (caseArriveePlateau[0] == 2) ? 0 : 7;

        CssModifier.ChangeBackgroundImage(grille.getChildren().get(partieActuel.getNumCaseGrille(new int[]{xTour, caseArriveePlateau[1]})), ""); // La pièce de la case de départ disparaît..
        changementsPlateauRoque(partieActuel); // Le plateau effectue les changements de position
        if (caseArriveePlateau[0] == 2)
            CssModifier.ChangeBackgroundImage(grille.getChildren().get(partieActuel.getNumCaseGrille(new int[]{caseArriveePlateau[0] + 1, caseArriveePlateau[1]})), partieActuel.getEchiquier().getCase(caseArriveePlateau[0] + 1, caseArriveePlateau[1]).getPiece().getImage());
        else
            CssModifier.ChangeBackgroundImage(grille.getChildren().get(partieActuel.getNumCaseGrille(new int[]{caseArriveePlateau[0] - 1, caseArriveePlateau[1]})), partieActuel.getEchiquier().getCase(caseArriveePlateau[0] - 1, caseArriveePlateau[1]).getPiece().getImage());

        // Pour arriver sur la case d'arrivée
        pieceMangee = changerBackgroundCase(partieActuel);

    }
}
