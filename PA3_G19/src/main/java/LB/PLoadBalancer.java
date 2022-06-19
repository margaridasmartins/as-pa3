/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB;
import LB.GUI.ConfigurationGUI;
import LB.GUI.GUI;
import LB.Communication.ClientSocket;
import LB.Communication.TServerSocket;
import LB.Handlers.TClientHandler;
import LB.Entities.LoadBalancer;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Load Balancer process class
 */
public class PLoadBalancer {
    
    private static int portNumber;
    private static int monitorPortNumber;
    private static boolean  started = false;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        // Configuration GUI
        ConfigurationGUI configurationGUI =  new ConfigurationGUI();
        configurationGUI.setVisible(true);
        
       // Wait for configuration GUI
       while(!started){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(PLoadBalancer.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
       
       // Start main GUI
       GUI gui = new GUI();
       gui.setLoadBalancerInformation(portNumber, monitorPortNumber);
       gui.setVisible(true);
    
       // Creat LoadBalancer Entity
       LoadBalancer lb = new LoadBalancer();
       
       // Fist loadbalancer should connect to the monitor at the respective port
       ClientSocket monitorSocket = new ClientSocket(monitorPortNumber, "127.0.0.1");
       monitorSocket.creatSocket();
       
       // Handle monitor messages
       TClientHandler monitorHandler = new TClientHandler(monitorSocket, lb);
       monitorHandler.start();
       
       
       // Wait for configuration GUI
       while(!lb.getPrimary()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(PLoadBalancer.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
       
       
       // LB is primary so it can start accepting Client messages
       TServerSocket  serverSocket = new TServerSocket(portNumber);
       serverSocket.start();
        
    }
    
    public static void startLoadBalancer(int portNumber, int monitorPortNumber){
        PLoadBalancer.portNumber = portNumber;
        PLoadBalancer.monitorPortNumber = monitorPortNumber;
        PLoadBalancer.started = true;
    }
    
    public static void terminate(){
        System.exit(0);
    }
    
}
