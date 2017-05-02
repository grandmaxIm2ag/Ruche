/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Properties;
import java.util.Scanner;

/**
 *
 * @author grandmax
 */
public class Chargeur {
    Scanner input;
    Properties prop;
   
    public void init(Properties p){
        prop = p;
    }
    public void init(Properties p, String plateau){
        prop = p;
    }
    
    public Plateau charger(){
        
        return null;
    }
}
