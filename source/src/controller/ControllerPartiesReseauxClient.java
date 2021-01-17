package controller;

import model.plateau.Plateau;
import model.parties.PartiePvP;
import javafx.fxml.FXML;
import res.interfaceGraphique.CssModifier;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ControllerPartiesReseauxClient extends ControllerPartiesPvP{

    public static final int LONGUEUR_EN_CASE=8;
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
        for (int y = 0; y <LONGUEUR_EN_CASE; y++) {
            for (int x = 0; x <LONGUEUR_EN_CASE; x++) {
                grille.getChildren().get((LONGUEUR_EN_CASE * (y + 1) - (LONGUEUR_EN_CASE - x))).setOnMouseClicked(mouseEvent -> {
                    if (PremierDeplacementServerEffectuer) {
                        switch (numeroClique(partie,mouseEvent.getSource())) {
                            case 1:
                                if (!listeDeplacements.isEmpty()) {
                                    retablissementCouleurCaseDeplacementPossibles(); // Les cases des déplacements possible retrouvent leur couleur d'origine
                                    restaurationImageDeplacementPossible(partie); // Les cases qui contenaient des pièces les retrouves
                                }
                                traitementCliqueUn(mouseEvent.getSource(),partie);
                                cliqueUnPasse = true;
                            break;
                            case 2:
                                if (cliqueUnPasse) {
                                    traitementCliqueDeux(mouseEvent.getSource(),partie);
                                    try {
                                        in = new ObjectInputStream(serverSocket.getInputStream());
                                        out = new ObjectOutputStream(serverSocket.getOutputStream());
                                        partie.changementJoueurCourant();
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
                    CssModifier.changeBackgroundImage(grille.getChildren().get((LONGUEUR_EN_CASE * (y + 1) - (LONGUEUR_EN_CASE - x))), echiquier.getCase(x, y).getPiece().getImage());
                }
            }
        }
    }


    /**--------------------------partie reseau-------------------*/


}
