/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ruche;

//http://www.redblobgames.com/grids/hexagons/

import Joueurs.Ordinateur;
import Modele.Arbitres.*;
import Vue.Interface;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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
        
        File rep = new File("Sauvegardes");
        if(!(rep.exists() && rep.isDirectory())){
            rep.mkdir();
        }
        File sauv = new File("Sauvegardes/Sauvegarde");
        if (!sauv.exists()) {
            new FileOutputStream(sauv).close();
        }
        Properties p = Configuration.proprietes();
        
        TestIA t=new TestIA(p,Ordinateur.FACILE_ALEATOIRE,Ordinateur.FACILE_ALEATOIRE,100);
        t.simulation();
        //Interface it = new Interface();
        //FabriqueArbitre.init(p);
        //Interface.creer(args);
        //Interface.goFin("Coucou", Arbitre.GAGNE);
    }
    
}
