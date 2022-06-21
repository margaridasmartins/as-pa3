/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Monitor;

import Monitor.Communication.TServerSocket;
import Monitor.Entities.Monitor;
import Monitor.GUI.ConfigurationGUI;
import Monitor.GUI.GUI;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Monitor process class
 */
public class PMonitor {

    private static int portNumber;
    private static int heartbeatThreshold;
    private static boolean started = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // Configuration GUI
        ConfigurationGUI configurationGUI = new ConfigurationGUI();
        configurationGUI.setVisible(true);

        // Wait for configuration GUI
        while (!started) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(PMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Start main GUI
        GUI gui = new GUI();
        gui.setMonitorInformation(portNumber, heartbeatThreshold);
        gui.setVisible(true);
        
        Monitor monitor = new Monitor(gui, heartbeatThreshold);
        gui.setMonitor(monitor);

        // Create Client Server to receive requests
        TServerSocket serverSocket = new TServerSocket(portNumber, monitor);
        serverSocket.start();
    }
    
    public static void startMonitor(int portNumber, int heartBeatThreshold){
        PMonitor.heartbeatThreshold = heartBeatThreshold;
        PMonitor.portNumber = portNumber;
        PMonitor.started = true;
    }
    
    public static void terminate(){
        System.exit(0);
    }
}
