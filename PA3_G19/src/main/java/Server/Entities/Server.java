/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Entities;

import Entities.Request;
import Communication.ClientSocket;
import Utils.CodeMessages;
import Utils.RequestMessage;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author guids
 */
public class Server {
    
    private Request[] pendingRequests;
    private int nPendingRequest = 0;
    private int ncurrentRequests = 0;
    private ClientSocket monitorSocket;
    private final TWorker[] workers;
    private final ReentrantLock rl;
     
    private Request[] currentRequests;

    
    public Server(ClientSocket socket){
        workers = new TWorker[3];
        
        workers[1] = new TWorker(1, this);
        workers[2]= new TWorker(2, this);
        workers[3] = new TWorker(3, this);
        
        workers[1].start();
        workers[2].start();
        workers[3].start();
        
        pendingRequests = new Request[2];
        currentRequests = new Request[3];
        
        rl = new ReentrantLock();
    }
    
    public void newRequest(Request r) throws InterruptedException{
        if(nPendingRequest == 2 && ncurrentRequests== 3){
            // send reject message
            ClientSocket s = new ClientSocket(r.clientID(), "127.0.0.1");
            
            RequestMessage rm = new RequestMessage(CodeMessages.REPLY,r.clientID(),
            r.requestID(), r.serverID(), 0,r.nIterations(), 0,r.deadline(),3);
            
            s.sendMessage(rm);
            
            monitorSocket.sendMessage(rm);
        }
        else if(ncurrentRequests==3){
            pendingRequests[nPendingRequest] = r;
            nPendingRequest++;
        }
        else{
            
            for(int i=0; i<3; i++){
                if(currentRequests[i] == null){
                    currentRequests[i] = r;
                    workers[i].newWork(r.nIterations());
                    ncurrentRequests++;
                }
            }
            
        }
    }
    
    public void result(int workerId, double result) throws InterruptedException{
        
        rl.lock();
        try {
            Request r = currentRequests[workerId];
            
            // send message to client
            ClientSocket s = new ClientSocket(r.clientID(), "127.0.0.1");
            
            RequestMessage rm = new RequestMessage(CodeMessages.REPLY,r.clientID(),
                    r.requestID(), r.serverID(), 0,r.nIterations(), result,r.deadline(),2);
            
            s.sendMessage(rm);
            
            monitorSocket.sendMessage(rm);
            
            // worker is available
            currentRequests[workerId] = null;
            ncurrentRequests--;
            
            // process pending request
            if(nPendingRequest > 0){
                int id =1;
                if(pendingRequests[1]== null || (pendingRequests[1].deadline() > pendingRequests[0].deadline())){
                    id=0;
                }
                nPendingRequest--;
                pendingRequests[id]= null;
                currentRequests[workerId] = pendingRequests[id];
                workers[workerId].newWork(pendingRequests[id].nIterations());
                ncurrentRequests++;
            }
        } finally {
            rl.unlock();
        }
        
    }
    
    
    
}
