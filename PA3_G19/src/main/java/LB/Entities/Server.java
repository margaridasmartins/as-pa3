/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Entities;

import Communication.ClientSocket;

/**
 *
 * @author guids
 */
public class Server {
    
    private final int serverID;
    private int njobs = 0;
    private int ni = 0;
    private ClientSocket socket;
    
    public Server(int serverID, ClientSocket socket){
        this.serverID = serverID; 
        this.socket = socket;
    }
    
    public void setNJobs(int njobs){
        this.njobs = njobs;
    }
    
    public ClientSocket getSocket(){
        return socket;
    }
    
    public int getServerId(){
        return serverID;
    }
    
    public int getNJobs(){
        return njobs;
    }
    
    public void setNI(int ni){
        this.ni = ni;
    }
    
    public int getNI(){
        return ni;
    }
}
