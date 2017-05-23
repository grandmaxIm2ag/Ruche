/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import Joueurs.*;
import Modele.*;

import ruche.Reglage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**Classe de test pour les IA: /!/utiliser en lancant simulation (pas init)*/
public class TestIA extends Arbitre{
    
    int difficulteJ1;
    int difficulteJ2;

   
    int nbSimulations;
    long[] tempsParties;
    int[] nbCoupsJouesJ1;
    int[] victoires;
    int victorieux;
    
    String nom;
    String resultatsBrut;
    String resultatMoyenne;
    
    
    /**
     * 
     * @param p
     * @param d1
     * @param d2
     * @param simulations 
     */
    public TestIA(Properties p, int d1, int d2, int simulations){
        super (p, "", "");
        difficulteJ1=d1;
        difficulteJ2=d2;
        nbSimulations=simulations;
        tempsParties= new long[nbSimulations];
        nbCoupsJouesJ1= new int[nbSimulations];
        victoires=new int[nbSimulations];
        nom=d1+"::"+d2+"_"+nbSimulations;
    }
    
    public int[] simulation(){
        long debutPartie;
        long finPartie;
        String res="";
        for(int i=0;i<nbSimulations;i=i+1){
            System.err.println("Nouvelle Partie "+i);
            configurations.clear();
            debutPartie=System.nanoTime();
            simulation2();
            victoires[i]=victorieux;
            nbCoupsJouesJ1[i]=nbCoup[0];
            finPartie=System.nanoTime();
            tempsParties[i]=finPartie-debutPartie;
            
            if(victorieux==-1){
                res=res+"EGALITE, nbCoups:"+nbCoup[0];
            }else if(victorieux==0){
                res=res+"VICTOIRE J1, difficulte:"+difficulteJ1+", nbCoups:"+nbCoup[0];
            }else{
                res=res+"VICTOIRE J2, difficulte:"+difficulteJ2+", nbCoups:"+nbCoup[1];
            }
            res=res+", temps:"+tempsParties[i]+"(en nanosec)\n";
        }
        resultatsBrut=res;
        System.out.println(resultatsBrut);
        calcul();
        System.out.println(resultatMoyenne);
        return victoires;
    }
    
    public void calcul(){
        int nbVicJ1=0;
        int nbVicJ2=0;
        int nbNul=0;
        long sommeTemps=0;
        int sommeCoups=0;
        for(int i=0;i<nbSimulations;i=i+1){
            if(victoires[i]==0){
                nbVicJ1=nbVicJ1+1;
            }else if(victoires[i]==1){
                nbVicJ2=nbVicJ2+1;
            }else{
                nbNul=nbNul+1;
            }
            sommeTemps=sommeTemps+tempsParties[i];
            sommeCoups=sommeCoups+nbCoupsJouesJ1[i];
        }
        resultatMoyenne="J1:"+nbVicJ1+" (diff:"+difficulteJ1+"), J2:"+nbVicJ2+" (diff:"+difficulteJ2+"), NUL:"+nbNul+
                ", nbCoupsMoy:"+sommeCoups/nbSimulations+", tempsMoy:"+sommeTemps/nbSimulations+"(en nanosec), nbSimulations:"+nbSimulations;
            }
            
    
    @Override
    public void init(){
        int[] tabPieces = new int[8];
        tabPieces[0]=(int)Reglage.lis("nbReine");
        tabPieces[1]=(int)Reglage.lis("nbScarabee");
        tabPieces[2]=(int)Reglage.lis("nbSauterelle");
        tabPieces[3]=(int)Reglage.lis("nbFourmi");
        tabPieces[4]=(int)Reglage.lis("nbAraignee");
        tabPieces[5]=(int)Reglage.lis("nbCoccinelle");
        tabPieces[6]=(int)Reglage.lis("nbMoustique");  
        tabPieces[7]=(int)Reglage.lis("nbCloporte");
        
        int[] tabPieces2 = new int[8];
        System.arraycopy(tabPieces, 0, tabPieces2, 0, tabPieces2.length);
        
        joueurs[J1] = new Ordinateur(true,difficulteJ1, prop, tabPieces, J1, "");
        joueurs[J2] = new Ordinateur(true,difficulteJ2, prop, tabPieces2, J2, "");
        
        go();
    }
    
    
    @Override
    public void go(){
        if(joueurs[J1] instanceof Ordinateur){
            Ordinateur o = (Ordinateur) joueurs[J1];
            List<Coup[]> tab = new LinkedList();
                for(int i=0; i<joueurs[jCourant].pions().length; i++){
                    if(joueurs[jCourant].pions()[i]!=0){
                        Coup[] tmp = depotPossible(jCourant, i);
                        if(tmp!=null)
                            tab.add(tmp);
                    }     
                }   
                Coup[] tmp;
                if((tmp=deplacementPossible(jCourant))!=null)
                    tab.add(tmp);

                int taille= 0;
                Iterator<Coup[]> it = tab.iterator();
                while(it.hasNext())
                    taille+=it.next().length;
                it = tab.iterator();
                coups= new Coup[taille];
                for(int i=0; i<taille;i++){
                    Coup[] x = it.next();
                System.arraycopy(x, 0, coups, i, x.length);
                }
                joue(o.coup(this, coups));
        }
    }
      
    @Override
    public void prochainJoueur() {
        if(plateau.estEncerclee(jCourant)){
            etat=FIN;
            victorieux=++jCourant % 2;
        }else if(configurations.contains(plateau.hashCode())){
            etat=FIN;
            System.err.println("Match nul");
            victorieux=-1;
        }else{
            configurations.add(plateau.hashCode());
            jCourant = ++jCourant % 2;
            List<Coup[]> tab = new LinkedList();
            for(int i=0; i<joueurs[jCourant].pions().length; i++){
                if(joueurs[jCourant].pions()[i]!=0){
                    Coup[] tmp = depotPossible(jCourant, i);
                    if(tmp!=null)
                        tab.add(tmp);
                }
            }

            Coup[] tmp;
            if((tmp=deplacementPossible(jCourant))!=null)
                tab.add(tmp);

            int taille= 0;
            Iterator<Coup[]> it = tab.iterator();
            while(it.hasNext())
                taille+=it.next().length;
            it = tab.iterator();
            //System.out.println(nbCoup[J1]+" "+nbCoup[J2]);
            coups = new Coup[taille];
            int i=0;
            while(it.hasNext()){
                Coup[] x = it.next();
                int j;
                for(j=0; j<x.length; j++){
                    coups[i+j]=x[j];
                }
                 i+=j;
            }
            aucun = coups == null || coups.length<=0;
            if(aucun){
                etat=JOUE_EN_COURS;
            }else if(precAucun && aucun){
                etat=FIN;
                victorieux=-1;
            }else{
                if(joueurs[jCourant] instanceof Ordinateur){
                    Ordinateur o = (Ordinateur) joueurs[jCourant];
                    precAucun = aucun;
                    joue(o.coup(this, coups));
                }
            }
        }
    }
    
     /**
     *
     * @param d
     */
    @Override
    public void joue(Deplacement d){
                enCoursIt = d.route().iterator();
                deplacePion(d);
                nbCoup[jCourant]++;
                historique.add(d);
                etat = JOUE_EN_COURS;
    }

    /**
     *
     * @param d
     */
    @Override
    public void joue(Depot d){
        if(nbCoup[jCourant]==0 && jCourant == J1){
            joueurs[jCourant].jouer(d.type());
            plateau.premierPion(FabriqueInsecte.creer(d.type(), jCourant, new Point(0,0)));
            nbCoup[jCourant]++;
            historique.add(d);
        }else if(nbCoup[jCourant]==0 && jCourant == J2){
            if(plateau.premierPionValide(d)){
            joueurs[jCourant].jouer(d.type());
                deposePion(d);
                nbCoup[jCourant]++;
                historique.add(d);
                joueurs[jCourant].jouer(d.type());
            }else{
                //System.err.println("Depot impossible");
            }
        }else if(deposePionValide(d) && joueurs[jCourant].pion(d.type())>0){
            joueurs[jCourant].jouer(d.type());
            deposePion(d);
            nbCoup[jCourant]++;
            historique.add(d);
        }else{
            //System.err.println("Depot impossible");
        }
        etat = JOUE_EN_COURS;
    }
        
    /**
     * 
     * @return nom du fichier de simulation (type d'IA, nombres de tests)
     */
    public String nom(){
        return nom;
    }
    
    /**
     * 
     * @return le descriptif de toutes les parties
     */
    public String resultatsBrut(){
        return resultatsBrut;
    }
    
    /**
     * 
     * @return les carectéristiques moyennes de la simulation+nb victoires des deux joueurs
     */
    public String resultatMoyenne(){
        return resultatMoyenne;
    }


    /**
     *
     * @return le niveau de difficulte du joueur 1
     */
    public int difficulteJ1(){
        return difficulteJ1;
    }
    
    /**
     *
     * @return 
     * @returnle niveau de difficulte du joueur 2
     */
    public int difficulteJ2(){
        return difficulteJ2;
    }
    
    public void simulation2(){
        int joue = 0;
        int prochain = 1;
        Coup c;
        int et = prochain;
        boolean b = true;
        nouvellePartie();
        while(b){
            switch(etat){
                case ATTENTE_COUP:
                    prochainJoueur();
                    break;
                case JOUE_EN_COURS:
                    prochainJoueur();
                    break;
                case FIN:
                    b = false;
                    break;
            }
            
        }
    }

}
 
    