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
    private boolean hasPrimaryLB;

    private final ReentrantLock rl;

    public Monitor(GUI gui) {
        this.gui = gui;
        serversInfo = new HashMap<>();
        serversRequests = new HashMap<>();
        clientsInfo = new HashMap<>();
        loadBalancerRequests = new ArrayList<>();
        rl = new ReentrantLock();
    }

    public boolean hasPrimaryLB() {
        return hasPrimaryLB;
    }

    public void setLBUp() {
        hasPrimaryLB = true;
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
            System.out.println("forward "+ pending +","+ beingProcessed+","+ rejected+","+ processed);
            update();
        }

        void processingRequest() {
            pending--;
            beingProcessed++;
            System.out.println("process "+ pending +","+ beingProcessed+","+ rejected+","+ processed);
            update();
        }

        void rejectingRequest() {
            pending--;
            rejected++;
            System.out.println("reject "+ pending +","+ beingProcessed+","+ rejected+","+ processed);
            update();
        }

        void replyingRequest() {
            beingProcessed--;
            processed++;
            System.out.println("reply "+ pending +","+ beingProcessed+","+ rejected+","+ processed);
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
