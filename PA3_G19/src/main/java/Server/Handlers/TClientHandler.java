/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Handlers;
import Communication.ClientSocket;
import Utils.HelloMessage;
import Utils.HeartBeatMessage;
import Utils.Message;
/**
 *
 * @author guids
 */
public class TClientHandler extends Thread{
    
    private final ClientSocket socket;
    private final int serverID;
    
    public TClientHandler(ClientSocket socket, int serverID){
        this.socket = socket; 
        this.serverID = serverID;
    }
    
    @Override
    public void run(){
    
        Message inputMessage;
        
        // Hello to Monitor
        socket.sendMessage(new HelloMessage(serverID, "S", null));
        
        while (true) {
            // keep listening to incoming messages
            if ((inputMessage = socket.getMessage()) != null) {
                
               
                switch(inputMessage.code()){
                    
                    // HB message -> HB|ServerID
                    case HEARTBEAT:
                        {
                            socket.sendMessage(new HeartBeatMessage(serverID));
                        }
                        break;
                    // LB HELLO message from monitor -> HELLO|[P or S]
                    case REQUEST:
                        {
                            
                        }
                        break;
                }
            }
    
        }
    }
    
}
