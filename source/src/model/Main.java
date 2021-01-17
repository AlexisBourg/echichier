package model;

import controller.ControllerMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int LONGUEUR_DEFAUT = 1000;
    public static final int HAUTEUR_DEFAUT = 800;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader load = new FXMLLoader(getClass().getResource("../res/interfaceGraphique/menuPrincipal.fxml"));

        ControllerMenu controller = new ControllerMenu();

        load.setController(controller);

        Parent root = load.load();

        primaryStage.setTitle("Echec");
        primaryStage.setScene(new Scene(root, LONGUEUR_DEFAUT, HAUTEUR_DEFAUT));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}



