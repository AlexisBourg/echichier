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

    //Atribut
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

    public static final int HAUTEUR_DEFAUT = 800;
    public static final int LONGUEUR_DEFAUT = 1000;
    //Constructeur

    //Methode
    @FXML
    public void initialize() {
        comportementBoutonHeberger();
        comportementBoutonRejoindre();
        comportementBoutonRetourMenu();
    }

    /**
     * Permet de definir le comportement du bouton "Creer"
     */
    public void comportementBoutonHeberger() {
        heberger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                FXMLLoader load = new FXMLLoader(getClass().getResource("../res/interfaceGraphique/plateau.fxml"));
                PartiePvP partie = new PartiePvP();
                ControllerPartiesReseauServeur controller = new ControllerPartiesReseauServeur(partie);
                try {
                    System.out.println(controller.getLocalAdresse());
                    System.out.println(controller.getPublicAdresse());
                    System.out.println(controller.getPort());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                controller.commencerPartie();

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
                primaryStage.setScene(new Scene(root, LONGUEUR_DEFAUT, HAUTEUR_DEFAUT));
                primaryStage.show();
            }

        });

    }

    /**
     * Permet de definir le comportement du bouton "Rejoindre"
     */
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
                primaryStage.setScene(new Scene(root, LONGUEUR_DEFAUT, HAUTEUR_DEFAUT));
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