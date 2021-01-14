package Model.Parties;


import Model.Joueur.InterfaceJoueur;

import Model.Joueur.Joueur;
import Model.PLateau.Plateau;
import Model.PLateau.Position;
import Model.Piece.Piece;
import Model.Piece.Pion;
import Model.Piece.Roi;

import java.util.HashMap;
import java.util.List;

public class PartiePvP extends Parties{


    public PartiePvP(){
        super();
    }

    /**
     * @param tabCoord : coordonnées plateau
     * @return : l'url de l'image de la case pointée
     */
    public String getUrlImageCase(int[] tabCoord){
        return getEchiquier().getCase(tabCoord[0], tabCoord[1]).getPiece().getImage();
    }

//    public HashMap<Integer, int[]> getDeplacementsEchec(int x, int y, List<Position> menace){
//        HashMap<Integer, int[]> liste = new HashMap<>();
//
//        getEchiquier().getCase(x, y).getPiece().setListeDep(getEchiquier());
//
//        if (!(getEchiquier().getCase(x, y).getPiece() instanceof Roi))
//            affinageDeplacements(getEchiquier().getCase(x, y).getPiece().getListeDep(), menace);
//
//        for (Position p: getEchiquier().getCase(x, y).getPiece().getListeDep()){
//            liste.put(8*(p.getY()+1)-(8-p.getX()), new int[]{p.getX(), p.getY()});
//        }
//
//        return liste;
//    }

//    public void affinageDeplacements(List<Position> listeDep, List<Position> menace) {
//        for (Position p : listeDep){ // Pour chaque déplacement possible pour la pièce
//            for (Position m : menace){ // Pour chaque menace directe du roi
//                if (!m.getPiece().getListeDep().contains(p)) // Si la position possible pour la pièce ne peut pas protéger le roi
//                    listeDep.remove(p); // On l'enlève de la liste de ses déplacements
//            }
//        }
//    }



//    public boolean isRoiSelectionne(int[] caseDepartPlateau) {
//        return (this.getEchiquier().getCase(caseDepartPlateau[0], caseDepartPlateau[1]).getPiece() instanceof Roi);
//    }


}
