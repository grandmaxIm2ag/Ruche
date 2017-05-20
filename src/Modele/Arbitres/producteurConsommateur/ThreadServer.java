/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres.producteurConsommateur;

import Modele.Arbitres.Arbitre;
import static Modele.Arbitres.Arbitre.J1;
import static Modele.Arbitres.Arbitre.J2;
import Modele.Arbitres.ReseauServer;
import Vue.Interface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author maxence
 */
public class ThreadServer implements Runnable{
    private int port;
    private ServerSocket serverSocket;
    Socket client;
    private PrintWriter out;
    private BufferedReader in;
    ReseauServer arbitre;
    public ThreadServer(ServerSocket s,Socket c, ReseauServer r){
        serverSocket = s;
        client = c;
        arbitre = r;
    }
    
    @Override
    public void run() {
        try{
            Socket c = serverSocket.accept();
            System.out.println("ThreadServer Accepté");
            arbitre.accept(c);
            arbitre.launch();
        }catch(IOException e){
            System.err.println(e);
        }
        System.out.println("Fin du thread");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
