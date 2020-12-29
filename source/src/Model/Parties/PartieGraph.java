package Model.Parties;

import Controller.ControllerPlateau;
import Model.PLateau.Plateau;
import Model.PLateau.Position;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PartieGraph extends Parties{

    public PartieGraph(){
        super();
    }

    public LinkedList<Integer> getDeplacements(int x, int y){
        LinkedList<Integer> resultats = new LinkedList<>();
        int X, Y;

        if(super.getEchiquier().getCase(x, y).getPiece().getListeDep().isEmpty())
            super.getEchiquier().getCase(x, y).getPiece().setListeDep(super.getEchiquier());

        for (Position p: super.getEchiquier().getCase(x, y).getPiece().getListeDep()){
            X = p.getX();
            Y = p.getY();
            resultats.add((8*(Y+1)-(8-X))+1);
        }

        return resultats;
    }


}
