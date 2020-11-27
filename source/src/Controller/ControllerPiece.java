package Controller;

import Model.PLateau.Position;
import Model.Piece.Piece;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class ControllerPiece {
    public boolean deplacementPiècePossible(Position posPieceDep, Position posPieceArr){


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


    public void deplacerunePièce(Position positionD, Position positionA){
        if(!positionD.isOccupe()){ return; }
        if (!deplacementPiècePossible(positionD, positionA)){
            System.out.println("Déplacement impossible");
        }
        /**
         * à finir
         */

    }
}
