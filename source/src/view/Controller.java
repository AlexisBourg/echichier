package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Controller {
    private int firstSelected = 0;
    private Button selected = new Button();
    private String oldStyle;
    @FXML
    private GridPane grid;

    @FXML
    public void caseSelected(MouseEvent mouseEvent) {
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
