package Controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import res.ChessGrid;
import Model.PLateau.Plateau;
import Model.PLateau.Position;
import Model.Piece.Piece;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import res.CssModifier;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerPlateau{
    private boolean firstSelected = false;
    private Button selected = new Button();
    private String oldStyle;

    @FXML
    private ChessGrid grille;

    @FXML
    public void initialize() {
        System.out.println(grille.getChildren());
    }

    @FXML
    public String caseSelected(MouseEvent mouseEvent) {
        String xy;
        if (firstSelected) {
            selected = (Button) mouseEvent.getSource();
            oldStyle = selected.getStyle();
            selected.setStyle("-fx-background-color: gray;");
            firstSelected = true;
            return (String) selected.getId();

        } else {
            selected.setStyle(oldStyle);
            selected = (Button) mouseEvent.getSource();
            oldStyle = selected.getStyle();
            selected.setStyle("-fx-background-color: gray;");
            return null;
        }
    }


    public void chargementPlateau(Plateau echiquier){
        for(int x = 0; x<8; x++){
            for(int y = 0; y<8; y++){
                if(echiquier.getCasse(x, y).isOccupe()){
                    //CssModifier.ChangeBackgroundImage(grille.getChildren().get(8*(x+1)-(8-y)), echiquier.getCasse(x, y).getPiece().getImage());
                    //System.out.println(grille.getChildren().get(8*(x+1)-(8-y)));
                }
            }
        }
    }

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


    /*public void deplacerunePièce(Position positionD){
        Position positionD = new Position();
        Position positionA = new Position();
        if(!positionD.isOccupe()){ return; }
        if(!deplacementPiècePossible(positionD, positionA)){
            System.out.println("Déplacement impossible");
        }
        /**
         * à finir
         *

    }
    */
}
