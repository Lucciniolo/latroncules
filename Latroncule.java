
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Classe Latroncule héritant de la classe Pion
 * Contient donc toutes les spécificités de cette classe
 * 
 * Un latroncule est représenté par une image de pion d'échec
 * 
 * Le latroncule se déplace que d'une case en avant selon les règles du jeu
 * 
 */
public class Latroncule extends Pion implements java.io.Serializable{
    protected static final long serialVersionUID = 2; // Pour permettre de serialiser

    public Latroncule(int pX, int pY, GroupeDePions pGroupe, Case pCase, String pCouleur, Damier pDamier) {
        super(pX, pY, pGroupe, pCase, pDamier);

        this.couleur = pCouleur;
      
         setCouleurEtListener();
    }
    
    /**
     *  Affecte les images representant le latroncule en fonction de sa couleur
     *  Place le listener sur l'image contenant des fonctionnalités propre au déplacement des pions
     */
    public  void setCouleurEtListener(){  
          // Si le pion est bleu, on lui affecte l'image de la tour bleu
        if (this.couleur.equals("bleu")) {
            icone = new ImageIcon("Image" +  Constante.S + "pionBleu.png");
            img = new JLabel(icone);
        }
        if (this.couleur.equals("rouge")) {
            icone = new ImageIcon("Image" +  Constante.S + "pionRouge.png");
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
        super.seDeplacer();
               
    }
    
     /**
     * @return une liste de  tableaux contenant les coordonnées des deplacements permis pour le Larron selectionné 
     */
    
    public ArrayList<int[]> dePlacementsPermis() {
        ArrayList<int[]> coordonneesPermises = new ArrayList<int[]>();

        int y = getY();

        // ajoute toutes les coordonnées des cases ou il est possibles d'aller pour le Latroncule
        if (this.getGroupe().getHautOuBas().equals("bas") && ( y!=0)) {
            if (this.getSaCase().getDamier().cases[getX()][y - 1].getPion() == null) {
                int[] coordonnees = {getX(), y - 1};
                coordonneesPermises.add(coordonnees);
            } 
        }
        if (this.getGroupe().getHautOuBas().equals("haut")&& ( y!=7)) {
            if (this.getSaCase().getDamier().cases[getX()][y + 1].getPion() == null) {
                int[] coordonnees = {getX(), y + 1};
                coordonneesPermises.add(coordonnees);
            } 
        }            
            
    return coordonneesPermises ;
    }
}