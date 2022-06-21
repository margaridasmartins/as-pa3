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
    private final HashMap<Integer, List<RequestMessage>> serversRequests;
    
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
        
        serversInfo.getOrDefault(serverID, new ServerRequestsInfo())
                .receiveRequest(request.nIterations());
        serversRequests.getOrDefault(serverID, new ArrayList<>())
                .add(request);
        clientsInfo.getOrDefault(clientID, new ClientRequestsInfo())
                .forwardingRequest();
        loadBalancerRequests.add(request);
    }
    
    public void processingRequest(Message msg) {
        int cliendID = msg.port();
        
        clientsInfo.getOrDefault(cliendID, new ClientRequestsInfo())
                .forwardingRequest();
    }
    
    
    class ClientRequestsInfo {
        private int pending = 0;
        private int beingProcessed = 0;
        private int rejected = 0;
        private int processed = 0;
        
        public void forwardingRequest() {
            pending++;
        }
        
        public void processingRequest() {
            pending--;
            beingProcessed++;
        }
        
        public void rejectingRequest() {
            beingProcessed--;
            rejected++;
        }
        
        public void replingRequest() {
            beingProcessed--;
            processed++;
        }
    } 
    
    class ServerRequestsInfo {
        private int nRequests = 0;
        private int totalIterations = 0;
        
        public void receiveRequest(int iterations) {
            nRequests++;
            totalIterations += iterations;
        }
        
        public void completeRequest(int iterations) {
            nRequests--;
            totalIterations -= iterations;
        }
    }

}
