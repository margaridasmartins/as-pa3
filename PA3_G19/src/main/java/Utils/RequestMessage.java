package Utils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author leand
 */
public class RequestMessage extends Message {
    
    /* Client ID. */
    int clientID;
    /* Request ID. */
    int requestID;
    /* Server ID. */
    int serverID;
    /* Number of requests. */
    int nRequests;
    /* Number of iterations. */
    int nIterations;
    /* Priority in number of seconds to handle the request. */
    int deadline;
    /* Value of pi calculated. */
    double pi;
    
    public RequestMessage(CodeMessages code, int clientID, int requestID, int serverID, int nRequests, int nIterations, double pi, int deadline) {
        super(code);
        this.clientID = clientID;
        this.requestID = requestID;
        this.serverID = serverID;
        this.nRequests = nRequests;
        this.nIterations = nIterations;
        this.pi = pi;
        this.deadline = deadline;
    }
    
     public Message clientID(int clientID) {
        this.clientID = clientID;
        return this;
    }

    public int clientID() {
        return clientID;
    }
    
    public Message requestID(int requestID) {
        this.requestID = requestID;
        return this;
    }

    public int requestID() {
        return requestID;
    }
    
    public Message serverID(int serverID) {
        this.serverID = serverID;
        return this;
    }

    public int serverID() {
        return serverID;
    }
    
    public Message nRequests(int nRequests) {
        this.nRequests = nRequests;
        return this;
    }

    public int nRequests() {
        return nRequests;
    }
    
    public Message nIterations(int nIterations) {
        this.nIterations = nIterations;
        return this;
    }
    
    public int nIterations() {
        return nIterations;
    }
    
    public Message pi(double pi) {
        this.pi = pi;
        return this;
    }
    
    public double pi() {
        return pi;
    }
    
    public Message deadline(int deadline) {
        this.deadline = deadline;
        return this;
    }
    
    public int deadline() {
        return deadline;
    }
}
