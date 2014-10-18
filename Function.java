
import javax.swing.JLabel;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
/**
* Cette class permert de centraliser les fonction
*/
public class Function {

    /**
     * Que faire lors de l'appuie sur "Nouveau jeux"
    */
    public static void NewGame() {
        if (AmariOthmann.listePlayer.size() < 2) {
            JOptionPane.showMessageDialog(null, "Vous devez créer au moins deux joueurs");
        } else {
            FenetreChoixP fenetreChoixP = new FenetreChoixP();
        }

    }
    /**
     * Que faire lors de la fin d'un partie
     */
    public static void finPartie(Player p, Damier pDamier) {
        System.out.println("Sauvegarde des joueurs ...");
        AmariOthmann.window.enregistrerJoueurs("joueurs.dat");
        new FenetreFin(p, pDamier);

    }
    /**
     * Que faire lorsque le joueur appuie sur option
     */ 
    public static void Option() {
        if (AmariOthmann.listePlayer.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vous devez créer au moins un joueur");
        } else {
            new FenetreOption();
        }
    }
    
    /**
     * Que faire lors de l'appuie sur "Nouveau Joueur"
     */
    public static void NewPlayer() {
        FenetreNewP fenetreNewP = new FenetreNewP();
    }

    /**
     * Que faire lors de l'appuie sur "High Score"
     */
    public static void HighScore() {
        boolean bool = false;
        if (AmariOthmann.listePlayer.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vous devez créer au moins un joueur");
        } else {
            for (Player p : AmariOthmann.listePlayer) {
                if (p.getNbPartie() != 0) {
                    bool = true;
                }
            }
            if (bool) {
                FenetreHigh fenetreHigh = new FenetreHigh();
            } else {
                JOptionPane.showMessageDialog(null, "Aucune partie n'a été jouée.");
            }
        }
    }

   /**
    * Permet de redimensionner une image 
    */
    public static ImageIcon redimensionner(String chemin, int width, int height) {
        JLabel image = new JLabel();
        image.setSize(width, height);
        ImageIcon icon = new ImageIcon(chemin);
        //Redimensionnement 
        ImageIcon resultat = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        return resultat;

    }

    /**
     * Methode permettant la copie d'un fichier
     */ 
    public static boolean copier(File source, File destination) {
        boolean resultat = false;
        // Declaration des flux 
        java.io.FileInputStream sourceFile = null;
        java.io.FileOutputStream destinationFile = null;
        try {
            // Création du fichier : 
            destination.createNewFile();
            // Ouverture des flux 
            sourceFile = new java.io.FileInputStream(source);
            destinationFile = new java.io.FileOutputStream(destination);
            // Lecture par segment de 0.5Mo  
            byte buffer[] = new byte[512 * 1024];
            int nbLecture;
            while ((nbLecture = sourceFile.read(buffer)) != -1) {
                destinationFile.write(buffer, 0, nbLecture);
            }

            // Copie réussie 
            resultat = true;
        } catch (java.io.FileNotFoundException f) {
        } catch (java.io.IOException e) {
        } finally {
            // Quoi qu'il arrive, on ferme les flux 
            try {
                sourceFile.close();
            } catch (Exception e) {
            }
            try {
                destinationFile.close();
            } catch (Exception e) {
            }
        }
        return (resultat);
    }
}
