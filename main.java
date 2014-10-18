
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author OTHMANN Marwan (marwanothmann@gmail.com)   ET  AMARI Sofiane (amari.sofiane@gmail.com)
 * 
 * Dans le cadre du projet JAVA de fin d'année  
 * 
 * Classe : L3 FI MIAGE de Creteil 
 * Année : 2013
 * Professeur : Frédéric Gava (lacl.fr/gava/)
 * 
 * Classe main du projet. Acceuille l'unique fonction main du projet
 * Stock de manière static  
 */

public class AmariOthmann {

    /**
     *
     * @param nom fichier a ouvrir
     * @return une liste de joueurs trouvé dans un fichier que la fonction permet de sélectionner
     */
    public static CopyOnWriteArrayList<Player> charger(String pNomFichier) {
        try {
                File file = new File(pNomFichier);
                FileInputStream fichier = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fichier);
                return (CopyOnWriteArrayList<Player>) ois.readObject();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static CopyOnWriteArrayList<Player> listePlayer;
    public static Fenetre window;
    public static File f;
    
    public static void main(String[] args) {
      f = new File("joueurs.dat");
      
    if ( f.exists() ) {         // Tester si le fichier «joueurs» existe
            System.out.println("Le fichier joueurs.dat existe. On le charge."); 
            listePlayer = charger("joueurs.dat");        // Si le fichier existe, on le Charge
            for(Player p : listePlayer){
                p.setNbPartieEncours(0);
            }
     } else{        // Si il n'existe pas
             System.out.println("Le fichier joueurs.dat n'existe pas. On créer une liste de joueurs vide."); 
             listePlayer = new CopyOnWriteArrayList<Player>(); // on creer une nouvelle liste de joueurs
      }
        // L'enregistrement de la liste de joueurs se fait losque l'on quitte l'application
      
        // On lance la fenetre principale
        window = new Fenetre();        
    }
}
