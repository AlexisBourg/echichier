package Model.Piece;

public class Tour extends Pièce{
    //Attribue

    //Constructeur
    public Tour(int x, int y, Couleur couleur){
        super(x,y,Image.TOUR);
    }



    //Methode
    @Override
    public boolean déplacer(int x, int y) {
        return false;
    }
}
