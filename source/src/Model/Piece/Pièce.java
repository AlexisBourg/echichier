package Model.Piece;

public abstract class Pièce {

    //Atribut
    private int coordX;
    private int coordY;
    private Image image;
    private Couleur couleur;

    //Constructeur
    public Pièce(int x,int y, Image img, Couleur couleur){
        coordX=x;
        coordY=y;
        image=img;
        this.couleur = couleur;
    }

    //Methode

    //Getter et Setter
    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public Image getCouleur() {
        return couleur;
    }

    public abstract int[][] deplacementsPoss(int x, int y);
}
