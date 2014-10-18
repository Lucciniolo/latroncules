
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *
 * Fenetre nouveau joueur
 */

public class FenetreNewP extends JFrame {

    private File fichier;
    private File sauv;
    private String avatarPlayer;

    public FenetreNewP() {
        this.setSize(500, 520);
        this.setResizable(false);
        this.setLayout(new FlowLayout());
        // Au cas ou l'utilisateur ne selection rien
        fichier = new File("Avatar" + Constante.S + "avatar.jpg");
        sauv = new File("Avatar" + Constante.S + "avatar.jpg");
        JPanel pNom = new JPanel();
        JTextField fNom = new JTextField();
        JLabel lNom = new JLabel("Nom :");
        fNom.setColumns(10);



        pNom.setLayout(new FlowLayout());
        pNom.add(lNom);
        pNom.add(fNom);

        JPanel pPrenom = new JPanel();
        JTextField fPrenom = new JTextField();
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
        Checkbox check1 = new Checkbox("Homme", check, true);
        Checkbox check2 = new Checkbox("Femme", check, false);

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
        ImageIcon resultat = Function.redimensionner("Avatar" + Constante.S + "avatar.jpg", 300, 300);
        JLabel image = new JLabel();
        image.setIcon(resultat);

        pAvatar.setLayout(new BorderLayout());
        pAvatar.add(lAvatar, BorderLayout.NORTH);
        pAvatar.add(image, BorderLayout.CENTER);
        pAvatar.add(parcourir, BorderLayout.SOUTH);

        JPanel pBt = new JPanel();
        JButton ok = new JButton("OK");
        pBt.add(ok);

        FenetreNewP.Ecouteur ecoute = new FenetreNewP.Ecouteur(parcourir, ok, fNom, fPrenom, check, image, this);
        parcourir.addActionListener(ecoute);
        ok.addActionListener(ecoute);

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 1));
        p.add(pAvatar);
        p.add(pBt);

        this.add(nomPrenomSexe);
        this.add(p);

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

        public Ecouteur(JButton parcourir, JButton ok, JTextField fNom, JTextField fPrenom, CheckboxGroup check, JLabel image, JFrame newP) {
            this.parcourir = parcourir;
            this.ok = ok;
            this.fNom = fNom;
            this.fPrenom = fPrenom;
            this.check = check;
            this.image = image;
            this.newP = newP;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String avatar = "Avatar" + Constante.S + "avatar.jpg";
            if (e.getSource().equals(parcourir)) {
                if (!fNom.getText().equals("") && !fPrenom.getText().equals("")) {


                    // choix du fichier avec limitation par extention
                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new File("Avatar" + Constante.S));

                    // limitation par l'extention
                    FenetreNewP.FiltreExtension filtreImg = new FenetreNewP.FiltreExtension(new String[]{"gif", "tif", "jpeg", "jpg", "tiff"}, "*.gif, *.tif ou *.jpeg");
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
                    JOptionPane.showMessageDialog(null, "Veuillez remplir les champs nom et prenom d'abord");
                }
            } else if (e.getSource().equals(ok)) {
                String sexe = this.check.getSelectedCheckbox().getLabel();
                String nom = this.fNom.getText();
                String prenom = this.fPrenom.getText();

                String ext = fichier.toString();
                ext = ext.substring(ext.lastIndexOf("."));
                avatarPlayer = fNom.getText() + "_" + fPrenom.getText() + ext;
                if (!nom.equals("") && !prenom.equals("")) {
                    boolean bool = true;
                    for (Player p : AmariOthmann.listePlayer) {
                        if (p.getNom().equals(nom) && p.getPrenom().equals(prenom)) {
                            bool = false;
                        }
                    }
                    if (bool) {
                        AmariOthmann.listePlayer.add(new Player(nom, prenom, sexe, avatarPlayer));
                        //Copie l'avatar                   
                        if (sauv.getName().equals("avatar.jpg")) {
                            sauv = new File("Avatar" + Constante.S + avatarPlayer);
                        }

                        Function.copier(fichier, sauv);

                        newP.setVisible(false);
                        // Si le joueur a le même nom et prenom qu'un autre joueur  
                    } else {
                        JOptionPane.showMessageDialog(null, "Ce joueur existe déja");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Les champs nom et prénom sont obligatoires");
                }
            }
        }
    }

    /**
     * class filtrant les fichiers images.
     */
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

        /**
        * fonction a utiliser pour tester le fichier
        */
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
