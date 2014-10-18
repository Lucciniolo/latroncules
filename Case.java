
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * La classe Case est le plus petit element composant le damier. Elle hérite de
 * JPanel Elle acceuille les pions et chacune des 64 cases du damier contient
 * une abscisse x et une ordonnee y
 *
 * Cette classe acceuille un addMouseListener trés important. Il est le point de
 * départ de nombreux mécanismes du jeu
 */
public class Case extends JPanel implements java.io.Serializable {

    Pion pion;
    private int x;
    private int y;
    transient private Damier damier;

    /**
     * Unique construceur. acceuille un des deux addMouseListener important
     */
    public Case(Color couleur, int pX, int pY, Damier pDamier) {
        super();
        this.x = pX;
        this.y = pY;
        this.setPreferredSize(new Dimension(80, 80));
        this.setBackground(couleur);
        this.damier = pDamier;

        final Case laCase = this;

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Si un pion est selectionné et que la case n'a pas deja un pion et que la case est permise
                if ((laCase.getPion() == null) && (getDamier().pionSelectionne != null) && (casePermise() == true)) {

                    // On retire la bordure de la case verte 
                    getDamier().pionSelectionne.getSaCase().setBorder(BorderFactory.createEmptyBorder());

                    // On retire la coloration des cases ou le deplacement est permis
                    getDamier().pionSelectionne.collorerDeplacementPermis(false);

                    // On supprime le pion selectionne
                    //damier.pionSelectionne.getSaCase().supprimerPion();

                    // On met à jour la case du pion
                    getDamier().cases[x][getCoordonnéeY()].setPion(getDamier().pionSelectionne);
                    getDamier().cases[x][getCoordonnéeY()].getPion().setSaCase(laCase);

                    // On met à jour les coordonnées du Pion
                    getDamier().cases[x][getCoordonnéeY()].getPion().setX(x);
                    getDamier().cases[x][getCoordonnéeY()].getPion().setY(getCoordonnéeY());

                    // On affiche le pion
                    getDamier().cases[x][getCoordonnéeY()].afficherPion();



                    damier.setCasesEncadreesParJoueur1(damier.casesEncadrees(damier.getPionsJoueur1())); // On determine quelles sont les cases encadrées par les pions du joueur1 dans la situation actuelle
                    damier.setCasesEncadreesParJoueur2(damier.casesEncadrees(damier.getPionsJoueur2())); // On determine quelles sont les cases encadrées par les pions du joueur2 dans la situation actuelle

                    manger(damier.getPionsJoueur2(), damier.getCasesEncadreesParJoueur1()); // On supprime si nécessaire des pions du joueur 2
                    manger(damier.getPionsJoueur1(), damier.getCasesEncadreesParJoueur2()); // On supprime si nécessaire des pions du joueur 1


                    // Ce qui ce passe lorsqu'un joueur fini de jouer
                    // Ce n'est plus au meme joueur de jouer, on change aQuiDeJouer
                    if (damier.getaQuiDeJouer().equals(damier.getJoueur1())) {
                        // Si le pion selectionné est un latroncule ET va sur la derniere case
                        if ((damier.pionSelectionne instanceof Latroncule) && (laCase.getCoordonnéeY() == 7)) {
                            //On supprime le Latroncule
                            damier.pionSelectionne.getSaCase().supprimerPion();
                            damier.getPionsJoueur1().remove(damier.pionSelectionne);
                            //On le remplace par un Larron
                            Larron nouveauLarron = new Larron(damier.pionSelectionne.getX(), damier.pionSelectionne.getY(), damier.getPionsJoueur1(), damier.cases[damier.pionSelectionne.getX()][damier.pionSelectionne.getY()], damier.getPionsJoueur1().getCouleur(), damier);
                            damier.getPionsJoueur1().add(nouveauLarron);
                            // On met a jour la case
                            damier.pionSelectionne.getSaCase().afficherPion();
                            damier.pionSelectionne.getSaCase().repaint();
                            // On ajoute un larron au joueur sur le compteur
                            int nouveauNbLarrons = Integer.parseInt(damier.getNbLarrons1()) + 1;
                            damier.setNbLarrons1(Integer.toString(nouveauNbLarrons));
                            // On retire un latroncule au joueur sur le compteur
                            int nouveauNblatroncules = Integer.parseInt(damier.getNbLatroncules1()) - 1;
                            damier.setNbLatroncules1(Integer.toString(nouveauNblatroncules));
                        }

                        System.out.println("C'est au tour de " + damier.getJoueur2().getNom() + " de jouer.");

                        // On indique sur le damier' grace a l'image, a qui c'est de jouer
                        damier.getImageHand1().setVisible(true);
                        damier.getImageHand2().setVisible(false);

                        damier.setaQuiDeJouer(damier.getJoueur2());

                    } else if (damier.getaQuiDeJouer().equals(damier.getJoueur2())) {
                        // Si le pion selectionné est un latroncule ET va sur la derniere case
                        if ((damier.pionSelectionne instanceof Latroncule) && (laCase.getCoordonnéeY() == 0)) {
                            //On supprime le Latroncule
                            damier.pionSelectionne.getSaCase().supprimerPion();
                            damier.getPionsJoueur2().remove(damier.pionSelectionne);
                            //On le remplace par un Larron
                            Larron nouveauLarron = new Larron(damier.pionSelectionne.getX(), damier.pionSelectionne.getY(), damier.getPionsJoueur2(), damier.cases[damier.pionSelectionne.getX()][damier.pionSelectionne.getY()], damier.getPionsJoueur2().getCouleur(), damier);
                            damier.getPionsJoueur2().add(nouveauLarron);
                            // On met a jour la case
                            damier.pionSelectionne.getSaCase().afficherPion();
                            damier.pionSelectionne.getSaCase().repaint();
                            // On ajoute un larron au joueur sur le compteur
                            int nouveauNbLarrons = Integer.parseInt(damier.getNbLarrons2()) + 1;
                            damier.setNbLarrons2(Integer.toString(nouveauNbLarrons));
                            // On retire un latroncule au joueur sur le compteur
                            int nouveauNblatroncules = Integer.parseInt(damier.getNbLatroncules2()) - 1;
                            damier.setNbLatroncules2(Integer.toString(nouveauNblatroncules));
                        }

                        System.out.println("C'est au tour de " + damier.getJoueur1().getNom() + " de jouer.");

                        // On indique sur le damier' grace a l'image, a qui c'est de jouer
                        damier.getImageHand2().setVisible(true);
                        damier.getImageHand1().setVisible(false);


                        damier.setaQuiDeJouer(damier.getJoueur1());
                    }

                    // On synchronise aQuiDeJouer de damier avec celui de partie (damier.partie)
                    damier.getPartie().setaQuiDeJouer(damier.getaQuiDeJouer());


                    // Pour pas que l'on  puisse mettre (copier) le pion selectionné un peu partout, on le remet a null
                    damier.pionSelectionne = null;

                    // On vérifie si quelqu'un a gagné
                    if (quiAGagner() != null) {
                        // si queqlu'un a gagné on augmente son h2h et on annonce le gagnant dans une fenetre

                        Player quiAPerdu;

                        if (damier.getJoueur1() == quiAGagner()) {
                            quiAPerdu = damier.getJoueur2();
                        } else {
                            quiAPerdu = damier.getJoueur1();
                        }
                        quiAGagner().setNbPartie(quiAGagner().getNbPartie() + 1);
                        quiAPerdu.setNbPartie(quiAPerdu.getNbPartie() + 1);

                        quiAGagner().setNbPartieEncours(quiAGagner().getNbPartieEncours() - 1);
                        quiAPerdu.setNbPartieEncours(quiAPerdu.getNbPartieEncours() - 1);

                        quiAGagner().setHead2head(quiAGagner().getHead2head() + 1);
                        Function.finPartie(quiAGagner(), damier);
                        damier.dispose();
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }
        });

    }

    /**
     * Parcours tous les pions et regarde si quelqu'un a gagné Si oui, retourne
     * le joueur qui a gagné
     */
    public Player quiAGagner() {
        int nbLatroncules = 0;
        int nbLarrons = 0;

        // On parcours les pions du joueur 1 et on compte ses pions
        for (Pion p : damier.getPionsJoueur1()) {
            if (p instanceof Latroncule) {
                nbLatroncules++;
            } else if (p instanceof Larron) {
                nbLarrons++;
            }
        }

        if ((nbLarrons == 0) && (nbLatroncules == 0)) {
            return damier.getJoueur1();
        }

        nbLatroncules = 0;
        nbLarrons = 0;
        // On parcours les pions du joueur 2 et on compte ses pions
        for (Pion p : damier.getPionsJoueur2()) {
            if (p instanceof Latroncule) {
                nbLatroncules++;
            } else if (p instanceof Larron) {
                nbLarrons++;
            }
        }

        if ((nbLarrons == 0) && (nbLatroncules == 0)) {
            return damier.getJoueur2();
        }

        // On retourne null si aucun joueur n'a perdu
        return null;

    }

    /**
     * Parcours tous les pions du groupe mis en parametre et cherche sur le
     * plateau les pions a retirer de ce groupe de part la position des pions du
     * joueur B
     */
    public void manger(GroupeDePions pionsJoueurA, ArrayList<int[]> casesEncadréeParJoueurB) {
        for (Pion p : pionsJoueurA) { // On parcours tous les pions du joueur A.

            ArrayList<int[]> deplacementPermis = p.dePlacementsPermis();
            int[] coordonees = {p.getX(), p.getY()};
            deplacementPermis.add(coordonees); // On ajoute au deplacement permis la case ou se situe le pion afin que la case ou se situe le pion soit prise en compte

            boolean pionPeutFuir = false;

            for (int[] DP : deplacementPermis) {
                if (!ArrayListContientTableau(casesEncadréeParJoueurB, DP)) { // Est-ce que casesEncadréesParJoueurB ne contient pas DP ? 
                    // Ce qui veut dire : il suffit Si qu'une case ou le pion peut aller ne soit pas encadrée par les pions de l'adversaire, pour que le pion puisse fuir.
                    pionPeutFuir = true;
                    break;
                }
            }

            if (pionPeutFuir == false) { // Si le pion est piégé (toutes les cases ou le pion aller sont encadrées) 
                System.out.println(p + " mangé.");


                // Permet de mettre a jour le nombre de pions de chaque joueur sur le damier
                if (damier.getPionsJoueur1().contains(p)) {
                    if ((p instanceof Larron) && (Integer.parseInt(damier.getNbLarrons1()) > 0)) {
                        int nouveauNbLarrons = Integer.parseInt(damier.getNbLarrons1()) - 1;
                        damier.setNbLarrons1(Integer.toString(nouveauNbLarrons));
                    } else if ((p instanceof Latroncule) && (Integer.parseInt(damier.getNbLatroncules1()) > 0)) {
                        int nouveauNbLatroncules = Integer.parseInt(damier.getNbLatroncules1()) - 1;
                        damier.setNbLatroncules1(Integer.toString(nouveauNbLatroncules));
                    }
                } else if (damier.getPionsJoueur2().contains(p)) {
                    if ((p instanceof Larron) && (Integer.parseInt(damier.getNbLarrons2()) > 0)) {
                        int nouveauNbLarrons = Integer.parseInt(damier.getNbLarrons2()) - 1;
                        damier.setNbLarrons2(Integer.toString(nouveauNbLarrons));
                    } else if ((p instanceof Latroncule) && (Integer.parseInt(damier.getNbLatroncules2()) > 0)) {
                        int nouveauNbLatroncules = Integer.parseInt(damier.getNbLatroncules2()) - 1;
                        damier.setNbLatroncules2(Integer.toString(nouveauNbLatroncules));
                    }
                }


                p.getSaCase().supprimerPion();
                p.getSaCase().repaint();
                pionsJoueurA.remove(p);
                //break; // On a trouvé un pion a supprimer, on arrete de parcourir tous les pions (on estime par la qu'il ne peut y avoir qu'un pion a supprimer a la fois)
            }
        }
    }

    /**
     * Cherche par valeur la présence d'un tableau dans une liste de tableau a
     * été créé pour pallier a la déficience de ArrayList.contains
     */
    public boolean ArrayListContientTableau(ArrayList<int[]> al, int[] tab) {
        for (int[] t : al) {
            if (Arrays.equals(t, tab)) {
                return true;
            }
        }
        return false;

    }

    /**
     * verifie que la case selectionnée (celle ou le joueur veut mettre son
     * pion) correspond bien a un déplacement permis
     */
    public boolean casePermise() {
        int[] coordonnees = {x, getCoordonnéeY()};

        ArrayList<int[]> coordonneesPermises = getDamier().pionSelectionne.dePlacementsPermis();

        // On ajoute les coordonnes du pion afin qu'il puisse se déplacer sur lui meme.
        //coordonneesPermises.add(coordonnees);

        for (int[] c : coordonneesPermises) {
            if ((c[0] == coordonnees[0]) && (c[1] == coordonnees[1])) {
                return true;
            }
        }

        //  if (coordonneesPermises.contains(coordonnees))
        //    return true;
        return false;
    }

    public void setPion(Pion pPion) {
        this.pion = pPion;
        if (this.pion != null) {
            this.pion.setSaCase(this);
        }
    }

    public Pion getPion() {
        return this.pion;
    }

    /**
     * Affiche le pion qu'il y a sur sa case
     */
    public void afficherPion() {
        this.add(pion.getImg());
    }

    /**
     * Seule et unique fonction a utiliser quand on supprime un pion. Il ne faut
     * pas utiliser setPion(null) met a null le lion et retire l'image de la
     * case. exemple : cases[0][0].supprimerPion();
     */
    public void supprimerPion() {
        try {
            this.remove(pion.getImg());
            this.setPion(null);
        } catch (Exception e) {
            System.out.println("Suppression du pion impossible.");
        }
    }

    /**
     * @return the damier
     */
    public Damier getDamier() {
        return damier;
    }

    /**
     * @param damier the damier to set
     */
    public void setDamier(Damier damier) {
        this.damier = damier;
    }

    /**
     * @return the y
     */
    public int getCoordonnéeY() {
        return y;
    }
}
