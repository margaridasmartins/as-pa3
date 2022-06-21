/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Entities;

/**
 *
 * @author guids
 */
public class Request {
    
    private final int clientID;
    private final int requestID;
    private  int serverID;
    private final int nIterations;
    private final int deadline;
    
    
    
    public Request(int clientID, int requestID, int serverID, int nIterations, int deadline){
    
        this.clientID = clientID;
        this.requestID = requestID;
        this.serverID = serverID;
        this.nIterations = nIterations;
        this.deadline = deadline;
        
    }
    
     public int clientID() {
        return clientID;
    }
    
    public int requestID() {
        return requestID;
    }
    
    public void serverID(int serverID) {
        this.serverID = serverID;

    }

    public int serverID() {
        return serverID;
    }
    

    
    public int nIterations() {
        return nIterations;
    }


    public int deadline() {
        return deadline;
    }
}
