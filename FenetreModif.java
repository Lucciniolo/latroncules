
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * Fenetre permettant de modifier un joueur
 */
public class FenetreModif extends JFrame {

    private File fichier;
    private File sauv;
    private String avatarPlayer;
    private JComboBox comboBoxP;
    private JTextField fNom;
    private JTextField fPrenom;
    private Checkbox check1;
    private Checkbox check2;
    private JLabel image;
    private FenetreOption winOption;

    public FenetreModif(FenetreOption winOption) {
        this.setSize(600, 620);
        
        this.winOption = winOption;

        this.setResizable(false);
        this.setLayout(null);

        comboBoxP = new JComboBox();

        for (Player p : AmariOthmann.listePlayer) {
            comboBoxP.addItem(p.toString());
        }
        Player pdeb = AmariOthmann.listePlayer.get(0);
       
        comboBoxP.setBounds(0, 0, 595, 20);
        
        fichier = new File("Avatar" + Constante.S + pdeb.getAvatar());
        sauv = new File("Avatar" + Constante.S + pdeb.getAvatar());
        

        JPanel modif = new JPanel();
        modif.setLayout(new FlowLayout());
        // Au cas ou l'utilisateur ne selection rien

        JPanel pNom = new JPanel();
        fNom = new JTextField(pdeb.getNom());
        JLabel lNom = new JLabel("Nom :");
        fNom.setColumns(10);



        pNom.setLayout(new FlowLayout());
        pNom.add(lNom);
        pNom.add(fNom);

        JPanel pPrenom = new JPanel();
        fPrenom = new JTextField(pdeb.getPrenom());
        JLabel lPrenom = new JLabel("Prenom :");
        fPrenom.setColumns(10);

        pPrenom.setLayout(new FlowLayout());
        pPrenom.add(lPrenom);
        pPrenom.add(fPrenom);

        JPanel nomPrenom = new JPanel();
        nomPrenom.add(pNom);
        nomPrenom.add(pPrenom);


        JPanel pSexe = new JPanel();


        JLabel lsexe = new JLabel("Sexe :");
        CheckboxGroup check = new CheckboxGroup();
        if (pdeb.getSexe() == "Femme") {
            check1 = new Checkbox("Homme", check, false);
            check2 = new Checkbox("Femme", check, true);
        } else {
            check1 = new Checkbox("Homme", check, true);
            check2 = new Checkbox("Femme", check, false);
        }
        pSexe.setLayout(new FlowLayout());
        pSexe.add(lsexe);
        pSexe.add(check1);
        pSexe.add(check2);

        JPanel nomPrenomSexe = new JPanel();
        nomPrenomSexe.setLayout(new GridLayout(2, 1));
        nomPrenomSexe.add(nomPrenom);
        nomPrenomSexe.add(pSexe);

        JPanel pAvatar = new JPanel();
        JLabel lAvatar = new JLabel("Votre avatar:");
        JButton parcourir = new JButton("Parcourir");
        ImageIcon resultat = Function.redimensionner("Avatar" + Constante.S + pdeb.getAvatar(), 300, 300);
        image = new JLabel();
        image.setIcon(resultat);

        pAvatar.setLayout(new BorderLayout());
        pAvatar.add(lAvatar, BorderLayout.NORTH);
        pAvatar.add(image, BorderLayout.CENTER);
        pAvatar.add(parcourir, BorderLayout.SOUTH);

        JPanel pBt = new JPanel();
        pBt.setLayout(new GridLayout(1, 3));
        JButton ok = new JButton("OK");
        JButton zero = new JButton("Effacer le score");
        JButton effacer = new JButton("Supprimer le joueur");
        pBt.add(ok);
        pBt.add(zero);
        pBt.add(effacer);
        pBt.setBounds(0, 560, 600, 30);
        
        
        Ecouteur ecoute = new Ecouteur(parcourir, ok, fNom, fPrenom, check, image, this, comboBoxP, zero, effacer,winOption);
        parcourir.addActionListener(ecoute);
        ok.addActionListener(ecoute);
        zero.addActionListener(ecoute);
        effacer.addActionListener(ecoute);

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 1));
        p.add(pAvatar);
        

        modif.add(nomPrenomSexe);
        modif.add(p);
        modif.setBounds(30, 20, 500, 435);
        
        // ajoute un ecouteur sur le changement de selection de la combo
        // afin de mettre a jours les champs
        comboBoxP.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                Player p = AmariOthmann.listePlayer.get(comboBoxP.getSelectedIndex());
     
                image.setIcon(Function.redimensionner("Avatar" + Constante.S + p.getAvatar(), 300, 300));
                fPrenom.setText(p.getPrenom());
                fNom.setText(p.getNom());
                fichier = new File("Avatar" + Constante.S + p.getAvatar());
                sauv = new File("Avatar" + Constante.S + p.getAvatar());
                if (p.getSexe() == "Femme") {
                    check2.setState(true);
                    check1.setState(false);
                } else {
                    check1.setState(true);
                    check2.setState(false);
                }

            }
        });
        
        this.add(comboBoxP);
        this.add(modif);
        this.add(pBt);
        this.setVisible(true);
        this.setLocationRelativeTo(this.getParent());
    }

    public class Ecouteur implements ActionListener {

        private JButton parcourir;
        private JButton ok;
        private JTextField fNom;
        private JTextField fPrenom;
        private CheckboxGroup check;
        private JLabel image;
        private JFrame newP;
        private JComboBox comboBoxP;
        private JButton zero;
        private JButton effacer;
        private FenetreOption winOption;

        public Ecouteur(JButton parcourir, JButton ok, JTextField fNom, JTextField fPrenom, CheckboxGroup check, JLabel image, JFrame newP, JComboBox comboBoxP, JButton zero, JButton effacer,FenetreOption winOption) {
            this.parcourir = parcourir;
            this.ok = ok;
            this.fNom = fNom;
            this.fPrenom = fPrenom;
            this.check = check;
            this.image = image;
            this.newP = newP;
            this.comboBoxP = comboBoxP;
            this.zero = zero;
            this.effacer = effacer;
            this.winOption = winOption;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
           
            Player oldp = AmariOthmann.listePlayer.get(comboBoxP.getSelectedIndex());


            String avatar = "Avatar" + Constante.S + "avatar.jpg";
            if (e.getSource().equals(parcourir)) {
                if (!fNom.getText().equals("") && !fPrenom.getText().equals("")) {


                    // choix du fichier avec limitation par extention
                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new File("Avatar" + Constante.S));

                    // limitation par l'extention
                    FiltreExtension filtreImg = new FiltreExtension(new String[]{"gif", "tif", "jpeg", "jpg", "tiff"}, "*.gif, *.tif ou *.jpeg");
                    chooser.addChoosableFileFilter(filtreImg);

                    chooser.setApproveButtonText("OK");
                    int retour = chooser.showOpenDialog(null);
                    if (retour == JFileChooser.APPROVE_OPTION) {

                        fichier = chooser.getSelectedFile();
                        //Si le fichier a la bonne extention
                        if (filtreImg.accept(fichier)) {
                            // renome l'avatar en nom_prenom.extention
                            String ext = fichier.toString();
                            ext = ext.substring(ext.lastIndexOf("."));
                            avatarPlayer = fNom.getText() + "_" + fPrenom.getText() + ext;
                            image.setIcon(Function.redimensionner(fichier.getPath(), 300, 300));
                        } else {
                            JOptionPane.showMessageDialog(null, "Le fichier doit etre un: " + filtreImg.getDescription());

                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir les champs nom et prénom d'abord");

                }
            } else if (e.getSource().equals(ok)) {

                String sexe = this.check.getSelectedCheckbox().getLabel();
                String nom = this.fNom.getText();
                String prenom = this.fPrenom.getText();

                String ext = fichier.toString();
                ext = ext.substring(ext.lastIndexOf("."));
                avatarPlayer = nom + "_" + prenom + ext;
                if (!nom.equals("") && !prenom.equals("")) {
                    boolean bool = true;
                    int i = 0;
                    // permet de tester si le joueur ajouter existe deja 
                    //ou si il ne s'agit pas du méme
                    for (Player p : AmariOthmann.listePlayer) {
                        if (p.getNom().equals(nom) && p.getPrenom().equals(prenom) && i != comboBoxP.getSelectedIndex()) {
                            bool = false;

                        }
                        i++;
                    }
                    if (bool) {

                        //Copie l'avatar                   
                        avatarPlayer = nom + "_" + prenom + ext;
                        sauv = new File("Avatar" + Constante.S + avatarPlayer);
                        // dans le cas ou l'avatar n aurrai pas changer
                        if(!sauv.equals(fichier)){
                            Function.copier(fichier, sauv);
                        }
                        Player nouvp = new Player(nom, prenom, sexe, avatarPlayer);
                        nouvp.setHead2head(oldp.getHead2head());
                        nouvp.setNbPartie(oldp.getNbPartie());
                        nouvp.setNbPartieEncours(oldp.getNbPartieEncours());
                        AmariOthmann.listePlayer.remove(comboBoxP.getSelectedIndex());
                        AmariOthmann.listePlayer.add(nouvp);
                        
                        newP.setVisible(false);
                        // Si le joueur a le même nom et prenom qu'un autre joueur  
                    } else {

                        JOptionPane.showMessageDialog(null, "Ce joueur existe déja");
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Les champs nom et prénom sont obligatoires");
                }
            } else if (e.getSource().equals(zero)) {
                String txt = "Etes vous sur de vouloir effacer le scores du joueur ?";
                //l'appui sur raz affiche un popup qui demande de valider l'action 
                int retour = JOptionPane.showConfirmDialog(null, txt, "Suppresion des joueurs", JOptionPane.OK_CANCEL_OPTION);
                // Si la reponse est positive 
                if (retour == 0) {
                    oldp.setHead2head(0);
                    oldp.setNbPartie(0);
                    oldp.setNbPartieEncours(0);

                }

            } else if (e.getSource().equals(effacer)) {
                String txt = "Etes vous sur de vouloir supprimer le joueur ?";
                //l'appui sur raz affiche un popup qui demande de valider l'action 
                int retour = JOptionPane.showConfirmDialog(null, txt, "Suppresion des joueurs", JOptionPane.OK_CANCEL_OPTION);
                // Si la reponse est positive 
                if (retour == 0) {
                   
                    Player p = AmariOthmann.listePlayer.get(comboBoxP.getSelectedIndex());
                    File f = new File ("Avatar" + Constante.S + p.getAvatar());
                    f.delete();
                    newP.setVisible(false);
                    AmariOthmann.listePlayer.remove(comboBoxP.getSelectedIndex());
                    if(AmariOthmann.listePlayer.isEmpty()){
                        winOption.setVisible(false);
                    }
                }
            }
        }
    }

    // class filtrant les fichiers images.
    public class FiltreExtension extends javax.swing.filechooser.FileFilter {

        String[] lesSuffixes;
        String laDescription;

        public FiltreExtension(String[] lesSuffixes, String laDescription) {
            this.lesSuffixes = lesSuffixes;
            this.laDescription = laDescription;
        }

        // test si l'extention est bonne
        boolean appartient(String suffixe) {
            for (int i = 0; i < lesSuffixes.length; ++i) {
                if (suffixe.equals(lesSuffixes[i])) {
                    return true;
                }
            }
            return false;
        }

        //fonction a utiliser pour tester le fichier
        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String suffixe = null;
            String s = f.getName();
            int i = s.lastIndexOf('.');
            // teste si l'extention est valide
            if (i > 0 && i < s.length() - 1) {
                suffixe = s.substring(i + 1).toLowerCase();
            }
            return suffixe != null && appartient(suffixe);
        }

        @Override
        public String getDescription() {
            return laDescription;
        }
    }
}
