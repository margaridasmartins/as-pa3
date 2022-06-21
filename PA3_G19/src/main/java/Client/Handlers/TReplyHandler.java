/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.Handlers;

import Client.GUI.GUI;
import Utils.CodeMessages;
import Utils.Message;
import Utils.RequestMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    
        ObjectOutputStream out;
        ObjectInputStream in;
        
        Message reply = null;
        
        while (true) {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                
                // keep listening to incoming messages
                if ((reply =(Message)in.readObject()) != null) {
                                        
                    switch(reply.code()){
                        
                        // Reply Message
                        case REPLY:
                        {
                           RequestMessage rm = (RequestMessage) reply;
                           gui.addReply(rm.requestID(), rm.serverID(), rm.nIterations(), 
                                   rm.pi(), rm.deadline());
                            
                        }
                        break;  
                    }
                }
            } catch (IOException ex) {
                System.err.println("Socket error");
            } catch (ClassNotFoundException ex) {
                System.err.println(ex);
            }
    
        }
        
    }
    
}
