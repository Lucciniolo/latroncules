
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * La classe Pion est abstraite. Elle ne peut etre instancié.
 * Elle sert de base aux classes Larron et latroncule : elle factorise leur fonctionnalités communes :
 * 
 * Un pion a une abscisse et une ordonnée, est sur une case, a une couleur et une image.
 * 
 * Cette classe acceuille un addMouseListener trés important. Il est le point de départ de nombreux mécanismes du jeu
 * 
 * 
 */
public abstract class Pion implements java.io.Serializable {
    protected static final long serialVersionUID = 2; // Pour permettre de serialiser

    private int x;
    private int y;

    private GroupeDePions groupe;
    private Case saCase;
    protected ImageIcon icone;
    protected JLabel img;
    protected String couleur;
    protected Pion lePion;
    transient protected Damier damier;
    
    public Pion(int pX, int pY, GroupeDePions pGroupe, Case pCase, Damier pDamier) {
        this.damier = pDamier;
        setCoordonnees(pX, pY);
        this.groupe = pGroupe;
        this.saCase = pCase;
        this.lePion = this;
    }

     /**
      * Fonction abstraite. N'a de réalité que dans les classes filles devant l'implémenter
      * 
     *  Affecte les images representant le Larron en fonction de sa couleur
     *  Place le listener sur l'image contenant des fonctionnalités propre au déplacement des pions
     */
    public abstract void setCouleurEtListener();
    
    /**
     *  Attention ! Seule fonction à appeler pour modifier les coordonnées
     * En effet, elle permet d'affecter a la case de coordonnées pX,pY son pion
     *
     */
    public void setCoordonnees(int pX, int pY) {
        this.setX(pX);
        this.setY(pY);
        damier.cases[pX][pY].setPion(this);
    }

    public JLabel getImg() {
        return this.img;
    }

    /**
     * N'a de sens qu'au sein de mouseClicked(MouseEvent e) (de Latroncule et Larron)
     * Factorise les fonctions communes de seDeplacer de latroncule et de Larron
     * Déplace les pions sur toutes les cases vides
     */
    public void seDeplacer() {
        // Collore les déplacements permis et encadre la case selectionnée  
        if (    (this.getSaCase().getDamier().pionSelectionne == null) &&
                (dePlacementsPermis().isEmpty() !=  true) 
               && (this.groupe.getProprietaire().equals(this.getSaCase().getDamier().getaQuiDeJouer()))
           )
        {
                collorerDeplacementPermis(true);
                // La case selecionnee passe en jaune
                getSaCase().setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
                getSaCase().supprimerPion();
                this.getSaCase().getDamier().pionSelectionne = this;
        }

    }

    /**
     *
     * Colorre les cases ou le déplacement est permis en vert
     * 
     */
    public void collorerDeplacementPermis(boolean bool) {
        if (dePlacementsPermis() != null) {
            if (bool == true) {
                ArrayList<int[]> coordonneesPermises = dePlacementsPermis();
                for (int[] c : coordonneesPermises) {
                    this.getSaCase().getDamier().cases[c[0]][c[1]].setBorder(BorderFactory.createLineBorder(Color.green, 5));
                }
            } else {
                ArrayList<int[]> coordonneesPermises = dePlacementsPermis();
                for (int[] c : coordonneesPermises) {
                    this.getSaCase().getDamier().cases[c[0]][c[1]].setBorder(BorderFactory.createEmptyBorder());
                }

            }
        }
    }

    /**
     *
     *  Fonction abstraite. N'a de réalité que dans les classes filles devant l'implémenter.
     * Permes d'indiquer les déplacements permis par chaque type de pion
     */
    public abstract ArrayList<int[]> dePlacementsPermis();
   
    /**
     *@return  les informations du pion (propriétaire, position X et Y)
     */
    public String toString() {
        return "Pion de " + this.groupe.getProprietaire().getPrenom()
                + " position X : " + this.getX()
                + ", Y : " + this.getY();
    }


    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the saCase
     */
    public Case getSaCase() {
        return saCase;
    }

    /**
     * @param saCase the saCase to set
     */
    public void setSaCase(Case saCase) {
        this.saCase = saCase;
    }

    /**
     * @return the groupe
     */
    public GroupeDePions getGroupe() {
        return groupe;
    }

    /**
     * @param groupe the groupe to set
     */
    public void setGroupe(GroupeDePions groupe) {
        this.groupe = groupe;
    }
}
