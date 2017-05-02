/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ruche;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author grandmax
 */
public class Configuration {
   
    static void erreur(Exception e, String nom) {
        System.err.println("Impossible de charger " + nom);
        System.err.println(e);
        System.exit(1);
    }

    static void chargerProprietes(Properties p, InputStream in) {
        try {
            p.load(in);
        } catch (IOException e) {
            erreur(e, "Ressources/Reglages/defaut.cfg");
        }
    }

    public static Properties proprietes() {
        try{
            Properties p;
            InputStream in = new FileInputStream("Ressources/Reglages/defaut.cfg");
            Properties defaut = new Properties();
            chargerProprietes(defaut, in);
            return defaut;
        }catch(FileNotFoundException e){
            System.out.println("coucou");
            return null;
        }
}
}
