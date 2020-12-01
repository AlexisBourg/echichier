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
            GridPane a = (GridPane) selected.getParent();
            System.out.println(a.getChildren());
            oldStyle = selected.getStyle();
            selected.setStyle("-fx-background-color: gray;");
            firstSelected = true;
        } else {
            selected.setStyle(oldStyle);
            selected = (Button) mouseEvent.getSource();
            System.out.println(selected.getLayoutX());
            GridPane a = (GridPane) selected.getParent();
            System.out.println(a.getChildren());
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
                //System.out.print(8*(x+1)-(8-y)+" :  ");
                //System.out.print(echiquier.getCasse(x, y).getX() + " , "+echiquier.getCasse(x, y).getY());
                //System.out.println(echiquier.getCasse(x, y).isOccupe());
                //bonjour = (Button)
                if(echiquier.getCasse(x, y).isOccupe()) {
                    //image = echiquier.getCasse(x, y).getPiece().getImage();
                    //System.out.println(image);
                    //System.out.println(imaged);
                    //System.out.println("x:"+x);
                    //System.out.println("y"+y);
                    //System.out.println(8*(x+1)-(8-y)+"\n");
                    //grid.getChildren().get(8*(x+1)-(8-y)).setStyle("-fx-background-image: url(\""+image+"\")");
                }
                //System.out.println(grid.getChildren().get(24));
                //System.out.println(echiquier.getCasse(3, 0).getPiece());
                //grid.getChildren().get(24).setStyle("-fx-background-color: blue;");
                //grid.getChildren().get(8*(x+1)-(8-y)).setStyle("-fx-background-color: yellow;");
            }
        }
    }

}
