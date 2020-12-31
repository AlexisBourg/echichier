package Model.Parties;

import Model.Joueur.Joueur;
import Model.PLateau.Plateau;
import Model.PLateau.Position;
import Model.Piece.Piece;
import Model.Piece.Pion;

import java.util.HashMap;

public class PartieGraph extends Parties{
    private int indexJoueurCourant;

    public PartieGraph(){
        super();
        indexJoueurCourant = 0;
    }

    /**
     *
     * @param x : coordonnée x du bouton cliqué (coordonnée plateau)
     * @param y : coordonnée y du bouton cliqué (coordonnée plateau)
     * @return : le numéro du clic en fonction des propriétés du bouton cliqué
     */
    public int numClique(int x, int y){
        if (!isCaseSansPiece(x, y) && isPieceSelecAppartientAuJoueurCourant(x, y, getJoueur(indexJoueurCourant)))
            return 1;
        return 2;
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
     * @param tabCoord : coordonnées plateau
     * @return : l'url de l'image de la case pointée
     */
    public String getUrlImageCase(int[] tabCoord){
        return getEchiquier().getCase(tabCoord[0], tabCoord[1]).getPiece().getImage();
    }

    /**
     *
     * @param x : coordonnée x de la case cliquée (coordonnée plateau)
     * @param y : coordonnée x de la case cliquée (coordonnée plateau)
     * @param joueurCourant : joueur en train de jouer
     * @return : si la case ciblée est libre et si elle contient ou non une pièce appartenant au joueur
     */
    public boolean verifCase(int x, int y, Joueur joueurCourant){
        if (isCaseSansPiece(x, y))
            return false;

        return !isPieceSelecAppartientAuJoueurAdverse(x, y, joueurCourant);
    }

    public Joueur getJoueurCourant(){
        return getJoueur(indexJoueurCourant);
    }

    public Joueur getJoueurNonCourant(){
        return getJoueur((indexJoueurCourant+1)%2);
    }

    /**
     * change le joueur courant
     */
    public void ChangementJoueurCourant(){
        indexJoueurCourant = (indexJoueurCourant+1)%2;
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

    public boolean isCaseSansPiece(int x, int y){
        return (getEchiquier().isCaseSansPiece(getEchiquier().getCase(x, y)));
    }

    public boolean isPieceSelecAppartientAuJoueurAdverse(int x, int y, Joueur joueurCourant){
        return (getEchiquier().getCase(x, y).getPiece().getCouleur()!=joueurCourant.getCouleur());
    }

    public boolean isPieceSelecAppartientAuJoueurCourant(int x, int y, Joueur joueurCourant){
        return (getEchiquier().getCase(x, y).getPiece().getCouleur()==joueurCourant.getCouleur());
    }

    /** Cette méthode déplace la pièce sur le plateau et gère les pièces mangées
     *
     * @param depart : coordonnées plateau de la case de départ
     * @param arrivee : coordonnées plateau de la case d'arrivée
     */
    public void actualiserPlateau(int[] depart, int[] arrivee){
        Piece pieceMorte = deplacerPiece(depart, arrivee);
        if(pieceMorte!=null) {
            getJoueur((indexJoueurCourant + 1) % 2).addPieceMorte(pieceMorte);
        }
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

        pieceDeplacee.setCoordX(arrivee[0]);
        pieceDeplacee.setCoordY(arrivee[1]);

        if (pieceDeplacee instanceof Pion && ((Pion) pieceDeplacee).getPremierDeplacement())
            ((Pion) pieceDeplacee).setPremierDeplacement();

        return pieceMorte;
    }
}
