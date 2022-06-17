/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Entities;

import java.util.ArrayList;

/**
 *
 * @author guids
 */
public class LoadBalancer {
    
    private boolean isPrimary = false;
    
    private ArrayList<Server> servers;
    
    private int jobIdx = 0;
    
    public  LoadBalancer(){
    
    }
    
    public void setPrimary(){
        this.isPrimary = true;    
    }
    
    public void addServer(int serverId){
        Server s = new Server(serverId);
        servers.add(0,s);
    }
    
    public Server getServerforRequest(){
        Server s = servers.get(0);
        s.addJob(jobIdx);
        servers.remove(s);
        
        int i = 0;
        for(Server si : servers){
            if(si.getNJobs()> s.getNJobs() ){
                servers.add(i, s);
            }
            i++;
        }
        
        jobIdx++;
        return s;
    }
}
