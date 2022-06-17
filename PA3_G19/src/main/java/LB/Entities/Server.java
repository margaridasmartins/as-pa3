/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Entities;

/**
 *
 * @author guids
 */
class Server {
    
    private int serverID;
    private int njobs = 0;
    private int lastJobIdx = 0;
    
    public Server(int serverID){
        this.serverID = serverID; 
    }
    
    public void addJob(int jobIdx){
        if(njobs<3)
            njobs++;
        lastJobIdx = jobIdx;
    }
    
    public int getNJobs(){
        return njobs;
    }
    
    public int getLastJob(){
        return lastJobIdx;
    }
}
