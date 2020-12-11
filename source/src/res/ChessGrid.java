package res;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import res.CssModifier;

public class ChessGrid extends GridPane {

    public ChessGrid(){
        super();
        int x=0, y=0;

        for(int i=0; i<8; i++){
            this.addColumn(i);
            this.addRow(i);
        }

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(12.5);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(12.5);

        this.setGridLinesVisible(true);

        for(int i=0; i<64; i++){
            Button bouton = new Button();
            if(i<10)
                bouton.setId("0"+i);
            else
                bouton.setId(""+i);

            bouton.setPrefHeight(100);
            bouton.setPrefWidth(100);

            if((x+y)%2==0)
                bouton.getStyleClass().add("white");
            else
                bouton.getStyleClass().add("black");

            this.add(bouton, x, y);
            x+=1;
            if(x==8){
                y+=1;
                x=0;
            }

        }
    }
}