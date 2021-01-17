package res.son;

public enum Son {
    MOVEPIECE("src/res/son/mouvementDePiece.wav");

    //Attribue
    private final String bruit;

    //Constructeur
    Son(String bruit) {
        this.bruit=bruit;
    }

    //Methode
    public String getBruit() {
        return bruit;
    }
}
