package res.son;

public enum Son {
    MOVEPIECE("src/res/son/mouvementDePiece.wav");

    private final String bruit;
    Son(String bruit) {
        this.bruit=bruit;
    }

    public String getBruit() {
        return bruit;
    }
}
