package Model.Piece;

public class Fou extends Pièce{


    //Attribue

    //Constructeur
    public Fou(int x, int y) {
        super(x, y, Image.FOU);
    }

    //Methode
    @Override
    public boolean déplacer(int x, int y) {
        return false;
    }
}
