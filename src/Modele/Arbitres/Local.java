/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import Joueurs.Humain;
import Joueurs.Ordinateur;
import static Modele.Arbitres.Arbitre.J1;
import static Modele.Arbitres.Arbitre.J2;
import Modele.Coup;
import Modele.Deplacement;
import Modele.Depot;
import Modele.FabriqueInsecte;
import Modele.Insecte;
import Modele.Plateau;
import Modele.Point;
import Vue.Interface;
import Vue.PaneToken;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import ruche.Reglage;

/**
 *
 * @author maxence
 */
public class Local extends Arbitre{
    
    /**
     *
     * @param p
     * @param t
     * @param d
     */
    public Local(Properties p, int t, int d, String n1, String n2 ) {
        super(p, n1, n2);
        difficulte = d;
        type = t;
    }
    
    public Local(Properties p, int t, int d, String pl, String n1, String n2 ) {
        this(p,t,d, n1, n2);
        chargement = true;
        pla = pl;
    }

    /**
     *
     */
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
        tabPieces2[0]=(int)Reglage.lis("nbReine");
        tabPieces2[1]=(int)Reglage.lis("nbScarabee");
        tabPieces2[2]=(int)Reglage.lis("nbSauterelle");
        tabPieces2[3]=(int)Reglage.lis("nbFourmi");
        tabPieces2[4]=(int)Reglage.lis("nbAraignee");
        tabPieces2[5]=(int)Reglage.lis("nbCoccinelle");
        tabPieces2[6]=(int)Reglage.lis("nbMoustique");  
        tabPieces2[7]=(int)Reglage.lis("nbCloporte");
        
        switch(type){
            case FabriqueArbitre.LOCAL_JVJ:
                joueurs[J1] = new Humain(true, prop, tabPieces, J1, nom1);
                joueurs[J2] = new Humain(true, prop, tabPieces2, J2, nom2);
                break;
            case FabriqueArbitre.LOCAL_JVIA:
                joueurs[J1] = new Humain(true, prop, tabPieces,  J1, nom1);
                joueurs[J2] = new Ordinateur(true,difficulte, prop, tabPieces2,  J2, nom2);
                break;
        }
        
        if(chargement)
            charger(pla);
        
        go();
    }
    
    /**
     *
     * @param d
     */
    @Override
    public void joue(Deplacement d){
        System.out.println ("J'ai fait caca ici aussi :x" + d);
        if(plateau().reine(jCourant)!=null){
                //if(deplacePionValide(d)){
                enCoursIt = d.route().iterator();
                enCours = new Deplacement(d.joueur(), enCoursIt.next(),enCoursIt.next());
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                etat = JOUE_EN_COURS;
                temps_ecoule=0;
                System.err.println(d+" déplacement effectué "+enCours);
            //}else{
                //System.err.println("Deplacement impossible "+d);
            //}
        }else{
            System.err.println("Déplacement impossible tant que la reine n'a pas été déposée "+jCourant);
        }
    }

    /**
     *
     * @param d
     */
    @Override
    public void joue(Depot d){
        System.err.println("caca" + d.joueur());
        if(nbCoup[jCourant]==0 && jCourant == J1){
            joueurs[d.joueur()].jouer(d.type());
            plateau.premierPion(FabriqueInsecte.creer(d.type(), jCourant, new Point(0,0)));
            etat=A_JOUER;
            nbCoup[jCourant]++;
            refaire.clear();
            historique.add(d);
            System.err.println("1- Dépot effectué "+d);
            prochainJoueur();
        }else if(nbCoup[jCourant]==0 && jCourant == J2){
            if(plateau.premierPionValide(d)){
                joueurs[jCourant].jouer(d.type());
                deposePion(d);
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                System.err.println("2- Dépot effectué "+d);
                prochainJoueur();
            }else{
                System.err.println("Depot impossible");
            }
        }else if(deposePionValide(d) && joueurs[jCourant].pion(d.type())>0){
            
            if((plateau.reine(jCourant)==null && (d.type()==Insecte.REINE || nbCoup[jCourant]<3)) || plateau.reine(jCourant)!=null){
                joueurs[jCourant].jouer(d.type());
                deposePion(d);
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                System.err.println("3- Dépot effectué "+d);
                prochainJoueur();
            }else{
                System.err.println("Vous devez déposé une reine "+jCourant);
            }
        }else{
            System.err.println("Depot impossible");
        }
        
        

    }

    /**
     *
     */
    @Override
    public void prochainJoueur() {
        
        if(plateau.estEncerclee(jCourant)){
            etat=FIN;
            Interface.goFin(joueurs[jCourant].nom(), GAGNE);
        }else if(plateau.estEncerclee((jCourant+1)%2)){
            etat=FIN;
            Interface.goFin(joueurs[jCourant].nom(), PERDU);
        }else if(configurations.contains(plateau.hashCode())){
            etat=FIN;
            System.err.println("Match nul");
        }else{
            configurations.add(plateau.hashCode());
            //System.err.println(plateau.hashCode());
            
            etat = ATTENTE_COUP;
            PaneToken.getInstance(this).update();
            jCourant = ++jCourant % 2;
            plateau.setJoueur(jCourant);
            configurations.add(plateau.hashCode());
            //System.err.println(plateau.hashCode());
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
            System.out.println(nbCoup[J1]+" "+nbCoup[J2]);
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
                prochainJoueur();
            }else if(precAucun && aucun){
                etat=FIN;
                System.err.println("Match nul");
            }else{
                if(joueurs[jCourant] instanceof Ordinateur){
                    Ordinateur o = (Ordinateur) joueurs[jCourant];
                    precAucun = aucun;
                    joue(o.coup(this, coups));
                }
            }
        }
    }

    
}
