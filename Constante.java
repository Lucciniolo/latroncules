

import java.awt.Dimension;
import java.io.File;
/*
 * Cette classe permet d'acceder a toutes les constantes utiliser dans le proje
 */

public class Constante {
	
	/*
         * separateur specifique OS
        */
	
        public static String S =  File.separator;
        
        /*
        * régles du jeu afficher dans la fenetre principale
        */
	
        
        public static String RULE = "La table à jouer est un carré divisé en un certain nombre de cases, soit 64."+
                                    " Les cases sont alternativement blanches et noires. Les pièces sont au nombre"+
                                    " de trende-deux, seize noires pour un des joueurs, seize blanches pour l'autre."+
                                    " Ces seize pièces se divisent en deux groupes de huit pièces chacun, le groupe"+ 
                                    " des larrons et celui des latroncules. On commence par ranger les larrons sur les"+
                                    " cases qui bordent les côtés supérieurs et inférieurs de l'échiquier. Devant eux,"+ 
                                    " on place les latroncules. Chaque jeu est ainsi rangé sur deux lignes, les latroncules"+ 
                                    " devant et les larrons derrière. Les latroncules ne peuvent marcher qu'en avant, droit"+ 
                                    " devant eux, et n'avancent jamais que d'une case à la fois. Les larrons peuvent marcher"+ 
                                    " dans toutes les directions et franchir plusieurs cases à la fois, mais en ligne droite"+ 
                                    " et à la condition que les cases intermédiaires soient inoccupées. Le but du jeu est"+ 
                                    " de prendre toutes les pièces de l'adversaire. Une pièce est prise lorsqu'elle ne peut"+ 
                                    " se soustraire à un échec. Une pièce est en échec quand elle se trouve entre deux pièces ennemies."+ 
                                    " Quand un latroncule arrive à la dernière case de sa rangée verticale, le joueur change ce latroncule"+ 
                                    " contre un larron.";

        
       
}
