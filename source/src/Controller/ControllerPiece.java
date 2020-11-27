package Controller;

import Model.PLateau.Position;
import Model.Piece.Piece;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class ControllerPiece {
    public boolean deplacementPiècePossible(Position posPieceDep, Position posPieceArr){
        if(!posPieceDep.isOccupe()){ return false; }

        Piece pieceTmpD = posPieceDep.getPiece();

        if(posPieceArr.isOccupe()){
            Piece pieceTmpA = posPieceArr.getPiece();
            if(pieceTmpA.getCouleur() == pieceTmpD.getCouleur()){ return false; }
        }

        List<Position> listP =pieceTmpD.getListeDep();
        if (!listP.contains(posPieceArr)){
            return false;
        }
        return true;
    }

    /*
    * Methode appeller par caseSelected()
     */
    public void deplacerunePièce(Piece piece){


    }
}
