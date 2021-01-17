package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.parties.Parties;
import model.plateau.Plateau;
import res.interfaceGraphique.ChessGrid;
import res.interfaceGraphique.CssModifier;
import res.interfaceGraphique.LettrePlateau;

import java.util.HashMap;
import java.util.Map;

public class ControllerAffichage {

<<<<<<< HEAD
    public static final int LONGUEUR_EN_CASE = 8;
=======
    //Atribut
>>>>>>> 2946f13a9d740fa9a4f0f4f5e965d439eb623709
    protected HashMap<Integer, int[]> listeDeplacements;
    protected ObservableList<String> listeCoups = FXCollections.observableArrayList();
    @FXML
    protected ChessGrid grille;
    @FXML
    protected ListView<String> coups;
    @FXML
    protected Button arriere;
    @FXML
    protected Button suivant;

    //Constructeur

    //Methode
    /**
     * Permet d'incrementer la liste des coups jouer
     * @param caseDepartPlateau
     * @param caseArriveePlateau
     */
    public void ajoutCoup(int[] caseDepartPlateau, int[] caseArriveePlateau) {
        String coup = "    "+traductionIntChar(caseDepartPlateau[0])+""+traductionCoordPlateau(caseDepartPlateau[1])+"  ->  "+traductionIntChar(caseArriveePlateau[0])+""+traductionCoordPlateau(caseArriveePlateau[1]);
        listeCoups.add(coup);
    }

    /**
     * Permet de traduire l'abscisse en une lettre
     * @param caractere : numero de l'abscisse
     * @return : la lettre asscocié
     */
    public char traductionIntChar(int caractere){
        return LettrePlateau.getLettre(caractere);
    }

    /**
     * permet de traduire l'ordonnée en nombre
     * @param nombre : le numero de l' ordonée
     * @return : le numero associé
     */
    public int traductionCoordPlateau(int nombre){
        return 8-nombre;
    }

    /**
     * Ré-affecte le style de base des cases de déplacement possible
     */
    public void retablissementCouleurCaseDeplacementPossibles() {
        int coordGrille;
        int[] coordPlateau;

        for (Map.Entry coord : listeDeplacements.entrySet()) {
            coordGrille = (int) coord.getKey();
            coordPlateau = (int[]) coord.getValue();

            if (coordPlateau[1] % 2 == 0) {
                if (coordPlateau[0] % 2 == 0)
                    CssModifier.changeBackgroundColor(grille.getChildren().get(coordGrille), "white;");
                else
                    CssModifier.changeBackgroundColor(grille.getChildren().get(coordGrille), "black;");
            } else {
                if (coordPlateau[0] % 2 == 1)
                    CssModifier.changeBackgroundColor(grille.getChildren().get(coordGrille), "white;");
                else
                    CssModifier.changeBackgroundColor(grille.getChildren().get(coordGrille), "black;");
            }
        }
    }

    /**
     * Permet d'actualisé le plateau de l'échiquier après qu'un coup ait été jouer
     * @param partieActuel : est la partie à actualiser
     */
    public void actualiserEtatPlateau(Parties partieActuel){
        Plateau echiquier = partieActuel.getEchiquier();

        for (int i=0; i<LONGUEUR_EN_CASE; i++){
            for (int j=0; j<LONGUEUR_EN_CASE; j++){
                if (echiquier.getCase(j, i).isOccupe()){
                    CssModifier.changeBackgroundImage(grille.getChildren().get((LONGUEUR_EN_CASE  * (i + 1) - (LONGUEUR_EN_CASE  - j))), echiquier.getCase(j, i).getPiece().getImage());
                }else
                    CssModifier.changeBackgroundImage(grille.getChildren().get((LONGUEUR_EN_CASE  * (i + 1) - (LONGUEUR_EN_CASE  - j))), "");
            }
        }
    }

}
