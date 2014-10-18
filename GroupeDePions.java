
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * Liste de pions
 * Contient les informations quant à leur couleur et leur position sur le damier
 */

public class GroupeDePions extends CopyOnWriteArrayList<Pion> implements java.io.Serializable {
    // CopyOnWriteArrayList  Pour eviter Exception in thread "AWT-EventQueue-0" java.util.ConcurrentModificationException losque l'on mange un pion

    private Player proprietaire;
    private String hautOuBas; // Représente la position de début des pions
    private String Couleur;
    transient private Damier damier;

    /**
     *
     * Unique constructeur.
     * 
     */
    public GroupeDePions(Player pProprietaire, String pHautOuBas, String pCouleur, Damier pDamier) {
        this.proprietaire = pProprietaire;
        this.hautOuBas = pHautOuBas;
        this.Couleur = pCouleur;
        this.damier = pDamier;
    }

    /**
     * Remplit le goupe de pions de 8 larrons et 8 latroncules
     * Leur affecte leur coordonnées respective au bébut de la partie
     */
    public void initialiser() {
        // Si le groupe de pions a comme position initiale le haut du damier ou le bas (voir le else)
        // alors on remplit le groupe de pions
        String position = this.getHautOuBas();
        GroupeDePions g = this;
        int x;

        if (position.equals("haut")) {   
            for ( x = 0; x < 8; x++) {
                this.add(new Larron(x, 0, this, damier.cases[x][0], this.getCouleur(), damier));
                this.add(new Latroncule(x, 1, this, damier.cases[x][1], this.getCouleur(), damier));
            }
        } else if (position.equals("bas")) {
            for (x = 0; x < 8; x++) {
                this.add(new Larron(x, 7, this, damier.cases[x][7], this.getCouleur(), damier));
                this.add(new Latroncule(x, 6, this, damier.cases[x][6], this.getCouleur(), damier));
            }
        }
        
        
        
        
        
        
        
    }

    /**
     * @return the hautOuBas
     */
    public String getHautOuBas() {
        return hautOuBas;
    }

    /**
     * @param hautOuBas the hautOuBas to set
     */
    public void setHautOuBas(String hautOuBas) {
        this.hautOuBas = hautOuBas;
    }

    /**
     * @return the damier
     */
    public Damier getDamier() {
        return damier;
    }

    /**
     * @param damier the damier to set
     */
    public void setDamier(Damier damier) {
        this.damier = damier;
    }

    /**
     * @return the proprietaire
     */
    public Player getProprietaire() {
        return proprietaire;
    }

    /**
     * @param proprietaire the proprietaire to set
     */
    public void setProprietaire(Player proprietaire) {
        this.proprietaire = proprietaire;
    }

    /**
     * @return the Couleur
     */
    public String getCouleur() {
        return Couleur;
    }

    /**
     * @param Couleur the Couleur to set
     */
    public void setCouleur(String Couleur) {
        this.Couleur = Couleur;
    }
    
}
