/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs;

import Joueurs.IA.AI;
import Joueurs.IA.HeuristiqueV1;
import Joueurs.IA.HeuristiqueV2;
import Joueurs.IA.MinMax;
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

/**
 *
 * @author UGA L3 Projet Logiciel 2016-2017 groupe 7
 */
public class Ordinateur extends Joueur{
    int difficulte;
    
    public final static int FACILE_ALEATOIRE=0;
    public final static int FACILE_HEURISTIQUE=1;
    public final static int MOYEN=2;
    public final static int DIFFICILE=3;
    
    
    public final static long GRAINE = (long)System.nanoTime();
    //public final static long GRAINE =26043607173097L;
    //22115700504483L;
    Random r;
    
    Map<Plateau, Integer> configurations;

    /**
     *
     * @param m
     * @param d
     * @param p
     * @param tabP
     * @param j
     */
    public Ordinateur(boolean m, int d, Properties p, int[] tabP, int j) {
        super(m, p, tabP, j);
        difficulte = d;
       // System.out.println("Joueur "+j+" GRAINE: "+GRAINE);///////////////////////////////////////////////////////////////////////////////
        r= new Random(GRAINE);

        configurations = new HashMap();
    }
    
    public Ordinateur(boolean m, Properties p, int[] tabP, int j) {
        super(m, p, tabP, j);
    }
    
    //renvoie un coup aléatoire parmi le tableau d des coups possibles
    //a arbitre de la partie, d tableau des coups possibles
    public Coup coup(Arbitre a, Coup[] d){
        switch(difficulte){
            case FACILE_ALEATOIRE:
                return coupALEATOIRE_3(a, d);
            case FACILE_HEURISTIQUE:
                return heuristiqueSurUnSeulCoup(a, d);
            case MOYEN:
                return IA_Middle(a, d);
            case DIFFICILE:
                return IA_Hard(a, d);   
            default:        
                return null;
        }
    }

    //renvoie un coup aléatoire parmi le tableau d des coups possibles
    //a arbitre de la partie, d tableau des coups possibles
    public Coup coupALEATOIRE_3(Arbitre a, Coup[] d){
        int choix2 = r.nextInt(d.length);
        return d[choix2];
    }
    
    //renvoie un coup parmi le tableau d des coups possibles en choississant aléatoirement parmi les coups 
    //d'heuristique plus élevée
    //utilise l'heuristique_Simple_Profondeur1_PointDeVueIA
    //a arbitre de la partie, d tableau des coups possibles
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
                heurCoup=heuristique_Simple_Profondeur1_PointDeVueIA(tmp, d);
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
    
    
    //renvoie un coup parmi le tableau d des coups possibles en choississant parmi les coups 
    //d'heuristique plus élevée
    //utilise l'heuristique de HEURISTIQUEV1
    //a arbitre de la partie, d tableau des coups possibles
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
              //  System.out.println("board :"+EvalBoard);
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
            //System.out.println(res.get(choice));
                return res.get(choice);
        }
    }
    
    public Coup IA_Hard(Arbitre a, Coup[] d){
        if(d==null || d.length<= 0)
            return null;
        
        HeuristiqueV2 heurs = new HeuristiqueV2();
        MinMax mx = new MinMax(this,a,heurs,2,0,d);
       
        /* Affichage des coups possibles.
        System.out.println("Appel nextmove avec les coups:");
        for(int k = 0; k < d.length;k++)
            System.out.print(d[k]+"  ");
        System.out.println(d.length);*/
        return mx.nextmove();
    }
    
    //retourne l'heuristique de la configuration du plateau p
    //pour la difficultée FACILE_HEURISTIQUE
    //p: Plateau du jeu, joueur: indice du joueur (celui qui doit jouer)
    private int heuristique_Simple_Profondeur1_PointDeVueIA(Plateau p, Coup[] d){      
        if(configurations.get(p)!=null){
            return configurations.get(p);
        }      
        int heuristique=0;
        if(p.estEncerclee(numJoueur)){
            return Integer.MIN_VALUE;
        }else if(p.estEncerclee(numAdversaire())){
            return Integer.MAX_VALUE;
        }else{
            if(!reineLibre(p,numJoueur, d)){
                heuristique=heuristique-2;                                  
            }else{
                heuristique=heuristique+2;
            }
            if(!reineLibre(p,numAdversaire(), d)){
                heuristique=heuristique+2;
            }else{
                heuristique=heuristique-2;
            }
            heuristique = heuristique + nbLiberteesReine(p, numJoueur) - nbLiberteesReine(p, numAdversaire());
        }
        configurations.put(p, heuristique);
        return heuristique;
    }
    
    //renvoie le nombre de cases libres autour de la reine et 6 si elle n'est pas encore posée
    //p: Plateau du jeu, joueur: indice du joueur (celui qui doit jouer)
    public int nbLiberteesReine(Plateau p, int joueur){
        //compter les voisins
        if(p.reine(joueur)==null || p.voisins().get(p.reine(joueur))==null){
            return 6;
        }
        return 6-p.voisins().get(p.reine(joueur)).size();
    }
    
    //renvoie vrai ssi: aucune pièce n'est posée au dessus de la reine du joueur d'indice "joueur" et 
    //que la reine peut se déplacer sur une case voisine
    //p: Plateau du jeu, joueur: indice du joueur (celui qui doit jouer), d: tableau des coups possibles
    public boolean reineLibre(Plateau p, int joueur, Coup[] d){
        if(p.reine(joueur)==null){
            return true;
        }
        Case caseReine=p.matrice.get(p.reine(joueur));
        if(d==null || d.length==0 || caseReine.tete().type()!=Insecte.REINE ){
            return false;
        }
        boolean b = false;
        
        for(int i=0; i<d.length && !b; i++){
            if(d[i] instanceof Deplacement){
                Deplacement d2 = (Deplacement) d[i];
                b = b || (d2.joueur()==joueur && d2.source().equals(p.reine(joueur))) ;
            }
        }
        return b;
    }
    
    //renvoie l'indice de l'autre joueur
    public int numAdversaire(){
        if(numJoueur==0){
            return 1;
        }else{
            return 0;
        }
    }
        
    @Override
    public Ordinateur clone(){
        Ordinateur jH = new Ordinateur(main, prop, tabPieces.clone(), numJoueur);    
        return jH;
    }
    
}//fin de la classe
