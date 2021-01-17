package res.interfaceGraphique;

import javafx.scene.Node;

public class CssModifier {

    //Attribue

    //Constructeur

    //Methode

    /** Cette méthode mets une image de pièce sur un bouton (case) donné
     *
     * @param n : case du plateau à modifier
     * @param url : image à mettre sur la case
     */
    public static void changeBackgroundImage(Node n, String url){
        n.setStyle("-fx-background-image: url(\""+url+"\");");
    }

    /** Cette méthode peints un bouton (case) avec une couleur donnée
     *
     * @param n : case du plateau à modifier
     * @param color : couleur à mettre sur la case
     */
    public static void changeBackgroundColor(Node n, String color){
        n.setStyle("-fx-background-color: "+color+";");
    }

}
