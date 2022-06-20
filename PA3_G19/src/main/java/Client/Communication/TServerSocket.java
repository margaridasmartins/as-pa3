/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.Communication;
import Client.GUI.GUI;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import Client.Handlers.TReplyHandler;
/**
 *
 * @author guids
 */

public class TServerSocket extends Thread{
    
    private final int portNumber;
    private final ServerSocket serverSocket;
    private final GUI gui;
    
    public TServerSocket(int portNumber, GUI gui) throws IOException{
        this.portNumber  = portNumber;       
        this.serverSocket = new ServerSocket(portNumber);
        this.gui = gui;
    }
    
    @Override
    public void run(){
    
        while(true){
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            
                TReplyHandler socket = new TReplyHandler(clientSocket, gui);
                socket.start();
            
            } catch (IOException ex) {
                System.err.println("Socket error");
            }
        }
    }
    
    
}
