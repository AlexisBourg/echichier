package controller;

import model.plateau.Plateau;
import model.parties.PartiePvP;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import res.interfaceGraphique.CssModifier;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Scanner;

public class ControllerPartiesReseauServeur extends ControllerPartiesPvP {

    public static final int LONGUEUR_EN_CASE=8;
    private PartiePvP partie;
    private ServerSocket serverSocket = null;
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int port=-1;

    public ControllerPartiesReseauServeur(PartiePvP partie) {
        super();
        this.partie=partie;
        try {
            serverSocket = new ServerSocket(0, 1);
            port = serverSocket.getLocalPort();
        } catch (IOException e) {
            System.err.println("Impossible d'écouter sur le port: " + port);
        }
    }

    public void commencerPartie(){
        try {
            clientSocket = serverSocket.accept();
            System.out.println("2");
            clientSocket.setSoTimeout(20000); //TODO à decommenter dans la version final

        } catch (IOException e) {
            System.out.println("TimeOut: aucune connection");
        }
        try {
            System.out.println("test2");
            out = new ObjectOutputStream(clientSocket.getOutputStream() );  //sortie pour envoyer
            out.flush();//pour envoyer des info au client necessaire à une bonne connexion
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        listeDeplacements = new HashMap<>();
    }


    @FXML
    public void chargementPlateau() {
        Plateau echiquier = partie.getEchiquier();
        for (int y = 0; y <LONGUEUR_EN_CASE; y++) {
            for (int x = 0; x <LONGUEUR_EN_CASE; x++) {
                grille.getChildren().get((LONGUEUR_EN_CASE * (y + 1) - (LONGUEUR_EN_CASE - x))).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
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
                                        in = new ObjectInputStream(clientSocket.getInputStream());
                                        out = new ObjectOutputStream(clientSocket.getOutputStream());
                                        partie.changementJoueurCourant();
                                        out.writeObject(partie);
                                        try {
                                            partie =(PartiePvP) in.readObject();
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                cliqueUnPasse = false;
                            break;
                        }
                    }
                });
                if (echiquier.getCase(x, y).isOccupe()) {
                    CssModifier.changeBackgroundImage(grille.getChildren().get((LONGUEUR_EN_CASE * (y + 1) - (LONGUEUR_EN_CASE - x))), echiquier.getCase(x, y).getPiece().getImage());
                }
            }
        }
    }


    /**
     * -----------------------------partie reseau------------------------------
     */

    public InetAddress getLocalAdresse() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InetAddress getPublicAdresse()throws Exception
        {
            String site ="http://www.monip.org/";
            String prefixe ="<BR>IP : ";
            String suffixe ="<br>";
            Scanner sc = new Scanner(new URL(site).openStream());

            while (sc.hasNextLine())
            {
                String line = sc.nextLine();

                int a = line.indexOf(prefixe);
                if (a!=-1)
                {
                    int b = line.indexOf(suffixe,a);
                    if (b!=-1)
                    {
                        sc.close();
                        String adressePublic = line.substring(a+prefixe.length(),b);
                        return InetAddress.getByName(adressePublic);
                    }
                }
            }
            sc.close();
            return null;
        }

    public int getPort() {
        return port;
    }
}
