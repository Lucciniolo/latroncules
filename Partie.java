/**
 * La classe partie permet de stocker toutes les informations inhérente à une partie de latroncule à savoir :
 * Les joueurs, leurs pions ainsi que le nom du joueur qui a la main
 * 
 * Cette structuration des données permet de les enregistrer et de les retrouver facilement
 * 
 */
public class Partie implements java.io.Serializable, Cloneable{
        protected static final long serialVersionUID = 3; // Pour permettre de serialiser

    private Player joueur1;
    private Player joueur2;
    
    private GroupeDePions pionsJoueur1;
    private GroupeDePions pionsJoueur2;
    
    private Player aQuiDeJouer;
    transient private Damier damier; // transient Permet d'exclure le damier de la serialisation
    
    public Partie(Player pJoueur1, Player pJoueur2, GroupeDePions PPionsJoueur1, GroupeDePions PPionsJoueur2, Player pAQuiDeJouer){
    joueur1 = pJoueur1;
    joueur2 = pJoueur2;
    
    pionsJoueur1 = PPionsJoueur1;
    pionsJoueur2 = PPionsJoueur2;
    
    aQuiDeJouer = pAQuiDeJouer;
}

    /**
     * @return the joueur1
     */
    public Player getJoueur1() {
        return joueur1;
    }

    /**
     * @param joueur1 the joueur1 to set
     */
    public void setJoueur1(Player joueur1) {
        this.joueur1 = joueur1;
    }

    /**
     * @return the joueur2
     */
    public Player getJoueur2() {
        return joueur2;
    }

    /**
     * @param joueur2 the joueur2 to set
     */
    public void setJoueur2(Player joueur2) {
        this.joueur2 = joueur2;
    }

    /**
     * @return the pionsJoueur1
     */
    public GroupeDePions getPionsJoueur1() {
        return pionsJoueur1;
    }

    /**
     * @param pionsJoueur1 the pionsJoueur1 to set
     */
    public void setPionsJoueur1(GroupeDePions pionsJoueur1) {
        this.pionsJoueur1 = pionsJoueur1;
    }

    /**
     * @return the pionsJoueur2
     */
    public GroupeDePions getPionsJoueur2() {
        return pionsJoueur2;
    }

    /**
     * @param pionsJoueur2 the pionsJoueur2 to set
     */
    public void setPionsJoueur2(GroupeDePions pionsJoueur2) {
        this.pionsJoueur2 = pionsJoueur2;
    }

    /**
     * @return the aQuiDeJouer
     */
    public Player getaQuiDeJouer() {
        return aQuiDeJouer;
    }

    /**
     * @param aQuiDeJouer the aQuiDeJouer to set
     */
    public void setaQuiDeJouer(Player aQuiDeJouer) {
        this.aQuiDeJouer = aQuiDeJouer;
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

    public Object clone()
            throws CloneNotSupportedException {
                return super.clone();
    }
    
}
