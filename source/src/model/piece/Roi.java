package model.piece;

import model.pLateau.Plateau;
import model.pLateau.Position;
import model.parties.EchecEtMat;

import java.util.List;
//import org.jetbrains.annotations.NotNull;


public class Roi extends Piece {


    //Attribue
    private boolean premierDeplacement=true;

    //Constructeur
    public Roi(int x, int y, Couleur couleur) {
        super(x, y, couleur,Type.ROI);
    }

    //Methode
    public void setPremierDeplacement(){this.premierDeplacement = false;}

    public boolean getPremierDeplacement(){ return this.premierDeplacement;}

    public boolean isPremierDeplacement() {
        return premierDeplacement;
    }


    public void setListeDep( Plateau plateau){
        int[][] dep = {{1, 1}, {-1, 1}, {1, -1}, {-1, -1}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        getListeDep().clear();
        getListeProtecDep().clear();

        for(int i=0; i<8; i++){
            deplacementPossibleRoi(plateau, dep[i][0], dep[i][1]);
        }

        switch (roque(plateau, this.getCoordX(), this.getCoordY())){
            case 1:
                getListeDep().add(plateau.getCase(this.getCoordX()-2, this.getCoordY()));
                break;
            case 2:
                getListeDep().add(plateau.getCase(this.getCoordX()+2, this.getCoordY()));
                break;
            case 3:
                getListeDep().add(plateau.getCase(this.getCoordX()-2, this.getCoordY()));
                getListeDep().add(plateau.getCase(this.getCoordX()+2, this.getCoordY()));
                break;
        }

    }

    public void deplacementPossibleRoi(Plateau plateau, int tmpX, int tmpY) {
        Position caseTmp;
        int x = getCoordX();
        int y = getCoordY();

        if(x+tmpX > LIMIT_SUP || x+tmpX < LIMIT_INF || y+tmpY > LIMIT_SUP || y+tmpY < LIMIT_INF)
            return;

        caseTmp = plateau.getCase(x + tmpX, y + tmpY);
        int a = nombreDePiecesMenacantLaCase(plateau, x+tmpX, y+tmpY); // Pieces qui menacent la case accesible par le roi


        if (a == 0) { // Si aucune pièce ne menace la case, cette dernière est ajoutée à la liste de déplacement
            if (caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() == this.getCouleur()) {
                getListeProtecDep().add(caseTmp);
            }
            if (!caseTmp.isOccupe() || caseTmp.isOccupe() && caseTmp.getPiece().getCouleur() != this.getCouleur()) {
                getListeDep().add(caseTmp);
            }
        }
    }

    public int nombreDePiecesMenacantLaCase(Plateau plateau, int x, int y){
        int nbMenaces=0;
        List<Position> piecesMenacantLaCase = EchecEtMat.isPieceMenaOrProtecParAutre(x, y, plateau, 2);
        for (Position menace: piecesMenacantLaCase) {
            if (menace.getPiece().getCouleur()!=this.getCouleur())
                nbMenaces+=1;
        }
        piecesMenacantLaCase = EchecEtMat.isPieceMenaOrProtecParAutre(x, y, plateau, 1);
        for (Position menace: piecesMenacantLaCase) {
            if (menace.getPiece().getCouleur()!=this.getCouleur())
                nbMenaces+=1;
        }
        return nbMenaces;
    }

    public int roque(Plateau plateau, int x, int y){ // 0 = pas de roque, 1= roque gauche, 2 = roque droit, 3 = deux roques dispo
        int xTour=0, yTour, xDep=1, roqueDispo=0;
        Position caseTmp;

        yTour = this.getCouleur()==Couleur.BLANC ? 7 : 0;

        if (!this.premierDeplacement)
            return roqueDispo;

        for (int i=0; i<2; i++){
            if (plateau.getCase(xTour, yTour).isOccupe() && !(plateau.getCase(xTour, yTour).getPiece() instanceof Tour) || !plateau.getCase(xTour, yTour).isOccupe())
                continue;

            if (plateau.getCase(xTour, yTour).getPiece().getCouleur()==this.getCouleur() && plateau.getCase(xTour, yTour).getPiece() instanceof Tour){
                for (int j=0; j<3; j++){
                    if (xTour==0){ // Si la tour est à gauche
                        if (x-xDep<LIMIT_INF)
                            break;

                        caseTmp = plateau.getCase(x-xDep, y);
                        if (caseTmp.isOccupe() || nombreDePiecesMenacantLaCase(plateau, x-xDep, y)>0) // Si la case est occupée ou qu'elle est protégée par une pièce adverse
                            break;

                        if (x-xDep==1){
                            roqueDispo = 1;
                            xDep=1;
                        }

                        xDep += 1;
                    }
                    else{
                        if (x+xDep==7)
                            roqueDispo += 2;

                        if (x+xDep>LIMIT_SUP){
                            break;
                        }

                        caseTmp = plateau.getCase(x+xDep, y);
                        if (caseTmp.isOccupe() || nombreDePiecesMenacantLaCase(plateau, x+xDep, y)>0) { // Si la case est occupée ou qu'elle est protégée par une pièce adverse
                            break;
                        }

                        xDep += 1;
                    }
                }
            }
            xTour=7;
        }
        return roqueDispo;
    }
}
