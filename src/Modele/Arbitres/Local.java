/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import Joueurs.Humain;
import Joueurs.Ordinateur;
import static Modele.Arbitres.Arbitre.AIDE;
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
import javafx.scene.input.MouseEvent;
import ruche.Reglage;

/**
 *
 * @author maxence
 */
public class Local extends Arbitre{
    Depot dEnCours;
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
                joueurs[J2] = new Humain(false, prop, tabPieces2, J2, nom2);
                break;
            case FabriqueArbitre.LOCAL_JVIA:
                joueurs[J1] = new Humain(true, prop, tabPieces,  J1, nom1);
                joueurs[J2] = new Ordinateur(false,difficulte, prop, tabPieces2,  J2, nom2);
                break;
            case FabriqueArbitre.LOCAL_IAVJ:
                joueurs[J2] = new Humain(true, prop, tabPieces,  J2, nom2);
                joueurs[J1] = new Ordinateur(false,difficulte, prop, tabPieces2,  J1, nom1);
                break;
        }
        if(chargement){
            charger(pla);
        }
        
        go();
    }
    
    /**
     *
     * @param d
     */
    @Override
    public void joue(Deplacement d){
        if(plateau().reine(jCourant)!=null){
                //if(deplacePionValide(d)){
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                etat = JOUE_EN_COURS;
                temps_ecoule=0;
                if(chargement){
                    plateau.deplacePion(d);
                    prochainJoueur();
                }
                else{
                    enCoursIt = d.route().iterator();
                    enCours = new Deplacement(d.joueur(), enCoursIt.next(),enCoursIt.next());
                }
                System.err.println(d+" déplacement effectué ");
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
        if(nbCoup[jCourant]==0 && jCourant == J1){
            joueurs[d.joueur()].jouer(d.type());
            
            etat=A_JOUER;
            nbCoup[jCourant]++;
            if(!annulation)
                refaire.clear();
            historique.add(d);
            System.err.println("1- Dépot effectué "+d);
            if(chargement){
                plateau.premierPion(FabriqueInsecte.creer(d.type(), jCourant, new Point(0,0)));
                prochainJoueur();
                
            }else{
                dEnCours = d;
                etat = JOUE_EN_COURS;
            }
        }else if(nbCoup[jCourant]==0 && jCourant == J2){
            if(plateau.premierPionValide(d)){
                joueurs[jCourant].jouer(d.type());
                deposePion(d);
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                System.err.println("2- Dépot effectué "+d);
                if(chargement){
                    deposePion(d);
                    prochainJoueur();
                    
                }else{
                    dEnCours = d;
                    etat = JOUE_EN_COURS;
                }
            }else{
                System.err.println("Depot impossible 1");
            }
        }else if(deposePionValide(d) && joueurs[jCourant].pion(d.type())>0){
            
            if((plateau.reine(jCourant)==null && (d.type()==Insecte.REINE || nbCoup[jCourant]<3)) || plateau.reine(jCourant)!=null){
                joueurs[jCourant].jouer(d.type());
                nbCoup[jCourant]++;
                if(!annulation)
                    refaire.clear();
                historique.add(d);
                System.err.println("3- Dépot effectué "+d);
                if(chargement){
                    deposePion(d);
                    prochainJoueur();
                    
                }else{
                    dEnCours = d;
                    etat = JOUE_EN_COURS;
                }
            }else{
                System.err.println("Vous devez déposé une reine "+jCourant);
            }
        }else{
            System.err.println("Depot impossible 2");
        }
        
        

    }

    /**
     *
     */
    @Override
    public void prochainJoueur() {
        System.out.println("prochain");
        if(plateau.estEncerclee(jCourant)){
            etat=FIN;
            if(joueurs[jCourant] instanceof Humain && joueurs[(jCourant+1)%2] instanceof Ordinateur)
                Interface.dialogFin("Vous avez perdu");
            else if(joueurs[jCourant] instanceof Ordinateur && joueurs[(jCourant+1)%2] instanceof Humain)
                Interface.dialogFin("Vous avez Gagne");
            else
                Interface.dialogFin(joueurs[jCourant]+" a battu "+joueurs[(jCourant+1)%2]);
        }else if(plateau.estEncerclee((jCourant+1)%2)){
            etat=FIN;
            if(joueurs[jCourant] instanceof Humain && joueurs[(jCourant+1)%2] instanceof Ordinateur)
                Interface.dialogFin("Vous avez Gagné");
            else if(joueurs[jCourant] instanceof Ordinateur && joueurs[(jCourant+1)%2] instanceof Humain)
                Interface.dialogFin("Vous avez Perdu");
            else
                Interface.dialogFin(joueurs[(jCourant+1)%2]+" a battu "+joueurs[jCourant]);
        }else if(configurations.containsKey(plateau.hashCode()) && configurations.get(plateau.hashCode())>2 ){
            
            etat=FIN;
            //System.out.println(configurations.toString()+" "+plateau.hashCode());
            Interface.dialogFin("Match Nul");
            System.err.println("Match nul");
        }else{
            if(configurations.containsKey(plateau.hashCode()))
                configurations.put(plateau.hashCode(), configurations.get(plateau.hashCode())+1 );
            else
                configurations.put(plateau.hashCode(), 1 );
            
            if(!chargement)
                PaneToken.getInstance(this).update();
            etat = ATTENTE_COUP;
            
            jCourant = ++jCourant % 2;
            plateau.setJoueur(jCourant);
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
            aucun = (coups == null || coups.length<=0);
            if(aucun){
                System.out.println("coucou");
                prochainJoueur();
            }else{
                if(joueurs[jCourant] instanceof Ordinateur && !chargement){
                    Ordinateur o = (Ordinateur) joueurs[jCourant];
                    annulation=false;
                    joue(o.coup(this, coups));
                }
            }
        }
    }

    public void maj(long t){
        //System.gc();
        if(Interface.pointeur().event()!=null){
            boolean b = this.accept(Interface.pointeur());
            //System.out.println(b);
            if(b)
                plateau.clearAide();
                if((Interface.pointeur().event().getEventType() == MouseEvent.MOUSE_CLICKED) && (etat == AIDE)){
                    etat = ATTENTE_COUP;
                    plateau.setDepotAide(-1);
                    aide = false;
                }
            Interface.pointeur().traiter();
        }
        long nouv = t-temps;
        temps=t;
        switch(etat){
            case AIDE:
                temps_ecoule+=nouv;
                if(temps_ecoule>=1000000000){
                    temps_ecoule=0;
                    aide = !aide;
                }
                break;
            case ATTENTE_COUP:
                aide = false;
                break;
            case PAUSE:
                break;
            case JOUE_EN_COURS:
                temps_ecoule+=nouv;
                if(temps_ecoule>=100000000){
                    temps_ecoule=0;
                    if(enCours!=null){
                        
                        plateau.deplacePion(enCours);
                        if(!enCoursIt.hasNext()){
                            enCours = null;
                            etat=A_JOUER;
                        }else{
                            Point p = enCoursIt.next();
                            Point src = enCours.destination().clone();
                            enCours = new Deplacement(enCours.joueur(),src, p );
                        }
                    }else if(dEnCours !=null){
                        if(nbCoup[J1]==0 && nbCoup[J1]==0 ){
                            plateau.premierPion(FabriqueInsecte.creer(dEnCours.type(), dEnCours.joueur(), new Point(0,0) ));
                        }else{
                            plateau.deposePion(dEnCours);
                        }
                        dEnCours=null;
                    }else{
                        enCours = null;
                        etat=A_JOUER;
                    }
                }
                break;
            case A_JOUER:
                prochainJoueur();
                plateau.setJoueur(jCourant);
                plateau.clearAide();
                break;
            case FIN:
                Interface.fin();
                break;
        }
    }
    
    
    
    
}
