package Utils;


import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author leand
 */
public class ServerInfoMessage implements Serializable {
    /* Server ID. */
    int serverID;
    /* Number of Requests. */
    int nRequests;
    /* Total number of iterations. */
    int totalNIterations;
    
    public ServerInfoMessage(int serverID, int nRequests, int totalNIterations) {
        this.serverID = serverID;
        this.nRequests = nRequests;
        this.totalNIterations = totalNIterations;
    }
    
    public int serverID() {
        return serverID;
    }
    
    public int nRequests() {
        return nRequests;
    }
    
    public int totalNIterations() {
        return totalNIterations;
    }
}
