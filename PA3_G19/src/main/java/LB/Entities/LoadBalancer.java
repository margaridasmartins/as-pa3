/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Entities;

import java.util.concurrent.locks.ReentrantLock;
import Communication.ClientSocket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;

/**
 *
 * @author guids
 */
public class LoadBalancer {
    
    private boolean isPrimary = false;
    
    private HashMap<Integer,Server> servers;
    private Server bestServer; 
    
    private LinkedList<Request> requests;
    
    private int lbId;
    
    private final ReentrantLock rl;
    
    public  LoadBalancer(int lbId){
        this.lbId = lbId;
        this.rl = new ReentrantLock();
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
    
    public void updateServer(int serverId, int njobs, int ni){
    
        Server s;
        if(!hasServer(serverId)){
            s = addServer(serverId);
        }
        else{
            s = getServer(serverId);
        }
        
        s.setNI(ni);
        s.setNJobs(njobs);
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

    public void deleteNonExistingServers(ArrayList<Integer> existingServers) {
        for(Integer serverId : servers.keySet()){
        
            if(!existingServers.contains(serverId)){
                servers.remove(serverId);
            }
        }
    }
    
    

}
