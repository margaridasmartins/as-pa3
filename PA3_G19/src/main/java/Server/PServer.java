/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import Server.GUI.ConfigurationGUI;
import Server.GUI.GUI;

/**
 *
 * Server process class
 */
public class PServer {
    
    private static int portNumber;
    private static int monitorPortNumber;
    private static boolean started = false;
    
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
       
       // Start main GUI
       GUI gui = new GUI();
       gui.setServerInformation(portNumber, monitorPortNumber);
       gui.setVisible(true);
        
    }
    
    public static void startServer(int portNumber, int monitorPortNumber){
        PServer.portNumber = portNumber;
        PServer.monitorPortNumber = monitorPortNumber;
        PServer.started = true;
    }
    
    public static void terminate(){
        System.exit(0);
    }
}
