package res.interfaceGraphique;

public enum LettrePlateau {
    A(0,'A'),
    B(1,'B'),
    C(2,'C'),
    D(3,'D'),
    E(4,'E'),
    F(5,'H'),
    G(6,'G'),
    H(7,'H');

    //Attribue
    private final int numeroCase;
    private final char lettre;

    //Constructeur
    LettrePlateau(int numeroCase, char lettre) {
        this.numeroCase=numeroCase;
        this.lettre=lettre;
    }

    //Methode

    /** Cette méthode retourne la lettre correspondante au numéro donné
     *
     * @param num numéro de la case (de 0 à 7)
     * @return : la lettre associée au nombre donnée
     */
    public static char getLettre(int num){
        for(LettrePlateau v : values()) {
            if (v.numeroCase == num) {
                return v.lettre;
            }
        }
        return 'd';
    }
}
