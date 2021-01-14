package Controller;

import Model.Joueur.IA;
import Model.PLateau.Plateau;
import Model.Parties.PartieGraph;
import Model.Parties.PartiePvE;
import Model.Parties.Parties;
import Model.Piece.Couleur;
import Model.Piece.Piece;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import res.ChessGrid;
import res.CssModifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ControllerPartiesPvE extends ControllerPartiesPvP{

    private IA ia;
    private PartiePvE partiePvE;

    public ControllerPartiesPvE(PartiePvE partie){
        super();
        partiePvE = partie;
    }

    @FXML
    public void chargementPlateau() {
        Plateau echiquier = partiePvE.getEchiquier();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                grille.getChildren().get((8 * (y + 1) - (8 - x))).setOnMouseClicked(mouseEvent -> {
                    switch (NumeroClique(partiePvE,mouseEvent.getSource())) {
                        case 1 -> {
                            if (!listeDeplacements.isEmpty()) {
                                retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
                                restaurationImageDeplacementPossible(partiePvE); // Les cases qui contenaient des pièces les retrouves
                            }
                            TraitementCliqueUn(mouseEvent.getSource());
                            cliqueUnPasse = true;
                        }
                        case 2 -> {
                            if (cliqueUnPasse) {
                                TraitementCliqueDeux(mouseEvent.getSource());
                                partiePvE.ChangementJoueurCourant();


                                ia = partiePvE.getIA();
                                deplacementIA(ia);
                                partiePvE.ChangementJoueurCourant();
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
     *
     * @param source : bouton cliqué
     */
    public void TraitementCliqueDeux(Object source) {
        caseArriveeGrille = partiePvE.getNumCaseGrille(decompositionIdBouton(source));

        if (listeDeplacements.containsKey(caseArriveeGrille)) {
            caseArriveePlateau = decompositionIdBouton(source);
            finDeDeplacement();
            partiePvE.stockerCoup(caseDepartPlateau, caseArriveePlateau, pieceMangee, partiePvE.getJoueurCourant(), partiePvE.getJoueurNonCourant());

        }
    }

    /**
     * --------------------------------------GESTION DE L'IA------------------------------------------
     **/

    public Piece changementsPlateauIA(){
        return partiePvE.actualiserPlateauIA(caseDepartPlateau, caseArriveePlateau);
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
        pieceSelectione.setListeDep(partiePvE.getEchiquier());
        System.out.println("-------------------------------------------------------------");

        while (pieceSelectione.getCouleur()!= Couleur.NOIR || pieceSelectione.getListeDep().isEmpty() || pieceMorte || partiePvE.isCaseSansPiece(pieceSelectione.getCoordX(),pieceSelectione.getCoordY())) {   //verifie que la piece selectionné puisse se deplacer
            noPiece = genererInt(ia.getPieces().length);
            pieceSelectione = ia.getPieces()[noPiece];


            if (!ia.estPieceMorte(pieceSelectione)){
                pieceMorte=false;
                //System.out.println("test piece pas morte");
                pieceSelectione.setListeDep(partiePvE.getEchiquier());
                //TODO /!\ trouver pourquoi peut faire une boucle infinie
            }
        }

        System.out.println("la piece selectionné est : "+ pieceSelectione +" la couleur est : "+pieceSelectione.getCouleur());

        caseDepartPlateau = attributionCoord(pieceSelectione);
        caseDepartGrille = partiePvE.getNumCaseGrille(caseDepartPlateau);

        caseArriveePlateau = choisirDeplacementPiece(caseDepartPlateau);
        caseArriveeGrille = partiePvE.getNumCaseGrille(caseArriveePlateau);

        System.out.println("la piece selectionne se deplace vers : x"+ caseArriveePlateau[0]+" y:"+caseArriveePlateau[1]);
        finDeDeplacement();
    }

    public void finDeDeplacement() {
        Piece pieceMangee;
        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
        restaurationImageDeplacementPossible(partiePvE); // Les cases qui contenaient des pièces les retrouves
        pieceMangee = changementsPlateau();
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseDepartGrille), ""); // La pièce de la case de départ disparaît..
        if(partiePvE.getIndexJoueurCourant()==1){
            changementsPlateau(); // Le plateau effectue les changements de position pour l'ia
        }
        else {
            changementsPlateauIA(); // Le plateau effectue les changements de position
        }
        CssModifier.ChangeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partiePvE.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
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

        listeDeplacements = partiePvE.getDeplacements(caseDepPLa[0], caseDepPLa[1]);

        noDep = genererInt(64);
        while (!listeDeplacements.containsKey(noDep)) {
            noDep = genererInt(64);
        }
        tabCoord = listeDeplacements.get(noDep);
        return tabCoord;
    }
}
