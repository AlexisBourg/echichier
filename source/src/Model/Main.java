package Model;

import Controller.ControllerPartisPvE;
import Controller.ControllerPartisPvP;
import Model.Parties.PartieGraph;
import Model.Parties.PartiePvE;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("../res/new.fxml"));
        FXMLLoader load = new FXMLLoader(getClass().getResource("../res/new.fxml"));
        //PartieGraph partie = new PartieGraph();
        PartiePvE partie = new PartiePvE();
        //ControllerPartisPvP controller = new ControllerPartisPvP(partie);
        ControllerPartisPvE controller = new ControllerPartisPvE(partie);
        load.setController(controller);

        Parent root = load.load();
        controller.chargementPlateau();
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



