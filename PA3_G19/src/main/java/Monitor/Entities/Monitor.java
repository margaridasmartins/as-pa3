/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Monitor.Entities;

import Communication.ClientSocket;
import Monitor.GUI.GUI;
import Utils.Message;
import Utils.RequestMessage;
import Utils.ServerStatusMessage;
import Utils.PrimaryMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author leand
 */
public class Monitor {

    private final GUI gui;

    /* Status of every server by ID */
    private final HashMap<Integer, ServerRequestsInfo> serversInfo;

    /* List of requests of every server by ID */
    private final HashMap<Integer, HashMap<Integer, RequestMessage>> serversRequests;

    /* Requests information of every client by ID */
    private final HashMap<Integer, ClientRequestsInfo> clientsInfo;

    /* List of all requests handled by the Load Balancer */
    private final List<RequestMessage> loadBalancerRequests;

    /* Whether or not the primary Load Balancer is working */
    private final int[] loadBalancers;
    
    private final List<Integer> heartBeatMessages;
    
    private final HashMap<Integer, ClientSocket> sockets;
    
    private final int heartbeatThreashold;

    private final ReentrantLock rl;

    public Monitor(GUI gui, int heartbeatThreashold) {
        this.gui = gui;
        serversInfo = new HashMap<>();
        serversRequests = new HashMap<>();
        clientsInfo = new HashMap<>();
        loadBalancerRequests = new ArrayList<>();
        rl = new ReentrantLock();
        this.heartbeatThreashold = heartbeatThreashold;
        heartBeatMessages = new  ArrayList<>();
        loadBalancers = new int[2]; 
        sockets = new HashMap<>();
    }
    
    public boolean hasPrimaryLB(){
        return loadBalancers[0]!=0;
    }

    public void setLBUp(int ID) {
        if(loadBalancers[0] == 0){
            loadBalancers[0] = ID;
        }
        else{
            loadBalancers[1] = ID;
        }
    }
    
    public void addSocket(int ID, ClientSocket cs){
        sockets.put(ID, cs);
    }
    
    public void setDown(int ID){
        
        // Server Down
        if(serversInfo.containsKey(ID)){
            serversInfo.remove(ID);
            for(RequestMessage rm : serversRequests.get(ID).values()){
                sockets.get(loadBalancers[0]).sendMessage(rm);
            }
            serversRequests.remove(ID);
        }
        else{
            if(loadBalancers[0] == ID){
                PrimaryMessage pm = new PrimaryMessage(ID);
                sockets.get(loadBalancers[1]).sendMessage(pm);
                loadBalancers[1] = 0;
            }
            else{
                loadBalancers[1] = 0;
                sockets.remove(ID);
            }
        }
    }

    /**
     * Add a new server available to receive requests from the LB.
     *
     * @param port the server ID/port
     */
    public void addServer(int port) {
        try {
            rl.lock();
            serversInfo.put(port, new ServerRequestsInfo());
            serversRequests.put(port, new HashMap<>());
            gui.addServerToTable(port);
        } finally {
            rl.unlock();
        }
    }

    public List<RequestMessage> getServerRequests(int serverID) {
        try {
            rl.lock();
            return new ArrayList<>(serversRequests.get(serverID).values());
        } finally {
            rl.unlock();
        }
    }

    /**
     * Lock the monitor status and update it according to the forwarded request.
     *
     * @param request the forwarded request
     */
    public void forwardingRequest(RequestMessage request) {
        int clientID = request.clientID();
        int serverID = request.serverID();
        int requestID = request.requestID();

        try {
            rl.lock();
            serversInfo.getOrDefault(serverID, new ServerRequestsInfo())
                    .receiveRequest(request.nIterations());
            serversRequests.getOrDefault(serverID, new HashMap<>())
                    .put(requestID, request);
            clientsInfo.getOrDefault(clientID, new ClientRequestsInfo(clientID))
                    .forwardingRequest();
            loadBalancerRequests.add(request);

            gui.addRequestToLBTable(request);
            gui.addRequestToTableRequest(request);
            gui.setNRequestsServer(serverID, serversRequests.get(serverID).size());
        } finally {
            rl.unlock();
        }
    }

    /**
     * Lock the monitor status and update it according to the message.
     *
     * @param msg the message containing the request's client ID
     */
    public void processingRequest(Message msg) {
        int clientID = msg.port();

        try {
            rl.lock();
            clientsInfo.getOrDefault(clientID, new ClientRequestsInfo(clientID))
                    .processingRequest();
        } finally {
            rl.unlock();
        }
    }

    /**
     * Lock the monitor status and update it according to the replied message.
     *
     * @param request the replied message
     */
    public void replyingRequest(RequestMessage request) {
        int clientID = request.clientID();
        int serverID = request.serverID();
        int requestID = request.requestID();

        try {
            rl.lock();
            serversInfo.getOrDefault(serverID, new ServerRequestsInfo())
                    .completeRequest(request.nIterations());
            ClientRequestsInfo requestInfo = clientsInfo.getOrDefault(clientID, new ClientRequestsInfo(clientID));
            if (request.requestCode() == 2) {
                requestInfo.replyingRequest();
            } else {
                requestInfo.rejectingRequest();
            }
            serversRequests.getOrDefault(serverID, new HashMap<>())
                    .remove(requestID);

            gui.setNRequestsServer(serverID, serversRequests.get(serverID).size());
        } finally {
            rl.unlock();
        }
    }

    /**
     * Build the status message list of all requests for every server.
     *
     * @return the status message list
     */
    public List<ServerStatusMessage> getServersStatus() {
        List<ServerStatusMessage> serversStatus = new ArrayList<>();

        try {
            rl.lock();
            for (Map.Entry<Integer, ServerRequestsInfo> entry : serversInfo.entrySet()) {
                int serverID = entry.getKey();
                int nRequests = entry.getValue().nRequests;
                int totalIterations = entry.getValue().totalIterations;
                serversStatus.add(new ServerStatusMessage(serverID, nRequests, totalIterations));
            }
            return serversStatus;

        } finally {
            rl.unlock();
        }
    }
    
    public void addHeartBeat(int ID){
        heartBeatMessages.add(ID);
    }
    
    public void removeHeartBeat(int ID){
        heartBeatMessages.remove(ID);
    }
    
    public boolean hasHeartBeat(int ID){
        return heartBeatMessages.contains(ID);
    }
    
    public int getHeartbeatThreashold(){
        return heartbeatThreashold;
    }

    /**
     * Representation of the Client's requests information
     */
    class ClientRequestsInfo {

        private int clientID;
        private int pending = 0;
        private int beingProcessed = 0;
        private int rejected = 0;
        private int processed = 0;

        ClientRequestsInfo(int clientID) {
            this.clientID = clientID;
        }

        void forwardingRequest() {
            pending++;
            update();
        }

        void processingRequest() {
            pending--;
            beingProcessed++;
            update();
        }

        void rejectingRequest() {
            pending--;
            rejected++;
            update();
        }

        void replyingRequest() {
            beingProcessed--;
            processed++;
            update();
        }

        void update() {
            gui.updateClientTable(clientID, pending, beingProcessed,
                    rejected, processed);
        }
    }

    /**
     * Representation of the Server's requests information
     */
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
