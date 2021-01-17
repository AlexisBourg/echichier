package model.parties;

import model.joueur.InterfaceJoueur;
import model.plateau.Plateau;
import model.plateau.Position;
import model.piece.*;

import java.util.LinkedList;
import java.util.List;

public class EchecEtMat {

    //Atribut
    private static final int ROI=12;
    public static final int LIMIT_SUP = 7;
    public static final int LIMIT_INF = 0;


    //Methode
    /**
     * @param joueurAdverse : joueur qui n'a pas joué ce tour-ci
     * @param echiquier : plateau du jeu
     * @return : le fait que le roi adverse soit en situation d'échec ou non
     */
    public static List<Position> echec(InterfaceJoueur joueurAdverse, Plateau echiquier){
        Roi roiAdverse = (Roi)joueurAdverse.getPieces()[ROI];
        System.out.println(roiAdverse);
        int xRoi = roiAdverse.getX(), // On récupère les coordonnées du Roi
                yRoi = roiAdverse.getY();
        return isPieceMenaOrProtecParAutre(xRoi, yRoi, echiquier, 0); // Si le roi est menacé, échec sinon rien
    }


    /**
     *
     * @param joueurAdverse : joueur qui n'a pas joué ce tour-ci
     * @param echiquier : plateau de jeu
     * @param menace : liste des menaces du roi adverse
     * @return : le fait que la partie soit terminée ou non
     */
    public static boolean echecEtMat(InterfaceJoueur joueurAdverse, Plateau echiquier, List<Position> menace){
        Roi roiAdverse = (Roi)joueurAdverse.getPieces()[ROI];
        int xRoi = roiAdverse.getX(), // On récupère les coordonnées du Roi
                yRoi = roiAdverse.getY();
        List<Position> depRoiAdverse = joueurAdverse.getPieces()[ROI].getListeDep(); // Liste de déplacements possibles pour le roi adverse
        List<Position> menacesDeLaMenace;

        if(nombreDeMenace(menace)==1){ // S'i n'y a qu'une menace

            menacesDeLaMenace = isPieceMenaOrProtecParAutre(menace.get(0).getX(), menace.get(0).getY(), echiquier, 0);
            if(menaceEstUnCavalier(menace) && // Si la menace est un cavalier ET
                    roiAdverseBloque(depRoiAdverse) && // Si le roi ne peut plus se déplacer ET
                    nombreDePieceQuiMenaceLaMenace(menacesDeLaMenace) == 0) // Si le cavalier n'est pas menacé
                return true;
            if(!menaceEstUnCavalier(menace)) { // Si la menace n'est pas un cavalier
                if(roiAdverseBloque(depRoiAdverse) && // Si le Roi ne peut plus de se déplacer ET
                        (nombreDePieceQuiMenaceLaMenace(menacesDeLaMenace) == 0 || // Si la menace n'est pas elle même menacée OU
                                isPossibInterpo(joueurAdverse, menace.get(0).getX(), menace.get(0).getY(), echiquier, new LinkedList<>()))) // Si aucune pièce alliée au Roi ne peut s'interposer pour le protéger
                    return true;
                // Que cette même menace est protégée, Echec et mat
                return (roiAdverseAUnSeulDeplacementPossible(depRoiAdverse) && // Si le Roi ne peut se déplacer qu'à un seul endroit
                        depRoiAdverse.contains(menace.get(0))) && // et que cet endroit est l'emplacement de sa seule menace ET
                        (nombreDePieceQuiMenaceLaMenace(menacesDeLaMenace)== 1 &&
                                seulLeRoiAdverseMenaceLaMenace(menacesDeLaMenace, echiquier, xRoi, yRoi)) && // Que sa menace n'est menacée que par lui
                        isPieceMenaOrProtecParAutre(menace.get(0).getX(), menace.get(0).getY(), echiquier, 1).size() > 0;
            }
        }else if(nombreDeMenace(menace)>1){ // S'il y a plusieurs menaces
            return roiAdverseBloque(depRoiAdverse); // Si le roi adverse ne peut plus se déplacer, ECHEC ET MAT
        }
        return false; // Cas où il n'y a aucune menace
    }

    public static boolean roiAdverseBloque(List<Position> depRoiAdverse){
        return (depRoiAdverse.size()==0);
    }

    public static boolean roiAdverseAUnSeulDeplacementPossible(List<Position> depRoiAdverse){
        return (depRoiAdverse.size()==1);
    }

    public static boolean menaceEstUnCavalier(List<Position> menace){
        return menace.get(0).getPiece() instanceof Cavalier;
    }

    public static int nombreDeMenace(List<Position> menace){
        return menace.size();
    }

    public static int nombreDePieceQuiMenaceLaMenace(List<Position> menaceDeLaMenace){
        return menaceDeLaMenace.size();
    }

    public static boolean seulLeRoiAdverseMenaceLaMenace(List<Position> menaceDeLaMenace, Plateau echiquier, int xRoi, int yRoi){
        return menaceDeLaMenace.contains(echiquier.getCase(xRoi, yRoi));
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

    /**
     *
     * @param x : colonne de la case potentiellement menacée
     * @param y : ligne de la case potentiellement menacée
     * @param depX : direction vers laquelle une menace pourrait se trouver
     * @param depY : direction vers laquelle une menace pourrait se trouver
     * @param echiquier plateau du jeu contenant toutes les pièces
     * @param liste : liste qui contiendra l'ensemble des positions qui contiennent une pièce qui menace la position donnée
     * @param option : option qui permet de savoir si on détermine une menace ou une pièce qui protège une position
     */
    private static void depCommun(int x, int y, int depX, int depY, Plateau echiquier, List<Position> liste, int option) {
        int tmpx=x+depX;
        int tmpy=y+depY;

        if(tmpx > LIMIT_SUP || tmpx < LIMIT_INF || tmpy > LIMIT_SUP || tmpy < LIMIT_INF)
            return;

        for (int i=0; i<8;i++){
            if(option==0) {
                if (!echiquier.isCaseSansPiece(echiquier.getCase(tmpx, tmpy))){
                    if (!(echiquier.getCase(tmpx, tmpy).getPiece() instanceof Roi)) {
                        echiquier.getCase(tmpx, tmpy).getPiece().setListeDep(echiquier, tmpx, tmpy);
                    }

                    if (echiquier.getCase(tmpx, tmpy).getPiece().getListeDep().contains(echiquier.getCase(x, y))) {
                            liste.add(echiquier.getCase(tmpx, tmpy));
                        break;
                    }
                }
            }
            else if (option==1){
                if (!echiquier.isCaseSansPiece(echiquier.getCase(tmpx, tmpy))) {
                    //if (!(echiquier.getCase(tmpx, tmpy).getPiece() instanceof Roi)) {
                        echiquier.getCase(tmpx, tmpy).getPiece().setListeProtecDep(echiquier);
                        if (echiquier.getCase(tmpx, tmpy).getPiece().getListeProtecDep().contains(echiquier.getCase(x, y))) {
                            liste.add(echiquier.getCase(tmpx, tmpy));
                            break;
                        }
                    //}
                }
            }
            else if(option==2){
                if (!echiquier.isCaseSansPiece(echiquier.getCase(tmpx, tmpy))){
                    if (!(echiquier.getCase(tmpx, tmpy).getPiece() instanceof Roi)) {
                        echiquier.getCase(tmpx, tmpy).getPiece().setListeDep(echiquier, tmpx, tmpy);
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

    /** Cette méthode permet d'ignorer les cases juste devant un pion lors des restrictions de déplacement du roi
     *
     * @param echiquier : plateau du jeu
     * @param x x de la case menacée
     * @param y y de la case menacée
     * @param tmpX x de la piece menaçante
     * @param tmpY y de la piece menaçante
     * @return  : le fait que la piece qui "menace" la case (x, y) soit un pion ou non
     */
    public static boolean isPionLaMenace(Plateau echiquier, int x, int y, int tmpX, int tmpY){
        if (echiquier.getCase(tmpX, tmpY).getPiece().getCouleur()== Couleur.BLANC){
            return (tmpY == y+2 || tmpY == y+1) && tmpX== x && echiquier.getCase(tmpX, tmpY).getPiece() instanceof Pion;
        }else{
            return (tmpY == y-2 || tmpY == y-1) && tmpX== x && echiquier.getCase(tmpX, tmpY).getPiece() instanceof Pion;
        }
    }

    public static void menaceCavalier(int x, int y, int depX, int depY, Plateau echiquier, List<Position> liste, int option) {
        int tmpX=x+depX;
        int tmpY=y+depY;

        if (tmpX > LIMIT_SUP || tmpX < LIMIT_INF || tmpY > LIMIT_SUP || tmpY < LIMIT_INF || echiquier.isCaseSansPiece(echiquier.getCase(tmpX, tmpY)))
            return;
        // x et y représente la case qui est peut être menacée
        // tmpX et tmpY représente la case qui peut être menace l'autre
        if(option==0){
            if (!(echiquier.getCase(tmpX, tmpY).getPiece() instanceof Roi))
                echiquier.getCase(tmpX, tmpY).getPiece().setListeDep(echiquier, tmpX, tmpY);

            if(echiquier.getCase(tmpX, tmpY).getPiece().getListeDep().contains(echiquier.getCase(x, y))){
                liste.add(echiquier.getCase(tmpX, tmpY));
            }
        }
        else{
            echiquier.getCase(tmpX, tmpY).getPiece().setListeProtecDep(echiquier);
            if(echiquier.getCase(tmpX, tmpY).getPiece().getListeProtecDep().contains(echiquier.getCase(x, y))){
                liste.add(echiquier.getCase(tmpX, tmpY));
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
    public static boolean isPossibInterpo(InterfaceJoueur joueurAdverse, int xMen, int yMen, Plateau echiquier, LinkedList<Position> casesDispo) {
        Roi roiAdverse = (Roi)joueurAdverse.getPieces()[ROI];
        int xRoi = roiAdverse.getX(), // On récupère les coordonnées du Roi
                yRoi = roiAdverse.getY(),
                x = xRoi, y = yRoi;
        List<Position> pieceDispo;

        // On va vérifier toutes les cases entre le roi adverse et sa menace pour voir si une pièce peut s'interposer et donc protéger le roi

        if (xMen > xRoi) { // Si la menace est sur la droite du Roi
            if (yMen > yRoi) { // Si la menace est en bas à droite du Roi
                x += 1;
                y += 1;
                while (x != xMen && y != yMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    for (Position p : pieceDispo) {
                        if (p.getPiece().getCouleur()==joueurAdverse.getCouleur())
                            casesDispo.add(echiquier.getCase(x, y));
                    }
                    x +=1;
                    y +=1;
                }
            }
            if (yMen < yRoi) { // Si la menace est en haut à droite du Roi
                x += 1;
                y -= 1;
                while (x != xMen && y != yMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    for (Position p : pieceDispo) {
                        if (p.getPiece().getCouleur()==joueurAdverse.getCouleur())
                            casesDispo.add(echiquier.getCase(x, y));
                    }
                    x +=1;
                    y -=1;
                }
            }
            if (yMen == yRoi) { // Si à droite du Roi et sur la même ligne
                x += 1;
                while (x != xMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    for (Position p : pieceDispo) {
                        if (p.getPiece().getCouleur()==joueurAdverse.getCouleur())
                            casesDispo.add(echiquier.getCase(x, y));
                    }
                    x += 1;
                }
            }
        } else if (xMen < xRoi) { // Si la menace est sur la gauche du roi
            if (yMen > yRoi) { // Si la menace est en bas à gauche du Roi
                x -= 1;
                y += 1;
                while (x != xMen && y != yMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    for (Position p : pieceDispo) {
                        if (p.getPiece().getCouleur()==joueurAdverse.getCouleur())
                            casesDispo.add(echiquier.getCase(x, y));
                    }
                    x -= 1;
                    y += 1;
                }
            }
            if (yMen < yRoi) { // Si la menace est en haut à gauche du Roi
                x -= 1;
                y -= 1;
                while (x != xMen && y != yMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    for (Position p : pieceDispo) {
                        if (p.getPiece().getCouleur()==joueurAdverse.getCouleur())
                            casesDispo.add(echiquier.getCase(x, y));
                    }
                    x -= 1;
                    y -= 1;
                }
            }

            if (yMen == yRoi) { // Si la menace est à gauche du Roi et sur la même ligne
                x -= 1;
                while (x != xMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    for (Position p : pieceDispo) {
                        if (p.getPiece().getCouleur()==joueurAdverse.getCouleur())
                            casesDispo.add(echiquier.getCase(x, y));
                    }
                    x -= 1;
                }
            }
        } else { // Si la menace est dans la même colonne que le roi
            if (yMen > yRoi) { // Si la menace est sur la même colonne et est plus haute que le Roi
                y += 1;
                while (y != yMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    for (Position p : pieceDispo) {
                        if (p.getPiece().getCouleur()==joueurAdverse.getCouleur())
                            casesDispo.add(echiquier.getCase(x, y));
                    }
                    y += 1;
                }
            }

            if (yMen < yRoi) { // Si la menace est sur la même colonne et est plus basse que le Roi
                y -= 1;
                while (y != yMen) { // Tant que l'on a pas parcouru toutes les cases entre le Roi et la menace
                    pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
                    for (Position p : pieceDispo) {
                        if (p.getPiece().getCouleur()==joueurAdverse.getCouleur())
                            casesDispo.add(echiquier.getCase(x, y));
                    }
                    y -= 1;
                }
            }
        }

        return casesDispo.size() <= 0;
    }

    public static void examinerPiecesAutour(int comportementX, int comportementY, int xRoi, int yRoi, Plateau echiquier, int xMen, int yMen, LinkedList<Position> casesDispo, InterfaceJoueur joueurAdverse){
        List<Position> pieceDispo;
        int diffX = (comportementX>0) ? 1 : -1,
            diffY = (comportementY>0) ? 1 : -1,
            x = xRoi,
            y = yRoi;

        if (comportementX==0)
            diffX=0;
        if (comportementY==0)
            diffY=0;

        x+=diffX;
        y+=diffY;

        while(x!=xMen && y!=yMen){
            pieceDispo = isPieceMenaOrProtecParAutre(x, y, echiquier, 0); // On regarde si la case peut être occupée par une pièce
            for (Position p : pieceDispo) {
                if (p.getPiece().getCouleur()==joueurAdverse.getCouleur())
                    casesDispo.add(echiquier.getCase(x, y));
            }
            x+=diffX;
            y+=diffY;
        }

    }
}
