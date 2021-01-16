package controller;

import model.pLateau.Plateau;
import model.parties.PartiePvP;
import javafx.fxml.FXML;
import res.CssModifier;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ControllerPartiesReseauxClient extends ControllerPartiesPvP{

    private PartiePvP partie;
    private Socket serverSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean PremierDeplacementServerEffectuer = false;


    public ControllerPartiesReseauxClient(PartiePvP partie, InetAddress addr, int port){
        super();
        this.partie = partie;
        try{
            serverSocket = new Socket(addr,port);
        } catch (IOException e) {
            System.out.println("Impossible de se connecter au server");
        }
        try {
            out = new ObjectOutputStream(serverSocket.getOutputStream());  //sortie pour envoyer
            out.flush();//pour envoyer des info au client necessaire à une bonne connexion
            in = new ObjectInputStream(serverSocket.getInputStream());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void chargementPlateau() {
        Plateau echiquier = partie.getEchiquier();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                grille.getChildren().get((8 * (y + 1) - (8 - x))).setOnMouseClicked(mouseEvent -> {
                    if (PremierDeplacementServerEffectuer) {
                        switch (NumeroClique(partie,mouseEvent.getSource())) {
                            case 1:
                                if (!listeDeplacements.isEmpty()) {
                                    retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
                                    restaurationImageDeplacementPossible(partie); // Les cases qui contenaient des pièces les retrouves
                                }
                                TraitementCliqueUn(mouseEvent.getSource(),partie);
                                cliqueUnPasse = true;
                            break;
                            case 2:
                                if (cliqueUnPasse) {
                                    TraitementCliqueDeux(mouseEvent.getSource());
                                    try {
                                        in = new ObjectInputStream(serverSocket.getInputStream());
                                        out = new ObjectOutputStream(serverSocket.getOutputStream());
                                        partie.ChangementJoueurCourant();
                                        out.writeObject(partie);

                                        partie = (PartiePvP) in.readObject();

                                    } catch (IOException | ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                                cliqueUnPasse = false;
                            break;
                        }
                    } else {
                        try {
                            partie = (PartiePvP) in.readObject();
                            PremierDeplacementServerEffectuer = true;
                        } catch (ClassNotFoundException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                if (echiquier.getCase(x, y).isOccupe()){
                    CssModifier.ChangeBackgroundImage(grille.getChildren().get((8 * (y + 1) - (8 - x))), echiquier.getCase(x, y).getPiece().getImage());
                    //CssModifier.test((Button)grille.getChildren().get((8 * (y + 1) - (8 - x))), echiquier.getCase(x, y).getPiece().getImage());
                }
            }
        }
    }

    /**
     * Cette méthode traite le cas du second clique, c'est à dire, de faire déplacer la pièce dans le plateau et d'actualiser l'interface en conséquence
     *
     * @param source : bouton cliqué
     */
    public void TraitementCliqueDeux(Object source) {
        caseArriveeGrille = partie.getNumCaseGrille(decompositionIdBouton(source));

        if (listeDeplacements.containsKey(caseArriveeGrille)) {
            caseArriveePlateau = decompositionIdBouton(source);
            finDeDeplacement();
            partie.stockerCoup(caseDepartPlateau, caseArriveePlateau, pieceMangee, partie.getJoueurCourant(), partie.getJoueurNonCourant());
            partie.ChangementJoueurCourant();
        }

    }

    /**--------------------------partie reseau-------------------*/


}
