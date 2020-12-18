package Model.Parties;

import Model.Joueur.Joueur;
import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.LinkedList;
import java.util.List;

public class EchecEtMat {
    private static final int ROI=12;

    /**
     *
     * @param joueurAdverse : joueur qui n'a pas joué ce tour-ci
     * @param echiquier : plateau de jeu
     * @return : le fait que la partie soit terminée ou non
     */
    public static boolean echecEtMat(Joueur joueurAdverse, Plateau echiquier){
        int xRoi = joueurAdverse.getPieces()[ROI].getCoordX(), // On récupère les coordonnées du Roi
                yRoi = joueurAdverse.getPieces()[ROI].getCoordY();
        List<Position> menace= isPieceMenaOrProtecParAutre(xRoi, yRoi, echiquier, 0); // on voit si une ou plusieurs pièces menance(nt) le roi adverse.
        List<Position> DepRoiAdverse = joueurAdverse.getPieces()[ROI].getListeDep();
        List<Position> MenacesDeLaMenace = isPieceMenaOrProtecParAutre(menace.get(0).getX(), menace.get(0).getY(), echiquier, 0);


        if(menace.size()==1){ // S'i n'y a qu'une menace
            if(menace.get(0).getClass().getName().equals("Cavalier") && // Si la menace est un cavalier ET
                    DepRoiAdverse.size()==0 && // Si le roi ne peut plus se déplacer ET
                    MenacesDeLaMenace.size() == 0) // Si le cavalier n'est pas menacé
                return true;
            if(!menace.get(0).getClass().getName().equals("Cavalier")) { // Si la menace n'est pas un cavalier
                if(DepRoiAdverse.size() == 0 && // Si le Roi ne peut plus de se déplacer ET
                        (MenacesDeLaMenace.size() == 0 || // Si la menace n'est pas elle même menacée OU
                                isPossibInterpo(joueurAdverse, menace.get(0).getX(), menace.get(0).getY(), echiquier))) // Si aucune pièce alliée au Roi ne peut s'interposer pour le protéger
                    return true;
                if((DepRoiAdverse.size()==1 && // Si le Roi ne peut se déplacer qu'à un seul endroit
                        DepRoiAdverse.contains(menace.get(0))) && // et que cet endroit est l'emplacement de sa seule menace ET
                        (MenacesDeLaMenace.size()==1 && // Que sa menace n'est menacée que par lui
                                MenacesDeLaMenace.contains(echiquier.getCasse(xRoi, yRoi))) && // ET
                        isPieceMenaOrProtecParAutre(menace.get(0).getX(), menace.get(0).getY(), echiquier, 1).size()>0) // Que cette même menace est protégée, Echec et mat
                    return true;
            }
        }else if(menace.size()>1){ // S'il y a plusieurs menaces
            return DepRoiAdverse.size() == 0; // Si le roi adverse ne peut plus se déplacer, ECHEC ET MAT
        }
        return false; // Cas où il n'y a aucune menace
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
            depCommun(x+dep[i][0], y+dep[i][1], echiquier, liste, option);

           /* if (echiquier.getCasse(x - i, y).getPiece().getListeDep().contains(echiquier.getCasse(x, y))) { // GAUCHE
                liste.add(echiquier.getCasse(x-i, y));
            }
            if (echiquier.getCasse(x, y + i).getPiece().getListeDep().contains(echiquier.getCasse(x, y))) { // BAS
                liste.add(echiquier.getCasse(x, y+i));
            }
            if (echiquier.getCasse(x, y - i).getPiece().getListeDep().contains(echiquier.getCasse(x, y))) { // HAUT
                liste.add(echiquier.getCasse(x, y - i));
            }

            // Vérification des diagonales communes au roi adverse. Si une pièce qui s'y trouve possède la case de la pièce ciblée dans sa liste de déplacement, on ajoute la pièce à la liste des menaces
            if(echiquier.getCasse(x + i, y + i).getPiece().getListeDep().contains(echiquier.getCasse(x, y))){ // BAS DROITE
                liste.add(echiquier.getCasse(x+i, y+i));
            }

            if(echiquier.getCasse(x + i, y - i).getPiece().getListeDep().contains(echiquier.getCasse(x, y))){ // HAUT DROITE
                liste.add(echiquier.getCasse(x+i, y-i));
            }

            if(echiquier.getCasse(x - i, y + i).getPiece().getListeDep().contains(echiquier.getCasse(x, y))){ // BAS GAUCHE
                liste.add(echiquier.getCasse(x-i, y+i));
            }

            if(echiquier.getCasse(x - i, y - i).getPiece().getListeDep().contains(echiquier.getCasse(x, y))){ // HAUT GAUCHE
                liste.add(echiquier.getCasse(x-i, y-i));
            }*/
        }

        // Vérification des emplacements cavaliers. Si ces pièces contiennent un cavalier, on ajoute la pièce à la liste des menaces
        for (int i=0;i<8; i++){
            menaceCavalier(x+depC[i][0],x+depC[i][1], echiquier, liste, option);
        }



        /*
        if(echiquier.getCasse(x-1, y+2).getPiece().getListeDep().contains(echiquier.getCasse(x, y))){
            liste.add(echiquier.getCasse(x-1, y+2));
        }

        if(echiquier.getCasse(x+1, y-2).getPiece().getListeDep().contains(echiquier.getCasse(x, y))){
            liste.add(echiquier.getCasse(x+1, y-2));
        }

        if(echiquier.getCasse(x-1, y-2).getPiece().getListeDep().contains(echiquier.getCasse(x, y))){
            liste.add(echiquier.getCasse(x-1, y-2));
        }

        if(echiquier.getCasse(x+2, y-1).getPiece().getListeDep().contains(echiquier.getCasse(x, y))){
            liste.add(echiquier.getCasse(x+2, y-1));
        }

        if(echiquier.getCasse(x+2, y+1).getPiece().getListeDep().contains(echiquier.getCasse(x, y))){
            liste.add(echiquier.getCasse(x+2, y+1));
        }

        if(echiquier.getCasse(x-2, y-1).getPiece().getListeDep().contains(echiquier.getCasse(x, y))){
            liste.add(echiquier.getCasse(x-2, y-1));
        }

        if(echiquier.getCasse(x-2, y+1).getPiece().getListeDep().contains(echiquier.getCasse(x, y))){
            liste.add(echiquier.getCasse(x-2, y+1));
        }
        */
        return liste;
    }

    private static void depCommun(int x, int y, Plateau echiquier, List<Position> liste, int option) {
        int tmpx=x;
        int tmpy=y;
        for (int i=0; i<8;i++){
            if(option==0)
                if (echiquier.getCasse(tmpx+x, tmpy+y).getPiece().getListeDep().contains(echiquier.getCasse(x, y))) { // DROITE
                    liste.add(echiquier.getCasse(tmpx + x, tmpy+y));
                    tmpx+=x;
                    tmpy+=y;
                }
                else
                if (echiquier.getCasse(tmpx+x, tmpy+y).getPiece().getListeProtecDep().contains(echiquier.getCasse(x, y))) { // DROITE
                    liste.add(echiquier.getCasse(tmpx + x, tmpy+y));
                    tmpx+=x;
                    tmpy+=y;
                }
        }
    }


    private static void menaceCavalier(int x, int y, Plateau echiquier, List<Position> liste, int option) {
        if(option==0)
            if(echiquier.getCasse(x, y).getPiece().getListeDep().contains(echiquier.getCasse(x, y))){
                liste.add(echiquier.getCasse(x, y));
            }
            else
            if(echiquier.getCasse(x, y).getPiece().getListeProtecDep().contains(echiquier.getCasse(x, y))){
                liste.add(echiquier.getCasse(x, y));
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
    public static boolean isPossibInterpo(Joueur joueurAdverse, int xMen, int yMen, Plateau echiquier) {
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
