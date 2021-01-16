package controller;

import model.parties.PartiePvP;
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


public class ControllerMenu {
    @FXML
    private VBox page;

    @FXML
    private Button boutonLocalPvp;

    @FXML
    private Button boutonLocalPve;

    @FXML
    private Button boutonEnLigne;



    private Parent root=null;
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
                ControllerPartiesPvP controller = new ControllerPartiesPvP();
                load.setController(controller);


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
                ControllerPartiesPvE controller = new ControllerPartiesPvE();
                load.setController(controller);


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
                FXMLLoader load = new FXMLLoader(getClass().getResource("../res/menuOnline.fxml"));

                ControllerMenuOnline controller= new ControllerMenuOnline();
                load.setController(controller);


                try {
                    root =load.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Echec");
                primaryStage.setScene(new Scene(root, 1000, 800));
                primaryStage.show();

            }
        });
    }

    /**----------------------------------------------------
     */

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
                PartiePvP partie = new PartiePvP();
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
                PartiePvP partie = new PartiePvP();
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
