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
import Modele.Point;
import Vue.PaneToken;
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
    public Local(Properties p, int t, int d) {
        super(p);
        difficulte = d;
        type = t;
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
        
        
        switch(type){
            case FabriqueArbitre.LOCAL_JVJ:
                joueurs[J1] = new Humain(true, prop, tabPieces, J1);
                joueurs[J2] = new Humain(true, prop, tabPieces, J2);
                break;
            case FabriqueArbitre.LOCAL_JVIA:
                joueurs[J1] = new Humain(true, prop, tabPieces,  J1);
                joueurs[J2] = new Ordinateur(true,difficulte, prop, tabPieces,  J2);
                break;
        }
    }
    
    /**
     *
     * @param d
     */
    @Override
    public void joue(Deplacement d){
        if(plateau().reine(jCourant)!=null){
            if(deplacePionValide(d)){
                enCoursIt = d.route().iterator();
                enCours = new Deplacement(d.joueur(), enCoursIt.next(),enCoursIt.next());
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                System.err.println(d+" déplacement effectué");
                prochainJoueur();
            }else{
                System.err.println("Deplacement impossible "+d);
            }
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
        if(nbCoup[jCourant]==0 && jCourant == J1){
            joueurs[jCourant].jouer(d.type());
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
                joueurs[jCourant].jouer(d.type());
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
        etat = ATTENTE_COUP;
        PaneToken.getInstance(this).update();
        jCourant = ++jCourant % 2;

        if(plateau.estEncerclee(jCourant)){
            etat=FIN;
            System.err.println(jCourant+" à perdu");
        }else{
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
