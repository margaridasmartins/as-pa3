/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Handlers;
import Communication.ClientSocket;
import Utils.CodeMessages;
import Utils.PrimaryMessage;
import Utils.HeartBeatMessage;
import Utils.HelloMessage;
import LB.Entities.LoadBalancer;
import LB.Entities.Request;
import LB.Entities.Server;
import LB.GUI.GUI;
import Utils.Message;
import Utils.RequestMessage;
import java.util.ArrayList;

/**
 *
 * @author guids
 */
public class TClientHandler extends Thread{
    
    private final ClientSocket socket;
    private LoadBalancer lb;
    private GUI gui;
    
    public TClientHandler(ClientSocket socket, LoadBalancer lb, GUI gui){
        this.socket = socket; 
        this.lb = lb;
        this.gui = gui;
    }
    
    @Override
    public void run(){
    
        Message message;
        
        // Hello to Monitor
        socket.sendMessage(new HelloMessage(lb.getLoadBalencerID(),"LB", null));
        
        while (true) {
            // keep listening to incoming messages
            if ((message = socket.getMessage()) != null) {
                
                
                switch(message.code()){
                    
                    // HB message -> HB|LBID
                    case HEARTBEAT:
                        {
                            socket.sendMessage(new HeartBeatMessage(lb.getLoadBalencerID()));
                        }
                        break;
                    // LB HELLO message from monitor -> HELLO|[P or S]
                    case HELLO:
                        {
                            HelloMessage hello = (HelloMessage) message;
                            if(hello.function().equals("P")){                     
                                // setPrimary
                                lb.setPrimary();         
                            }
                        }
                        break;
                    // SERVERs DETAILS
                    case STATUS:
                        {
                            // Update serverStatus                  
                            
                            lb.updateServers(message.serversStatus());
                            
                            // Choose server and  forward request
                            Request r = lb.getFirstRequest();
                            Server s = lb.getBestServer();
                            
                            RequestMessage rm = new RequestMessage(CodeMessages.REQUEST, r.clientID(),
                            r.requestID(), s.getServerId(), 0, r.nIterations(), 0, r.deadline());
                            
                            s.getSocket().sendMessage(rm);
                            
                            //forward also to monitor
                            socket.sendMessage(rm);
                            
                            // Update GUI
                            gui.addRequest(r.clientID(), r.requestID(), s.getServerId(),
                                    r.nIterations(), r.deadline());
                        }
                        break;
                    // Primary LoadBalancer is down need to start as primary
                    case PRIMARY:  
                        {
                            PrimaryMessage pm = (PrimaryMessage) message;
                            // update Port
                            lb.setLoadBalencerID(pm.ID());
                            
                            // setPrimary
                            lb.setPrimary();
                        }
                }
            }
    
        }
        
    }
    
    
}
