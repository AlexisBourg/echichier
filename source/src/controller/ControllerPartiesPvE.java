package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import model.plateau.Plateau;
import model.parties.PartiePvE;
import model.piece.Couleur;
import model.joueur.IA;
import model.piece.Piece;
import javafx.fxml.FXML;
import res.interfaceGraphique.CssModifier;

import java.util.Random;

public class ControllerPartiesPvE extends ControllerPartie {

    //Attribue
    public static final int LONGUEUR_EN_CASE = 8;
    public static final int NB_CASES_GRILLE = 64;
    private PartiePvE partieActuel;
    private IA ia;

    //Constructeur
    public ControllerPartiesPvE() {
        super();
        partieActuel = new PartiePvE();
    }


    //Methode

    /**
     * permet de charger une partie contre un ordinateur
     */
    @FXML
    public void chargementPlateau() {
        Plateau echiquier = partieActuel.getEchiquier();
        coups.setItems(listeCoups);
        editeurCoup.ajoutCoup(partieActuel.creerEtatPlateau());
        arriere.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (editeurCoup.getIndexCourant() > 0) {
                    partieActuel.recupEtat(editeurCoup.coupPrecedent());
                    editeurCoup.enleverCoup();
                    actualiserEtatPlateau(partieActuel);
                    partieActuel.changementJoueurCourant();
                    enleverCoup();
                }
            }
        });
        suivant.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (editeurCoup.getIndexCourant() != (editeurCoup.getNbEtat() - 1)) {
                    partieActuel.recupEtat(editeurCoup.coupSuivant());
                    actualiserEtatPlateau(partieActuel);
                    partieActuel.changementJoueurCourant();
                    ;
                }
            }
        });
        for (int y = 0; y < LONGUEUR_EN_CASE; y++) {
            for (int x = 0; x < LONGUEUR_EN_CASE; x++) {
                grille.getChildren().get((LONGUEUR_EN_CASE * (y + 1) - (LONGUEUR_EN_CASE - x))).setOnMouseClicked(mouseEvent -> {
                    switch (numeroClique(partieActuel, mouseEvent.getSource())) {
                        case 1:
                            if (partieActuel.getJoueurCourant().getCouleur() == Couleur.BLANC && !echecEtMat) {
                                if (!listeDeplacements.isEmpty()) {
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
                                ia = partieActuel.getIA();
                                deplacementIA(ia);
                                editeurCoup.ajoutCoup(partieActuel.creerEtatPlateau());
                                ajoutCoup(caseDepartPlateau, caseArriveePlateau);
                                partieActuel.changementJoueurCourant();
                            }
                            cliqueUnPasse = false;

                            break;
                    }
                });
                if (echiquier.getCase(x, y).isOccupe())
                    CssModifier.changeBackgroundImage(grille.getChildren().get((LONGUEUR_EN_CASE * (y + 1) - (LONGUEUR_EN_CASE - x))), echiquier.getCase(x, y).getPiece().getImage());
            }
        }
    }

    public void enleverCoup() {
        listeCoups.remove(listeCoups.size() - 1);
    }


    /**-----------------------------------GESTION DE L'IA-----------------------------------------*/


    /**
     * permet de généré un nombre entre 0 et celui entré en paramètre
     *
     * @param borneSup est le nombre de piece encore "vivante"
     * @return le nombre généré
     */
    int genererInt(int borneSup) {
        Random random = new Random();
        int nb, borneInf = 0;
        nb = borneInf + random.nextInt(borneSup - borneInf);
        return nb;
    }

    /**
     * Effectue un déplacement de l'IA
     */
    public void deplacementIA(IA ia) {
        Piece pieceSelectione = null;
        String s;
        int x = genererInt(LONGUEUR_EN_CASE), y = genererInt(LONGUEUR_EN_CASE);
        //caseDepartPlateau = decompositionIdBoutonIA(s);
        pieceSelectione = partieActuel.getEchiquier().getCase(x, y).getPiece();
        if (pieceSelectione != null)
            pieceSelectione.setListeDep(partieActuel.getEchiquier(), x, y);

        while (pieceSelectione == null || pieceSelectione.getCouleur() != Couleur.NOIR || pieceSelectione.getListeDep().isEmpty() || partieActuel.isCaseSansPiece(x, y)) {
            x = genererInt(LONGUEUR_EN_CASE);
            y = genererInt(LONGUEUR_EN_CASE);
            s = x + "" + y;
            caseDepartPlateau = decompositionIdBoutonIA(s);
            pieceSelectione = partieActuel.getEchiquier().getCase(x, y).getPiece();
            if (pieceSelectione != null)
                pieceSelectione.setListeDep(partieActuel.getEchiquier(), x, y);

            System.out.println(x + "" + y);
            //System.out.println(pieceSelectione.toString()+"  ");
        }
        caseDepartGrille = partieActuel.getNumCaseGrille(caseDepartPlateau);

        caseArriveePlateau = choisirDeplacementPiece(caseDepartPlateau);
        caseArriveeGrille = partieActuel.getNumCaseGrille(caseArriveePlateau);

        finDeDeplacement(partieActuel);

    }

    /**
     * @param source : bouton cliqué
     * @return : retourne les coordonnées du PLATEAU correspondant à l'endroit cliqué
     */
    public int[] decompositionIdBoutonIA(Object source) {
        int[] tabCoord = new int[2];
        String id = source.toString();
        tabCoord[0] = Integer.parseInt(id.substring(0, 1));
        tabCoord[1] = Integer.parseInt(id.substring(1));
        return tabCoord;
    }

    /**
     * Permet de choisr un deplacement au hazard
     *
     * @param caseDepartPLateau : est la case de depart de la piece selectionné
     * @return la case d'arrivé de la pièce
     */
    public int[] choisirDeplacementPiece(int[] caseDepartPLateau) {
        int noDep;
        int[] tabCoord;

        listeDeplacements = partieActuel.getDeplacements(caseDepartPLateau[0], caseDepartPLateau[1]);

        noDep = genererInt(NB_CASES_GRILLE);
        while (!listeDeplacements.containsKey(noDep)) {
            noDep = genererInt(NB_CASES_GRILLE);
        }
        tabCoord = listeDeplacements.get(noDep);
        return tabCoord;
    }
}
