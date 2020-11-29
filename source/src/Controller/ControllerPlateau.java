package Controller;

import Model.PLateau.Plateau;
import Model.PLateau.Position;
import Model.Piece.Piece;
import javafx.collections.ObservableList;
import javafx.css.converter.URLConverter;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.image.*;

import java.net.URL;
import java.util.List;


public class ControllerPlateau {
    private boolean firstSelected = false;
    private Button selected = new Button();
    private String oldStyle;

    @FXML
    public void caseSelected(MouseEvent mouseEvent) {
        if (firstSelected) {
            selected = (Button) mouseEvent.getSource();
            oldStyle = selected.getStyle();
            selected.setStyle("-fx-background-color: gray;");
            firstSelected = true;
        } else {
            selected.setStyle(oldStyle);
            selected = (Button) mouseEvent.getSource();
            oldStyle = selected.getStyle();
            selected.setStyle("-fx-background-color: gray;");
        }
    }

    @FXML
    public void chargementPlateau(Plateau echiquier, GridPane grid){
        Node bonjour;
        String image;
        Piece imaged;

        for(int x = 0; x<8; x++){
            for(int y = 0; y<8; y++){
                bonjour = (Button)grid.getChildren().get(8*(x+1)-(8-y));
                if(echiquier.getCasse(x, y).isOccupe()) {
                    image = echiquier.getCasse(x, y).getPiece().getImage();
                    //System.out.println(image);
                    //System.out.println(imaged);
                    // setStyle("-fx-background-color: url(\""+image+"\")");
                }
            }
        }
    }

}
