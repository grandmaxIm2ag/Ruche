/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ruche;

//http://www.redblobgames.com/grids/hexagons/

import Joueurs.Ordinateur;
import Modele.Arbitres.*;
import Modele.Arbitres.SimulationIA;
import Modele.Arbitres.TestArbitre;
import Modele.Arbitres.TestIA;
import Vue.Interface;
import java.util.Properties;

/**
 *
 * @author grandmax
 */
public class Ruche {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Properties p = Configuration.proprietes();
        //TestIA t=new TestIA(p,Ordinateur.FACILE_HEURISTIQUE,Ordinateur.FACILE_ALEATOIRE,100);
        //t.simulation();
        Interface it = new Interface();
        it.creer(args, new FabriqueArbitre(p));
    }
    
}
