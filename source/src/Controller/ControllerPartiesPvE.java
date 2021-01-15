package Controller;

import Model.Joueur.IA;
import Model.PLateau.Plateau;
import Model.Parties.PartiePvE;
import Model.Piece.Couleur;
import Model.Piece.Piece;
import javafx.fxml.FXML;
import res.CssModifier;

import java.util.Random;

public class ControllerPartiesPvE extends ControllerPartie {

    private IA ia;
    private PartiePvE partieActuel;

    public ControllerPartiesPvE(){
        super();
        partieActuel= new PartiePvE();
    }

    @FXML
    public void chargementPlateau() {
        Plateau echiquier = partieActuel.getEchiquier();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                grille.getChildren().get((8 * (y + 1) - (8 - x))).setOnMouseClicked(mouseEvent -> {
                    switch (NumeroClique(partieActuel,mouseEvent.getSource())) {
                        case 1 -> {
                            if (!listeDeplacements.isEmpty()) {
                                retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
                                restaurationImageDeplacementPossible(partieActuel); // Les cases qui contenaient des pièces les retrouves
                            }
                            TraitementCliqueUn(mouseEvent.getSource(), partieActuel);
                            cliqueUnPasse = true;
                            break;
                        }
                        case 2 -> {
                            if (cliqueUnPasse) {
                                TraitementCliqueDeux(mouseEvent.getSource());


                                partieActuel.ChangementJoueurCourant();
                                System.out.println("debut ia");
                                ia = partieActuel.getIA();
                                deplacementIA(ia);
                                partieActuel.ChangementJoueurCourant();
                                System.out.println("joueuer courant" + partieActuel.getIndexJoueurCourant());
                            }
                            cliqueUnPasse = false;

                            break;
                        }
                    }
                });
                if (echiquier.getCase(x, y).isOccupe())
                    CssModifier.ChangeBackgroundImage(grille.getChildren().get((8 * (y + 1) - (8 - x))), echiquier.getCase(x, y).getPiece().getImage());
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
            finDeDeplacement();
        }

    }

    public void finDeDeplacement() {
        pieceMangee=changerBackgroundCase(partieActuel);
        //changementsPlateau(partieActuel); // Le plateau effectue les changements de position pour l'ia

        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partieActuel.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
        // Pour arriver sur la case d'arrivée
    }



    /**
     * --------------------------------------GESTION DE L'IA------------------------------------------
     **/

    /**
     * Cette méthode traite le cas du second clique, c'est à dire, de faire déplacer la pièce dans le plateau et d'actualiser l'interface en conséquence
     * @param source : bouton cliqué
     */

    public void TraitementCliqueDeuxIA(Object source) {
        caseArriveeGrille = partieActuel.getNumCaseGrille(decompositionIdBouton(source));
        if (listeDeplacements.containsKey(caseArriveeGrille)) {
            caseArriveePlateau = decompositionIdBouton(source);
            finDeDeplacementIA();
            partieActuel.stockerCoup(caseDepartPlateau, caseArriveePlateau, pieceMangee, partieActuel.getJoueurCourant(), partieActuel.getJoueurNonCourant());

        }
    }

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

    public void deplacementIA(IA ia){
        int noPiece = genererInt(ia.getPieces().length);
        boolean pieceMorte=true;
        Piece pieceSelectione = ia.getPieces()[noPiece];
        pieceSelectione.setListeDep(partieActuel.getEchiquier());
        System.out.println("-------------------------------------------------------------");

        while (pieceSelectione.getCouleur()!= Couleur.NOIR || pieceSelectione.getListeDep().isEmpty() || pieceMorte || partieActuel.isCaseSansPiece(pieceSelectione.getCoordX(),pieceSelectione.getCoordY())) {   //verifie que la piece selectionné puisse se deplacer
            noPiece = genererInt(ia.getPieces().length);
            pieceSelectione = ia.getPieces()[noPiece];


            if (!ia.estPieceMorte(pieceSelectione)){
                pieceMorte=false;
                //System.out.println("test piece pas morte");
                pieceSelectione.setListeDep(partieActuel.getEchiquier());

            }
        }

        caseDepartPlateau = attributionCoord(pieceSelectione);
        caseDepartGrille = partieActuel.getNumCaseGrille(caseDepartPlateau);

        caseArriveePlateau = choisirDeplacementPiece(caseDepartPlateau);
        caseArriveeGrille = partieActuel.getNumCaseGrille(caseArriveePlateau);

        finDeDeplacement();
    }

    public void finDeDeplacementIA() {

        changerBackgroundCase(partieActuel);

        pieceMangee=changementsPlateauIA(); // Le plateau effectue les changements de position

        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partieActuel.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
        // Pour arriver sur la case d'arrivée

    }

    private int[] attributionCoord(Piece piece) {
        int[] tabCoord = new int[2];
        tabCoord[0] = piece.getCoordX();
        tabCoord[1] = piece.getCoordY();
        return tabCoord;
    }

    public int[] choisirDeplacementPiece(int[] caseDepPLa) {
        int noDep;
        int[] tabCoord ;

        listeDeplacements = partieActuel.getDeplacements(caseDepPLa[0], caseDepPLa[1]);

        noDep = genererInt(64);
        while (!listeDeplacements.containsKey(noDep)) {
            noDep = genererInt(64);
        }
        tabCoord = listeDeplacements.get(noDep);
        return tabCoord;
    }

    public Piece changementsPlateauIA(){
        return partieActuel.actualiserPlateauIA(caseDepartPlateau, caseArriveePlateau);
    }
}
