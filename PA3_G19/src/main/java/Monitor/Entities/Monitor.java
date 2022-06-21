/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Monitor.Entities;

import Monitor.GUI.GUI;
import Utils.Message;
import Utils.RequestMessage;
import Utils.ServerStatusMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author leand
 */
public class Monitor {
    
    /* Status of every server by ID */
    private final HashMap<Integer, ServerRequestsInfo> serversInfo;
    
    /* List of requests of every server by ID */
    private final HashMap<Integer, HashMap<Integer, RequestMessage>> serversRequests;
    
    /* Requests information of every client by ID */
    private final HashMap<Integer, ClientRequestsInfo> clientsInfo;
    
    /* List of all requests handled by the Load Balancer */
    private final List<RequestMessage> loadBalancerRequests;
    
    /* Whether or not the primary Load Balancer is working */
    private boolean hasPrimaryLB;
    
    private final ReentrantLock rl;
    
    public Monitor(GUI gui) {
        serversInfo = new HashMap<>();
        serversRequests = new HashMap<>();
        clientsInfo = new HashMap<>();
        loadBalancerRequests = new ArrayList<>();
        rl = new ReentrantLock();
    }
    
    public boolean hasPrimaryLB() {
        return hasPrimaryLB;
    }
    
    public void forwardingRequest(RequestMessage request) {
        int clientID = request.clientID();
        int serverID = request.serverID();
        int requestID = request.requestID();
        
        serversInfo.getOrDefault(serverID, new ServerRequestsInfo())
                .receiveRequest(request.nIterations());
        serversRequests.getOrDefault(serverID, new HashMap<>())
                .put(requestID, request);
        clientsInfo.getOrDefault(clientID, new ClientRequestsInfo())
                .forwardingRequest();
        loadBalancerRequests.add(request);
    }
    
    public void processingRequest(Message msg) {
        int cliendID = msg.port();
        
        clientsInfo.getOrDefault(cliendID, new ClientRequestsInfo())
                .forwardingRequest();
    }
    
    public void replyingRequest(RequestMessage request) {
        int cliendID = request.port();
        int serverID = request.serverID();
        int requestID = request.requestID();
        
        serversInfo.getOrDefault(serverID, new ServerRequestsInfo())
                .completeRequest(request.nIterations());
        clientsInfo.getOrDefault(cliendID, new ClientRequestsInfo())
                .replyingRequest();
        serversRequests.getOrDefault(serverID, new HashMap<>())
                .remove(requestID);
    }
    
    class ClientRequestsInfo {
        private int pending = 0;
        private int beingProcessed = 0;
        private int rejected = 0;
        private int processed = 0;
        
        void forwardingRequest() {
            pending++;
        }
        
        void processingRequest() {
            pending--;
            beingProcessed++;
        }
        
        void rejectingRequest() {
            beingProcessed--;
            rejected++;
        }
        
        void replyingRequest() {
            beingProcessed--;
            processed++;
        }
    } 
    
    class ServerRequestsInfo {
        int nRequests = 0;
        int totalIterations = 0;
        
        void receiveRequest(int iterations) {
            nRequests++;
            totalIterations += iterations;
        }
        
        void completeRequest(int iterations) {
            nRequests--;
            totalIterations -= iterations;
        }
    }

}
