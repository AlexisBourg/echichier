package Controller;

import Model.Parties.PartieGraph;
import Model.Parties.PartiePvE;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;


public class ControllerMenu {
    @FXML
    private VBox page;

    @FXML
    private Button boutonLocalPvp;

    @FXML
    private Button boutonLocalPve;

    @FXML
    private Button boutonEnLigne;

    private LinkedList<Button> premiersBoutons;
    private int port;
    private InetAddress addr;

    public ControllerMenu(){

    }

    @FXML
    public void initialize(){
        premiersBoutons = new LinkedList<>();
        comportementBoutonPVE();
        comportementBoutonPVP();
        comportementBoutonOnline();
        premiersBoutons.add(boutonLocalPvp);
        premiersBoutons.add(boutonLocalPve);
        premiersBoutons.add(boutonEnLigne);
    }

    public void comportementBoutonPVP() {
        boutonLocalPvp.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader load = new FXMLLoader(getClass().getResource("../res/plateau.fxml"));
                PartieGraph partie = new PartieGraph();
                ControllerPartiesPvP controller = new ControllerPartiesPvP(partie);
                load.setController(controller);

                Parent root;
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
        boutonLocalPve.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader load = new FXMLLoader(getClass().getResource("../res/plateau.fxml"));
                PartiePvE partie = new PartiePvE();
                ControllerPartiesPvE controller = new ControllerPartiesPvE(partie);
                load.setController(controller);

                Parent root;
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

    public void comportementBoutonOnline(){
        boutonEnLigne.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                menuOnline();
            }
        });
    }

    public void menuOnline(){
        Button heberger, rejoindre, retourMenu;
        heberger = new Button("Héberger partie");
        rejoindre = new Button("Rejoindre partie");
        retourMenu = new Button("Retour menu");

        heberger.setId("boutonLocalPvp");
        rejoindre.setId("boutonLocalPve");
        retourMenu.setId("boutonEnLigne");
        retourMenu.setPrefWidth(170);

        heberger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader load = new FXMLLoader(getClass().getResource("../res/plateau.fxml"));
                PartieGraph partie = new PartieGraph();
                ControllerPartiesReseauServeur controller = new ControllerPartiesReseauServeur(partie);
                try {
                    System.out.println(controller.getPublicAdresse());
                    System.out.println(controller.getPort());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                controller.CommencerPartie();

                load.setController(controller);

                Parent root;
                try {
                    root = load.load();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                controller.chargementPlateau();

                Stage primaryStage = new Stage();

                primaryStage.setTitle("Partie Online (Server)");
                primaryStage.setScene(new Scene(root, 1000, 800));
                primaryStage.show();
            }
        });

        rejoindre.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader load = new FXMLLoader(getClass().getResource("../res/plateau.fxml"));
                PartieGraph partie = new PartieGraph();
                ControllerPartiesReseauxClient controller = new ControllerPartiesReseauxClient(partie,addr,port);

                load.setController(controller);

                Parent root;
                try {
                    root = load.load();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                controller.chargementPlateau();

                Stage primaryStage = new Stage();

                primaryStage.setTitle("Partie Online (Server)");
                primaryStage.setScene(new Scene(root, 1000, 800));
                primaryStage.show();

            }
        });

        retourMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                page.getChildren().remove(0, 3);
                page.getChildren().add(premiersBoutons.get(0));
                page.getChildren().add(premiersBoutons.get(1));
                page.getChildren().add(premiersBoutons.get(2));
            }
        });

        page.getChildren().remove(0, 3);
        page.getChildren().add(heberger);
        page.getChildren().add(rejoindre);
        page.getChildren().add(retourMenu);
        VBox.setMargin(heberger, new Insets(-50, 0, 30, 0));
        VBox.setMargin(rejoindre, new Insets(0, 0, 30, 0));

    }
}
