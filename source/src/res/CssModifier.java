package res;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CssModifier {

    public CssModifier(){

    }

    public static void ChangeBackgroundImage(Node n, String url){
        n.setStyle("-fx-background-image: url(\""+url+"\");");
    }

    public static void ChangeBackgroundColor(Node n, String color){
        n.setStyle("-fx-background-color: "+color+";");
    }

    /*public static void test(Button n, String url){
        ImageView a = new ImageView(new Image(CssModifier.class.getResourceAsStream("ImagesPieces/reineB.png")));
        n.setGraphic(a);
    }*/
}
