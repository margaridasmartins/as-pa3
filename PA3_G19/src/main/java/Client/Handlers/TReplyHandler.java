/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.Handlers;

import Client.GUI.GUI;
import Utils.CodeMessages;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


/**
 *
 * @author guids
 */
public class TReplyHandler extends Thread{
    
    private final Socket socket;
    private final GUI gui;
    
    public TReplyHandler(Socket socket, GUI gui){
        this.socket = socket; 
        this.gui = gui;
    }
    
    @Override
    public void run(){
    
        BufferedReader in;
        String inputLine;
        
        while (true) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // keep listening to incoming messages
                if ((inputLine = in.readLine()) != null) {
                    
                    String[] serverMessage = inputLine.split("|");
                    
                    switch(CodeMessages.valueOf(serverMessage[0])){
                        
                        // HB message -> HB|LBID
                        case REPLY:
                        {
                            String requestId = serverMessage[2];
                            String serverId = serverMessage[3];
                            String ni = serverMessage[5];
                            String pi =  serverMessage[6];
                            String deadline =  serverMessage[7];
                            
                            gui.addReply(requestId, serverId, ni, pi, deadline);
                            
                        }
                        break;  
                    }
                }
            } catch (IOException ex) {
                System.err.println("Socket error");
            }
    
        }
        
    }
    
}
