package controller;

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
import java.net.InetAddress;
import java.util.LinkedList;


public class ControllerMenu {

    //Atribut
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

    //Constructeur


    //Methode
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

    /**
     * Permet de definir le comportement du bouton "Partie local PvP"
     */
    public void comportementBoutonPVP() {
        boutonLocalPvp.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader load = new FXMLLoader(getClass().getResource("../res/interfaceGraphique/plateau.fxml"));
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

    /**
     * Permet de definir le comportement du bouton "Partie local contre IA"
     */
    public void comportementBoutonPVE(){
        boutonLocalPve.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader load = new FXMLLoader(getClass().getResource("../res/interfaceGraphique/plateau.fxml"));
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

    /**
     * Permet de definir le comportement du bouton "Partie en ligne"
     */
    public void comportementBoutonOnline(){
        boutonEnLigne.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader load = new FXMLLoader(getClass().getResource("../res/interfaceGraphique/menuOnline.fxml"));

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
}
