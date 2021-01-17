package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.joueur.IA;
import model.plateau.Plateau;
import model.parties.PartiePvE;
import model.piece.Couleur;
import model.piece.Piece;
import javafx.fxml.FXML;
import res.interfaceGraphique.CssModifier;

import java.util.Random;

public class ControllerPartiesPvE extends ControllerPartie {

    public static final int LONGUEUR_EN_CASE=8;
    public static final int NB_CASES_GRILLE = 64;
    private IA ia;
    private PartiePvE partieActuel;

    public ControllerPartiesPvE() {
        super();
        partieActuel = new PartiePvE();
    }


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
                    //System.out.println(partieActuel.getEchiquier().toString());
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
                    //System.out.println(partieActuel.getEchiquier().toString());
                }
            }
        });
        for (int y = 0; y < LONGUEUR_EN_CASE; y++) {
            for (int x = 0; x < LONGUEUR_EN_CASE; x++) {
                grille.getChildren().get((LONGUEUR_EN_CASE * (y + 1) - (LONGUEUR_EN_CASE - x))).setOnMouseClicked(mouseEvent -> {
                    switch (numeroClique(partieActuel, mouseEvent.getSource())) {
                        case 1:
                            if (partieActuel.getJoueurCourant().getCouleur() == Couleur.BLANC) {
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
                                System.out.println("joueuer courant " + partieActuel.getIndexJoueurCourant());
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


    /*
      --------------------------------------GESTION DE L'IA------------------------------------------
     */


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


    public void deplacementIA(IA ia) {
        String s;
        Piece pieceSelectione=null;
        int x, y,i=0;
        while (pieceSelectione==null || pieceSelectione.getCouleur() != Couleur.NOIR || pieceSelectione.getListeDep().isEmpty()/* || ia.estPieceMorte(pieceSelectione) */|| partieActuel.isCaseSansPiece(caseDepartPlateau[0], caseDepartPlateau[1])) {
            x = genererInt(LONGUEUR_EN_CASE);
            y =genererInt(LONGUEUR_EN_CASE);
            s = x+""+y;
            System.out.println(s);
            caseDepartPlateau = decompositionIdBoutonIA(s);
            pieceSelectione = partieActuel.getEchiquier().getCase(caseDepartPlateau[0], caseDepartPlateau[1]).getPiece();
            System.out.println("piece select " + partieActuel.getEchiquier().getCase(caseDepartPlateau[0], caseDepartPlateau[1]).getPiece());
        }
    caseDepartGrille =partieActuel.getNumCaseGrille(caseDepartPlateau);

    caseArriveePlateau =choisirDeplacementPiece(caseDepartPlateau);
    caseArriveeGrille =partieActuel.getNumCaseGrille(caseArriveePlateau);

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
//    public void deplacementIA(IA ia){
//        int noPiece = genererInt(ia.getPieces().length);
//        boolean pieceMorte=true;
//        Piece pieceSelectione = ia.getPieces()[noPiece];
//        pieceSelectione.setListeDep(partieActuel.getEchiquier());
//
//        while (pieceSelectione.getCouleur()!= Couleur.NOIR || pieceSelectione.getListeDep().isEmpty() || pieceMorte /*|| partieActuel.isCaseSansPiece(pieceSelectione.getCoordX(),pieceSelectione.getCoordY())*/) {   //verifie que la piece selectionné puisse se deplacer
//            noPiece = genererInt(ia.getPieces().length);
//            pieceSelectione = ia.getPieces()[noPiece];
//
//
//            if (!ia.estPieceMorte(pieceSelectione)){
//                pieceMorte=false;
//                //System.out.println("test piece pas morte");
//                pieceSelectione.setListeDep(partieActuel.getEchiquier());
//
//            }
//        }
//
//        caseDepartPlateau = attributionCoord(partieActuel.get);
//        caseDepartGrille = partieActuel.getNumCaseGrille(caseDepartPlateau);
//
//        caseArriveePlateau = choisirDeplacementPiece(caseDepartPlateau);
//        caseArriveeGrille = partieActuel.getNumCaseGrille(caseArriveePlateau);
//
//        finDeDeplacement(partieActuel);
//    }


//    private int[] attributionCoord(Piece piece) {
//        int[] tabCoord = new int[2];
//
//
//        tabCoord[0] = piece.getCoordX();
//        tabCoord[1] = piece.getCoordY();
//        return tabCoord;
//    }

    public int[] choisirDeplacementPiece(int[] caseDepPLa) {
        int noDep;
        int[] tabCoord ;

        listeDeplacements = partieActuel.getDeplacements(caseDepPLa[0], caseDepPLa[1]);

        noDep = genererInt(NB_CASES_GRILLE);
        while (!listeDeplacements.containsKey(noDep)) {
            noDep = genererInt(NB_CASES_GRILLE);
        }
        tabCoord = listeDeplacements.get(noDep);
        return tabCoord;
    }

}
