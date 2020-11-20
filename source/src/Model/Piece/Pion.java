package Model.Piece;

public class Pion extends Pièce {
    //Atttribue
    private boolean premierDeplacement=true;

    //Construccteur
    public Pion(int x, int y, Couleur couleur){
        super(x,y,Image.PION);
    }

    //Methode
    @Override
    public boolean déplacer(int x, int y) {
        if (premierDeplacement){
            //a renplir
        }



        return false;
    }
}
