/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Entities;

import LB.Communication.ClientSocket;
import LB.Handlers.TClientHandler;
import java.util.HashMap;

/**
 *
 * @author guids
 */
public class LoadBalancer {
    
    private boolean isPrimary = false;
    
    private HashMap<Integer,Server> servers;
    
    
    private int lbId;
    
    public  LoadBalancer(int lbId){
        this.lbId = lbId;
    }
    
    public void setPrimary(){
        this.isPrimary = true;    
    }
    
    public boolean getPrimary(){
        return this.isPrimary;  
    }
    
    public int getLoadBalencerID(){
        return lbId;
    }
    
    public void setLoadBalencerID(int lbId){
        this.lbId = lbId;
    }   

    public boolean hasServer(int serverId){
        return servers.containsKey(serverId);
    }
    
    
    public void addServer(int serverId){
        
        // new server, needs to create a new connection
        // Fist loadbalancer should connect to the monitor at the respective port
        ClientSocket serverSocket = new ClientSocket(serverId, "127.0.0.1");
        serverSocket.creatSocket();
        
        Server s = new Server(serverId, serverSocket);
        
        servers.put(serverId, s);
    }
    
    public void deleteServer(int serverId){
        servers.remove(serverId);
    }
    
    

}
