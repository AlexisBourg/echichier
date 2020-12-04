package Model;

import Controller.ControllerPlateau;
import Model.PLateau.Plateau;
import Model.PLateau.Position;
import Model.Parties.Parties;
import Model.Piece.Couleur;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    /*public boolean doDeplacement(Plateau echiquier,ControllerPlateau controller, Couleur joueur){
        String xy;
        int x,y;
        MouseEvent mouseEvent;
        xy=controller.caseSelected(mouseEvent);             //trouver comment faire
        x= Integer.valueOf((String) xy.subSequence(0,1));
        y= Integer.valueOf((String) xy.subSequence(1,2));
        Position posDep= echiquier.getCasse(x,y);
        if(joueur!=posDep.getPiece().getCouleur()){
            return false;
        }
        xy=controller.caseSelected(mouseEvent);
        x= Integer.valueOf((String) xy.subSequence(0,1));
        y= Integer.valueOf((String) xy.subSequence(1,2));
        Position posArr=echiquier.getCasse(x,y);
        if(posArr.isOccupe()  && joueur==posArr.getPiece().getCouleur()){
            return false;
        }
        if(!controller.deplacementPiècePossible(posDep,posArr)){
            System.out.println("Déplacement impossible");
            return false;
        }

        return true;
    } */

    @Override
    public void start(Stage primaryStage) throws Exception{
        ControllerPlateau controller = new ControllerPlateau();
        Parties parties = new Parties();
        parties.partieLocal();
        Couleur joueur=Couleur.BLANC;
        boolean gameOver = false;
        Parent root = FXMLLoader.load(getClass().getResource("../res/new.fxml"));
        ObservableList<Node> p = root.getChildrenUnmodifiable();
        GridPane q = (GridPane) p.get(2); // On prend la grosse grid
        ObservableList<Node> l =q.getChildrenUnmodifiable();
        GridPane n = (GridPane) l.get(16); // On prend la petite grid
        controller.chargementPlateau(parties.getEchiquier(), n);

        /*while(!gameOver){
            doDeplacement(echiquier,controller,joueur);
            switch (joueur){
                case BLANC:
                    joueur=Couleur.NOIR;
                    break;
                case NOIR:
                    joueur=Couleur.NOIR;
                    break;
            }
        } */

        primaryStage.setTitle("Echec");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
