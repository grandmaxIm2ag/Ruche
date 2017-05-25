/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ruche;

//http://www.redblobgames.com/grids/hexagons/

import Joueurs.Ordinateur;
import Modele.Arbitres.*;
import Modele.Coup;
import Vue.Interface;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grandmax
 */
public class Ruche {

    /**
     * 
     * @param args the command line arguments
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        
        Properties p = Configuration.proprietes();
        
        if(args.length==0){
            File rep = new File("Sauvegardes");
            if(!(rep.exists() && rep.isDirectory())){
                rep.mkdir();
            }
            File sauv = new File("Sauvegardes/Sauvegarde");
            if (!sauv.exists()) {
                new FileOutputStream(sauv).close();
            }

            Interface it = new Interface();
            FabriqueArbitre.init(p);
            Interface.creer(args);
        }else{
            if(args[0].equals("-s")){
                
            }else if((args[0].equals("-a")) && !(args[1].equals("-e"))){
                Apprentissage t=new Apprentissage(p, Ordinateur.MOYEN);
                t.apprentissageHeuristique();
                t.apprentissageCoup();
            }else if((args[0].equals("-a")) && (args[1].equals("-e"))){
                ConcurrentHashMap<Integer, Coup> app = new ConcurrentHashMap();
                Thread[] threads = new Thread[50];
                
                for(int i=0; i<50; i++){
                    threads[i] = new Thread(new ApprentissageEndGame(p,"","", app));
                    threads[i].start();
                }
                for(int i=0; i<50; i++){
                    try {
                        threads[i].join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Ruche.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                FileWriter f;
                if(args[2].equals("-jar"))
                    f = new FileWriter("endGame", true);
                else
                    f = new FileWriter("Ressources/Simulations/Apprentissage/endGame", true);
                try (PrintWriter bdd = new PrintWriter(f)) {
                    app.entrySet().forEach((entry) -> {
                        bdd.println(entry.getKey()+"::"+entry.getValue());
                    });
                }
            }
        }
        /*Interface it = new Interface();
        FabriqueArbitre.init(p);
        Interface.creer(args);*/
        //Interface.goFin("Coucou", Arbitre.GAGNE);
    }
    
}
