package controller;

import model.coups.EditeurCoup;
import model.plateau.Position;
import model.parties.Parties;
import model.piece.Piece;
import res.son.Son;
import res.interfaceGraphique.CssModifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ControllerPartie extends ControllerAffichage {

    //Atribut
    public static final int LIMIT_INF=0;
    public static final int LIMIT_SUP=7;
    public static final int CLIQUE_1=1;
    public static final int CLIQUE_2=2;
    public static final int DISTANCE_VERS_TOUR=2;
    protected boolean cliqueUnPasse = false;
    protected int caseDepartGrille;
    protected int[] caseDepartPlateau;
    protected int caseArriveeGrille;
    protected int[] caseArriveePlateau;
    protected Piece pieceMangee;
    protected boolean echec =false;
    protected boolean echecEtMat =false;
    protected List<Position> menace;

    protected ControllerAffichage affichage=new ControllerAffichage();
    protected ControllerSon son= new ControllerSon();
    protected EditeurCoup editeurCoup = new EditeurCoup();

    //Constructeur
    public ControllerPartie(){
        listeDeplacements = new HashMap<>();
    }

    //Methode
    /**
     * @param source : bouton cliqué
     * @return : retourne les coordonnées du PLATEAU correspondant à l'endroit cliqué
     */
    public int[] decompositionIdBouton(Object source) {
        int[] tabCoord = new int[2];
        String id = source.toString();
        id = id.substring(10, 12);
        tabCoord[0] = Integer.parseInt(id.substring(0, 1));
        tabCoord[1] = Integer.parseInt(id.substring(1));
        return tabCoord;
    }

    /**
     * @param source bouton qui a reçcu le clique
     * @return : le numéro du clique (1 si le joueur a sélectionné une pièce de son jeu qu'il veut déplacer, 2 s'il indique où déplacer la pièce de son jeu)
     */
    public int numeroClique(Parties parties, Object source) {
        int[] coord = decompositionIdBouton(source);
        return numClique(coord[0], coord[1], parties);
    }

    /**
     * @param x : coordonnée x du bouton cliqué (coordonnée plateau)
     * @param y : coordonnée y du bouton cliqué (coordonnée plateau)
     * @return : le numéro du clic en fonction des propriétés du bouton cliqué
     */
    public int numClique(int x, int y, Parties parties) {
        if (!parties.isCaseSansPiece(x, y) && parties.isPieceSelecAppartientAuJoueurCourant(x, y, parties.getJoueur(parties.getIndexJoueurCourant()))){
            return CLIQUE_1;
        }
        return CLIQUE_2;
    }

    /**
     * Efface les case de deplacement lorsqu'une autre piéce est selectionné
     * @param parties : est la partie actuel
     */
    public void restaurationImageDeplacementPossible(Parties parties) {
        int x, y, coordGrille;
        for (Map.Entry coord : listeDeplacements.entrySet()) {
            coordGrille = (int) coord.getKey();
            x = ((int[]) coord.getValue())[0];
            y = ((int[]) coord.getValue())[1];

            if (!parties.isCaseSansPiece(x, y))
                CssModifier.changeBackgroundImage(grille.getChildren().get(coordGrille), urlImageDeplacementPossible(x, y, parties));
        }
    }

    /**
     * Permet de remetre l'image d'une piece sur une case pour une pièce pouvait étre déplacé
     * @param x : est l'abscisse de la case à modifier
     * @param y : est l'ordonnée de la case à modifier
     * @param parties : est la partie en cours
     * @return : l'url de l'image de la piece se trouvant sur la case
     */
    public String urlImageDeplacementPossible(int x, int y, Parties parties) {
        return parties.getEchiquier().getCase(x, y).getPiece().getImage();
    }

    /**
     * Cette méthode s'occupe de traiter le cas du premier clique, c'est à dire, afficher les cases sur lesquelles la pièce peut se déplacer
     * @param source : bouton cliqué
     * @param parties : est la partie actuel
     */
    public void traitementCliqueUn(Object source, Parties parties) {
        caseDepartPlateau = decompositionIdBouton(source);
        if (verificationClique(parties)) {
            montrerDeplacementDispo(parties);
            caseDepartGrille = parties.getNumCaseGrille(decompositionIdBouton(source));
        }

    }

    /**
     * Cette méthode traite le cas du second clique, c'est à dire, de faire déplacer la pièce dans le plateau et d'actualiser l'interface en conséquence
     * @param source : bouton cliqué
     */
    public void traitementCliqueDeux(Object source, Parties partieActuel) {

        caseArriveeGrille = partieActuel.getNumCaseGrille(decompositionIdBouton(source));

        if (listeDeplacements.containsKey(caseArriveeGrille)) {
            caseArriveePlateau = decompositionIdBouton(source);
            if (roiSelectionne(partieActuel) && (caseArriveePlateau[0] == caseDepartPlateau[0]+DISTANCE_VERS_TOUR || caseArriveePlateau[0] == caseDepartPlateau[0]-DISTANCE_VERS_TOUR))
                roque(partieActuel);
            else{
                finDeDeplacement(partieActuel);
            }

            menace = partieActuel.echec();
            this.echec = menace.size() > 0;

            if (this.echec && partieActuel.echecEtMat(menace)){
                echecEtMat = true;
                ajoutCoupFin(partieActuel.getJoueurCourant());
            }
            else
                ajoutCoup(caseDepartPlateau, caseArriveePlateau);
//-----
            if(echec){
                System.out.println("echec");
            }
//----
            partieActuel.changementJoueurCourant();
        }
    }

    /**
     * Modifie le plateau après que le joueur ou l'ia est confirmé son déplacement
     * @param partieActuel : est la partie en cours
     */
    public void finDeDeplacement(Parties partieActuel){
        // Pour arriver sur la case d'arrivée
        this.pieceMangee=changerBackgroundCase(partieActuel);
    }

    /**
     * Cette méthode indique si la case sélectionnée contient une pièce appartenant au joueur et par conséquent, si l'on peut ou non la déplacer
     * @param parties : est la partie actuel en cours
     * @return : le fait que la case fasse (Parties) partieActuel du jeu du joueur
     */
    public boolean verificationClique(Parties parties) {
        return parties.verifCase(caseDepartPlateau[0], caseDepartPlateau[1], parties.getJoueurCourant());
    }

    /**
     * Cette méthode récupère les liste des déplacements possibles avec la pièce sélectionnée.
     * @param parties : est la parties actuel en cours
     */
    public void montrerDeplacementDispo(Parties parties) {
        HashMap<Integer, int[]> listeDeplacements = (!this.echec) ? parties.getDeplacements(caseDepartPlateau[0], caseDepartPlateau[1]) : parties.getDeplacementsEchec(caseDepartPlateau[0], caseDepartPlateau[1], menace);
        int coordGrille;

        for (Map.Entry coord : listeDeplacements.entrySet()) { // Pour chaque case dans la liste de déplacements possibles
            coordGrille = (int) coord.getKey();
            declarationDeplacementPossible(coordGrille); // On les met en surbrillance
        }

        this.listeDeplacements = listeDeplacements;
    }

    /**
     * Permet de modifier une case pour la metre en evidance quand la pièce sélectionné peut se déplacer dessus
     * @param coordGrille : est la coordonnée de la grille à metre évidence
     */
    public void declarationDeplacementPossible(int coordGrille) {
        CssModifier.changeBackgroundColor(grille.getChildren().get(coordGrille), "red");
    }

    /**
     * Permet de changer les case d'un plateau quand on déplace d'une pièce
     * @param parties : est la partie en cours
     * @return : la piece qui à été mangé (si mangé)
     */
    public Piece changementsPlateau(Parties parties) {
        return parties.actualiserPlateau(caseDepartPlateau, caseArriveePlateau);
    }

    /**
     * Permet de changer les case d'un plateau quand on éffectu un roque
     * @param parties : est la partie actuelle en cours
     */
    public void changementsPlateauRoque(Parties parties) { // Déplace la bonne tour par rapport au déplacement fait par le Roi
        parties.roqueTour(caseArriveePlateau);
    }

    /**
     * Effectue tout les changements lors de n'importe qu'elle deéplacement de pièce
     * @param partie : est la partie actuelle en cours
     * @return la piece mangé (si mangé)
     */
    public Piece changerBackgroundCase(Parties partie){
        son.jouerSon(Son.MOVEPIECE.getBruit());
        Piece pieceMangee;
        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
        restaurationImageDeplacementPossible(partie); // Les cases qui contenaient des pièces les retrouves
        CssModifier.changeBackgroundImage(grille.getChildren().get(caseDepartGrille), ""); // La pièce de la case de départ disparaît..
        pieceMangee = changementsPlateau(partie); // Le plateau effectue les changements de position
        CssModifier.changeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partie.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
        // Pour arriver sur la case d'arrivée
        return pieceMangee;
    }

    /**
     * Verifie si la piece selectionné est un Roi
     * @param partieActuel : est la partie actuel en cours
     * @return vrai si un roi est sélectionné sinon retourne faux
     */
    public boolean roiSelectionne(Parties partieActuel){
        return  partieActuel.isRoiSelectionne(caseDepartPlateau);
    }

    /**
     * effectuer un roque
     * @param partieActuel : est la partie actuel en cours
     */
    public void roque(Parties partieActuel){
        int xTour;
        retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
        restaurationImageDeplacementPossible(partieActuel); // Les cases qui contenaient des pièces les retrouves
        CssModifier.changeBackgroundImage(grille.getChildren().get(caseDepartGrille), ""); // La pièce de la case de départ disparaît..
        pieceMangee = changementsPlateau(partieActuel); // Le plateau effectue les changements de position
        CssModifier.changeBackgroundImage(grille.getChildren().get(caseArriveeGrille), partieActuel.getEchiquier().getCase(caseArriveePlateau[0], caseArriveePlateau[1]).getPiece().getImage());
        // Pour arriver sur la case d'arrivée

        xTour = (caseArriveePlateau[0]==2) ? LIMIT_INF : LIMIT_SUP;

        CssModifier.changeBackgroundImage(grille.getChildren().get(partieActuel.getNumCaseGrille(new int[]{xTour, caseArriveePlateau[1]})), ""); // La pièce de la case de départ disparaît..
        changementsPlateauRoque(partieActuel); // Le plateau effectue les changements de position
        if (caseArriveePlateau[0]==2)
            CssModifier.changeBackgroundImage(grille.getChildren().get(partieActuel.getNumCaseGrille(new int[]{caseArriveePlateau[0]+1, caseArriveePlateau[1]})), partieActuel.getEchiquier().getCase(caseArriveePlateau[0]+1, caseArriveePlateau[1]).getPiece().getImage());
        else
            CssModifier.changeBackgroundImage(grille.getChildren().get(partieActuel.getNumCaseGrille(new int[]{caseArriveePlateau[0]-1, caseArriveePlateau[1]})), partieActuel.getEchiquier().getCase(caseArriveePlateau[0]-1,  caseArriveePlateau[1]).getPiece().getImage());

        // Pour arriver sur la case d'arrivée
    }

}
