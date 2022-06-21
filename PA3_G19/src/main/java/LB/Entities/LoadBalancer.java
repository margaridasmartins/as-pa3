/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Entities;

import Entities.Request;
import java.util.concurrent.locks.ReentrantLock;
import Communication.ClientSocket;
import Utils.ServerStatusMessage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author guids
 */
public class LoadBalancer {
    
    private boolean isPrimary = false;
    
    private HashMap<Integer,Server> servers;
    private Server bestServer; 
    
    private final LinkedList<Request> requests;
    
    private int lbId;
    
    private final ReentrantLock rl;
    
    public  LoadBalancer(int lbId){
        this.lbId = lbId;
        this.rl = new ReentrantLock();
        this.requests = new LinkedList<>();
        this.servers = new HashMap<>();
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
    
    public Server getServer(int serverId){
        
        return servers.get(serverId);
    }
    
    public Server addServer(int serverId){
        
        // new server, needs to create a new connection
        // Fist loadbalancer should connect to the monitor at the respective port
        
        ClientSocket serverSocket = new ClientSocket(serverId, "127.0.0.1");
        
        Server s = new Server(serverId, serverSocket);
        
        servers.put(serverId, s);
        
        return s;
    }
    
    public void updateServers(List<ServerStatusMessage> status){
        
        int bNI = 100; // max ni slhoud not be more than 20
        int bNJ = 10; // servers cannot have more that 5 requests
        
        // delete and update unexisting servers
        for(int serverID : servers.keySet()){
            boolean exists = false;
            for(ServerStatusMessage s : status){
                if(s.serverID() == serverID){
                    Server si = getServer(serverID);
                    si.setNI(s.totalNIterations());
                    si.setNJobs(s.nRequests());
                    exists = true;
                    if(bNJ>s.nRequests() || (bNJ==s.nRequests() && bNI>s.totalNIterations())){
                    
                        bestServer = si;
                        bNJ = s.nRequests();
                        bNI = s.totalNIterations();
                    }
                    break;
                }
            }
            
            if(!exists){
                deleteServer(serverID);
            }
            
        }
        
        // add new ones
        for(ServerStatusMessage s : status){
            if(!servers.containsKey(s.serverID())){
                System.out.println(String.valueOf(s.serverID()));
                Server si = addServer(s.serverID());
                si.setNI(s.totalNIterations());
                si.setNJobs(s.nRequests());
                if(bNJ>s.nRequests() || (bNJ==s.nRequests() && bNI>s.totalNIterations())){
                    
                    bestServer = si;
                    bNJ = s.nRequests();
                    bNI = s.totalNIterations();
                }
            }
        }
    
       
    }
    
    public Server getBestServer(){
        return bestServer;
    }
    
    public void addRequest(Request request){
        rl.lock();
        try {
            requests.add(request);
        } finally {
            rl.unlock();
        }
        
    }
    
    public Request getFirstRequest(){
        Request request;
        
        rl.lock();
        try {
            request = requests.pollFirst();
        } finally {
            rl.unlock();
        }
        return request;
    }
    
    public void deleteServer(int serverId){
        servers.remove(serverId);
    }  
    

}
