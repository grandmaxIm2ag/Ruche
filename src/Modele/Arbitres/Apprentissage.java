/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import Joueurs.IA.HeuristiqueMoy;
import Joueurs.IA.HeuristiqueV2;
import Joueurs.Ordinateur;
import static Modele.Arbitres.Arbitre.FIN;
import static Modele.Arbitres.Arbitre.J1;
import static Modele.Arbitres.Arbitre.J2;
import static Modele.Arbitres.Arbitre.JOUE_EN_COURS;
import Modele.Coup;
import Modele.Deplacement;
import Modele.Depot;
import Modele.FabriqueInsecte;
import Modele.Plateau;
import Modele.Point;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import ruche.Reglage;

/**
 *
 * @author maxence
 */
public class Apprentissage extends Arbitre{
    PrintWriter bdd;
    HeuristiqueV2 difficile;
    HeuristiqueMoy moyen;
    int nbSimulations;
    HashMap<Integer, Integer> hDiff;
    HashMap<Integer, Integer> hMoy;
    HashMap<Integer, Coup> hashCoup;
    boolean end = false;
    
    boolean heur;
    /**
     * 
     * @param p
     */
    public Apprentissage(Properties p){
        super (p, "", "");
        nbSimulations = 100;
        hDiff = new HashMap();
        hMoy = new HashMap();
        difficile = new HeuristiqueV2(null);
        moyen = new HeuristiqueMoy(null);
        heur = true;
    }
    
    public Apprentissage(Properties p, int d){
        super (p, "", "");
        nbSimulations = 25;
        hashCoup = new HashMap();
        difficile = new HeuristiqueV2(null);
        moyen = new HeuristiqueMoy(null);
        difficulte = d;
        heur = false;
    }
    
    public void apprentissageHeuristique(){
        try {
            for(int i=0;i<nbSimulations;i++){
                System.err.println("Nouvelle Partie "+i);
                configurations.clear();
                simulation2();
            }
            File f = new File("Ressources/Simulations/Apprentissage/difficileHeuristique");
            f.createNewFile();
            bdd = new PrintWriter(f);
            for(Map.Entry<Integer, Integer> entry : hDiff.entrySet() ){
                bdd.println(entry.getKey()+"::"+entry.getValue());
            }
            bdd.close();
            f = new File("Ressources/Simulations/Apprentissage/moyenHeuristique");
            f.createNewFile();
            bdd = new PrintWriter(f);
            for(Map.Entry<Integer, Integer> entry : hMoy.entrySet() ){
                bdd.println(entry.getKey()+"::"+entry.getValue());
            }
            bdd.close();
        } catch (IOException ex) {
            Logger.getLogger(Apprentissage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void apprentissageCoup(){
        try {
            for(int i=0; i<nbSimulations; i++){
                System.err.println("Nouvelle Partie "+i);
                configurations.clear();
                simulation2();
            }
            File f = new File("Ressources/Simulations/Apprentissage/coups"+difficulte);
            f.createNewFile();
            bdd = new PrintWriter(f);
            for(Map.Entry<Integer, Coup> entry : hashCoup.entrySet()){
                bdd.println(entry.getKey()+"::"+entry.getValue());
            }
            bdd.close();
        } catch (IOException ex) {
            Logger.getLogger(Apprentissage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void apprentissageEndGame(){
        try {
            for(int i=0; i<nbSimulations; i++){
                System.err.println("Nouvelle Partie "+i);
                configurations.clear();
                simulation2();
                for(int j=0; j<10; j++){
                    precedent();
                }
                etat = ATTENTE_COUP;
                end = true;
                hashCoup = new HashMap();
                joueurs[J1] = new Ordinateur( true,Ordinateur.DIFFICILE, prop, joueurs[J1].pions(), J1, "");
                joueurs[J2] = new Ordinateur( true,Ordinateur.DIFFICILE, prop, joueurs[J2].pions(), J2, "");
                simulation2();
            }
            File f = new File("Ressources/Simulations/Apprentissage/endGame");
            f.createNewFile();
            bdd = new PrintWriter(f);
            for(Map.Entry<Integer, Coup> entry : hashCoup.entrySet() ){
                bdd.println(entry.getKey()+"::"+entry.getValue());
            }
            bdd.close();
        } catch (IOException ex) {
            Logger.getLogger(Apprentissage.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
        if(heur){
            joueurs[J1] = new Ordinateur(true,Ordinateur.FACILE_ALEATOIRE, prop, tabPieces, J1, "");
            joueurs[J2] = new Ordinateur(false,Ordinateur.FACILE_ALEATOIRE, prop, tabPieces2, J2, "");
        }else{
            joueurs[J1] = new Ordinateur(true,difficulte, prop, tabPieces, J1, "");
            joueurs[J2] = new Ordinateur(false,difficulte, prop, tabPieces2, J2, "");
        }
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
        //hDiff.put(plateau.hashCode(),difficile.EvalPlateau(this, depots, plateau, (Ordinateur)joueurs[jCourant] , enCours) );
        
        if(plateau.estEncerclee(jCourant)){
            etat=FIN;
        }else if(plateau.estEncerclee((jCourant+1)%2)){
            etat=FIN;
        }else if(configurations.containsKey(plateau.hashCode()) && configurations.get(plateau.hashCode())>2 ){
            etat=FIN;
            System.err.println("Match nul");
        }else{
            if(configurations.containsKey(plateau.hashCode())){
                configurations.put(plateau.hashCode(), configurations.get(plateau.hashCode())+1 );
            }else{
                configurations.put(plateau.hashCode(), 1 );
            }
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
            aucun = coups == null || coups.length<=0;
            if(aucun){
                prochainJoueur();
            }else if(precAucun && aucun){
                etat=FIN;
            }else{
                if(heur){
                    hDiff.put(plateau.hashCode(),difficile.EvalPlateau(this, coups, plateau, (Ordinateur)joueurs[jCourant] , null) );
                    hMoy.put(plateau.hashCode() ,moyen.EvalPlateau(this, coups, plateau, (Ordinateur)joueurs[jCourant], null) );
                }
                if(joueurs[jCourant] instanceof Ordinateur){
                    Ordinateur o = (Ordinateur) joueurs[jCourant];
                    precAucun = aucun;
                    if(heur || plateau.reine(jCourant)==null)
                        joue(o.coup(this, coups));
                    else if(!heur || end){
                        Coup c = o.coup(this, coups);
                        //System.out.println(c+" "+translateConcreteToAbstract(c, jCourant, plateau)+" "+translateAbstractToConcrete(translateConcreteToAbstract(c, jCourant, plateau), jCourant, plateau) );
                        hashCoup.put(plateau.hashCode(),translateConcreteToAbstract(c, jCourant, plateau));
                        joue(c);
                    }
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
    
    public static Coup translateAbstractToConcrete(Coup c, int j, Plateau p){
        if(c instanceof Deplacement){
            Deplacement d = (Deplacement) c;
            Point reference = p.reine(j);
            int diffX = (int)reference.x();
            int diffY = (int)reference.y();
            List<Point> r = d.route(); 
            Point newSrc = new Point(r.get(0).x()+diffX, r.get(0).y()+diffY );
            Point newSuiv = new Point(r.get(1).x()+diffX, r.get(1).y()+diffY );
            Deplacement res = new Deplacement(d.joueur(), newSrc, newSuiv );
            for(int i=2; i<r.size(); i++){
                res.add(new Point(r.get(i).x()+diffX, r.get(i).y()+diffY) );
            }
            
            return res;
        }else if(c instanceof Depot){
            Depot d = (Depot) c;
            Point reference = p.reine(j);
            int diffX = (int)reference.x();
            int diffY = (int)reference.y();
            Point newDest = new Point(d.destination().x()+diffX, d.destination().y()+diffY );
            return new Depot(d.joueur(),d.type(), newDest );
        }
        return null;
    }
    
    public static Coup translateConcreteToAbstract(Coup c, int j, Plateau p){
        if(c instanceof Deplacement){
            Deplacement d = (Deplacement) c;
            Point reference = p.reine(j);
            int diffX = (int) -reference.x();
            int diffY = (int) -reference.y();
            List<Point> r = d.route(); 
            Point newSrc = new Point(r.get(0).x()+diffX, r.get(0).y()+diffY );
            Point newSuiv = new Point(r.get(1).x()+diffX, r.get(1).y()+diffY );
            Deplacement res = new Deplacement(d.joueur(), newSrc, newSuiv );
            for(int i=2; i<r.size(); i++){
                res.add(new Point(r.get(i).x()+diffX, r.get(i).y()+diffY) );
            }
            return res;
        }else if(c instanceof Depot){
            Depot d = (Depot) c;
            Point reference = p.reine(j);
            int diffX = (int)-reference.x();
            int diffY = (int)-reference.y();
            Point newDest = new Point(d.destination().x()+diffX, d.destination().y()+diffY );
            return new Depot(d.joueur(),d.type(), newDest );
        }
        return null;
    }
}
