package Controller;

import Model.Joueur.Joueur;
import Model.Parties.PartieGraph;
import Model.Parties.Parties;
import javafx.event.EventHandler;
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

import java.util.HashMap;
import java.util.LinkedList;


public class ControllerPlateau{
    PartieGraph partie;

    @FXML
    private ChessGrid grille;

    public ControllerPlateau(PartieGraph partie){
        this.partie = partie;
    }



    @FXML
    public void chargementPlateau(){
        Plateau echiquier = partie.getEchiquier();
        for(int y = 0; y<8; y++){
            for(int x = 0; x<8; x++){
                grille.getChildren().get((8*(y+1)-(8-x))+1).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        montrerDeplacementDispo(mouseEvent.getSource());
                    }
                });
                if(echiquier.getCase(x, y).isOccupe())
                    CssModifier.ChangeBackgroundImage(grille.getChildren().get((8*(y+1)-(8-x))+1), echiquier.getCase(x, y).getPiece().getImage());
            }
        }
    }

    public void montrerDeplacementDispo(Object source){
        LinkedList<Integer> bonjour = partie.getDeplacements(1, 0);
        for (Integer i: bonjour){
            System.out.println(i);
            CssModifier.ChangeBackgroundColor(grille.getChildren().get(i), "red");
        }
    }
}
