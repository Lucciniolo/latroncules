
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * fenetre qui permet de choisir les joueurs d'une partie
 */
class FenetreChoixP extends JFrame {

    static GroupeDePions pionsJoueur1;
    static GroupeDePions pionsJoueur2;
    public static Player aQuiDeJouer;
    static Damier damier;

    public FenetreChoixP() {
        this.setSize(500, 500);
        this.setName("Choix des joueurs");
        this.setResizable(false);
        this.setLayout(new GridLayout(3, 1));

        JComboBox comboBoxP1 = new JComboBox();
        JComboBox comboBoxP2 = new JComboBox();

        comboBoxP1.addItem("Joueur 1");
        comboBoxP2.addItem("Joueur 2");

        for (Player p : AmariOthmann.listePlayer) {

            comboBoxP1.addItem(p.toString());
            comboBoxP2.addItem(p.toString());
        }

        JButton ok = new JButton("Jouer");
        FenetreChoixP.Ecouteur ecoute = new Ecouteur(comboBoxP1, comboBoxP2, ok, this);
        ok.addActionListener(ecoute);

        this.add(comboBoxP1);
        this.add(comboBoxP2);
        this.add(ok);

        this.setVisible(true);
        this.setLocationRelativeTo(this.getParent());
    }

    public class Ecouteur implements ActionListener {

        private JComboBox comboBoxP1;
        private JComboBox comboBoxP2;
        private JButton ok;
        private FenetreChoixP win;

        public Ecouteur(JComboBox comboBoxP1, JComboBox comboBoxP2, JButton ok, FenetreChoixP win) {
            this.comboBoxP1 = comboBoxP1;
            this.comboBoxP2 = comboBoxP2;
            this.ok = ok;
            this.win = win;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().equals(ok)) {
                if (comboBoxP1.getSelectedIndex() == comboBoxP2.getSelectedIndex() && comboBoxP2.getSelectedIndex() != 0 && comboBoxP1.getSelectedIndex() != 0) {
                    JOptionPane.showMessageDialog(null, "Le joueur 1 doit etre different du joueur 2");
                } else if (comboBoxP1.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Veuillez choisir le joueur 1");
                } else if (comboBoxP2.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Veuillez choisir le joueur 2");
                } else {
                    Player pA = AmariOthmann.listePlayer.get(comboBoxP1.getSelectedIndex() - 1);
                    Player pB = AmariOthmann.listePlayer.get(comboBoxP2.getSelectedIndex() - 1);
                    if (pA.getNbPartieEncours() > 1) {
                        JOptionPane.showMessageDialog(null, pA.getNom() + " " + pA.getPrenom() + " a déja trop de partie en cours");
                    } else if (pB.getNbPartieEncours() > 1) {
                        JOptionPane.showMessageDialog(null, pB.getNom() + " " + pB.getPrenom() + " a déja trop de partie en cours");
                    } else {
                       
                        pA.setNbPartieEncours(pA.getNbPartieEncours() + 1);
                        pB.setNbPartieEncours(pB.getNbPartieEncours() + 1);

                            // On créé deux groupes de pions avec leur position, leur joueur et leur couleur
                            pionsJoueur1 = new GroupeDePions(pA, "haut", "rouge", damier);
                            pionsJoueur2 = new GroupeDePions(pB, "bas", "bleu", damier);
                            aQuiDeJouer = pA; // Le joueur 1 commence

                            Partie partie1 = new Partie(pA, pB, pionsJoueur1, pionsJoueur2, aQuiDeJouer);

                            damier = new Damier(partie1);
                            partie1.setDamier(damier);
                            pionsJoueur1.setDamier(damier);
                            pionsJoueur2.setDamier(damier);

                            damier.initialiseDamier(true);
                            win.dispose();
                        
                    }
                }
            }
        }
    }
}
