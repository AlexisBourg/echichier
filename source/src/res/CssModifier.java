package res;

import javafx.scene.Node;

public class CssModifier {

    public CssModifier(){

    }

    public static void ChangeBackgroundImage(Node n, String url){
        n.setStyle("-fx-background-image: url(\""+url+"\");");
        //System.out.println(n.getStyle());
    }
}
