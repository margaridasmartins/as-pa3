/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Communication;
import LB.Entities.LoadBalancer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import LB.Handlers.TServerHandler;
/**
 *
 * @author guids
 */

public class TServerSocket extends Thread{
    
    private final ServerSocket serverSocket;
    private final ClientSocket monitorSocket;
    private final LoadBalancer lb;
    
    
    public TServerSocket(int portNumber, ClientSocket monitorSocket, LoadBalancer lb) throws IOException{  
        this.serverSocket = new ServerSocket(portNumber);
        this.monitorSocket = monitorSocket;
        this.lb = lb;
    }
    
    @Override
    public void run(){
    
        while(true){
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            
                TServerHandler socket = new TServerHandler(clientSocket, monitorSocket, lb);
                socket.start();
            
            } catch (IOException ex) {
                System.err.println("Socket error");
            }
        }
    }
    
    
}
