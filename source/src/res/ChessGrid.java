package res;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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

        for(y=0; y<8; y++){
            for (x=0; x<8; x++){
                Button bouton = new Button();
                bouton.setId(x+""+y);

                bouton.setPrefWidth(100);
                bouton.setPrefHeight(100);

                if((x+y)%2==0)
                    bouton.getStyleClass().add("white");
                else
                    bouton.getStyleClass().add("black");

                this.add(bouton, x, y);
            }
        }
    }
}