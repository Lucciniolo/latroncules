
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Fenetre de jeu. Contient principalement un damier composé de 64 Cases (classe
 * Case) De part et l'autre de ce damier on trouve un panneau avec des
 * informations pour les joueurs
 *
 */
public class Damier extends JFrame {

    private JLabel imageHandB;
    private JLabel imageHandA;
    private JLabel nbPionA;
    private JLabel nbPionB;
    private JLabel nbTourA;
    private JLabel nbTourB;
    private static final long serialVersionUID = 1; // Pour permettre de serialiser
    // Le tableau qui contient toutes les cases du jeu
    public Case[][] cases;
    private Player aQuiDeJouer;
    public Pion pionSelectionne;
    //L'objet servant à positionner les composants
    GridBagConstraints gbc;
    //Le conteneur principal
    JPanel content;
    private GroupeDePions pionsJoueur1;
    private GroupeDePions pionsJoueur2;
    private Player joueur1;
    private Player joueur2;
    Damier leDamier;
    private Partie partie;
    private ArrayList<int[]> casesEncadreesParJoueur1;
    private ArrayList<int[]> casesEncadreesParJoueur2;

    //
    public Damier(Partie pPartie) {
        this.setTitle("Partie : " + pPartie.getJoueur1().getNom() + " contre " + pPartie.getJoueur2().getNom());
        this.setSize(960, 730);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.leDamier = this;
        this.setLayout(null);
        this.partie = pPartie;
        //Le conteneur principal
        content = new JPanel();
        content.setPreferredSize(new Dimension(640, 640));

        // Propose au joueur de sauvegarder ou d'annuler lors de l'appuie sur la croix en haut a droite
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                joueur1.setNbPartieEncours(joueur1.getNbPartieEncours() - 1);
                joueur2.setNbPartieEncours(joueur2.getNbPartieEncours() - 1);
                String content = "Sauvegarder ?";
                int retour = JOptionPane.showConfirmDialog(null, content, "Quitter", JOptionPane.YES_NO_CANCEL_OPTION);
                if (retour == 0) {
                    enregistrer();
                    dispose();
                } else if (retour == 1) {
                    joueur1.setNbPartieEncours(joueur1.getNbPartieEncours() - 1);
                    joueur2.setNbPartieEncours(joueur2.getNbPartieEncours() - 1);
                    dispose();
                } else {
                    setVisible(true);
                }
            }
        });
        ////////////////////////////////////////////////////////////////////////

        //On définit le layout manager
        content.setLayout(new GridBagLayout());

        // On initilise le tableau avec des objet Case noire et blanc
        initialiserTableau();
        // On affiche les cases a l'ecran a partir du tableau cases

        affichageCases();
        // On copie les données de la partie dans le joueur1
        joueur1 = pPartie.getJoueur1();
        joueur2 = pPartie.getJoueur2();

        JPanel bigPanel1 = panelLeft(joueur2);

        bigPanel1.setBounds(0, 0, 160, 560);
        JPanel bigPanel2 = panelRight(joueur1);

        bigPanel2.setBounds(800, 0, 160, 560);
        JPanel bigPanel3 = panelCenter(this);

        bigPanel3.setBounds(163, 643, 640, 50);

        content.setBounds(160, 0, 640, 640);

        pionsJoueur1 = pPartie.getPionsJoueur1();
        pionsJoueur2 = pPartie.getPionsJoueur2();
        aQuiDeJouer = pPartie.getaQuiDeJouer();

        System.out.println(
                "C'est à " + aQuiDeJouer.getNom() + " de commencer.");

        // On indique sur le damier' grace a l'image, a qui c'est de commencer
        if (aQuiDeJouer.equals(joueur1)) {
            getImageHand2().setVisible(true);
            getImageHand1().setVisible(false);
        } else {
            getImageHand1().setVisible(true);
            getImageHand2().setVisible(false);
        }
        // On ajoute une barre de menu
        MenuBar mb = new MenuBar();

        this.setMenuBar(mb);
        // On ajoute un menu File
        Menu mFile = new Menu("Fichier");

        mb.add(mFile);
        // ---------- Sauvegarder
        MenuItem iFileSauvegarder = new MenuItem("Sauvegarder");

        mFile.add(iFileSauvegarder);

        iFileSauvegarder.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enregistrer();
            }
        });

        //On ajoute le conteneur
        this.add(bigPanel1);
        this.add(bigPanel2);
        this.add(bigPanel3);
        this.add(content);

    }

    /*
     * Permet de syncroniser le compteur de pion avec le nombre de pion de la partie chargé
     * 
     */
    public void syncroCompteursPion() {
        int nouveauNbLarrons = 0;
        int nouveauNbLatroncules = 0;
        // Permet de mettre a jour le nombre de pions de chaque joueur sur le damier
        for (Pion p : this.getPartie().getPionsJoueur1()) {
            if ((p instanceof Larron)) {
                nouveauNbLarrons++;
            } else if (p instanceof Latroncule) {
                nouveauNbLatroncules++;
            }
        }

        this.setNbLarrons1(Integer.toString(nouveauNbLarrons));
        this.setNbLatroncules1(Integer.toString(nouveauNbLatroncules));

        nouveauNbLarrons = 0;
        nouveauNbLatroncules = 0;
        // Permet de mettre a jour le nombre de pions de chaque joueur sur le damier
        for (Pion p : this.getPartie().getPionsJoueur2()) {
            if ((p instanceof Larron)) {
                nouveauNbLarrons++;
            } else if (p instanceof Latroncule) {
                nouveauNbLatroncules++;
            }
        }

        this.setNbLarrons2(Integer.toString(nouveauNbLarrons));
        this.setNbLatroncules2(Integer.toString(nouveauNbLatroncules));

    }

    /**
     * On initialise ces groupes de pions avec 8 larrons et 8 latroncules bien
     * placés
     */
    public void initialiseDamier(boolean pBool) {
        getPionsJoueur1().initialiser();
        getPionsJoueur2().initialiser();

        afficherPions();

        setVisible(pBool);
    }

    /**
     * On affiche les pions sur le damier (appeler par charger)
     *
     * @param pBool
     */
    public void afficherPionsSurDamier(boolean pBool) {
        // On parcourt les pions de chaque joeur et on les positionne dans les cases
        for (Pion p : getPionsJoueur1()) {
            cases[p.getX()][p.getY()].setPion(p);
            p.setSaCase(cases[p.getX()][p.getY()]);
            p.setCouleurEtListener();
        }

        for (Pion p : getPionsJoueur2()) {
            cases[p.getX()][p.getY()].setPion(p);
            p.setSaCase(cases[p.getX()][p.getY()]);
            p.setCouleurEtListener();
        }

        afficherPions();

        setVisible(pBool);
    }

    /**
     * Permet d'initialiser le tableau de cases de cases
     */
    public void initialiserTableau() {
        // Le tableau 8x8 cases contient toutes les cases du jeu
        cases = new Case[8][8];
        //---------------------------------------------
        //On remplit l'arrayList de cases
        for (int i = 0; i < 8; i++) {
            if (i % 2 == 1) {   // Si la colonne est impaire
                for (int j = 0; j < 8; j++) {
                    if (j % 2 == 1) { // Si la ligne est paire
                        // Ajout de la case blanche
                        cases[i][j] = new Case(new Color(191, 126, 48), i, j, this);
                    } else { // Si la ligne est impaire
                        // Ajout de la case noire            
                        cases[i][j] = new Case(Color.WHITE, i, j, this);
                    }
                }
            } else { // Si la colonne est paire
                for (int j = 0; j < 8; j++) {
                    // Ajout de la case noire            
                    if (j % 2 == 1) { // Si la ligne est paire
                        cases[i][j] = new Case(Color.WHITE, i, j, this);
                    } else { // Si la ligne est impaire
                        // Ajout de la case blanche
                        cases[i][j] = new Case(new Color(191, 126, 48), i, j, this);
                    }
                }
            }
        }
        //---------------------------------------------
    }

    // Permet d'afficher a l'écran les cases a partir du tableau cases
    /**
     *
     */
    public void affichageCases() {
        //---------------------------------------------
        // On remplit le JPanel avec les cases dans le tableau de cases
        //L'objet servant à positionner les composants
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        // nbCase est le numéro de la case dans laquelle on est
        int nbCase = 0;
        for (int x = 0; x < 8; x++) {
            gbc.gridx = x;
            for (int y = 0; y < 8; y++) {
                gbc.gridy = y;
                content.add(cases[x][y], gbc);
                nbCase++;
            }
        }
    }

    /**
     * Affiche les pions dans leur position
     */
    public void afficherPions() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (cases[x][y].getPion() != null) {
                    cases[x][y].afficherPion();
                }
            }
        }
    }

    /**
     * Enregistre en serialisant l'objet Partie
     */
    public void enregistrer() {
        try {
            JFileChooser choix = new JFileChooser();
            choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
            choix.setDialogType(JFileChooser.SAVE_DIALOG);
            FileFilter filter = new FileNameExtensionFilter("Latroncule file", "lat");
            choix.addChoosableFileFilter(filter);

            choix.setCurrentDirectory(new File("parties-enregistrees" + Constante.S));

            choix.setAcceptAllFileFilterUsed(false);
            int retour = choix.showSaveDialog(this);
            if (retour == JFileChooser.APPROVE_OPTION) {
                File file = choix.getSelectedFile();
                FileOutputStream fichier = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fichier);

                oos.writeObject(this.getPartie());

                oos.flush();
                oos.close();
                File sav = new File(file.getAbsolutePath());
                sav.renameTo(new File(file.getAbsolutePath() + ".lat"));
                
                

            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Un arrayList de tableau contenant les coordonnées x et y des
     * cases encadrées par les pions du groupe en parametre
     */
    public ArrayList<int[]> casesEncadrees(GroupeDePions pGroupe) {
        ArrayList<int[]> casesEncadrees = new ArrayList<int[]>();

        // On parcours le tableau de cases et on effectue le traitement pour chaque cases encadrable (ce qui exclue les cases sur les bords
        for (int x = 1; x < 7; x++) {
            for (int y = 0; y <= 7; y++) {
                if ((cases[x - 1][y].getPion() != null) && (cases[x + 1][y].getPion() != null)) { // On test d'abord si il y a des pions a gauche et a droite pour eviter une excepion Null pointeur
                    if ((cases[x - 1][y].getPion().getGroupe().equals(pGroupe)) && (cases[x + 1][y].getPion().getGroupe().equals(pGroupe))) {
                        int[] coordonnees = {x, y};
                        casesEncadrees.add(coordonnees);
                        // System.out.println("Les coordonnées des cases encadrées par " + pGroupe.getProprietaire().getNom() + " sont : x = " + coordonnees[0] + " y = " + coordonnees[1]);
                    }
                }
            }
        }

        return casesEncadrees;
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

    public String getNbLatroncules1() {
        return nbPionB.getText();
    }

    public void setNbLatroncules1(String txt) {
        this.nbPionB.setText(txt);
    }

    public String getNbLatroncules2() {
        return nbPionA.getText();
    }

    public void setNbLatroncules2(String txt) {
        this.nbPionA.setText(txt);
    }

    public String getNbLarrons1() {
        return nbTourB.getText();
    }

    public void setNbLarrons1(String txt) {
        this.nbTourB.setText(txt);
    }

    public String getNbLarrons2() {
        return nbTourA.getText();
    }

    public void setNbLarrons2(String txt) {
        this.nbTourA.setText(txt);
    }

    public JLabel getImageHand2() {
        return imageHandB;
    }

    public JLabel getImageHand1() {
        return imageHandA;
    }

    /**
     * @return the casesEncadreesParJoueur1
     */
    public ArrayList<int[]> getCasesEncadreesParJoueur1() {
        return casesEncadreesParJoueur1;
    }

    /**
     * @param casesEncadreesParJoueur1 the casesEncadreesParJoueur1 to set
     */
    public void setCasesEncadreesParJoueur1(ArrayList<int[]> casesEncadreesParJoueur1) {
        this.casesEncadreesParJoueur1 = casesEncadreesParJoueur1;
    }

    /**
     * @return the casesEncadreesParJoueur2
     */
    public ArrayList<int[]> getCasesEncadreesParJoueur2() {
        return casesEncadreesParJoueur2;
    }

    /**
     * @param casesEncadreesParJoueur2 the casesEncadreesParJoueur2 to set
     */
    public void setCasesEncadreesParJoueur2(ArrayList<int[]> casesEncadreesParJoueur2) {
        this.casesEncadreesParJoueur2 = casesEncadreesParJoueur2;
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

    // panel de gauche (avatar,nom,...) 
    private JPanel panelLeft(Player pA) {
        Font font = new Font("Arial", Font.BOLD, 30);
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(null);

        ImageIcon resultat = Function.redimensionner("Avatar" + Constante.S + pA.getAvatar(), 160, 160);
        JLabel imageA = new JLabel();
        imageA.setIcon(resultat);
        imageA.setBounds(0, 0, 160, 160);

        JLabel nomA = new JLabel(pA.getNom());
        JLabel prenomA = new JLabel(pA.getPrenom());

        prenomA.setFont(font);
        nomA.setFont(font);
        nomA.setHorizontalAlignment(JLabel.CENTER);
        nomA.setBounds(0, 160, 160, 30);
        prenomA.setHorizontalAlignment(JLabel.CENTER);
        prenomA.setBounds(0, 190, 160, 30);

        JLabel headA = new JLabel("Head2Head : " + pA.getHead2head());
        headA.setBounds(0, 220, 160, 20);
        headA.setHorizontalAlignment(JLabel.CENTER);

        resultat = Function.redimensionner("Image" + Constante.S + "avous.png", 160, 160);
        this.imageHandA = new JLabel();
        this.imageHandA.setIcon(resultat);
        this.imageHandA.setBounds(0, 380, 160, 160);
        ///
        JPanel lPionA = new JPanel();
        lPionA.setLayout(new GridLayout(2, 2));

        resultat = Function.redimensionner("Image" + Constante.S + "pionBleu.png", 80, 80);
        JLabel pionA = new JLabel();
        pionA.setIcon(resultat);

        resultat = Function.redimensionner("Image" + Constante.S + "tourBleu.png", 80, 80);
        JLabel tourA = new JLabel();
        tourA.setIcon(resultat);

        this.nbPionA = new JLabel("8");
        this.nbTourA = new JLabel("8");

        nbPionA.setFont(font);
        nbPionA.setHorizontalAlignment(JLabel.CENTER);
        nbTourA.setFont(font);
        nbTourA.setHorizontalAlignment(JLabel.CENTER);

        lPionA.add(pionA);
        lPionA.add(nbPionA);
        lPionA.add(tourA);
        lPionA.add(nbTourA);
        lPionA.setBounds(0, 240, 160, 160);

        bigPanel.add(imageA);
        bigPanel.add(nomA);
        bigPanel.add(prenomA);
        bigPanel.add(headA);
        bigPanel.add(lPionA);
        bigPanel.add(this.imageHandA);

        return bigPanel;
    }
    // panel de droite (avatar,nom,...)

    private JPanel panelRight(Player pB) {
        Font font = new Font("Arial", Font.BOLD, 30);
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(null);

        ImageIcon resultat = Function.redimensionner("Avatar" + Constante.S + pB.getAvatar(), 160, 160);
        JLabel imageB = new JLabel();
        imageB.setIcon(resultat);
        imageB.setBounds(0, 0, 160, 160);

        JLabel nomB = new JLabel(pB.getNom());
        JLabel prenomB = new JLabel(pB.getPrenom());
        prenomB.setFont(font);
        prenomB.setHorizontalAlignment(JLabel.CENTER);
        nomB.setFont(font);
        nomB.setHorizontalAlignment(JLabel.CENTER);
        nomB.setBounds(0, 160, 160, 30);
        prenomB.setBounds(0, 190, 160, 30);

        JLabel headB = new JLabel("Head2Head : " + pB.getHead2head());
        headB.setBounds(0, 220, 160, 20);
        headB.setHorizontalAlignment(JLabel.CENTER);

        resultat = Function.redimensionner("Image" + Constante.S + "avous.png", 160, 160);
        this.imageHandB = new JLabel();
        this.imageHandB.setIcon(resultat);
        this.imageHandB.setBounds(0, 380, 160, 160);

        ///////////////////////////////:

        JPanel lPionB = new JPanel();
        lPionB.setLayout(new GridLayout(2, 2));

        resultat = Function.redimensionner("Image" + Constante.S + "pionRouge.png", 80, 80);
        JLabel pionB = new JLabel();
        pionB.setIcon(resultat);

        resultat = Function.redimensionner("Image" + Constante.S + "tourRouge.png", 80, 80);
        JLabel tourB = new JLabel();
        tourB.setIcon(resultat);

        this.nbPionB = new JLabel("8");
        this.nbTourB = new JLabel("8");

        nbPionB.setFont(font);
        nbPionB.setHorizontalAlignment(JLabel.CENTER);
        nbTourB.setFont(font);
        nbTourB.setHorizontalAlignment(JLabel.CENTER);

        lPionB.add(pionB);
        lPionB.add(nbPionB);
        lPionB.add(tourB);
        lPionB.add(nbTourB);
        lPionB.setBounds(0, 240, 160, 160);


        bigPanel.add(imageB);
        bigPanel.add(nomB);
        bigPanel.add(prenomB);
        bigPanel.add(headB);
        bigPanel.add(lPionB);
        bigPanel.add(this.imageHandB);
        return bigPanel;
    }
    // panel de millieu

    private JPanel panelCenter(Damier win) {
        JPanel damBouton = new JPanel();
        damBouton.setLayout(null);

        JButton save = new JButton("ENREGISTER");
        save.setBounds(0, 0, 213, 30);
        JButton nul = new JButton("ARMISTICE");
        nul.setBounds(213, 0, 213, 30);
        JButton abandon = new JButton("DEPOSER LES ARMES");
        abandon.setBounds(426, 0, 213, 30);

        damBouton.add(abandon);
        damBouton.add(save);
        damBouton.add(nul);

        Damier.Ecouteur ecoute = new Damier.Ecouteur(save, nul, abandon, win);
        nul.addActionListener(ecoute);
        save.addActionListener(ecoute);
        abandon.addActionListener(ecoute);

        return damBouton;
    }

    /**
     * @return the partie
     */
    public Partie getPartie() {
        return partie;
    }

    /**
     * @param partie the partie to set
     */
    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    private class Ecouteur implements ActionListener {

        private JButton save;
        private JButton nul;
        private JButton abandon;
        private Damier win;

        public Ecouteur(JButton save, JButton nul, JButton abandon, Damier win) {
            this.save = save;
            this.nul = nul;
            this.abandon = abandon;
            this.win = win;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(save)) {
                enregistrer();
            } else if (e.getSource().equals(nul)) {

                String txt = win.aQuiDeJouer.getNom() + " " + win.aQuiDeJouer.getPrenom() + " propose un traiter de paix.";
                //l'appui sur Armistice affiche un popup qui demande l'avis du 2eme joueur 
                int retour = JOptionPane.showConfirmDialog(this.win, txt, "Armistice", JOptionPane.OK_CANCEL_OPTION);
                // Si la reponse est positive 
                if (retour == 0) {
                    win.joueur1.setNbPartie(win.joueur1.getNbPartie() + 1);
                    win.joueur2.setNbPartie(win.joueur2.getNbPartie() + 1);
                    win.joueur1.setNbPartieEncours(win.joueur1.getNbPartieEncours() - 1);
                    win.joueur2.setNbPartieEncours(win.joueur2.getNbPartieEncours() - 1);
                    win.dispose();
                    Function.finPartie(null, leDamier);
                }
                //l'appui sur deposer les armes 
            } else if (e.getSource().equals(abandon)) {
                win.joueur1.setNbPartie(win.joueur1.getNbPartie() + 1);
                win.joueur2.setNbPartie(win.joueur2.getNbPartie() + 1);

                win.joueur1.setNbPartieEncours(win.joueur1.getNbPartieEncours() - 1);
                win.joueur2.setNbPartieEncours(win.joueur2.getNbPartieEncours() - 1);

                if (win.aQuiDeJouer.getAvatar().equals(win.joueur1.getAvatar())) {
                    win.joueur2.setHead2head(win.joueur2.getHead2head() + 1);
                    Function.finPartie(win.joueur2, leDamier);
                } else {
                    win.joueur1.setHead2head(win.joueur1.getHead2head() + 1);
                    Function.finPartie(win.joueur1, leDamier);
                }
                win.dispose();
            }

        }
    }
}
