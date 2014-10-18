import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * Classe Larron héritant de la classe Pion
 * Contient donc toutes les spécificités de cette classe
 * 
 * Un larron est représenté par une image de tour d'échec
 * 
 * Le latroncule se déplace d'autant de case qu'il lui est permis sur les cotés et de haut en bas les règles du jeu
 * Il ne peut pas sauter de pions
 */

public class Larron extends Pion implements java.io.Serializable{

    public Larron(int pX, int pY, GroupeDePions pGroupe, Case pCase, String pCouleur, Damier pDamier) {
        super(pX, pY, pGroupe, pCase, pDamier);
        this.couleur = pCouleur;
        setCouleurEtListener();
    }
    
    /**
     *  Affecte les images representant le Larron en fonction de sa couleur
     *  Place le listener sur l'image contenant des fonctionnalités propre au déplacement des pions
     */
    public  void setCouleurEtListener(){ 
        // Si le pion est bleu, on lui affecte l'image de la tour bleu
        if (this.couleur.equals("bleu")) {
            icone = new ImageIcon("Image" +  Constante.S + "tourBleu.png");
            img = new JLabel(icone);
        }
        if (this.couleur.equals("rouge")) {
            icone = new ImageIcon("Image" +  Constante.S + "tourRouge.png");
            img = new JLabel(icone);
        }
 
        img.addMouseListener(new MouseListener() {
            @Override
            // Ce qui ce passe lorque l'on clique sur un latroncule
            public void mouseClicked(MouseEvent e) {
                lePion.seDeplacer();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    /**
     * On appelle la fonction seDeplacer de la classe mere qui est propre a toutes les classes
     */
    public void seDeplacer() {
                    // 
            super.seDeplacer();
  //      }

    }

     /**
     * @return des tableaux contenant les coordonnées des deplacements permis pour le Larron selectionné 
     */
    public ArrayList<int[]> dePlacementsPermis() {
        ArrayList<int[]> coordonneesPermises = new ArrayList<int[]>();
        int x = getX();
        int i = 1;
        boolean stop = false;

        // Collore toutes les cases ou il est possibles d'aller a la DROITE du Larron
        while (((x + i) < 8) && (stop == false)) {
            if (this.getSaCase().getDamier().cases[x + i][getY()].getPion() == null) {
                int[] coordonnees = {x + i, getY()};
                coordonneesPermises.add(coordonnees);
            } else {
                stop = true;
            }
            i++;
        }

        // Collore toutes les cases ou il est possibles d'aller a la GAUCHE du Larron
        i = 1;
        stop = false;
        while (((x - i) >= 0) && (stop == false)) {
            if (this.getSaCase().getDamier().cases[x - i][getY()].getPion() == null) {
                int[] coordonnees = {x - i, getY()};
                coordonneesPermises.add(coordonnees);
            } else {
                stop = true;
            }
            i++;
        }
        // HAUT ET BAS

        int y = getY();
        i = 1;
        stop = false;

        // Collore toutes les cases ou il est possibles d'aller en BAS du Larron
        while (((y + i) < 8) && (stop == false)) {
            if (this.getSaCase().getDamier().cases[getX()][y + i].getPion() == null) {
                int[] coordonnees = {getX(), y + i};
                coordonneesPermises.add(coordonnees);
            } else {
                stop = true;
            }
            i++;
        }
        i = 1;
        stop = false;

        // Collore toutes les cases ou il est possibles d'aller en HAUT du Larron
        while (((y - i) >= 0) && (stop == false)) {
            if (this.getSaCase().getDamier().cases[getX()][y - i].getPion() == null) {
                int[] coordonnees = {getX(), y - i};
                coordonneesPermises.add(coordonnees);
            } else {
                stop = true;
            }
            i++;
        }
    return coordonneesPermises ;
    }
}