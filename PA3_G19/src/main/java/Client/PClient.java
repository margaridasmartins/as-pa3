/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import Client.GUI.ConfigurationGUI;
import Client.GUI.GUI;
import Client.Communication.ClientSocket;


/**
 *
 * Client process class
 */
public class PClient {
    
    private static int portNumber;
    private static int loadBalancerPortNumber;
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
                System.err.println(ex);
            }
       }
       
       // Connect to Load Balancer
       ClientSocket lbSocket = new ClientSocket(loadBalancerPortNumber, "127.0.0.1", portNumber);
       lbSocket.creatSocket();       
       
       // Start main GUI
       GUI gui = new GUI();
       gui.setClientInformation(portNumber, loadBalancerPortNumber);
       gui.setLoadBalancerSocket(lbSocket);
       gui.setVisible(true);
       
       
    }
    
    public static void startClient(int portNumber, int loadBalancerPortNumber){
        PClient.portNumber = portNumber;
        PClient.loadBalancerPortNumber = loadBalancerPortNumber;
        PClient.started = true;
    }
    
    public static void terminate(){
        System.exit(0);
    }
}
