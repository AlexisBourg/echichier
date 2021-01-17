package res.interfaceGraphique;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ChessGrid extends GridPane {

    //Attribue
    public static final double POURCENTAGE_TAILLE= 12.5;
    public static final double TAILLE_PREF= 100;
    public static final int LIMIT_SUP=7;
    public static final int LIMIT_INF=0;

    //Constructeur

    /**
     * Créé une grid qui servira de plateau de jeu
     */
    public ChessGrid(){
        super();
        int x, y;

        for(int i=LIMIT_INF; i<=LIMIT_SUP; i++){
            this.addColumn(i);
            this.addRow(i);
        }

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(POURCENTAGE_TAILLE);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(POURCENTAGE_TAILLE);

        for(y=LIMIT_INF; y<=LIMIT_SUP; y++){
            for (x=LIMIT_INF; x<=LIMIT_SUP; x++){
                Button bouton = new Button();
                bouton.setId(x+""+y);

                bouton.setPrefWidth(TAILLE_PREF);
                bouton.setPrefHeight(TAILLE_PREF);

                bouton.setStyle("-fx-background-size: cover;");

                if((x+y)%2==0)
                    bouton.getStyleClass().add("white");
                else
                    bouton.getStyleClass().add("black");

                this.add(bouton, x, y);
            }
        }
    }
}