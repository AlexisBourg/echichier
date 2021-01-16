package controller;

import model.parties.PartiePvP;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class ControllerMenuOnline {

    @FXML
    private Button heberger;
    @FXML
    private Button rejoindre;
    @FXML
    private Button retourMenu;
    @FXML
    private TextField ecrirAdresse;
    @FXML
    private TextField ecrirPort;
    @FXML
    private Text textAdresse;
    @FXML
    private Text textPort;


    public ControllerMenuOnline() {

    }

    @FXML
    public void initialize() {
        comportementBoutonHeberger();
        comportementBoutonRejoindre();
        comportementBoutonRetourMenu();


        //ecrirAdresse.textProperty().bindBidirectional(adressePcServer.);
        //ecrirPort.textProperty().bindBidirectional(adressePcServer.portProperty());
    }

    public void comportementBoutonHeberger() {
        heberger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                FXMLLoader load = new FXMLLoader(getClass().getResource("../res/interfaceGraphique/plateau.fxml"));
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

    }

    public void comportementBoutonRejoindre() {
        rejoindre.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader load = new FXMLLoader(getClass().getResource("../res/interfaceGraphique/plateau.fxml"));
                PartiePvP partie = new PartiePvP();
                ControllerPartiesReseauxClient controller = null;
                try {
                    controller = new ControllerPartiesReseauxClient(partie, InetAddress.getByName(ecrirAdresse.getText()),Integer.parseInt(ecrirPort.getText()));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

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
    }

    public void comportementBoutonRetourMenu() {

        /*Stage stage = (Stage) retourMenu.getScene().getWindow();
        if(!stage.equals(null)) {
            stage.close();
        }*/
    }
}
/*
    public void menuOnline(){

        heberger = new Button("Héberger partie");
        rejoindre = new Button("Rejoindre partie");
        retourMenu = new Button("Retour menu");
        adresse = new TextField();
        ecrirePort =  new TextField();
        textAdresse = new Text("Saisir l'adresse de connection :");
        textPort = new Text("saisir le Port de connection :");

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
                page.getChildren().remove(0, 7);
                page.getChildren().add(premiersBoutons.get(0));
                page.getChildren().add(premiersBoutons.get(1));
                page.getChildren().add(premiersBoutons.get(2));
            }
        });

        page.getChildren().remove(0, 3);
        page.getChildren().add(heberger);
        page.getChildren().add(rejoindre);
        page.getChildren().add(retourMenu);
        page.getChildren().add(textAdresse);
        page.getChildren().add(adresse);
        page.getChildren().add(textPort);
        page.getChildren().add(ecrirePort);
        VBox.setMargin(heberger, new Insets(-50, 0, 30, 0));
        VBox.setMargin(rejoindre, new Insets(0, 0, 30, 0));

    }
}
*/