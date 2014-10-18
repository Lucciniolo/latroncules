


/**
 * 
 * Classe Joueur la variable nbPartie represente les parties finies 
 * 
 */

public class Player implements java.io.Serializable{

    private String nom;
    private String prenom;
    private String sexe;
    private String avatar;
    private int head2head;
    private int nbPartie;
    private int nbPartieEncours;

    public Player(String nom, String prenom, String sexe, String avatar) {
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.avatar = avatar;
        this.head2head = 0;
        this.nbPartie = 0;
        this.nbPartieEncours = 0;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getHead2head() {
        return head2head;
    }

    public void setHead2head(int head2head) {
        this.head2head = head2head;
    }

    public int getNbPartie() {
        return nbPartie;
    }

    public void setNbPartie(int nbPartie) {
        this.nbPartie = nbPartie;
    }

    public int getNbPartieEncours() {
        return nbPartieEncours;
    }

    public void setNbPartieEncours(int nbPartieEncours) {
        this.nbPartieEncours = nbPartieEncours;
    }
    

    @Override
    public String toString() {
        String jeux;
        String gagne;
        String perdre;
        String chaine = nom + " " + prenom + " ";
        if (sexe == "Femme") {
            
            jeux = "jouée.";
        } else {
           
            jeux = "joué.";
        }
        
         if (head2head > 1) {
            
            gagne  = " parties gagnées";
        } else {
           
            gagne = " partie gagnée";
        }
         
         if ((nbPartie-head2head) > 1) {
            
            perdre  = " perdues";
        } else {
           
            perdre = " perdue";
        }
        
        if(nbPartie != 0){
            float ratio = (float)head2head/(float)nbPartie;
            chaine = chaine + "qui a un ratio de " +ratio+" pour "+head2head+gagne+" et "+(nbPartie-head2head)+perdre;
        }else{
            chaine = chaine + "qui n'a jamais "+jeux;
        } 
        return chaine;
    }    
}
