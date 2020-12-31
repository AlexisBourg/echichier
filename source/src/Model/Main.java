package Model;

import Controller.ControllerMenu;
import Controller.ControllerPartiesPvP;
import Model.PLateau.Plateau;
import Model.PLateau.Position;
import Model.Parties.PartieConsole;
import Model.Parties.PartieGraph;
import Model.Parties.Parties;
import Model.Piece.Couleur;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader load = new FXMLLoader(getClass().getResource("../res/menuPrincipal.fxml"));
        //PartieGraph partie = new PartieGraph();
        //ControllerPartiesPvP controller = new ControllerPartiesPvP(partie);
        //load.setController(controller);

        ControllerMenu controller = new ControllerMenu();
        load.setController(controller);

        Parent root = load.load();
        //controller.chargementPlateau();
        //PartieConsole parties = new PartieConsole();
        //parties.partie();


        primaryStage.setTitle("Echec");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}



