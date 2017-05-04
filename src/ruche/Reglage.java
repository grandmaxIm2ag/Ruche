/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ruche;

import java.util.NoSuchElementException;
import java.util.Properties;

/**
 *
 * @author grandmax
 */
public class Reglage {
    static Properties prop;

    public static void init(Properties p) {
        prop = p;
    }
    
    public static double lis(String nom) {
        String value = prop.getProperty(nom);
        if (value != null)
            return Double.parseDouble(value);
        else
            throw new NoSuchElementException("Propriété "+nom+" manquante");
    }
}
