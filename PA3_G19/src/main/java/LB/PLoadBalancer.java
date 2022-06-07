/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB;
import LB.GUI.ConfigurationGUI;
import LB.GUI.GUI;
import LB.Communication.MonitorSocket;

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
    public static void main(String[] args) {
        
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
       gui.setVisible(true);
    
       // Fist loadbalancer should connect to the monitor at the respective port
       MonitorSocket monitorSocket = new MonitorSocket(monitorPortNumber, "127.0.0.1");
       monitorSocket.creatSocket();
       
        
        
        
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
