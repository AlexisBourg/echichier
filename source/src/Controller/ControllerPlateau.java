package Controller;

import Model.PLateau.Position;
import Model.Piece.Piece;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.List;


public class ControllerPlateau {
    private int firstSelected = 0;
    private Button selected = new Button();
    private String oldStyle;
    @FXML
    private GridPane grid;

    @FXML
    public /* Piece ->*/ void caseSelected(MouseEvent mouseEvent) {
        if (firstSelected == 0) {
            selected = (Button) mouseEvent.getSource();
            oldStyle = selected.getStyle();
            selected.setStyle("-fx-background-color: gray;");
            firstSelected += 1;
        } else {
            selected.setStyle(oldStyle);
            selected = (Button) mouseEvent.getSource();
            oldStyle = selected.getStyle();
            selected.setStyle("-fx-background-color: gray;");
        }
    }






}
