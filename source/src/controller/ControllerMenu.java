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


public class ControllerMenu {

    //Atribut
    public static final int HAUTEUR_DEFAUT = 800;
    public static final int LONGUEUR_DEFAUT = 1000;
    private Parent root=null;
    @FXML
    private VBox page;

    @FXML
    private Button boutonLocalPvp;

    @FXML
    private Button boutonLocalPve;

    @FXML
    private Button boutonEnLigne;

    //Constructeur


    //Methode
    @FXML
    public void initialize(){
        comportementBoutonPVE();
        comportementBoutonPVP();
        comportementBoutonOnline();
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
                primaryStage.setScene(new Scene(root, LONGUEUR_DEFAUT, HAUTEUR_DEFAUT));
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
                primaryStage.setScene(new Scene(root, LONGUEUR_DEFAUT, HAUTEUR_DEFAUT));
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
                primaryStage.setScene(new Scene(root, LONGUEUR_DEFAUT, HAUTEUR_DEFAUT));
                primaryStage.show();
            }
        });
    }
}
