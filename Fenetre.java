
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/* 
 * FENETRE PRINCIPAL
 */
public class Fenetre extends JFrame {

    static Damier damier2;
    static Damier damier;

    public Fenetre() {
        this.setSize(600, 740);
        this.setTitle("Latroncule 1.0");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(2, 1));
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                quitter();
            }
        });
        this.setJMenuBar(new BarMenu());
        this.add(new PanTop());
        this.add(new ChampBt());
        this.setVisible(true);

    }

    /*
     *Que faire lors de l'appuie sur "charger"
     */
    public void load() {
        System.out.println("Chargement demandé ...");

        Partie partie2 = charger();
        partie2.setDamier(damier2);

        damier2 = new Damier(partie2);
        damier2.setaQuiDeJouer(partie2.getaQuiDeJouer());

        partie2.getPionsJoueur1().setDamier(damier2);
        partie2.getPionsJoueur2().setDamier(damier2);
        damier2.afficherPionsSurDamier(true);
        damier2.syncroCompteursPion();


    }

    /* Ouvre un JFileChooser et déserialise un fichier. 
     * Ajoute les joueurs de la partie chargée dans la liste de joueurs si ceux-si en sont absents
     * Si ils sont présents, synchronise leurs statistiques
     * 
     * @return une partie enregistrée dans un fichier
     */
    public Partie charger() {
        JFileChooser choix = new JFileChooser();
        //int retour = choix.showOpenDialog(AmariOthmann.window);
        choix.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileFilter filter = new FileNameExtensionFilter("Latroncule file", "lat");
        choix.setAcceptAllFileFilterUsed(false);

        choix.setCurrentDirectory(new File("parties-enregistrees" + Constante.S));

        choix.addChoosableFileFilter(filter);
        int retour = choix.showOpenDialog(null);
        if (retour == JFileChooser.APPROVE_OPTION) {

            File file = choix.getSelectedFile();
            Partie partieCharge;
            try {
                FileInputStream fichier = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fichier);
                partieCharge = (Partie) ois.readObject();

            } catch (java.io.IOException e) {
                e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
            // Est-ce que les joueurs que l'on charge sont dans la liste ?

            boolean joueur1DansLaListe = false;
            boolean joueur2DansLaListe = false;


            // On parcours la liste de joueurs
            for (Player j : AmariOthmann.listePlayer) {
                // Le joueur 1 de la partie chargée est-il déjà dans la liste de joueur ?
                if (j.getAvatar().equals(partieCharge.getJoueur1().getAvatar())) {
                    // Si le joueur a trop de partie en cours, on  empeche l'importation
                    if (j.getNbPartieEncours() > 1) {
                        JOptionPane.showMessageDialog(null, j.getNom() + " " + j.getPrenom() + " a déja trop de partie en cours. Chargement de la partie impossible.");
                        return null;
                    } else {
                        j.setNbPartieEncours(j.getNbPartieEncours() + 1);
                    }

                    // Comme on change un joueur dans partieCharge, la référence du joueur aQuiDeJouer renvoit vers rien
                    // Il faut mettre à jour aquidejouer

                    // Si c'est au joueur 1 de jouer 
                    if (partieCharge.getJoueur1().equals(partieCharge.getaQuiDeJouer())) {
                        partieCharge.setaQuiDeJouer(j);
                        System.out.println("22 c'est à " + partieCharge.getaQuiDeJouer());
                    }

                    //Si le joueur est déjà dans la liste de joueur, on le remplace par sa version plus récente qui est dans la liste
                    partieCharge.setJoueur1(j);
                    partieCharge.getPionsJoueur1().setProprietaire(j);
                    joueur1DansLaListe = true;

                    System.out.println("Chargement : le joueur 1 a été trouvé dans la liste de joueur. Mise à jour du joueur.");
                } // Le joueur 2 est-il déjà dans la liste de joueur ?
                else if (j.getAvatar().equals(partieCharge.getJoueur2().getAvatar())) {
                    // Si le joueur a trop de partie en cours, on  empeche l'importation
                    if (j.getNbPartieEncours() > 1) {
                        JOptionPane.showMessageDialog(null, j.getNom() + " " + j.getPrenom() + " a déja trop de partie en cours. Chargement de la partie impossible.");
                        return null;
                    } else {
                        j.setNbPartieEncours(j.getNbPartieEncours() + 1);
                    }

                    // Comme on change un joueur dans partieCharge, la référence du joueur aQuiDeJouer renvoit vers rien
                    // Il faut mettre à jour aquidejouer
                    // si c'est au joueur 2 de joueur 
                    if (partieCharge.getJoueur2().equals(partieCharge.getaQuiDeJouer())) {
                        System.out.println("22 c'est à " + partieCharge.getaQuiDeJouer());
                        partieCharge.setaQuiDeJouer(j);
                        
                    }


                    // le joueur est déjà dans la liste de joueur, on le remplace par sa version plus récente qui est dans la liste
                    partieCharge.setJoueur2(j);
                    partieCharge.getPionsJoueur2().setProprietaire(j);
                    joueur2DansLaListe = true;

                    System.out.println("Chargement : le joueur 2 a été trouvé dans la liste de joueur. Mise à jour du joueur.");
                }
            }

            // Si le joueur 1 n'est pas dans la liste actuelle de joueur
            if (joueur1DansLaListe == false) {
                // On lui met l'avatar standart pour pas qu'il se retrouve sans avatar

                File fichier = new File("Avatar" + Constante.S + "avatar.jpg");
                File sauv = new File("Avatar" + Constante.S + partieCharge.getJoueur1().getNom() + "_" + partieCharge.getJoueur1().getPrenom() + ".jpg");
                Function.copier(fichier, sauv);

                partieCharge.getJoueur1().setAvatar(partieCharge.getJoueur1().getNom() + "_" + partieCharge.getJoueur1().getPrenom() + ".jpg");

                // On l'y ajoute
                AmariOthmann.listePlayer.add(partieCharge.getJoueur1());
                // On met son nombre de partie a 1
                partieCharge.getJoueur1().setNbPartieEncours(1);
                System.out.println("Chargement : le joueur 1 n'a pas été trouvé dans la liste de joueur. On l'y ajoute...");
                System.out.println("Chargement : ajout de : " + partieCharge.getJoueur1());

                // On remet a zeros les données du joueurs importé
                partieCharge.getJoueur1().setHead2head(0);
                partieCharge.getJoueur1().setNbPartie(0);

            }
            // Si le joueur 2 n'est pas dans la liste actuelle de joueur
            if (joueur2DansLaListe == false) {
                // On lui met l'avatar standart pour pas qu'il se retrouve sans avatar
                File fichier = new File("Avatar" + Constante.S + "avatar.jpg");
                File sauv = new File("Avatar" + Constante.S + partieCharge.getJoueur2().getNom() + "_" + partieCharge.getJoueur2().getPrenom() + ".jpg");
                Function.copier(fichier, sauv);

                partieCharge.getJoueur2().setAvatar(partieCharge.getJoueur2().getNom() + "_" + partieCharge.getJoueur2().getPrenom() + ".jpg");   // On l'y ajoute

                AmariOthmann.listePlayer.add(partieCharge.getJoueur2());
                // On met son nombre de partie a 1                    
                partieCharge.getJoueur2().setNbPartieEncours(1);
                System.out.println("Chargement : le joueur 2 n'a pas été trouvé dans la liste de joueur. On l'y ajoute ...");
                System.out.println("Chargement : ajout de : " + partieCharge.getJoueur2());
                
                // On remet a zeros les données du joueurs importé
                partieCharge.getJoueur2().setHead2head(0);
                partieCharge.getJoueur2().setNbPartie(0);
            }

            return partieCharge;
        }
        return null;

    }

    // Permet d'enregistrer les joueurs dans le fichier "joueurs"
    public void enregistrerJoueurs(String pNomFichier) {
        try {
            File file = new File(pNomFichier);
            //file.createNewFile();

            FileOutputStream fichier = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fichier);
            oos.writeObject(AmariOthmann.listePlayer);

            oos.flush();
            oos.close();
            System.out.println("Sauvegarde réussie");

        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Impossible de créer le fichier");
        }
    }

    // permet de quitte l'application proprement 
    // et sauvegarde la liste de joueurs
    public void quitter() {
        System.out.println("Sauvegarde de fichier demandé.");
        enregistrerJoueurs("joueurs.dat");
        System.exit(0);
    }

    private class BarMenu extends JMenuBar {

        private BarMenu() {


            JMenu mFichier = new JMenu("Fichier");
            JMenuItem newGame = new JMenuItem("Nouveau Jeu");
            JMenuItem charger = new JMenuItem("Charger");
            JMenuItem newPlayer = new JMenuItem("Nouveau Joueur");
            JMenuItem high = new JMenuItem("High score");
            JMenuItem option = new JMenuItem("Options");
            JMenuItem quit = new JMenuItem("Quitter");

            BarMenu.Ecouteur ecoute = new BarMenu.Ecouteur(newGame, newPlayer, high, quit, charger, option);
            newGame.addActionListener(ecoute);
            charger.addActionListener(ecoute);
            newPlayer.addActionListener(ecoute);
            high.addActionListener(ecoute);
            option.addActionListener(ecoute);
            quit.addActionListener(ecoute);

            mFichier.add(newGame);
            mFichier.add(charger);
            mFichier.add(newPlayer);
            mFichier.add(high);
            mFichier.addSeparator();
            mFichier.add(option);
            mFichier.addSeparator();
            mFichier.add(quit);
            this.add(mFichier);
            this.add(Box.createHorizontalGlue());
        }

        private class Ecouteur implements ActionListener {

            private JMenuItem newGame;
            private JMenuItem newPlayer;
            private JMenuItem high;
            private JMenuItem quit;
            private JMenuItem charger;
            private JMenuItem option;

            public Ecouteur(JMenuItem newGame, JMenuItem newPlayer, JMenuItem high, JMenuItem quit, JMenuItem charger, JMenuItem option) {
                this.newGame = newGame;
                this.newPlayer = newPlayer;
                this.high = high;
                this.quit = quit;
                this.charger = charger;
                this.option = option;

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(quit)) {
                    enregistrerJoueurs("joueurs");
                    quitter();
                } else if (e.getSource().equals(newGame)) {
                    Function.NewGame();
                } else if (e.getSource().equals(newPlayer)) {
                    Function.NewPlayer();
                } else if (e.getSource().equals(high)) {
                    Function.HighScore();
                } else if (e.getSource().equals(charger)) {
                    load();

                } else if (e.getSource().equals(option)) {

                    Function.Option();
                }
            }
        }
    }

    private class ChampBt extends JPanel {

        //BOUTON FENETRE PRINCIPAL
        private ChampBt() {

            this.setLayout(new GridLayout(6, 1));
            JButton newGame = new JButton("Nouveau Jeu");
            JButton newPlayer = new JButton("Nouveau Joueur");
            JButton high = new JButton("High score");
            JButton quit = new JButton("Quitter");
            JButton charger = new JButton("Charger");
            JButton option = new JButton("Options");

            ChampBt.Ecouteur ecoute = new ChampBt.Ecouteur(newGame, newPlayer, high, quit, charger, option);
            newGame.addActionListener(ecoute);
            newPlayer.addActionListener(ecoute);
            charger.addActionListener(ecoute);
            high.addActionListener(ecoute);
            option.addActionListener(ecoute);
            quit.addActionListener(ecoute);


            this.add(newGame);
            this.add(charger);
            this.add(newPlayer);
            this.add(high);
            this.add(option);
            this.add(quit);


        }

        private class Ecouteur implements ActionListener {

            private JButton newGame;
            private JButton charger;
            private JButton newPlayer;
            private JButton high;
            private JButton quit;
            private JButton option;

            private Ecouteur(JButton newGame, JButton newPlayer, JButton high, JButton quit, JButton charger, JButton option) {
                this.newGame = newGame;
                this.newPlayer = newPlayer;
                this.high = high;
                this.quit = quit;
                this.charger = charger;
                this.option = option;
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource().equals(quit)) {
                    quitter();
                } else if (e.getSource().equals(newGame)) {
                    Function.NewGame();
                } else if (e.getSource().equals(newPlayer)) {
                    Function.NewPlayer();
                } else if (e.getSource().equals(high)) {
                    Function.HighScore();
                } else if (e.getSource().equals(charger)) {
                    load();
                } else if (e.getSource().equals(option)) {

                    Function.Option();
                }
            }
        }
    }

    private class PanTop extends JPanel {

        private PanTop() {
            ImageIcon resultat = Function.redimensionner("Image" + Constante.S + "Latroncule.png", 594, 70);
            JLabel image = new JLabel();
            image.setIcon(resultat);

            JTextPane txt = new JTextPane();


            txt.setText(Constante.RULE);
            txt.setEditable(false);
            // couleur de font et couleur du texte
            txt.setBackground(Color.BLACK);
            txt.setForeground(Color.WHITE);

            //gére l'allignement 
            StyledDocument doc = txt.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);

            image.setBounds(0, 0, 594, 70);
            txt.setBounds(0, 70, 594, 210);

            //////////
            JPanel pion = new JPanel();
            pion.setLayout(new GridLayout(1, 2));


            JPanel larron = new JPanel();
            larron.setLayout(new GridLayout(1, 3));
            JLabel lLarron = new JLabel("Larron");
            lLarron.setForeground(Color.WHITE);


            resultat = Function.redimensionner("Image" + Constante.S + "pionBleu.png", 50, 80);
            JLabel imgLarronBleu = new JLabel();
            imgLarronBleu.setIcon(resultat);

            resultat = Function.redimensionner("Image" + Constante.S + "pionRouge.png", 50, 80);
            JLabel imgLarronRouge = new JLabel();
            imgLarronRouge.setIcon(resultat);

            larron.add(imgLarronBleu);
            larron.add(lLarron);
            larron.add(imgLarronRouge);
            larron.setBackground(Color.black);

            ///////

            JPanel latroncules = new JPanel();
            latroncules.setLayout(new GridLayout(1, 3));
            JLabel lLatroncules = new JLabel("latroncules");
            lLatroncules.setForeground(Color.WHITE);

            resultat = Function.redimensionner("Image" + Constante.S + "tourBleu.png", 50, 80);
            JLabel imgLatronculesBleu = new JLabel();
            imgLatronculesBleu.setIcon(resultat);

            resultat = Function.redimensionner("Image" + Constante.S + "tourRouge.png", 50, 80);
            JLabel imgLatronculesRouge = new JLabel();
            imgLatronculesRouge.setIcon(resultat);

            latroncules.add(imgLatronculesBleu);
            latroncules.add(lLatroncules);
            latroncules.add(imgLatronculesRouge);
            latroncules.setBackground(Color.black);


            /////
            pion.add(latroncules);
            pion.add(larron);
            pion.setBounds(20, 265, 594, 90);

            JPanel latroncule = new JPanel();
            latroncule.setLayout(new GridLayout(3, 1));
            JLabel lLatroncule = new JLabel("latroncule");

            this.setBackground(Color.black);
            this.setLayout(null);
            this.add(image);
            this.add(txt);
            this.add(pion);
        }
    }
}
