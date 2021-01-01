package res;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class BoxCoups extends VBox{

    public BoxCoups(){
        super();

        this.setPrefHeight(800);

        Label a = new Label();
        Label b = new Label();
        Label c = new Label();
        Label d = new Label();

        a.setText("premier");
        a.setId("a");
        a.setStyle("-fx-border-color: black;");
        a.setMinWidth(50);
        a.setPrefWidth(100);
        a.setMaxWidth(400);

        this.getChildren().add(a);

    }

}
