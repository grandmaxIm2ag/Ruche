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
import Modele.Plateau;
import Modele.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class Ordinateur extends Joueur{
    int difficulte;
    
    /*public final static int FACILE_ALEATOIRE_MAUVAIS=-2;
    public final static int ALEATOIRE_LONG=-1;*/
    
    public final static int FACILE_ALEATOIRE=-1;
    public final static int FACILE_HEURISTIQUE=0;
    public final static int MOYEN=1;
    public final static int DIFFICILE=2;
    
    
    public final static long GRAINE = 548789;
    Random r;
    
    //public int[] tabPieces;
    //public int nbPieces;
    
    public Ordinateur(boolean m, int d, Properties p, int[] tabP, int nbP, int j) {
        super(m, p, tabP, nbP, j);
        difficulte = d;
        if(difficulte==0||difficulte==-1){
            r= new Random(GRAINE);
        }
        //this.tabPieces= tabP;
        //this.nbPieces=nbP;
        //Reglage.init(prop);
        /*
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
        */
    }
    public Coup coup(Arbitre a, Coup[] d){
        //Coup=Dépot ou Déplacement
        switch(difficulte){
            case FACILE_ALEATOIRE:
                return coupALEATOIRE_3(a, d);
            case FACILE_HEURISTIQUE:
                return heuristiqueSurUnSeulCoup(a, d);
            case 1:
                return null;
            case 2:
                return null;   
            default:        
                return null;
        }
    }

    public Coup coupALEATOIRE_3(Arbitre a, Coup[] d){
        System.out.println(Arrays.toString(tabPieces)+" "+numJoueur);
        ArrayList<Coup[]> l=new ArrayList<>();
        //Déplacements
        Coup[] t;
        if(d!=null && d.length>0)
            l.add(d);
        //Dépots
        //pour tout type de pièces
        int type;
        for(type=0;type<tabPieces.length;type=type+1){
            if(tabPieces[type]>0 && (t = a.depotPossible(this.numJoueur,type))!=null){
                l.add(t);
            }
        }
        //choisi le coup:choix aléatoire avec nb de coup possibles
        int taille;
        Iterator<Coup[]> it =l.iterator();
        taille=nbCoupPossiblesTotaux(it);
        System.out.println(taille);
        int choix2= r.nextInt(taille);
        
        it=l.iterator();
        int tmp=0;
        while(it.hasNext()){
            Coup[] c=it.next();
            if(choix2<=tmp+c.length-1){
                return c[choix2-tmp];
            }else{
                tmp=tmp+c.length;
            }
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
    
    public Coup heuristiqueSurUnSeulCoup(Arbitre a, Coup[] d){
        ArrayList<Coup[]> l=new ArrayList<>();
        //Déplacements
        Coup[] t;
        if(d!=null && d.length>0)
            l.add(d);
        //Dépots
        //pour tout type de pièces
        int type;
        for(type=0;type<tabPieces.length;type=type+1){
            if(tabPieces[type]>0 && (t = a.depotPossible(this.numJoueur,type))!=null){
                
                l.add(t);
            }
        }
        
        
        //choisir le coup pour lequel l'heuristique est maximale
        Iterator<Coup[]> it =l.iterator();
        if(it.hasNext()){
            ArrayList<Coup> res=new ArrayList();
            //res.add(it.next()[0]);
            Plateau tmp=a.plateau().clone();
            int max=Integer.MIN_VALUE; //heuristique_Simple_Profondeur1_PointDeVueIA(tmp);
            int heurCoup;
            
            while(it.hasNext()){
                Coup[] courant=it.next();
                for(int i=0;i<courant.length;i=i+1){
                    tmp=a.plateau().clone();
                    
                    if(courant[i] instanceof Depot)
                        tmp.deposePion((Depot)courant[i]);
                    else if(courant[i] instanceof Deplacement)
                        tmp.deplacePion((Deplacement)courant[i]);
                    else
                        continue;
                    
                    heurCoup=heuristique_Simple_Profondeur1_PointDeVueIA(tmp);
                    //System.out.println("MAX = "+max+" heurCoup"+heurCoup);
                    if(heurCoup==max){
                        //ajout à res
                        res.add(courant[i]);
                        //System.out.println(res);
                    }else if(heurCoup>max){
                        max=heurCoup;
                        res.clear();
                        res.add(courant[i]);
                        //System.out.println(res);
                    }
                }      
            }
            //choix aléatoire
            //System.out.println(res.size()+"    "+l.size());//////////////////////////////////////////////////////////////////////////////////////////
            int choix= r.nextInt(res.size());
            return res.get(choix);
        }else{
            System.out.println("BUG");
            return null;
        }
    }
    
    public int heuristique_Simple_Profondeur1_PointDeVueIA(Plateau p){
        int heuristique=0;
        if(p.estEncerclee(numJoueur)){
            heuristique=Integer.MIN_VALUE;
        }else if(p.estEncerclee(numAdversaire())){
            heuristique=Integer.MAX_VALUE;
        }else{
            if(!reineLibre(p,numJoueur)){
                heuristique=heuristique-2;
            }
            if(!reineLibre(p,numAdversaire())){
                heuristique=heuristique+2;
            }      
            heuristique=heuristique+nbLiberteesReine(p, numJoueur)-nbLiberteesReine(p, numAdversaire());
        }
        return heuristique;
    }
    
    public int nbLiberteesReine(Plateau p, int joueur){
        //compter les voisins
        if(p.reine(joueur)==null)
            return 6;
        
        int libertees=0;
        List<Point> l=p.voisins(p.reine(joueur));
        Iterator it= l.iterator();
        while(it.hasNext()){
            if(!p.matrice.get(it.next()).utilise()){
                libertees=libertees+1;
            }
        } 
        return libertees;
    }
    
    public boolean reineLibre(Plateau p, int joueur){
        if(p.reine(joueur)==null)
            return true;
        Case caseReine=p.matrice.get(p.reine(joueur));
        if(caseReine.tete().type()==0){
            List<Coup> deplPoss=p.deplacementPossible(caseReine.tete());
            if(deplPoss!=null && deplPoss.size()>0){
                return true;
            }
        }
            return false;
    }
    
    public int numAdversaire(){
        if(numJoueur==0){
            return 1;
        }else{
            return 0;
        }
    }
    
}//fin de la classe


    /*
    public Coup coup(Arbitre a){
        //Coup=Dépot ou Déplacement
        switch(difficulte){
            case -2:
                return coup_ALEATOIRE_MAUVAIS();
            case -1:
                return coup_ALEATOIRE_LONG(a);
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
            return new Depot(0,t,new Point(0,0));
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
