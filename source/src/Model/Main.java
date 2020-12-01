package Model;

import Controller.ControllerPlateau;
import Model.PLateau.Plateau;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ControllerPlateau controller = new ControllerPlateau();
        Plateau echiquier = new Plateau();
        Parent root = FXMLLoader.load(getClass().getResource("../res/new.fxml"));
        ObservableList<Node> p = root.getChildrenUnmodifiable();
        GridPane q = (GridPane) p.get(2); // On prend la grosse grid
        ObservableList<Node> x =q.getChildrenUnmodifiable();
        GridPane n = (GridPane) x.get(16); // On prend la petite grid
        controller.chargementPlateau(echiquier, n);
        primaryStage.setTitle("Echec");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
