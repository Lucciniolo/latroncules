
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * Fenetre pour le choix des options
 */
public class FenetreOption extends JFrame {

    public FenetreOption() {
        this.setSize(200, 200);
        this.setLayout(new GridLayout(3,1));
        
        JButton raz = new JButton("Effacer tous les joueurs");
        JButton score = new JButton("Remetre les scores à zéro");
        JButton modif = new JButton("Modifier un joueur");
        Ecouteur ecoute = new Ecouteur(raz,modif,score,this);
        raz.addActionListener(ecoute);
        score.addActionListener(ecoute);
        modif.addActionListener(ecoute);
        
        
        this.add(modif);
        this.add(score);
        this.add(raz);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
    public class Ecouteur implements ActionListener {
        private JButton raz;
        private JButton modif;
        private JButton score;
        private FenetreOption win;
        
        public Ecouteur(JButton raz, JButton modif,JButton score, FenetreOption win) {
            this.raz = raz;
            this.modif = modif;
            this.score = score;
            this.win = win;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource().equals(raz)){
                String txt = "Etes vous sur de vouloir effacer tous les joueurs ?";
                //l'appui sur raz affiche un popup qui demande de valider l'action 
                int retour = JOptionPane.showConfirmDialog(null, txt, "Suppresion des joueurs", JOptionPane.OK_CANCEL_OPTION);
                // Si la reponse est positive 
                if (retour == 0) {
                    // permet de supprimer l'avatar du joueur
                    File  f;
                    for(Player p : AmariOthmann.listePlayer){
                        f = new File ("Avatar" + Constante.S +p.getAvatar());
                        f.delete();
                    }
                    AmariOthmann.listePlayer.clear();
                    win.dispose();
                    JOptionPane.showMessageDialog(null, "Les joueurs ont été effacés.");
                }
            }else if(e.getSource().equals(modif)){
                new FenetreModif(win);                
            }else if(e.getSource().equals(score)){
                String txt = "Remetre les scores a zéro.";
                int retour = JOptionPane.showConfirmDialog(null, txt, "Suppresion des scores", JOptionPane.OK_CANCEL_OPTION);
                // Si la reponse est positive 
                if (retour == 0) {
                    for(Player p : AmariOthmann.listePlayer){
                        p.setNbPartie(0);
                        p.setNbPartieEncours(0);
                        p.setHead2head(0);
                    }
                    JOptionPane.showMessageDialog(null, "Les scores ont été remis à zéro.");
                }                
            }
        }
        
    }
    
}
