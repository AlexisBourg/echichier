package model.parties;

import model.coups.PlateauEtat;
import model.joueur.Joueur;
import model.joueur.InterfaceJoueur;
import model.plateau.Plateau;
import model.plateau.Position;
import model.piece.Piece;
import model.piece.Pion;
import model.piece.Roi;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class Parties{

    public static final int NB_JOUEURS = 2;
    public static final int LONGUEUR_EN_CASE = 8;
    public static final int X_TOUR_AVANT_PETIT_ROQUE = 7;
    public static final int X_TOUR_AVANT_GRAND_ROQUE = 0;
    public static final int X_TOUR_APRES_PETIT_ROQUE = 5;
    public static final int X_TOUR_APRES_GRAND_ROQUE = 3;

    //Atribut

    protected int indexJoueurCourant = 0;
    protected final InterfaceJoueur[] joueurs;
    private Plateau echiquier;

    //Constructeur
    public Parties(){
        joueurs = new InterfaceJoueur[NB_JOUEURS];
        joueurs[0] = new Joueur(1); // BLANC
        joueurs[1] = new Joueur(2); // NOIR
        echiquier = new Plateau(joueurs[0].getPieces(), joueurs[1].getPieces());
    }

    //Methode
    public Plateau getEchiquier(){
        return echiquier;
    }

    public void setEchiquier(Plateau plateau){
        this.echiquier = plateau;
    }

    public InterfaceJoueur getJoueur(int num){return  joueurs[num];}

    public int getIndexJoueurCourant() { return indexJoueurCourant; }

    public void setJoueurs(int i,InterfaceJoueur joueurs) {
        this.joueurs[i] = joueurs;
    }

    /** Cette méthode traduit des coordonnées correspondantes au plateau de jeu en des coordonnées correspondantes à la grille de l'interface
     *
     * @param tabCoord : coordonnées plateau
     * @return : la coordonnée grille
     */
    public int getNumCaseGrille(int[] tabCoord){
        return ((LONGUEUR_EN_CASE*(tabCoord[1]+1)-(LONGUEUR_EN_CASE-tabCoord[0])));
    }

    /**
     *
     * @param x : coordonnée x de la case sélectionnée (coordonnée plateau)
     * @param y : coordonnée y de la case sélectionnée (coordonnée plateau)
     * @return : une map contenant en clé : la coordonnée de la grille de la case sélectionnée et en valeur : les coordonnées correspondante pour le plateau
     */
    public HashMap<Integer, int[]> getDeplacements(int x, int y){
        HashMap<Integer, int[]> liste = new HashMap<>();

        if (echiquier.getCase(x, y).getPiece() instanceof Roi) {
            Roi roi = (Roi) echiquier.getCase(x, y).getPiece();
            roi.setListeDep(getEchiquier());
        }
        else
            getEchiquier().getCase(x, y).getPiece().setListeDep(getEchiquier(), x, y);

        for (Position p: getEchiquier().getCase(x, y).getPiece().getListeDep()){
            liste.put(LONGUEUR_EN_CASE*(p.getY()+1)-(LONGUEUR_EN_CASE-p.getX()), new int[]{p.getX(), p.getY()});
        }

        return liste;
    }

    public InterfaceJoueur getJoueurCourant(){
        return getJoueur(indexJoueurCourant);
    }

    public InterfaceJoueur getJoueurNonCourant(){
        return getJoueur((indexJoueurCourant+1)%2);
    }


    /**
     *
     * @param x : colonne de la case
     * @param y : ligne de la case
     * @return : le fait qu'une case comporte une pièce ou non
     */
    public boolean isCaseSansPiece(int x, int y){
        return (getEchiquier().isCaseSansPiece(getEchiquier().getCase(x, y)));
    }

    /**
     *
     * @param x : coordonnée x de la case cliquée (coordonnée plateau)
     * @param y : coordonnée x de la case cliquée (coordonnée plateau)
     * @param joueurCourant : joueur en train de jouer
     * @return : si la case ciblée est libre et si elle contient ou non une pièce appartenant au joueur
     */
    public boolean verifCase(int x, int y, InterfaceJoueur joueurCourant){
        if (isCaseSansPiece(x, y))
            return false;

        return !isPieceSelecAppartientAuJoueurAdverse(x, y, joueurCourant);
    }

    /**
     *
     * @param x : colonne de la case
     * @param y : ligne de la case
     * @param joueurCourant : joueur en train de jouer
     * @return : le fait que la pièce de case appartiennent au joueur non courant ou non
     */
    public boolean isPieceSelecAppartientAuJoueurAdverse(int x, int y, InterfaceJoueur joueurCourant){
        return (getEchiquier().getCase(x, y).getPiece().getCouleur()!=joueurCourant.getCouleur());
    }

    /**
     *
     * @param x : colonne de la case
     * @param y : ligne de la case
     * @param joueurCourant : joueur en train de jouer
     * @return : le fait que la pièce de case appartiennent au joueur courant ou non
     */
    public boolean isPieceSelecAppartientAuJoueurCourant(int x, int y, InterfaceJoueur joueurCourant){
        return (getEchiquier().getCase(x, y).getPiece().getCouleur()==joueurCourant.getCouleur());
    }

    /** Cette méthode déplace la pièce sur le plateau et gère les pièces mangées
     *
     * @param depart : coordonnées plateau de la case de départ
     * @param arrivee : coordonnées plateau de la case d'arrivée
     */
    public Piece actualiserPlateau(int[] depart, int[] arrivee){
        Piece pieceMorte = deplacerPiece(depart, arrivee);
        if(pieceMorte!=null) {
            getJoueur((indexJoueurCourant + 1) % NB_JOUEURS).addPieceMorte(pieceMorte);
        }
        return pieceMorte;
    }

    /** Cette méthode déplace la pièce dont les coordonnées sont données
     *
     * @param depart coordonnées plateau de la case de départ
     * @param arrivee coordonnées plateau de la case d'arrivée
     * @return : la pièce mangée ou null
     */
    public Piece deplacerPiece(int[] depart, int[] arrivee){
        Piece pieceDeplacee, pieceMorte;
        Plateau plateau = getEchiquier();

        pieceMorte = null;
        pieceDeplacee = plateau.getCase(depart[0], depart[1]).getPiece();

        if(plateau.getCase(arrivee[0], arrivee[1]).isOccupe()) { // Si la case d'arrivée est occupée, pieceMorte n'est plus null et prend la valeur de cette dernière
            // pieceMorte ne peut pas être une pièce alliée à celle qui se déplace (cette possibilité est éliminée dans setListeDep de la pièce déplacée)
            pieceMorte = plateau.getCase(arrivee[0], arrivee[1]).getPiece();
        }

        plateau.getCase(arrivee[0], arrivee[1]).setPiece(plateau.getCase(depart[0], depart[1]).getPiece());
        plateau.getCase(depart[0], depart[1]).unsetPiece();

        if (pieceDeplacee instanceof Roi){
            ((Roi) pieceDeplacee).setX(arrivee[0]);
            ((Roi) pieceDeplacee).setY(arrivee[1]);
        }

        if (pieceDeplacee instanceof Pion && ((Pion) pieceDeplacee).getPremierDeplacement())
            ((Pion)pieceDeplacee).setPremierDeplacement();
        if (pieceDeplacee instanceof Roi && ((Roi) pieceDeplacee).getPremierDeplacement()){
            ((Roi)pieceDeplacee).setPremierDeplacement();
        }

        return pieceMorte;
    }

    /**
     * Cette méthode détermine et effectue le déplacement de la tour en fonction de l'arrivée du roi (soit la trou droite, soit la tour gauche)
     * @param arriveeRoi : case d'arrivée du roi après le roque
     */
    public void roqueTour(int[] arriveeRoi){
        int x = arriveeRoi[0];
        int[] arr, dep;
        if (x==2){
            arr= new int[]{X_TOUR_APRES_GRAND_ROQUE, arriveeRoi[1]};
            dep= new int[]{X_TOUR_AVANT_GRAND_ROQUE, arriveeRoi[1]};
        }
        else{
            arr= new int[]{X_TOUR_APRES_PETIT_ROQUE, arriveeRoi[1]};
            dep= new int[]{X_TOUR_AVANT_PETIT_ROQUE, arriveeRoi[1]};
        }
        deplacerPiece(dep, arr);
    }

    /** Cette méthode permet de savoir si et par qui le roi est menacé
     *
     * @return : la liste des pièces qui menacent le roi
     */
    public List<Position> echec(){
        return EchecEtMat.echec(getJoueurNonCourant(), this.getEchiquier());
    }

    /**
     *
     * @param menace : pièce(s) qui menace(nt) le roi
     * @return : le fait que la partie soit terminée ou non
     */
    public boolean echecEtMat(List<Position> menace){
        return EchecEtMat.echecEtMat(getJoueurNonCourant(), this.getEchiquier(), menace);
    }

    /**
     * change le joueur courant
     */
    public void changementJoueurCourant(){
        indexJoueurCourant = (indexJoueurCourant+1)%NB_JOUEURS;
    }

    /**
     *
     * @param caseDepartPlateau : case contenant la pièce qui sera déplacée
     * @return : le fait que la pièce de la case soit un roi ou non
     */
    public boolean isRoiSelectionne(int[] caseDepartPlateau) {
        return (this.getEchiquier().getCase(caseDepartPlateau[0], caseDepartPlateau[1]).getPiece() instanceof Roi);
    }

    /**
     *
     * @param x : colonne de la case
     * @param y : ligne de la case
     * @param menace : menace(s) du roi
     * @return : une collection avec en clé, le numéro du bouton d'arrivée dans la grid et en valeur, les coordonnées du plateau (de type Plateau) de la case d'arrivée
     */
    public HashMap<Integer, int[]> getDeplacementsEchec(int x, int y, List<Position> menace){
        HashMap<Integer, int[]> liste = new HashMap<>();

        if (menace.size()>1)
            return liste;

        if (echiquier.getCase(x, y).getPiece() instanceof Roi) {
            Roi roi = (Roi) echiquier.getCase(x, y).getPiece();
            roi.setListeDep(getEchiquier());
        }
        else
            getEchiquier().getCase(x, y).getPiece().setListeDep(getEchiquier(), x, y); // set la liste de déplacements pour la pièce sélectionnée

        if (!(getEchiquier().getCase(x, y).getPiece() instanceof Roi))
            affinageDeplacements(getEchiquier().getCase(x, y).getPiece(), getEchiquier().getCase(x, y).getPiece().getListeDep(), menace.get(0));

        for (Position p: getEchiquier().getCase(x, y).getPiece().getListeDep()){ // On récupère puis retourne l'ensemble des positions disponibles pour la pièce sélectionnée après avoir retiré les positions qui ne permettent pas de protéger le roi
            liste.put(LONGUEUR_EN_CASE*(p.getY()+1)-(LONGUEUR_EN_CASE-p.getX()), new int[]{p.getX(), p.getY()});
        }

        return liste;
    }

    /** Cette méthode enlève les positions de la liste de déplacement de la pièce sélectionnée si elles ne permettent pas à cette même pièce de s'interposer entre la menace et le roi
     *
     * @param piece : piece sélectionnée
     * @param listeDep : liste de déplacements de la pièce sélectionnée
     * @param m : menace du roi
     */
    public void affinageDeplacements(Piece piece, List<Position> listeDep, Position m) {
        LinkedList<Position> caseDispo = new LinkedList<>();
        LinkedList<Position> newListeDep = new LinkedList<>();

        if (listeDep.contains(m)){
            newListeDep.add(m);
        }

        if(!EchecEtMat.isPossibInterpo(this.getJoueurCourant(), m.getX(), m.getY(), getEchiquier(), caseDispo)){
            listeDep.clear();
        }
        else{
            for (Position p : caseDispo){
                if (listeDep.contains(p))
                    newListeDep.add(p);
            }
        }
        piece.actualiserListeDep(newListeDep);
    }

    /** Cette méthode permet garder en mémoire l'état de l'échiquier à un instant T
     *
     * @return : les informations de l'état courant de l'échiquier
     */
    public PlateauEtat creerEtatPlateau(){
        return new PlateauEtat(this.echiquier);
    }

    /** Cette méthode met à jour l'état du plateau
     *
     * @param plateau : nouvel état du plateau
     */
    public void recupEtat(PlateauEtat plateau){
        this.echiquier.setEtat(plateau);
    }
}
