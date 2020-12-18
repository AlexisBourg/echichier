package Model.Parties;

import Model.Joueur.Joueur;
import Model.PLateau.Plateau;
import Model.PLateau.Position;
import Model.Piece.Piece;

import java.util.*;

public class PartieConsole extends Parties {

    public PartieConsole(){
        super();
        /*for(int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                System.out.println("X : "+j+"  Y :"+i+"  "+super.getEchiquier().getCasse(j, i).toString());
            }
        }*/
    }

    public void partie(){
        Piece pieceDéplacée;
        int i=0;
        String caseDep, caseArr;
        HashMap<String, int[]> coordDeplacements = new HashMap<>();
        Piece pieceMorte;
        int[] coordDepart= new int[2], coordArrive= new int[2];

        Joueur joueurCourant=super.getJoueur(0);
        Joueur joueurNonCourant=super.getJoueur(1);

        while(true){ // !EchecEtMat.echecEtMat(joueurNonCourant, super.getEchiquier())
            System.out.println("Tour du joueur "+joueurCourant.getCouleur()+":\n");

            //selectionne une case de depart

            coordDeplacements = infosDeplacement(super.getEchiquier(), joueurCourant);
            pieceMorte = traiterDeplacement(super.getEchiquier(), coordDeplacements, joueurCourant, joueurNonCourant);

            if(pieceMorte!=null)
                joueurNonCourant.addPieceMorte(pieceMorte);



            //selectionne une case d'arrivé

            //doDeplacement(echiquier,controller,joueur);
            //if(pieceDéplacée.getListeDep().get

            i=(i+1)%2;
        }
    }

    public void determinerCoord(String chaine, int[] coord){
        String[] tab = chaine.split("");
        coord[0] = Integer.parseInt(tab[0]);
        coord[1] = Integer.parseInt(tab[1]);
    }

    public HashMap<String, int[]> infosDeplacement(Plateau echiquier, Joueur joueurCourant){
        String caseDep, caseArr;
        int[] coordDepart = new int[2], coordArr = new int[2];
        List<Position> list = new LinkedList<>();
        HashMap<String, int[]> donnees = new HashMap<>();
        Scanner scan = new Scanner(System.in);

        System.out.println("joueur"+joueurCourant+": donnez une case de départ parmi cette liste:");
        for (int i=0; i<joueurCourant.getPieces().length; i++)
            System.out.print(joueurCourant.getPieces()[i].getCoordX()+""+joueurCourant.getPieces()[i].getCoordY()+", ");

        System.out.print("\n");

        caseDep = scan.next();
        determinerCoord(caseDep, coordDepart);

        while(coordNonCoherente(coordDepart[0]) || coordNonCoherente(coordDepart[1]) || !isPieceSelecDansListePiecesJoueur(joueurCourant, echiquier, coordDepart)){
            System.out.println("Coordonnées non cohérentes, recommencez:");
            caseDep = scan.next();
            determinerCoord(caseDep, coordDepart);
        }

        echiquier.getCasse(coordDepart[0], coordDepart[1]).getPiece().setListeDep(echiquier);
        list = echiquier.getCasse(coordDepart[0], coordDepart[1]).getPiece().getListeDep();

        while (list.size()==0){
            System.out.println("Cette case ne peut se déplacer! recommencez");
            System.out.println("joueur"+joueurCourant+": donnez une case de départ.");
            caseDep = scan.next();
            determinerCoord(caseDep, coordDepart);
            echiquier.getCasse(coordDepart[0], coordDepart[1]).getPiece().setListeDep(echiquier);
            list = echiquier.getCasse(coordDepart[0], coordDepart[1]).getPiece().getListeDep();
        }

        donnees.put("depart", coordDepart);

        System.out.println("joueur"+joueurCourant+": donnez une case d'arrivée:");
        for(Position courante: list)
            System.out.print(courante.getX()+""+courante.getY()+", ");
        System.out.print("\n");
        caseArr = scan.next();
        determinerCoord(caseArr, coordArr);

        while(!list.contains(echiquier.getCasse(coordArr[0], coordArr[1]))){
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

    public boolean coordNonCoherente(int a){
        return a > 8 || a < 0;
    }


    /**
     *
     * @param joueurCourant joueur qui est en train de jouer
     * @param echiquier plateau du jeu
     * @param coordDepart coordonnées de la case sélectionnée par le joueur
     * @return le fait que la case sélectionnée contienne une pièce que possède le joueur courant.
     */
    public boolean isPieceSelecDansListePiecesJoueur(Joueur joueurCourant, Plateau echiquier, int[] coordDepart){
        for (int i=0; i<joueurCourant.getPieces().length; i++){
            if(echiquier.getCasse(coordDepart[0], coordDepart[1]).getPiece()!=null) {
                if (echiquier.getCasse(coordDepart[0], coordDepart[1]).getPiece().equals(joueurCourant.getPieces()[i]))
                    return true;
            }
        }
        return false;
    }

    public Piece traiterDeplacement(Plateau plateau, HashMap<String, int[]> donnees, Joueur joueurCourant, Joueur joueurAdverse){
        Piece pieceDeplacee, pieceMorte;

        int[] depart =  donnees.get("depart");
        int[] arrivee =  donnees.get("arrivee");

        pieceMorte = null;
        pieceDeplacee = plateau.getCasse(depart[0], depart[1]).getPiece();

        if(plateau.getCasse(arrivee[0], arrivee[1]).isOccupe()) {
            pieceMorte = plateau.getCasse(arrivee[0], arrivee[1]).getPiece();
        }

        plateau.getCasse(arrivee[0], arrivee[1]).setPiece(plateau.getCasse(depart[0], depart[1]).getPiece());
        plateau.getCasse(depart[0], depart[1]).unsetPiece();

        pieceDeplacee.setCoordX(arrivee[0]);
        pieceDeplacee.setCoordY(arrivee[1]);

        return pieceMorte;
    }

}
