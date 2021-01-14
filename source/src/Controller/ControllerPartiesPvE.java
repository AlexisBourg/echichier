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
    private PartiePvE partiesActuel;

    public ControllerPartiesPvE(){
        super();
        partiesActuel= new PartiePvE();
    }

    @FXML
    public void chargementPlateau() {
        Plateau echiquier = partiesActuel.getEchiquier();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                grille.getChildren().get((8 * (y + 1) - (8 - x))).setOnMouseClicked(mouseEvent -> {
                    switch (NumeroClique(partiesActuel,mouseEvent.getSource())) {
                        case 1 -> {
                            if (!listeDeplacements.isEmpty()) {
                                retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
                                restaurationImageDeplacementPossible(partiesActuel); // Les cases qui contenaient des pièces les retrouves
                            }
                            TraitementCliqueUn(mouseEvent.getSource(), partiesActuel);
                            cliqueUnPasse = true;
                        }
                        case 2 -> {
                            if (cliqueUnPasse) {
                                TraitementCliqueDeuxIA(mouseEvent.getSource());
                                System.out.println("test");
                                partiesActuel.ChangementJoueurCourant();


                                ia = partiesActuel.getIA();
                                deplacementIA(ia);
                                partiesActuel.ChangementJoueurCourant();
                            }
                            cliqueUnPasse = false;
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
     * @param source : bouton cliqué
     */

    public void TraitementCliqueDeuxIA(Object source) {
        caseArriveeGrille = partiesActuel.getNumCaseGrille(decompositionIdBouton(source));
        if (listeDeplacements.containsKey(caseArriveeGrille)) {
            caseArriveePlateau = decompositionIdBouton(source);
            finDeDeplacementIA();
            partiesActuel.stockerCoup(caseDepartPlateau, caseArriveePlateau, pieceMangee, partiesActuel.getJoueurCourant(), partiesActuel.getJoueurNonCourant());

        }
    }

    /**
     * --------------------------------------GESTION DE L'IA------------------------------------------
     **/

    public Piece changementsPlateauIA(){
        return partiesActuel.actualiserPlateauIA(caseDepartPlateau, caseArriveePlateau);
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
        pieceSelectione.setListeDep(partiesActuel.getEchiquier());
        System.out.println("-------------------------------------------------------------");

        while (pieceSelectione.getCouleur()!= Couleur.NOIR || pieceSelectione.getListeDep().isEmpty() || pieceMorte || partiesActuel.isCaseSansPiece(pieceSelectione.getCoordX(),pieceSelectione.getCoordY())) {   //verifie que la piece selectionné puisse se deplacer
            noPiece = genererInt(ia.getPieces().length);
            pieceSelectione = ia.getPieces()[noPiece];


            if (!ia.estPieceMorte(pieceSelectione)){
                pieceMorte=false;
                //System.out.println("test piece pas morte");
                pieceSelectione.setListeDep(partiesActuel.getEchiquier());

            }
        }

        System.out.println("la piece selectionné est : "+ pieceSelectione +" la couleur est : "+pieceSelectione.getCouleur());

        caseDepartPlateau = attributionCoord(pieceSelectione);
        caseDepartGrille = partiesActuel.getNumCaseGrille(caseDepartPlateau);

        caseArriveePlateau = choisirDeplacementPiece(caseDepartPlateau);
        caseArriveeGrille = partiesActuel.getNumCaseGrille(caseArriveePlateau);

        System.out.println("la piece selectionne se deplace vers : x"+ caseArriveePlateau[0]+" y:"+caseArriveePlateau[1]);
        finDeDeplacementIA();
    }

    public void finDeDeplacementIA() {
        changerBackgroundCase(partiesActuel);
        if(partiesActuel.getIndexJoueurCourant()==1){
            changementsPlateau(partiesActuel); // Le plateau effectue les changements de position pour l'ia
        }
        else {
            changementsPlateauIA(); // Le plateau effectue les changements de position
        }
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partiesActuel.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
        // Pour arriver sur la case d'arrivée
        this.pieceMangee = pieceMangee;
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

        listeDeplacements = partiesActuel.getDeplacements(caseDepPLa[0], caseDepPLa[1]);

        noDep = genererInt(64);
        while (!listeDeplacements.containsKey(noDep)) {
            noDep = genererInt(64);
        }
        tabCoord = listeDeplacements.get(noDep);
        return tabCoord;
    }
}
