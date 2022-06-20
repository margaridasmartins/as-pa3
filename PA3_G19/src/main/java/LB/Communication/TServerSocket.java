/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Communication;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import LB.Handlers.TServerHandler;
/**
 *
 * @author guids
 */

public class TServerSocket extends Thread{
    
    private final int portNumber;
    private final ServerSocket serverSocket;
    
    public TServerSocket(int portNumber) throws IOException{
        this.portNumber  = portNumber;       
        this.serverSocket = new ServerSocket(portNumber);
    }
    
    @Override
    public void run(){
    
        while(true){
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            
                TServerHandler socket = new TServerHandler(clientSocket);
                socket.start();
            
            } catch (IOException ex) {
                System.err.println("Socket error");
            }
        }
    }
    
    
}
