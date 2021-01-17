package res.interfaceGraphique;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ChessGrid extends GridPane {

    //Attribue

    //Constructeur
    public ChessGrid(){
        super();
        int x, y;

        for(int i=0; i<8; i++){
            this.addColumn(i);
            this.addRow(i);
        }

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(12.5);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(12.5);

        for(y=0; y<8; y++){
            for (x=0; x<8; x++){
                Button bouton = new Button();
                bouton.setId(x+""+y);

                bouton.setPrefWidth(100);
                bouton.setPrefHeight(100);

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