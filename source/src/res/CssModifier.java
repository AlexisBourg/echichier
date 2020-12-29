package res;

import javafx.scene.Node;

public class CssModifier {

    public CssModifier(){

    }

    public static void ChangeBackgroundImage(Node n, String url){
        n.setStyle("-fx-background-image: url(\""+url+"\");");
        //n.setStyle("-fx-background-color: blue;");
    }

    public static void ChangeBackgroundColor(Node n, String color){
        n.setStyle("-fx-background-color: "+color+";");
    }
}
