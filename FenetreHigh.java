
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

/**
 *Fenetre high score
 * 
 */
public class FenetreHigh extends JFrame {

    public FenetreHigh() {
        this.setSize(550, 500);
        this.setName("Statistique");
        this.setResizable(false);
        
        // trie de l arrayList contenant les joueurs 
        ArrayList<Player> byHead = new ArrayList<Player>();
        byHead.addAll(AmariOthmann.listePlayer);
        Collections.sort(byHead, new PlayerComparableHead());


        ArrayList<Player> byRate = new ArrayList<Player>();
        byRate.addAll(AmariOthmann.listePlayer);
        Collections.sort(byRate, new PlayerComparableRatio());

        // crée les pannel correspondant 
        JPanel jp1 = onglet(byHead);
        JPanel jp2 = onglet(byRate);

        //premet de faire des onglets
        JTabbedPane panelOnglet = new JTabbedPane();
        panelOnglet.addTab("Meilleur Head2Head", null, jp1);
        panelOnglet.addTab("Meilleur ratio", null, jp2);

        this.getContentPane().add(panelOnglet);
        this.setVisible(true);
        this.setLocationRelativeTo(this.getParent());
    }

    /*
     * permet de créer un onglet
     * 
     */
    
    private JPanel onglet(ArrayList<Player> array) {
        JPanel jonglet = new JPanel();
        jonglet.setLayout(null);
        //Affichage des avatars des joueurs

        ImageIcon resultat = Function.redimensionner("Avatar" + Constante.S + array.get(0).getAvatar(), 100, 100);
        JLabel avatarUn = new JLabel();
        avatarUn.setIcon(resultat);
        avatarUn.setBounds(200, 10, 100, 100);
        jonglet.add(avatarUn);


        if (array.size() > 1) {
            resultat = Function.redimensionner("Avatar" + Constante.S + array.get(1).getAvatar(), 100, 100);
            JLabel avatarDeux = new JLabel();
            avatarDeux.setIcon(resultat);
            avatarDeux.setBounds(100, 35, 100, 100);
            jonglet.add(avatarDeux);

            if (array.size() > 2) {
                resultat = Function.redimensionner("Avatar" + Constante.S + array.get(2).getAvatar(), 100, 100);
                JLabel avatarTrois = new JLabel();
                avatarTrois.setIcon(resultat);
                avatarTrois.setBounds(300, 60, 100, 100);
                jonglet.add(avatarTrois);
            }
        }
        //Affichagedu poduim
        resultat = Function.redimensionner("Image" + Constante.S + "un.jpg", 100, 100);
        JLabel un = new JLabel();
        un.setIcon(resultat);
        un.setBounds(200, 110, 100, 100);

        resultat = Function.redimensionner("Image" + Constante.S + "deux.jpg", 100, 75);
        JLabel deux = new JLabel();
        deux.setIcon(resultat);
        deux.setBounds(100, 122, 100, 100);

        resultat = Function.redimensionner("Image" + Constante.S + "trois.jpg", 100, 50);
        JLabel trois = new JLabel();
        trois.setIcon(resultat);
        trois.setBounds(300, 135, 100, 100);


        JTextArea list = new JTextArea();
        list.setEditable(false);
        list.setBounds(0, 260, 545, 200);

        // ajout dans la liste
        String chaine = "";
        int i = 0;
        for (Player p : array) {
            i++;
            chaine = chaine + i + ": " + p.toString() + "\n";
        }
        list.setText(chaine);
        list.setBackground(null);
        jonglet.add(list);
        jonglet.add(trois);
        jonglet.add(deux);
        jonglet.add(un);


        return jonglet;

    }
    
    /*
     * Compare les joueur par rapport au Ratio
     */
    public class PlayerComparableRatio implements Comparator<Player> {

        @Override
        
        public int compare(Player p, Player p2) {
            int resultat = -1;

            // evite une division par 0 si le joueur a un nb de partie egale a 0
            if (p.getNbPartie() != 0 && p2.getNbPartie() != 0) {
                float ratio =  (float) p.getHead2head() / (float)p.getNbPartie();
                float ratio2 = (float)p2.getHead2head() /(float) p2.getNbPartie();


                if (ratio > ratio2) {
                    resultat = -1;
                }
                if (ratio < ratio2) {
                    resultat = 1;
                }
                if (ratio == ratio2) {
                    resultat = 0;
                }
            }
            
            return resultat;

        }
    }
    /*
     * Compare les joueur par rapport au head2head
     */
    public class PlayerComparableHead implements Comparator<Player> {

        
        @Override
        public int compare(Player p, Player p2) {
            int resultat = 0;
            if (p.getHead2head() > p2.getHead2head()) {
                resultat = -1;
            }
            if (p.getHead2head() < p2.getHead2head()) {
                resultat = 1;
            }
            if (p.getHead2head() == p2.getHead2head()) {
                resultat = 0;
            }
            return resultat;
        }
    }
}
