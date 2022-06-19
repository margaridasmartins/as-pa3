/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Handlers;
import LB.Communication.ClientSocket;
import Utils.CodeMessages;
import LB.PLoadBalancer;
import LB.Entities.LoadBalancer;

/**
 *
 * @author guids
 */
public class TClientHandler extends Thread{
    
    private final ClientSocket socket;
    private LoadBalancer lb;
    
    public TClientHandler(ClientSocket socket, LoadBalancer lb){
        this.socket = socket; 
        this.lb = lb;
    }
    
    @Override
    public void run(){
    
        String inputLine;
        
        while (true) {
            // keep listening to incoming messages
            if ((inputLine = socket.getMessage()) != null) {
                
                String[] clientMessage = inputLine.split("|");
                
                switch(CodeMessages.valueOf(clientMessage[0])){
                    
                    // HB message -> HB|LBID
                    case HB:
                        {
                            String hbMessage = CodeMessages.HB.name() + "|" + clientMessage[1];
                            socket.sendMessage(hbMessage);
                        }
                        break;
                    // LB HELLO message from monitor -> HELLO|[P or S]
                    case HELLO:
                        {
                            if(clientMessage[1].equals("P")){
                                
                                // setPrimary
                                lb.setPrimary();
                                
                                // get SERVERS
                                socket.sendMessage(CodeMessages.SERVER.name());
                            }
                        }
                        break;
                    // SERVERs DETAILS
                    case SERVER:
                        {
                            
                        }
                }
            }
    
        }
        
    }
    
}
