/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres.producteurConsommateur;

/**
 *
 * @author grandmax
 */
public class FileConso extends File{
    
    public synchronized void inserer(String o) {
        Maillon nouveau = new Maillon();

        nouveau.element = o;
        nouveau.suivant = null;
        if (tete == null) {
            tete = nouveau;
            queue = nouveau;
            notify();
        } else {
            queue.suivant = nouveau;
            queue = nouveau;
        }
    }

    public synchronized String extraire() {
        String resultat;
        while (tete == null)
            try {
                wait();
            } catch (InterruptedException e) {
            }
        resultat = tete.element;
        tete = tete.suivant;
        if (tete == null)
            queue = null;
        return resultat;
    }
}
