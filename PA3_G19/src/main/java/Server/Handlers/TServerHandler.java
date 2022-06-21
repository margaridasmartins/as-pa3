/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Handlers;

import Communication.ClientSocket;
import static Utils.CodeMessages.*;
import Utils.Message;
import Utils.RequestMessage;
import Entities.Request;
import Server.Entities.Server;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leand
 */
public class TServerHandler extends Thread {

    private final ClientSocket socket;
    private final Server server;

    public TServerHandler(ClientSocket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Handle messages received in a loop.
     */
    @Override
    public void run() {

        Object msg;
        while (true) {
            // keep listening to incoming messages
            if ((msg = socket.getMessage()) != null) {
                
                Message m = (Message) msg;
                switch (m.code()) {
                    case REQUEST: {
                        
                        try {
                            RequestMessage rm = (RequestMessage) msg;
                            server.newRequest(new Request(rm.clientID(), rm.requestID(), rm.serverID(),rm.nIterations(), rm.deadline()));
                        } catch (InterruptedException ex) {
                            System.err.println(ex);
                        }
                    }
                    break;
                }
            }

        }
    }
}
