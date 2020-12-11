package Model.Parties;

import Controller.ControllerPlateau;
import Model.Joueur.Joueur;
import Model.PLateau.Plateau;
import Model.PLateau.Position;
import Model.Piece.Couleur;
import Model.Piece.Piece;

public class Parties {
    private final int ROI = 11;
    private Plateau echiquier;
    private Joueur[] joueurs;

    public Parties(){
        joueurs = new Joueur[2];
        joueurs[0] = new Joueur(1);
        joueurs[1] = new Joueur(2);
        echiquier = new Plateau(joueurs[0].getPieces(), joueurs[1].getPieces());
    }

    public void partieLocal(){
        ControllerPlateau controller = new ControllerPlateau();
        Piece pieceDéplacée;
        int i=0;
        controller.chargementPlateau(echiquier);

        /*while(!echecEtMat()){
            Joueur joueurCourant=joueurs[i];
            Joueur joueurNonCourant=joueurs[(i+1)%2];

            System.out.println("Tour du joueur "+joueurCourant.getCouleur()+":\n");

            //selectionne une case de depart

            //selectionne une case d'arrivé

            //doDeplacement(echiquier,controller,joueur);
            //if(pieceDéplacée.getListeDep().get

            i=(i+1)%2;
        }*/
    }

    public Plateau getEchiquier(){
        return echiquier;
    }

    public Boolean echecEtMat(){
        return false;
    }

    /**
     *
     * @param pieceDep // Pièce qui vient d'être déplacée dans la partie
     * @param joueurAdverse // Joueur qui n'a pas joué ce tour ci
     * @return // le fait que le roi soit menacé par la pièce récemment déplacée ou non
     */
    public Boolean isRoiDansListeDep(Piece pieceDep, Joueur joueurAdverse){
        for (Position position : pieceDep.getListeDep()){ // Pour chaque position dans la liste des déplacements possibles de la pièce qui vient d'être déplacée
            if(position.getPiece().equals(joueurAdverse.getPieces()[ROI])) // Si cette liste contient le roi adverse
                return true;
        }
        return false;
    }

    /**
     *
     * @param x // coordonnée x de la pièce avant qu'elle se déplace
     * @param y // coordonnée y de la pièce avant qu'elle se déplace
     * @param joueurAdverse // Joueur qui n'a pas joué ce tour ci
     * @return // Le fait que le roi soit menacé par une autre pièce après que celle qui était sélectionnée se soit déplacée
     */
    public Boolean isRoiMenaParAutre(int x, int y, Joueur joueurAdverse){
        int LIGNE = 0, COLONNE = 1, DIAG = 2, typeLien=9,
        xRoi = joueurAdverse.getPieces()[ROI].getCoordX(),
        yRoi = joueurAdverse.getPieces()[ROI].getCoordY();

        if(x == xRoi) typeLien = LIGNE;
        if(y == yRoi) typeLien = COLONNE;
        //if()
        return true;
    }
}
