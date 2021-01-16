package model.parties;

import model.joueur.InterfaceJoueur;
import model.pLateau.Plateau;
import model.pLateau.Position;
import model.piece.Piece;
import model.piece.Pion;

import java.util.*;

public class PartieConsole extends Parties {

    public PartieConsole(){ super(); }

    public void partie(){
        int i=0;
        HashMap<String, int[]> coordDeplacements = new HashMap<>();
        Piece pieceMorte;

        InterfaceJoueur joueurCourant=super.getJoueur(0);
        InterfaceJoueur joueurNonCourant=super.getJoueur(1);

        while(true){
            System.out.println("Tour du joueur "+joueurCourant.getCouleur()+":\n");
            coordDeplacements = infosDeplacement(super.getEchiquier(), joueurCourant);
            pieceMorte = traiterDeplacement(super.getEchiquier(), coordDeplacements);

            if(pieceMorte!=null) {
                joueurNonCourant.addPieceMorte(pieceMorte);
            }

           /* if (EchecEtMat.echec(joueurNonCourant, super.getEchiquier()) && EchecEtMat.echecEtMat(joueurNonCourant, super.getEchiquier())){
                System.out.println("ECHEC ET MATTTTT");
                break;
            }
            else if(EchecEtMat.echec(joueurNonCourant, super.getEchiquier()))
                System.out.println("ECHEC");*/

            i=(i+1)%2;

            joueurCourant=super.getJoueur(i);
            joueurNonCourant= (i == 1) ?  super.getJoueur(i-1) :  super.getJoueur(i+1);
        }
    }

    /**
     * Cette méthode met en place la procédure pour proposer puis traiter la demande de déplacement d'une pièce appartenant au joueur courant.
     * @param echiquier : plateau du jeu
     * @param joueurCourant : joueur en train de jouer
     * @return : retourne une map comportant les coordonnées de la case de départ et d'arrivée
     */
    public HashMap<String, int[]> infosDeplacement(Plateau echiquier, InterfaceJoueur joueurCourant){
        String caseDep, caseArr;
        int[] coordDepart = new int[2], coordArr = new int[2];
        List<Position> list = new LinkedList<>();
        HashMap<String, int[]> donnees = new HashMap<>();
        Scanner scan = new Scanner(System.in);

        System.out.println("joueur"+joueurCourant+": donnez une case de départ parmi cette liste:");
        listePiecesJoueur(joueurCourant);

        System.out.print("\n");

        caseDep = scan.next();
        determinerCoord(caseDep, coordDepart);

        while(coordNonCoherente(coordDepart[0]) || coordNonCoherente(coordDepart[1]) || !isPieceSelecDansListePiecesJoueur(joueurCourant, echiquier, coordDepart)){
            System.out.println("Coordonnées non cohérentes, recommencez:");
            caseDep = scan.next();
            determinerCoord(caseDep, coordDepart);
        }

        echiquier.getCase(coordDepart[0], coordDepart[1]).getPiece().setListeDep(echiquier);
        list = echiquier.getCase(coordDepart[0], coordDepart[1]).getPiece().getListeDep();

        while (list.size()==0){
            System.out.println("Cette case ne peut se déplacer! recommencez");
            System.out.println("joueur"+joueurCourant+": donnez une case de départ.");
            caseDep = scan.next();
            determinerCoord(caseDep, coordDepart);
            echiquier.getCase(coordDepart[0], coordDepart[1]).getPiece().setListeDep(echiquier);
            list = echiquier.getCase(coordDepart[0], coordDepart[1]).getPiece().getListeDep();
        }

        donnees.put("depart", coordDepart);

        System.out.println("joueur"+joueurCourant+": donnez une case d'arrivée:");
        for(Position courante: list)
            System.out.print(courante.getX()+""+courante.getY()+", ");
        System.out.print("\n");
        caseArr = scan.next();
        determinerCoord(caseArr, coordArr);

        while(!list.contains(echiquier.getCase(coordArr[0], coordArr[1]))){
            System.out.println("Position non présente dans la liste, recommencez.");
            System.out.println("joueur"+joueurCourant+": donnez une case d'arrivée:");
            for(Position courante: list)
                System.out.print(courante.getX()+courante.getY()+", ");
            System.out.print("\n");
            caseArr = scan.next();
            determinerCoord(caseArr, coordArr);
        }

        donnees.put("arrivee", coordArr);
        return donnees;
    }

    /**
     *  Cette méthode sépare la chaîne composée de deux chiffres en deux. Puis, elle traduit ces caractères en entiers.
     * @param chaine : message comportant une case sélectionnée par le joueur courant
     * @param coord : tableau qui va recueillir les coordonnées extraites du message
     */
    public void determinerCoord(String chaine, int[] coord){
        String[] tab = chaine.split("");
        coord[0] = Integer.parseInt(tab[0]);
        coord[1] = Integer.parseInt(tab[1]);
    }

    /**
     *
     * @param a : coordonnée
     * @return : le fait que la coordonnée données est extérieure ou non au plateau
     */
    public boolean coordNonCoherente(int a){
        return a > 8 || a < 0;
    }

    /**
     *  Cette méthode affiche les pièces du joueur courant qui sont encore en vie et qui sont susceptibles de pouvoir se déplacer
     * @param joueurCourant : joueur en train de jouer
     */
    public void listePiecesJoueur(InterfaceJoueur joueurCourant){
        for (int i=0; i<joueurCourant.getPieces().length; i++) {
            if(joueurCourant.getPieces()[i]!=null)
                System.out.print(joueurCourant.getPieces()[i].getCoordX() + "" + joueurCourant.getPieces()[i].getCoordY() + ", ");
        }
    }


    /**
     *
     * @param joueurCourant joueur qui est en train de jouer
     * @param echiquier plateau du jeu
     * @param coordDepart coordonnées de la case sélectionnée par le joueur
     * @return le fait que la case sélectionnée contienne une pièce que possède le joueur courant.
     */
    public boolean isPieceSelecDansListePiecesJoueur(InterfaceJoueur joueurCourant, Plateau echiquier, int[] coordDepart){
        for (int i=0; i<joueurCourant.getPieces().length; i++){
            if(echiquier.getCase(coordDepart[0], coordDepart[1]).getPiece()!=null) {
                if (echiquier.getCase(coordDepart[0], coordDepart[1]).getPiece().equals(joueurCourant.getPieces()[i]))
                    return true;
            }
        }
        return false;
    }

    /**
     *  Cette méthode affecte le plateau en fonction des coordonnées demandées par je joueur courant
     * @param plateau : plateau du jeu
     * @param donnees : map qui contient les coordonnées des cases de départ et d'arrivée choisies par le joueur
     * @return : la pièce qui a été mangée durant le déplacement ou null sinon
     */
    public Piece traiterDeplacement(Plateau plateau, HashMap<String, int[]> donnees){
        Piece pieceDeplacee, pieceMorte;

        int[] depart =  donnees.get("depart");
        int[] arrivee =  donnees.get("arrivee");

        pieceMorte = null;
        pieceDeplacee = plateau.getCase(depart[0], depart[1]).getPiece();

        if(plateau.getCase(arrivee[0], arrivee[1]).isOccupe()) { // Si la case d'arrivée est occupée, pieceMorte n'est plus null et prend la valeur de cette dernière
            // pieceMorte ne peut pas être une pièce alliée à celle qui se déplace (cette possibilité est éliminée dans setListeDep de la pièce déplacée)
            pieceMorte = plateau.getCase(arrivee[0], arrivee[1]).getPiece();
        }

        plateau.getCase(arrivee[0], arrivee[1]).setPiece(plateau.getCase(depart[0], depart[1]).getPiece());
        plateau.getCase(depart[0], depart[1]).unsetPiece();

        pieceDeplacee.setCoordX(arrivee[0]);
        pieceDeplacee.setCoordY(arrivee[1]);

        if (pieceDeplacee instanceof Pion && ((Pion) pieceDeplacee).getPremierDeplacement())
            ((Pion) pieceDeplacee).setPremierDeplacement();

        return pieceMorte;
    }

}
