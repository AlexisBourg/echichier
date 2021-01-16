package model.parties;

import model.coups.PlateauEtat;
import model.joueur.Joueur;
import model.joueur.InterfaceJoueur;
import model.plateau.Plateau;
import model.plateau.Position;
import model.piece.Piece;
import model.piece.Pion;
import model.piece.Roi;
import model.plateau.Plateau;
import model.plateau.Position;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class Parties{
    protected int indexJoueurCourant = 0;
    private final int ROI = 12;
    protected final InterfaceJoueur[] joueurs;
    private Plateau echiquier;
    private LinkedList<model.parties.Coup> listeCoup;

    public Parties(){
        joueurs = new InterfaceJoueur[2];
        joueurs[0] = new Joueur(1); // BLANC
        joueurs[1] = new Joueur(2); // NOIR
        echiquier = new Plateau(joueurs[0].getPieces(), joueurs[1].getPieces());
        listeCoup = new LinkedList<>();
    }

    public Plateau getEchiquier(){
        return echiquier;
    }

    public void setEchiquier(Plateau plateau){
        this.echiquier = plateau;
    }

    public InterfaceJoueur getJoueur(int num){return  joueurs[num];}

    public int getIndexJoueurCourant() { return indexJoueurCourant; }


    public LinkedList<Coup> getListeCoup(){
        return listeCoup;
    }

    public void setJoueurs(int i,InterfaceJoueur joueurs) {
        this.joueurs[i] = joueurs;
    }

    /** Cette méthode traduit des coordonnées correspondantes au plateau de jeu en des coordonnées correspondantes à la grille de l'interface
     *
     * @param tabCoord : coordonnées plateau
     * @return : la coordonnée grille
     */
    public int getNumCaseGrille(int[] tabCoord){
        return ((8*(tabCoord[1]+1)-(8-tabCoord[0])));
    }

    /**
     *
     * @param x : coordonnée x de la case sélectionnée (coordonnée plateau)
     * @param y : coordonnée y de la case sélectionnée (coordonnée plateau)
     * @return : une map contenant en clé : la coordonnée de la grille de la case sélectionnée et en valeur : les coordonnées correspondante pour le plateau
     */
    public HashMap<Integer, int[]> getDeplacements(int x, int y){
        HashMap<Integer, int[]> liste = new HashMap<>();

        getEchiquier().getCase(x, y).getPiece().setListeDep(getEchiquier());

        for (Position p: getEchiquier().getCase(x, y).getPiece().getListeDep()){
            liste.put(8*(p.getY()+1)-(8-p.getX()), new int[]{p.getX(), p.getY()});
        }

        return liste;
    }

    public InterfaceJoueur getJoueurCourant(){
        return getJoueur(indexJoueurCourant);
    }

    public InterfaceJoueur getJoueurNonCourant(){
        return getJoueur((indexJoueurCourant+1)%2);
    }


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

    public boolean isPieceSelecAppartientAuJoueurAdverse(int x, int y, InterfaceJoueur joueurCourant){
        return (getEchiquier().getCase(x, y).getPiece().getCouleur()!=joueurCourant.getCouleur());
    }

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
            getJoueur((indexJoueurCourant + 1) % 2).addPieceMorte(pieceMorte);
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
        System.out.println("Déplacer pièce");
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

        System.out.println(pieceDeplacee+"ddddddddddddddddddddddddddddddddddddd");
        pieceDeplacee.setCoordX(arrivee[0]);
        pieceDeplacee.setCoordY(arrivee[1]);

        if (pieceDeplacee instanceof Pion && ((Pion) pieceDeplacee).getPremierDeplacement())
            ((Pion)pieceDeplacee).setPremierDeplacement();
        if (pieceDeplacee instanceof Roi && ((Roi) pieceDeplacee).getPremierDeplacement()){
            ((Roi)pieceDeplacee).setPremierDeplacement();
            System.out.println("ECHOOOOO");
        }

        return pieceMorte;
    }

    public void roqueTour(int[] arriveeRoi){
        int x = arriveeRoi[0];
        int[] arr, dep;
        if (x==2){
            arr= new int[]{3, arriveeRoi[1]};
            dep= new int[]{0, arriveeRoi[1]};
            Piece pieceMorte = deplacerPiece(dep, arr);
        }
        else{
            arr= new int[]{5, arriveeRoi[1]};
            dep= new int[]{7, arriveeRoi[1]};
            Piece pieceMorte = deplacerPiece(dep, arr);
        }
    }

    public List<Position> echec(){
        return EchecEtMat.echec(getJoueurNonCourant(), this.getEchiquier());
    }

    public boolean echecEtMat(List<Position> menace){
        return EchecEtMat.echecEtMat(getJoueurNonCourant(), this.getEchiquier(), menace);
    }

    /**
     * change le joueur courant
     */
    public void ChangementJoueurCourant(){
        indexJoueurCourant = (indexJoueurCourant+1)%2;
    }

    public boolean isRoiSelectionne(int[] caseDepartPlateau) {
        return (this.getEchiquier().getCase(caseDepartPlateau[0], caseDepartPlateau[1]).getPiece() instanceof Roi);
    }

    public HashMap<Integer, int[]> getDeplacementsEchec(int x, int y, List<Position> menace){
        HashMap<Integer, int[]> liste = new HashMap<>();

        getEchiquier().getCase(x, y).getPiece().setListeDep(getEchiquier());

        if (!(getEchiquier().getCase(x, y).getPiece() instanceof Roi))
            affinageDeplacements(getEchiquier().getCase(x, y).getPiece().getListeDep(), menace);

        for (Position p: getEchiquier().getCase(x, y).getPiece().getListeDep()){
            liste.put(8*(p.getY()+1)-(8-p.getX()), new int[]{p.getX(), p.getY()});
        }

        return liste;
    }

    public void affinageDeplacements(List<Position> listeDep, List<Position> menace) {
        for (Position p : listeDep){ // Pour chaque déplacement possible pour la pièce
            for (Position m : menace){ // Pour chaque menace directe du roi
                if (!m.getPiece().getListeDep().contains(p)) // Si la position possible pour la pièce ne peut pas protéger le roi
                    listeDep.remove(p); // On l'enlève de la liste de ses déplacements
            }
        }
    }

    /** Cette méthode permet garder en mémoire l'état de l'échiquier à un instant T
     *
     * @return : les informations de l'état courant de l'échiquier
     */
    public PlateauEtat creerEtatPlateau(){
        return new PlateauEtat(this.echiquier);
    }

    public void recupEtat(PlateauEtat plateau){
        this.echiquier.setEtat(plateau);
    }
}
