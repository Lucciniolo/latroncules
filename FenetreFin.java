
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * Fenetre affichée lors de la fin d'une partie
 */

public class FenetreFin extends JFrame {
    // si Player p est null cela veut dire que les joueurs ont fait match nul
    // sinon le Player p a gagné
    Damier damier;
    public FenetreFin(Player p, Damier pDamier) {
        this.damier = pDamier;
        JLabel txt;
        this.setLayout(null);
        this.setResizable(false);
        JButton ok = new JButton("OK");
        Ecouteur ecoute = new Ecouteur(this,ok);
        ok.addActionListener(ecoute);
        Font font = new Font("Arial", Font.BOLD, 20);
        
        
        if(p != null){
            this.setSize(440, 400);
            ImageIcon resultat = Function.redimensionner("Avatar" + Constante.S + p.getAvatar(), 435, 300);
            JLabel image = new JLabel();
            image.setIcon(resultat);
            image.setBounds(0, 0, 435, 300);
            this.add(image);
            txt = new JLabel(p.getNom()+" "+p.getPrenom()+" a remporté la bataille.");
            txt.setFont(font);
            txt.setBounds(0, 320, 440, 20);
            ok.setBounds(0, 345 ,434 ,30 );
        }else{
            this.setSize(440, 150);
            txt = new JLabel("Merci d'avoir joué.");
            txt.setFont(font);
            txt.setBounds(130, 20, 435, 40);
            ok.setBounds(0, 90 ,434 ,30 );
        }
        
               
        
        
        
        
        this.add(txt);
        this.add(ok);
        this.setVisible(true);
        this.setLocationRelativeTo(this.getParent());
        
        
    }
    
    private class Ecouteur implements ActionListener {

        private FenetreFin win;
        private JButton ok;

        public Ecouteur(FenetreFin win, JButton ok) {
            this.ok = ok;
            this.win = win;
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(ok)) {
                win.setVisible(false);
            }
        }
    }
}
