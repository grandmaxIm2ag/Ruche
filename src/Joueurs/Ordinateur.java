/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs;

import Joueurs.IA.AI;
import Joueurs.IA.HeuristiqueV1;
import Modele.Arbitres.*;
import Modele.Case;
import Modele.Coup;
import Modele.Depot;
import Modele.Deplacement;
import Modele.Insecte;
import Modele.Plateau;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**<b>Ordinateur est la classe représentant un joueur de type IA </b>
 * @author UGA L3 Projet Logiciel 2016-2017 groupe 7
 */
public class Ordinateur extends Joueur{
    
    /** La valeur de cette constante est comprise dans l'intervalle [0;3]. */
    int difficulte;
    
    /** La valeur de cette constante est {@value}. */
    public final static int FACILE_ALEATOIRE=0;
    
    /** La valeur de cette constante est {@value}. */
    public final static int FACILE_HEURISTIQUE=1;
    
    /** La valeur de cette constante est {@value}. */
    public final static int MOYEN=2;
    
    /** La valeur de cette constante est {@value}. */
    public final static int DIFFICILE=3;
    
    /** La valeur de la GRAINE dépend du résultat de la fonction System.nanoTime().
    * @see System.nanoTime()
    */
    
    public long GRAINE;//utiliser le constructeur pour débugger(graine en paramètre)
    //19783713274596 //bug de superposition(ou premier coup)
    
    
    Random r;
    
    /** @see ruche.Configuration */
    Map<Plateau, Integer> configurations;

    /**Constructeur
     * Utilise le constructeur de la classe Joueur
     * @param m true ssi c'est le tour du joueur
     * @param d indice de la difficulté de l'ordinateur
     * @param p propriétés de la partie
     * @param tabP tableau des pièces que le joueur n'a pas posé
     * @param j indice du joueur (0 ou 1)
     * @param n nom du joueur/ordinateur
     */
    public Ordinateur(boolean m, int d, Properties p, int[] tabP, int j, String n) {
        super(m, p, tabP, j, n);
        difficulte = d;
        GRAINE=(long)System.nanoTime();
        System.out.println("Joueur "+j+" GRAINE: "+GRAINE);///////////////////////////////////////////////////////////////////////////////
        r= new Random(GRAINE);

        configurations = new HashMap();
    }
    
    /**Constructeur
     * Utilise le constructeur de la classe Joueur
     * Constructeur de test: avec une graine choissie en paramètre
     * @param m true ssi c'est le tour du joueur
     * @param d indice de la difficulté de l'ordinateur
     * @param p propriétés de la partie
     * @param tabP tableau des pièces que le joueur n'a pas posé
     * @param j indice du joueur (0 ou 1)
     * @param n nom du joueur
     * @param graine graine de la fonction random
     */
    public Ordinateur(boolean m, int d, Properties p, int[] tabP, int j, String n, int graine) {
        super(m, p, tabP, j, n);
        difficulte = d;
        r= new Random(graine);
        configurations = new HashMap();
    }
    
    /**renvoie un coup valide correspondant à la difficulté de l'IA
     * @param a arbitre de la partie
     * @param d tableau des coups possibles
     * @return Le coup à jouer
     */
    public Coup coup(Arbitre a, Coup[] d){
        switch(difficulte){
            case FACILE_ALEATOIRE:
                return coupALEATOIRE_3(a, d);
            case FACILE_HEURISTIQUE:
                return heuristiqueSurUnSeulCoup(a, d);
            case MOYEN:
                return IA_Middle(a, d);
            case 3:
                return null;   
            default:        
                return null;
        }
    }

    /**renvoie un coup aléatoire parmi le tableau d des coups possibles
     * @param a arbitre de la partie
     * @param d tableau des coups possibles
     * @return Un coup aléatoire
     */
    public Coup coupALEATOIRE_3(Arbitre a, Coup[] d){
        int choix2 = r.nextInt(d.length);
        return d[choix2];
    }
    
    /**renvoie un coup parmi le tableau d des coups possibles en choississant aléatoirement parmi les coups 
     * d'heuristique plus élevée
     * utilise l'heuristique_Simple_Profondeur1
     * @param a arbitre de la partie
     * @param d tableau des coups possibles
     * @return Un coup aléatoire parmi les coups d'heuristique plus élevée
     */
    public Coup heuristiqueSurUnSeulCoup(Arbitre a, Coup[] d){
        if(d!=null && d.length>0){
            //choisir le coup pour lequel l'heuristique est maximale
            ArrayList<Coup> res=new ArrayList();
            Plateau tmp;
            int max=Integer.MIN_VALUE;
            int heurCoup;
            
            for(int i=0;i<d.length;i=i+1){
                tmp=a.plateau().clone();
                    
                if(d[i] instanceof Depot){
                    tmp.deposePion((Depot)d[i]);
                }else if(d[i] instanceof Deplacement){
                    tmp.deplacePion((Deplacement)d[i]);
                }else{
                    continue;
                }      
                heurCoup=heuristique_Simple_Profondeur1(tmp, d);
                if(heurCoup==max){
                    //ajout à res
                    res.add(d[i]);
                }else if(heurCoup>max){
                    max=heurCoup;
                    res.clear();
                    res.add(d[i]);
                }
            }
            //choix aléatoire
            int choix= r.nextInt(res.size());
            return res.get(choix);
        }else{
            System.err.println("BUG");
            return null;
        }
    }
    
    
    /**renvoie un coup parmi le tableau d des coups possibles en choississant aléatoirement parmi les coups 
     * d'heuristique plus élevée
     * utilise l'heuristique de HEURISTIQUEV1
     * @param a arbitre de la partie
     * @param d tableau des coups possibles
     * @return Un coup aléatoire parmi les coups d'heuristique plus élevée
     */
    public Coup IA_Middle(Arbitre a, Coup[] d){
        if(d==null || d.length<= 0)
            return null;
        else{
            HeuristiqueV1 heurs = new HeuristiqueV1();
            //find the best move for the heuristic
            ArrayList<Coup> res=new ArrayList();
            Plateau tmp;
            int max = AI.MIN;
            int EvalBoard;
            
            for(int i=0;i<d.length;i=i+1){
                tmp=a.plateau().clone();
                    
                if(d[i] instanceof Depot){
                    tmp.deposePion((Depot)d[i]);
                }else if(d[i] instanceof Deplacement){
                    tmp.deplacePion((Deplacement)d[i]);
                }else{
                    continue;
                }      
                EvalBoard = heurs.EvalPlateau(a, d, tmp, this);
                if(EvalBoard == max){
                    //Add to results
                    res.add(d[i]);
                }else if(EvalBoard>max){
                    max = EvalBoard;
                    res.clear();
                    res.add(d[i]);
                }
            }   
            //return a random move from res
            int choice= r.nextInt(res.size());
                return res.get(choice);
        }
    }
    
    
    /**retourne l'heuristique de la configuration du plateau p
     * pour la difficultée FACILE_HEURISTIQUE
     * utilise les fonctions nbLiberteesReine, reineLibre et estEncerclée(de plateau)
     * @param p Le plateau du jeu
     * @param c Le tableau des coups possibles
     * @return La valeur de l'heuristique pour cette configuration
     */
    private int heuristique_Simple_Profondeur1(Plateau p, Coup[] c){      
        if(configurations.get(p)!=null){
            return configurations.get(p);
        }      
        int heuristique=0;
        if(p.estEncerclee(numJoueur)){
            return Integer.MIN_VALUE;
        }else if(p.estEncerclee(numAdversaire())){
            return Integer.MAX_VALUE;
        }else{
            if(!reineLibre(p,numJoueur, c)){
                heuristique=heuristique-2;
            }else{
                heuristique=heuristique+2;
            }
            if(!reineLibre(p,numAdversaire(), c)){
                heuristique=heuristique+2;
            }else{
                heuristique=heuristique-2;
            }
            heuristique = heuristique + nbLiberteesReine(p, numJoueur) - nbLiberteesReine(p, numAdversaire());
        }
        configurations.put(p, heuristique);
        return heuristique;
    }
    
    /**renvoie le nombre de cases libres autour de la reine et 6 si elle n'est pas encore posée
     * @param p Plateau du jeu
     * @param joueur Indice du joueur (0 ou 1)
     * @return Le nombre de libertées de la reine du joueur, entre 0 et 5 si elle est déjà posée et 6 si elle n'est pas encore posée(arbitraire)
     */
    public int nbLiberteesReine(Plateau p, int joueur){
        //compter les voisins
        if(p.reine(joueur)==null || p.voisins().get(p.reine(joueur))==null){
            return 6;
        }
        return 6-p.voisins().get(p.reine(joueur)).size();
    }
    
    /**renvoie vrai ssi: aucune pièce n'est posée au dessus de la reine du joueur d'indice "joueur" et 
     * que la reine peut se déplacer sur une case voisine
     * @param p Plateau du jeu
     * @param joueur Indice du joueur (0 ou 1)
     * @param c Tableau des coups possibles
     * @return vrai ssi: aucune pièce n'est posée au dessus de la reine du joueur d'indice "joueur" et 
     * que la reine peut se déplacer sur une case voisine, faux sinon
     */
    private boolean reineLibre(Plateau p, int joueur, Coup[] c){
        if(p.reine(joueur)==null){
            return true;
        }
        Case caseReine=p.matrice.get(p.reine(joueur));
        if(c==null || c.length==0 || caseReine.tete().type()!=Insecte.REINE ){
            return false;
        }
        boolean b = false;
        
        for(int i=0; i<c.length && !b; i++){
            if(c[i] instanceof Deplacement){
                Deplacement d = (Deplacement) c[i];
                b = b || (d.joueur()==joueur && d.source().equals(p.reine(joueur))) ;
            }
        }
        return b;
    }
    
    /**renvoie l'indice de l'autre joueur
     * @return l'indice de l'autre joueur
     */
    public int numAdversaire(){
        if(numJoueur==0){
            return 1;
        }else{
            return 0;
        }
    }
    
}//fin de la classe
