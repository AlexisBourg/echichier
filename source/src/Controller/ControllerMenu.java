package Controller;

import Model.Parties.PartieGraph;
import Model.Parties.PartiePvE;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMenu {
    @FXML
    private VBox page;

    @FXML
    private Button boutonLocalPvp;

    @FXML
    private Button boutonLocalPve;

    @FXML
    private Button boutonEnLigne;

    public ControllerMenu(){

    }

    @FXML
    public void initialize(){
        comportementBoutonPVP();
        comportementBoutonPVE();
    }

    public void comportementBoutonPVP() {
        boutonLocalPvp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader load = new FXMLLoader(getClass().getResource("../res/plateau.fxml"));
                PartieGraph partie = new PartieGraph();
                ControllerPartiesPvP controller = new ControllerPartiesPvP(partie);
                load.setController(controller);

                Parent root = null;
                try {
                    root = load.load();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                controller.chargementPlateau();

                Stage primaryStage = new Stage();

                primaryStage.setTitle("Partie local PVP");
                primaryStage.setScene(new Scene(root, 1000, 800));
                primaryStage.show();
            }
        });
    }

        public void comportementBoutonPVE(){
            boutonLocalPve.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    FXMLLoader load = new FXMLLoader(getClass().getResource("../res/new.fxml"));
                    PartiePvE partie= new PartiePvE();
                    ControllerPartiesPvE controller = new ControllerPartiesPvE(partie);
                    load.setController(controller);

                    Parent root = null;
                    try {
                        root = load.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }

                    controller.chargementPlateau();

                    Stage primaryStage = new Stage();

                    primaryStage.setTitle("Partie local PVE");
                    primaryStage.setScene(new Scene(root, 1000, 800));
                    primaryStage.show();
                }
            });
    }
}
