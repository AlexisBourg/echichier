package Model.Parties;

import Model.Joueur.InterfaceJoueur;
import Model.Joueur.Joueur;
import Model.PLateau.Plateau;
import Model.PLateau.Position;
import Model.Piece.Cavalier;
import Model.Piece.Piece;

import java.util.LinkedList;
import java.util.List;

public class EchecEtMat {
    private static final int ROI=12;
    public static final int LIMIT_SUP = 7;
    public static final int LIMIT_INF = 0;

    /**
     *
     * @param joueurAdverse : joueur qui n'a pas joué ce tour-ci
     * @param echiquier : plateau du jeu
     * @return : le fait que le roi adverse soit en situation d'échec ou non
     */
    public static boolean echec(InterfaceJoueur joueurAdverse, Plateau echiquier){
        int xRoi = joueurAdverse.getPieces()[ROI].getCoordX(), // On récupère les coordonnées du Roi
                yRoi = joueurAdverse.getPieces()[ROI].getCoordY();
        List<Position> menace= isPieceMenaOrProtecParAutre(xRoi, yRoi, echiquier, 0); // on voit si une ou plusieurs pièces menance(nt) le roi adverse.
        return (menace.size()>0);
    }


    /**
     *
     * @param joueurAdverse : joueur qui n'a pas joué ce tour-ci
     * @param echiquier : plateau de jeu
     * @return : le fait que la partie soit terminée ou non
     */
    public static boolean echecEtMat(InterfaceJoueur joueurAdverse, Plateau echiquier){
        int xRoi = joueurAdverse.getPieces()[ROI].getCoordX(), // On récupère les coordonnées du Roi
                yRoi = joueurAdverse.getPieces()[ROI].getCoordY();
        List<Position> menace= isPieceMenaOrProtecParAutre(xRoi, yRoi, echiquier, 0); // on voit si une ou plusieurs pièces menance(nt) le roi adverse.
        List<Position> DepRoiAdverse = joueurAdverse.getPieces()[ROI].getListeDep(); // Liste de déplacements possibles pour le roi adverse
        List<Position> MenacesDeLaMenace;

        if(menace.size()==1){ // S'i n'y a qu'une menace
            MenacesDeLaMenace = isPieceMenaOrProtecParAutre(menace.get(0).getX(), menace.get(0).getY(), echiquier, 0);
            if(menaceEstUnCavalier(menace) && // Si la menace est un cavalier ET
                    roiAdverseBloque(DepRoiAdverse) && // Si le roi ne peut plus se déplacer ET
                    MenacesDeLaMenace.size() == 0) // Si le cavalier n'est pas menacé
                return true;
            if(!menaceEstUnCavalier(menace)) { // Si la menace n'est pas un cavalier
                if(roiAdverseBloque(DepRoiAdverse) && // Si le Roi ne peut plus de se déplacer ET
                        (MenacesDeLaMenace.size() == 0 || // Si la menace n'est pas elle même menacée OU
                                isPossibInterpo(joueurAdverse, menace.get(0).getX(), menace.get(0).getY(), echiquier))) // Si aucune pièce alliée au Roi ne peut s'interposer pour le protéger
                    return true;
                // Que cette même menace est protégée, Echec et mat
                return (roiAdverseAUnSeulDeplacementPossible(DepRoiAdverse) && // Si le Roi ne peut se déplacer qu'à un seul endroit
                        DepRoiAdverse.contains(menace.get(0))) && // et que cet endroit est l'emplacement de sa seule menace ET
                        (MenacesDeLaMenace.size() == 1 &&
                                MenacesDeLaMenace.contains(echiquier.getCase(xRoi, yRoi))) && // Que sa menace n'est menacée que par lui
                        isPieceMenaOrProtecParAutre(menace.get(0).getX(), menace.get(0).getY(), echiquier, 1).size() > 0;
            }
        }else if(menace.size()>1){ // S'il y a plusieurs menaces
            return roiAdverseBloque(DepRoiAdverse); // Si le roi adverse ne peut plus se déplacer, ECHEC ET MAT
        }
        return false; // Cas où il n'y a aucune menace
    }

    public static boolean roiAdverseBloque(List<Position> DepRoiAdverse){
        return (DepRoiAdverse.size()==0);
    }

    public static boolean roiAdverseAUnSeulDeplacementPossible(List<Position> DepRoiAdverse){
        return (DepRoiAdverse.size()==1);
    }

    public static boolean menaceEstUnCavalier(List<Position> menace){
        return menace.get(0).getPiece() instanceof Cavalier;
    }

    /**
     *  Cette fonction vérifie si la case donnée par x et y est dans listePosDep d'une autre pièce du plateau. Autrement dit, elle vérifie si la case donnée est menacée.
     * @param x : colonne de la case que l'on veut examiner
     * @param y : ligne de la case que l'on veut examiner
     * @param echiquier : plateau du jeu
     * @param option : permet de savoir s'il faut déterminer les menaces (0) ou les protecteurs (1)
     * @return : retourne la liste des position contenant des pièces pouvant manger la piece (x, y)
     */
    public static List<Position> isPieceMenaOrProtecParAutre(int x, int y, Plateau echiquier, int option){
        List<Position> liste = new LinkedList<>();
        int[][] dep={{0,1},{0,-1},{-1,0},{1,0},{1,1},{1,-1},{-1,-1},{-1,1}};
        int[][] depC={{1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2}};

        for(int i=0; i<8; i++) {
            // Vérification des lignes et colonnes communes au roi adverse. Si une pièce qui s'y trouve possède la case de la pièce ciblée dans sa liste de déplacement, on ajoute la pièce à la liste des menaces
            depCommun(x, y, dep[i][0], dep[i][1], echiquier, liste, option);
        }

        // Vérification des emplacements cavaliers. Si ces pièces contiennent un cavalier, on ajoute la pièce à la liste des menaces
        for (int i=0;i<8; i++){
            menaceCavalier(x, y, depC[i][0], depC[i][1], echiquier, liste, option);
        }
        return liste;
    }

    private static void depCommun(int x, int y, int depX, int depY, Plateau echiquier, List<Position> liste, int option) {
        int tmpx=x+depX;
        int tmpy=y+depY;
        //System.out.println(echiquier.toString());

        if(tmpx > LIMIT_SUP || tmpx < LIMIT_INF || tmpy > LIMIT_SUP || tmpy < LIMIT_INF || echiquier.isCaseSansPiece(echiquier.getCase(tmpx, tmpy)))
            return;

        for (int i=0; i<8;i++){
            if(option==0) {
                //echiquier.getCase(tmpx, tmpx).getPiece().setListeDep(echiquier);
                if (echiquier.getCase(tmpx, tmpy).getPiece().getListeDep().contains(echiquier.getCase(x, y))) { // DROITE
                    liste.add(echiquier.getCase(tmpx , tmpy ));
                    break;
                }
            }
            else {
                echiquier.getCase(tmpx, tmpy).getPiece().setListeProtecDep(echiquier);
                if (echiquier.getCase(tmpx, tmpy).getPiece().getListeProtecDep().contains(echiquier.getCase(x, y))) { // DROITE
                    liste.add(echiquier.getCase(tmpx, tmpy));
                    break;
                }
            }

            tmpx += depX;
            tmpy += depY;
            if (tmpx > LIMIT_SUP || tmpx < LIMIT_INF || tmpy > LIMIT_SUP || tmpy < LIMIT_INF || echiquier.isCaseSansPiece(echiquier.getCase(tmpx, tmpy)))
                return;
        }
    }

    private static void menaceCavalier(int x, int y, int depX, int depY, Plateau echiquier, List<Position> liste, int option) {
        int tmpX=x+depX;
        int tmpY=y+depY;

        if (tmpX > LIMIT_SUP || tmpX < LIMIT_INF || tmpY > LIMIT_SUP || tmpY < LIMIT_INF || echiquier.isCaseSansPiece(echiquier.getCase(tmpX, tmpY)))
            return;
        // x et y représente la case qui est peut être menacée
        // tmpX et tmpY représente la case qui peut être menace l'autre
        if(option==0){
            echiquier.getCase(tmpX, tmpY).getPiece().setListeDep(echiquier);
            if(echiquier.getCase(tmpX, tmpY).getPiece().getListeDep().contains(echiquier.getCase(x, y))){
                System.out.println("cava         x:"+(tmpX)+"  y:  "+(tmpY));
                liste.add(echiquier.getCase(x, y));
            }
        }
        else{
            echiquier.getCase(tmpX, tmpY).getPiece().setListeProtecDep(echiquier);;
            if(echiquier.getCase(tmpX, tmpY).getPiece().getListeProtecDep().contains(echiquier.getCase(x, y))){
                liste.add(echiquier.getCase(x, y));
            }
        }

    }

    /**
     *
     * @param joueurAdverse : joueur qui n'a pas joué ce tour-ci
     * @param xMen : colonne de la pièce menaçante
     * @param yMen : ligne de la pièce menaçante
     * @param echiquier : plateau du jeu
     * @return : le fait qu'une pièce puisse s'interposer entre le roi et la menace
     */
    public static boolean isPossibInterpo(InterfaceJoueur joueurAdverse, int xMen, int yMen, Plateau echiquier) {
        int xRoi = joueurAdverse.getPieces()[ROI].getCoordX(), yRoi = joueurAdverse.getPieces()[ROI].getCoordY(), // On récupère les coordonnées du roi adverse.
                x = xRoi, y = yRoi;
        List<Position> pieceDispo;
        int[][] dep={{1,1},{1,-1},{1,0},{-1,1},{-1,-1},{-1,0},{0,1},{0,-1}};

        // On va vérifier toutes les cases entre le roi adverse et sa menace pour voir si une pièce peut s'interposer et donc protéger le roi

        if (xMen > xRoi) { // Si la menace est sur la droite du Roi
            if (yMen > yRoi) { // Si la menace est en bas à droite du Roi
                x += 1;
                y += 1;
                while (x != xMen && y != yMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    if (pieceDispo.size() > 0 && pieceDispo.get(0).getPiece().getCouleur()==joueurAdverse.getCouleur()) // Si la ou une des pièce(s) disponibles sont du même camp que le roi adverse.
                        return true;
                    x = (x < xMen) ? x : x + 1;
                    y = (y < yMen) ? y : y + 1;
                }
            }

            if (yMen < yRoi) { // Si la menace est en haut à droite du Roi
                x += 1;
                y -= 1;
                while (x != xMen && y != yMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    if (pieceDispo.size() > 0 && pieceDispo.get(0).getPiece().getCouleur()==joueurAdverse.getCouleur()) // Si la ou une des pièce(s) disponibles sont du même camp que le roi adverse.
                        return true;
                    x = (x < xMen) ? x : x + 1;
                    y = (y > yMen) ? y : y - 1;
                }
            }

            if (yMen == yRoi) { // Si à droite du Roi et sur la même ligne
                x += 1;
                while (x != xMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    if (pieceDispo.size() > 0 && pieceDispo.get(0).getPiece().getCouleur()==joueurAdverse.getCouleur()) // Si la ou une des pièce(s) disponibles sont du même camp que le roi adverse.
                        return true;
                    x = (x < xMen) ? x : x + 1;
                }
            }
        } else if (xMen < xRoi) { // Si la menace est sur la gauche du roi
            if (yMen > yRoi) { // Si la menace est en bas à gauche du Roi
                x -= 1;
                y += 1;
                while (x != xMen && y != yMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    if (pieceDispo.size() > 0 && pieceDispo.get(0).getPiece().getCouleur()==joueurAdverse.getCouleur()) // Si la ou une des pièce(s) disponibles sont du même camp que le roi adverse.
                        return true;
                    x = (x < xMen) ? x : x - 1;
                    y = (y < yMen) ? y : y + 1;
                }
            }

            if (yMen < yRoi) { // Si la menace est en haut à gauche du Roi
                x -= 1;
                y -= 1;
                while (x != xMen && y != yMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    if (pieceDispo.size() > 0 && pieceDispo.get(0).getPiece().getCouleur()==joueurAdverse.getCouleur()) // Si la ou une des pièce(s) disponibles sont du même camp que le roi adverse.
                        return true;
                    x = (x < xMen) ? x : x - 1;
                    y = (y > yMen) ? y : y - 1;
                }
            }

            if (yMen == yRoi) { // Si la menace est à gauche du Roi et sur la même ligne
                x -= 1;
                while (x != xMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    if (pieceDispo.size() > 0 && pieceDispo.get(0).getPiece().getCouleur()==joueurAdverse.getCouleur()) // Si la ou une des pièce(s) disponibles sont du même camp que le roi adverse.
                        return true;
                    x = (x < xMen) ? x : x - 1;
                }
            }
        } else { // Si la menace est dans la même colonne que le roi
            if (yMen > yRoi) { // Si la menace est sur la même colonne et est plus bas que le Roi
                y += 1;
                while (y != yMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    if (pieceDispo.size() > 0 && pieceDispo.get(0).getPiece().getCouleur()==joueurAdverse.getCouleur()) // Si la ou une des pièce(s) disponibles sont du même camp que le roi adverse.
                        return true;
                    y = (y < yMen) ? y : y + 1;
                }
            }

            if (yMen < yRoi) { // Si la menace est sur la même colonne et est plus haute que le Roi
                y -= 1;
                while (y != yMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    if (pieceDispo.size() > 0 && pieceDispo.get(0).getPiece().getCouleur()==joueurAdverse.getCouleur()) // Si la ou une des pièce(s) disponibles sont du même camp que le roi adverse.
                        return true;
                    y = (y > yMen) ? y : y - 1;
                }
            }
        }

        return false;
    }
}
