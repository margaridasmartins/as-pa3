/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Handlers;
import LB.Communication.ClientSocket;
import Utils.CodeMessages;
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
                    case HEARTBEAT:
                        {
                            String hbMessage = CodeMessages.HEARTBEAT.name() + "|" + lb.getLoadBalencerID();
                            socket.sendMessage(hbMessage);
                        }
                        break;
                    // LB HELLO message from monitor -> HELLO|[P or S]
                    case HELLO:
                        {
                            if(clientMessage[1].equals("P")){
                                
                                // setPrimary
                                lb.setPrimary();
                                
                            }
                        }
                        break;
                    // SERVERs DETAILS
                    case STATUS:
                        {
                            
                        }
                        break;
                    // Primary LoadBalancer is down need to start as primary
                    case PRIMARY:  
                        {
                            // update Port
                            lb.setLoadBalencerID(Integer.getInteger(clientMessage[1]));
                            
                            // setPrimary
                            lb.setPrimary();
                        }
                }
            }
    
        }
        
    }
    
}
