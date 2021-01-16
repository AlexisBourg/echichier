package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.joueur.IA;
import model.parties.EchecEtMat;
import model.plateau.Plateau;
import model.parties.PartiePvE;
import model.piece.Couleur;
import model.piece.Piece;
import javafx.fxml.FXML;
import model.plateau.Position;
import res.interfaceGraphique.CssModifier;

import java.util.List;
import java.util.Random;

public class ControllerPartiesPvE extends ControllerPartie  {

    private IA ia;
    private PartiePvE partieActuel;

    public ControllerPartiesPvE(){
        super();
        partieActuel= new PartiePvE();
    }

    @FXML
    public void chargementPlateau() {
        Plateau echiquier = partieActuel.getEchiquier();
        editeurCoup.ajoutCoup(partieActuel.creerEtatPlateau());

        arriere.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (editeurCoup.getIndexCourant()>0) {
                    partieActuel.recupEtat(editeurCoup.coupPrecedent());
                    actualiserEtatPlateau(partieActuel);
                    partieActuel.ChangementJoueurCourant();
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
                    partieActuel.ChangementJoueurCourant();
                    //System.out.println(partieActuel.getEchiquier().toString());
                }
            }
        });
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                grille.getChildren().get((8 * (y + 1) - (8 - x))).setOnMouseClicked(mouseEvent -> {
                    switch (NumeroClique(partieActuel,mouseEvent.getSource())) {
                        case 1:
                            if (partieActuel.getJoueurCourant().getCouleur()==Couleur.BLANC) {
                                if (!listeDeplacements.isEmpty()) {
                                    retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
                                    restaurationImageDeplacementPossible(partieActuel); // Les cases qui contenaient des pièces les retrouves
                                }
                                TraitementCliqueUn(mouseEvent.getSource(), partieActuel);
                                cliqueUnPasse = true;
                            }
                            break;
                        case 2:
                            if (cliqueUnPasse) {
                                TraitementCliqueDeux(mouseEvent.getSource(),partieActuel);
                                editeurCoup.ajoutCoup(partieActuel.creerEtatPlateau());
                                //System.out.println("test");
                                //partieActuel.ChangementJoueurCourant();
                                //System.out.println("debut ia");
                                ia = partieActuel.getIA();
                                deplacementIA(ia);
                                editeurCoup.ajoutCoup(partieActuel.creerEtatPlateau());
                                partieActuel.ChangementJoueurCourant();
                                System.out.println("joueuer courant " + partieActuel.getIndexJoueurCourant());
                            }
                            cliqueUnPasse = false;

                            break;
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






    /**
     * --------------------------------------GESTION DE L'IA------------------------------------------
     **/


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

        finDeDeplacement(partieActuel);
    }

    public List<Position> IAechec(){
        return EchecEtMat.echec(partieActuel.getJoueurNonCourant(), this.partieActuel.getEchiquier());
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

//    public Piece changementsPlateauIA(){
//        return partieActuel.actualiserPlateauIA(caseDepartPlateau, caseArriveePlateau);
//    }
}
