package Model.Piece;

public class Cavalier extends Pièce{


    //Attribue

    //Constructeur
    public Cavalier(int x, int y){
        super(x, y, Image.CAVALIER);
    }


    //Methode
    @Override
    public boolean déplacer(int x, int y) {
        return false;
    }
}
