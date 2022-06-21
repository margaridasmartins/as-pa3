/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Handlers;
import LB.Communication.ClientSocket;
import Utils.CodeMessages;
import LB.Entities.LoadBalancer;
import LB.Entities.Server;
import LB.GUI.GUI;
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
    
        String inputLine;
        
        // Hello to Monitor
        socket.sendMessage(CodeMessages.HELLO.name() + "|" + "LB" + lb.getLoadBalencerID());
        
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
                            // Update serverStatus
                            int nservers = Integer.getInteger(clientMessage[1]);
                            
                            ArrayList<Integer> existingServers = new ArrayList<>();
                            
                            int j = 2;
                            for(int i =0; i<nservers; i++){
                                existingServers.add(Integer.getInteger(clientMessage[j]));
                                lb.updateServer(Integer.getInteger(clientMessage[j]),
                                        Integer.getInteger(clientMessage[++j]),
                                        Integer.getInteger(clientMessage[++j]));
                                j++;
                            }
                            
                            lb.deleteNonExistingServers(existingServers);
                            
                            // Choose server and  forward request
                            String r = lb.getFirstRequest();
                            Server s = lb.getBestServer();
                            
                            s.getSocket().sendMessage(r);
                            
                            String[] request = r.split("|");
                            
                            // Update GUI
                            gui.addRequest(Integer.getInteger(request[1]), 
                                    Integer.getInteger(request[2]), s.getServerId(),
                                    Integer.getInteger(request[5]) , Integer.getInteger(request[7]));
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
