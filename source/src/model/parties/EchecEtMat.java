package model.parties;

import model.joueur.InterfaceJoueur;
import model.plateau.Plateau;
import model.plateau.Position;
import model.piece.*;

import java.util.LinkedList;
import java.util.List;

public class EchecEtMat {

    //Atribut
    private static final int ROI = 12;
    public static final int LIMIT_SUP = 7;
    public static final int LIMIT_INF = 0;
    public static final int OPTION_0 = 0;
    public static final int OPTION_1 = 1;
    public static final int PREMIER_ELEMENT = 0;
    public static final int MINIMUM_NB_MENACE = 1;
    public static final int PREMIER_DEPLACEMENT_PION = 2;
    public static final int DEPLACEMENT_PION = 1;

    //Methode

    /**
     * @param joueurAdverse : joueur qui n'a pas joué ce tour-ci
     * @param echiquier     : plateau du jeu
     * @return : le fait que le roi adverse soit en situation d'échec ou non
     */
    public static List<Position> echec(InterfaceJoueur joueurAdverse, Plateau echiquier) {
        Roi roiAdverse = (Roi) joueurAdverse.getPieces()[ROI];
        int xRoi = roiAdverse.getX(), // On récupère les coordonnées du Roi
                yRoi = roiAdverse.getY();
        return isPieceMenaOrProtecParAutre(xRoi, yRoi, echiquier, OPTION_0); // Si le roi est menacé, échec sinon rien
    }

    /**
     * @param joueurAdverse : joueur qui n'a pas joué ce tour-ci
     * @param echiquier     : plateau de jeu
     * @param menace        : liste des menaces du roi adverse
     * @return : le fait que la partie soit terminée ou non
     */
    public static boolean echecEtMat(InterfaceJoueur joueurAdverse, Plateau echiquier, List<Position> menace) {
        Roi roiAdverse = (Roi) joueurAdverse.getPieces()[ROI];
        int xRoi = roiAdverse.getX(), // On récupère les coordonnées du Roi
                yRoi = roiAdverse.getY();
        List<Position> depRoiAdverse = joueurAdverse.getPieces()[ROI].getListeDep(); // Liste de déplacements possibles pour le roi adverse
        List<Position> menacesDeLaMenace;

        if (nombreDeMenace(menace) == MINIMUM_NB_MENACE) { // S'i n'y a qu'une menace

            menacesDeLaMenace = isPieceMenaOrProtecParAutre(menace.get(PREMIER_ELEMENT).getX(), menace.get(PREMIER_ELEMENT).getY(), echiquier, OPTION_0);
            if (menaceEstUnCavalier(menace) && // Si la menace est un cavalier ET
                    roiAdverseBloque(depRoiAdverse) && // Si le roi ne peut plus se déplacer ET
                    nombreDePieceQuiMenaceLaMenace(menacesDeLaMenace) == 0) // Si le cavalier n'est pas menacé
                return true;
            if (!menaceEstUnCavalier(menace)) { // Si la menace n'est pas un cavalier
                if (roiAdverseBloque(depRoiAdverse) && // Si le Roi ne peut plus de se déplacer ET
                        (nombreDePieceQuiMenaceLaMenace(menacesDeLaMenace) == 0 || // Si la menace n'est pas elle même menacée OU
                                isPossibInterpo(joueurAdverse, menace.get(PREMIER_ELEMENT).getX(), menace.get(PREMIER_ELEMENT).getY(), echiquier, new LinkedList<>()))) // Si aucune pièce alliée au Roi ne peut s'interposer pour le protéger
                    return true;
                // Que cette même menace est protégée, Echec et mat
                return (roiAdverseAUnSeulDeplacementPossible(depRoiAdverse) && // Si le Roi ne peut se déplacer qu'à un seul endroit
                        depRoiAdverse.contains(menace.get(PREMIER_ELEMENT))) && // et que cet endroit est l'emplacement de sa seule menace ET
                        (nombreDePieceQuiMenaceLaMenace(menacesDeLaMenace) == 1 &&
                                seulLeRoiAdverseMenaceLaMenace(menacesDeLaMenace, echiquier, xRoi, yRoi)) && // Que sa menace n'est menacée que par lui
                        isPieceMenaOrProtecParAutre(menace.get(PREMIER_ELEMENT).getX(), menace.get(PREMIER_ELEMENT).getY(), echiquier, OPTION_1).size() > 0;
            }
        } else if (nombreDeMenace(menace) > MINIMUM_NB_MENACE) { // S'il y a plusieurs menaces
            return roiAdverseBloque(depRoiAdverse); // Si le roi adverse ne peut plus se déplacer, ECHEC ET MAT
        }
        return false; // Cas où il n'y a aucune menace
    }

    /**
     * détermine si le roi advairse est bloqué
     *
     * @param depRoiAdverse : est la liste des deplacement possible du roi adverse
     * @return : vrai si le Roi adverse ne peut pas se déplacer, sinon retourne faux
     */
    public static boolean roiAdverseBloque(List<Position> depRoiAdverse) {
        return (depRoiAdverse.size() == 0);
    }

    /**
     * détermine si le roi advairse n'a qu'un seul déplacement
     *
     * @param depRoiAdverse : est la liste des deplacement possible du roi adverse
     * @return : vrai si le Roi adverse n'a qu'un seul déplacement possible, sinon retourne faux
     */
    public static boolean roiAdverseAUnSeulDeplacementPossible(List<Position> depRoiAdverse) {
        return (depRoiAdverse.size() == 1);
    }

    /**
     * détermine si un cavalier est une menace pour le Roi
     *
     * @param menace : est la liste des menace
     * @return : vrai si un cavalier est menaçant, sinon retourne faux
     */
    public static boolean menaceEstUnCavalier(List<Position> menace) {
        return menace.get(0).getPiece() instanceof Cavalier;
    }

    /**
     * determine le nombre de menace pour le Roi
     *
     * @param menace : est la liste de menace
     * @return : retourne la taille de la liste
     */
    public static int nombreDeMenace(List<Position> menace) {
        return menace.size();
    }

    /**
     * Retourne le nombre de piece qui menace le roi
     *
     * @param menaceDeLaMenace : est la liste des menace
     * @return : la taille de la liste
     */
    public static int nombreDePieceQuiMenaceLaMenace(List<Position> menaceDeLaMenace) {
        return menaceDeLaMenace.size();
    }

    /**
     * Determine si seul le roi adverse est une menace
     *
     * @param menaceDeLaMenace : est une liste de pieces qui peuvent manger la menace du Roi
     * @param echiquier        : est l'echiquier de la partie
     * @param xRoi             : est l'abscisse du Roi
     * @param yRoi             : est l'ordonnée du Roi
     * @return : vrai si seul le Roi adverse menace le Roi, sinon retourne faux
     */
    public static boolean seulLeRoiAdverseMenaceLaMenace(List<Position> menaceDeLaMenace, Plateau echiquier, int xRoi, int yRoi) {
        return menaceDeLaMenace.contains(echiquier.getCase(xRoi, yRoi));
    }

    /**
     * Cette fonction vérifie si la case donnée par x et y est dans listePosDep d'une autre pièce du plateau. Autrement dit, elle vérifie si la case donnée est menacée.
     *
     * @param x         : colonne de la case que l'on veut examiner
     * @param y         : ligne de la case que l'on veut examiner
     * @param echiquier : plateau du jeu
     * @param option    : permet de savoir s'il faut déterminer les menaces (0) ou les protecteurs (1)
     * @return : retourne la liste des position contenant des pièces pouvant manger la piece (x, y)
     */
    public static List<Position> isPieceMenaOrProtecParAutre(int x, int y, Plateau echiquier, int option) {

        List<Position> liste = new LinkedList<>();
        int[][] dep = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}, {1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
        int[][] depC = {{1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};

        for (int i = 0; i < 8; i++) {
            // Vérification des lignes et colonnes communes au roi adverse. Si une pièce qui s'y trouve possède la case de la pièce ciblée dans sa liste de déplacement, on ajoute la pièce à la liste des menaces
            depCommun(x, y, dep[i][0], dep[i][1], echiquier, liste, option);
        }

        // Vérification des emplacements cavaliers. Si ces pièces contiennent un cavalier, on ajoute la pièce à la liste des menaces
        for (int i = 0; i < 8; i++) {
            menaceCavalier(x, y, depC[i][0], depC[i][1], echiquier, liste, option);
        }
        return liste;
    }

    /**
     * @param x         : colonne de la case potentiellement menacée
     * @param y         : ligne de la case potentiellement menacée
     * @param depX      : direction vers laquelle une menace pourrait se trouver
     * @param depY      : direction vers laquelle une menace pourrait se trouver
     * @param echiquier : plateau du jeu contenant toutes les pièces
     * @param liste     : liste qui contiendra l'ensemble des positions qui contiennent une pièce qui menace la position donnée
     * @param option    : option qui permet de savoir si on détermine une menace ou une pièce qui protège une position
     */
    private static void depCommun(int x, int y, int depX, int depY, Plateau echiquier, List<Position> liste, int option) {
        int tmpx = x + depX;
        int tmpy = y + depY;

        if (tmpx > LIMIT_SUP || tmpx < LIMIT_INF || tmpy > LIMIT_SUP || tmpy < LIMIT_INF)
            return;

        for (int i = 0; i < 8; i++) {
            if (option == 0) {
                if (!echiquier.isCaseSansPiece(echiquier.getCase(tmpx, tmpy))) {
                    if (!(echiquier.getCase(tmpx, tmpy).getPiece() instanceof Roi)) {
                        echiquier.getCase(tmpx, tmpy).getPiece();
                    }

                    if (echiquier.getCase(tmpx, tmpy).getPiece().getListeDep().contains(echiquier.getCase(x, y))) {
                        liste.add(echiquier.getCase(tmpx, tmpy));
                        break;
                    }
                }
            } else if (option == 1) {
                if (!echiquier.isCaseSansPiece(echiquier.getCase(tmpx, tmpy))) {

                    echiquier.getCase(tmpx, tmpy).getPiece();
                    if (echiquier.getCase(tmpx, tmpy).getPiece().getListeProtecDep().contains(echiquier.getCase(x, y))) {
                        liste.add(echiquier.getCase(tmpx, tmpy));
                        break;
                    }
                }
            } else if (option == 2) {
                if (!echiquier.isCaseSansPiece(echiquier.getCase(tmpx, tmpy))) {
                    if (!(echiquier.getCase(tmpx, tmpy).getPiece() instanceof Roi)) {
                        echiquier.getCase(tmpx, tmpy).getPiece();
                    }

                    if (echiquier.getCase(tmpx, tmpy).getPiece().getListeDep().contains(echiquier.getCase(x, y))) {
                        if (!isPionLaMenace(echiquier, x, y, tmpx, tmpy))
                            liste.add(echiquier.getCase(tmpx, tmpy));
                        break;
                    }
                }
            }
            tmpx += depX;
            tmpy += depY;
            if (tmpx > LIMIT_SUP || tmpx < LIMIT_INF || tmpy > LIMIT_SUP || tmpy < LIMIT_INF)
                return;
        }
    }

    /**
     * Cette méthode permet d'ignorer les cases juste devant un pion lors des restrictions de déplacement du roi
     *
     * @param echiquier : plateau du jeu
     * @param x         x de la case menacée
     * @param y         y de la case menacée
     * @param tmpX      x de la piece menaçante
     * @param tmpY      y de la piece menaçante
     * @return : le fait que la piece qui "menace" la case (x, y) soit un pion ou non
     */
    public static boolean isPionLaMenace(Plateau echiquier, int x, int y, int tmpX, int tmpY) {
        if (echiquier.getCase(tmpX, tmpY).getPiece().getCouleur() == Couleur.BLANC) {
            return (tmpY == y + PREMIER_DEPLACEMENT_PION || tmpY == y + DEPLACEMENT_PION) && tmpX == x && echiquier.getCase(tmpX, tmpY).getPiece() instanceof Pion;
        } else {
            return (tmpY == y - PREMIER_DEPLACEMENT_PION || tmpY == y - DEPLACEMENT_PION) && tmpX == x && echiquier.getCase(tmpX, tmpY).getPiece() instanceof Pion;
        }
    }

    /**
     * @param x         : est l'abscisse de la case qui est peut être menacée
     * @param y         : est l'ordonnée de la case qui est peut être menacée
     * @param depX      : est l'abscisse de déplacement de la pièce
     * @param depY      : est l'ordonnée de déplacement de la pièce
     * @param echiquier : est l'echequier de la artie actuel
     * @param liste     : est la liste des positions
     * @param option    : permet de choisir quelle verificcation effectuer
     */
    public static void menaceCavalier(int x, int y, int depX, int depY, Plateau echiquier, List<Position> liste, int option) {
        int tmpX = x + depX;
        int tmpY = y + depY;

        if (tmpX > LIMIT_SUP || tmpX < LIMIT_INF || tmpY > LIMIT_SUP || tmpY < LIMIT_INF || echiquier.isCaseSansPiece(echiquier.getCase(tmpX, tmpY)))
            return;
        // x et y représente
        // tmpX et tmpY représente la case qui peut être menace l'autre
        if (option == 0) {
            if (!(echiquier.getCase(tmpX, tmpY).getPiece() instanceof Roi))
                echiquier.getCase(tmpX, tmpY).getPiece();

            if (echiquier.getCase(tmpX, tmpY).getPiece().getListeDep().contains(echiquier.getCase(x, y))) {
                liste.add(echiquier.getCase(tmpX, tmpY));
            }
        } else {
            echiquier.getCase(tmpX, tmpY).getPiece();
            if (echiquier.getCase(tmpX, tmpY).getPiece().getListeProtecDep().contains(echiquier.getCase(x, y))) {
                liste.add(echiquier.getCase(tmpX, tmpY));
            }
        }

    }

    /**
     * Regarde si une pièce peut l'interposer entre une menace et le roi
     *
     * @param joueurAdverse : joueur qui n'a pas joué ce tour-ci
     * @param xMen          : colonne de la pièce menaçante
     * @param yMen          : ligne de la pièce menaçante
     * @param echiquier     : plateau du jeu
     * @return : le fait qu'une pièce puisse s'interposer entre le roi et la menace
     */
    public static boolean isPossibInterpo(InterfaceJoueur joueurAdverse, int xMen, int yMen, Plateau echiquier, LinkedList<Position> casesDispo) {
        Roi roiAdverse = (Roi) joueurAdverse.getPieces()[ROI];
        int xRoi = roiAdverse.getX(), // On récupère les coordonnées du Roi
                yRoi = roiAdverse.getY(),
                x = xRoi, y = yRoi;
        // On va vérifier toutes les cases entre le roi adverse et sa menace pour voir si une pièce peut s'interposer et donc protéger le roi
        examinerPiecesAutour((xMen - xRoi), (yMen - yRoi), xRoi, yRoi, echiquier, xMen, yMen, casesDispo, joueurAdverse);

        return casesDispo.size() <= 0;
    }

    /**
     * Permet n'analyser toutes les cases entre la menace et le roi
     *
     * @param comportementX : est la différence entre xMen et xRoi
     * @param comportementY : est la différence entre yMen et yRoi
     * @param xRoi          : est l'abssice du Roi
     * @param yRoi          : est l'ordonnée du Roi
     * @param echiquier     : est l'échiquier de la paratie actuel
     * @param xMen          : est l'abssice de la pièce
     * @param yMen          : est l'ordonnée de la pièce
     * @param casesDispo    : est une liste qui va contenir les cases que les pièces alliées pourront occuper pour s'interposer
     * @param joueurAdverse : est le joueur advairse
     */
    public static void examinerPiecesAutour(int comportementX, int comportementY, int xRoi, int yRoi, Plateau echiquier, int xMen, int yMen, LinkedList<Position> casesDispo, InterfaceJoueur joueurAdverse) {
        List<Position> pieceDispo;
        int diffX = (comportementX > 0) ? 1 : -1,
                diffY = (comportementY > 0) ? 1 : -1,
                x = xRoi,
                y = yRoi;

        if (comportementX == 0)
            diffX = 0;
        if (comportementY == 0)
            diffY = 0;

        x += diffX;
        y += diffY;

        while ((y != yMen) && (x != xMen)) {
            pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, OPTION_0); // On regarde si la case peut être occupée par une pièce
            for (Position p : pieceDispo) {
                if (p.getPiece().getCouleur() == joueurAdverse.getCouleur())
                    casesDispo.add(echiquier.getCase(x, y));
            }
            x += diffX;
            y += diffY;
        }
    }
}
