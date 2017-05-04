/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs;

import Modele.Arbitre;
import Modele.Case;
import Modele.Coup;
import Modele.Depot;
import Modele.Deplacement;
import Modele.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class Ordinateur extends Joueur{
    int difficulte;
    
    public final static int FACILE_ALEATOIRE_MAUVAIS=-2;
    public final static int ALEATOIRE_LONG=-1;
    
    public final static int FACILE_ALEATOIRE=0;
    public final static int MOYEN=1;
    public final static int DIFFICILE=2;
    
    
    public final static long GRAINE = 548789;
    Random r;
    
    public int[] tabPieces;
    public int nbPieces;
    
    public Ordinateur(boolean m, int d, Properties p) {
        super(m, p);
        difficulte = d;
        if(difficulte==0||difficulte==-1){
            r= new Random(GRAINE);
        }
        this.tabPieces= new int [8];
        this.nbPieces=0;
        Reglage.init(prop);
        tabPieces[0]=(int)Reglage.lis("nbReine");
        tabPieces[1]=(int)Reglage.lis("nbScarabee");
        tabPieces[2]=(int)Reglage.lis("nbSauterelle");
        tabPieces[3]=(int)Reglage.lis("nbFourmi");
        tabPieces[4]=(int)Reglage.lis("nbAraignee");
        tabPieces[5]=(int)Reglage.lis("nbCoccinelle");
        tabPieces[6]=(int)Reglage.lis("nbMoustique");  
        tabPieces[7]=(int)Reglage.lis("nbCloporte");
        for(int i=0; i<tabPieces.length; i=i+1){
            nbPieces=nbPieces+tabPieces[i];
        }
        
    }
    
    /*
    public Coup coup(Arbitre a){
        //Coup=Dépot ou Déplacement
        switch(difficulte){
            case -2:
                return coup_ALEATOIRE_MAUVAIS();
            case -1:
                return coup_ALEATOIRE_LONG(a);
    */
            /*
            case 0:
                return...;
            case 1:
                return ...;
            case 2:
                return ...;
            */
    /*
            default:        
                return null;
        }
    }
    
    public Coup coup_ALEATOIRE_MAUVAIS(){
        Deplacement[] tabMouv=deplacementsPossibles_MAUVAIS();
        if(pionsDisponibles() && tabMouv.length!=0){
            //Dépots possibles et Déplacements possibles
            int choix=r.nextInt(1);
                if(choix==0){
                    //Deplacement
                    int depl=r.nextInt(tabMouv.length);
                    return tabMouv[depl];
                }else{
                    //Depot
                    return depotAleatoire_MAUVAIS();
                }  
        }else if(pionsDisponibles() || tabMouv.length!=0){
            //Dépots possibles ou Déplacements possibles
            if(tabMouv.length==0){
                //Dépots possibles
                return depotAleatoire_MAUVAIS();
            }else{
                //Déplacements possibles
                int depl=r.nextInt(tabMouv.length);
                return tabMouv[depl];
            } 
        }
        return null;
    }
    
    
    //Attention! condition initiale: pionsDisponibles==true
    public Coup depotAleatoire_MAUVAIS(){
            int t=r.nextInt(pions.length);
            while(pions[t]==0){
                t=r.nextInt(pions.length);
            }
            //t contient l'indice d'un pion pouvant être joué
            //TODO: choix placement
            //TODO: choix placement
            //TODO: choix placement
            return new Depot(t,new Point(0,0));
    }
    
    public Deplacement[] deplacementsPossibles_MAUVAIS(){
        Deplacement[] tabMouv=new Deplacement[9999999];
        //TODO:construire le tableau
        //TODO:construire le tableau
        //TODO:construire le tableau
        return tabMouv;
    }
    

    public Coup coup_ALEATOIRE_LONG(Arbitre a){
        //choix du type d'insecte joué
        int choix=r.nextInt(nbPieces);
        int typeChoisi=choixParSommesCumulees(choix,tabPieces);
        Case [][] mat=a.plateau().matrice;
        int i=0;
        int insectesTrouves=0;
        ArrayList<Coup[]> l=new ArrayList<>();
        
        while(i<mat.length && insectesTrouves!=tabPieces[typeChoisi]){
            int j=0;
            while(j<mat[0].length && insectesTrouves!=tabPieces[typeChoisi]){
                if(mat[i][j].tete().type()==typeChoisi){
                    insectesTrouves=insectesTrouves+1;
                    Coup[] t;
                    if( (t=mat[i][j].tete().deplacementValide(mat))!=null){
                        l.add(t);
                    }
                }               
                j=j+1;
            }
            i=i+1;
        }
        if(!l.isEmpty()){
            //choisi le coup
            //nb de coup possibles
            int taille;
            Iterator<Coup[]> it =l.iterator();
            taille=nbCoupPossiblesTotaux(it);
            int choix2= r.nextInt(taille);
            it=l.iterator();
            int tmp=0;
            while(it.hasNext()){
                Coup[] c=it.next();
                if(c.length<=tmp){
                    return c[choix2-tmp];
                }else{
                    tmp=tmp+c.length-1;
                }
            
            }              
        }else{
            System.err.println("Nouvelle recherche");
            return coup_ALEATOIRE_LONG(a);//relance si pas de coup possible
        }
        return null;
    }
    
    public int nbCoupPossiblesTotaux(Iterator<Coup[]> it){
        int taille=0;
        while(it.hasNext()){
            taille=taille+it.next().length;
        }
        return taille;
    }
    
    public int choixParSommesCumulees(int choix, int[] pieces){
        int tmp=0;
        int j=0;
        while(j<pieces.length && tmp<=choix){
            if(tmp>=choix){
                return j;
            }
            tmp=tmp+pieces[j];
            j=j+1;
        }
        return -1;
    }
    
    public boolean pionsDisponibles(){
        boolean dispo=false;
        int i=0;
        while(i<(pions.length) && !dispo){
            if(pions[i]!=0){
             dispo=true;
            }
            i=i+1;
        }
        return dispo;
    }*/
    
}
