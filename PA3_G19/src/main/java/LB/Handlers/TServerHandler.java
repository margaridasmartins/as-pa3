/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Handlers;

import LB.Communication.TServerSocket;
import Utils.CodeMessages;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author guids
 */
public class TServerHandler extends Thread{
    
     private final Socket socket;
    
    public TServerHandler(Socket socket){
        this.socket = socket; 
    }
    
    @Override
    public void run(){
    
        BufferedReader in = null;
         try {
             in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             String inputLine;
             while (true) {
                 // keep listening to incoming messages
                 if ((inputLine = in.readLine()) != null) {
                     
                     String[] clientMessage = inputLine.split("|");
                     
                     switch(CodeMessages.valueOf(clientMessage[0])){
                         
                         // Request message -> REQUEST | client id | request id | 00 | 01 | number of iterations | 00 | deadline |
                         case REQUEST:
                         {
                            // TODO Handle Request
                         }
                         break;
                     }
                 }
                 
             }
         } catch (IOException ex) {
             System.err.println("Socket error");
         } finally {
             try {
                 in.close();
             } catch (IOException ex) {
                 System.err.println("Socket error");
             }
         }
        
    }
    
}
