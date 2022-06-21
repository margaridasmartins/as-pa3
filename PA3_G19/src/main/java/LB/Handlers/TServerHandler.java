/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Handlers;

import LB.GUI.GUI;
import Utils.CodeMessages;
import Communication.ClientSocket;
import LB.Entities.LoadBalancer;
import LB.Entities.Request;
import Utils.Message;
import Utils.RequestMessage;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.net.Socket;


/**
 *
 * @author guids
 */
public class TServerHandler extends Thread {

    private final Socket socket;
    private final ClientSocket monitorSocket;
    private final LoadBalancer lb;

    public TServerHandler(Socket socket, ClientSocket monitorSocket, LoadBalancer lb) {
        this.socket = socket;
        this.monitorSocket = monitorSocket;
        this.lb = lb;
    }

    @Override
    public void run() {

        ObjectInputStream in = null;
        
        try {
            in =  new ObjectInputStream(socket.getInputStream());
            Message message = null;
            while (true) {
                // keep listening to incoming messages
                if ((message = (Message)in.readObject()) != null) {

                   
                    switch (message.code()) {

                        // Request message -> REQUEST | client id | request id | 00 | 01 | number of iterations | 00 | deadline |
                        case REQUEST: {
                            
                            RequestMessage rm = (RequestMessage) message;
                            
                            // Forward request to monitor
                            monitorSocket.sendMessage(new Message(CodeMessages.STATUS));

                            // Add request
                            lb.addRequest(new Request(rm.clientID(), rm.requestID(), rm.serverID(), rm.nIterations(), rm.deadline()));

                        }
                        break;
                    }
                }

            }
        } catch (IOException ex) {
            System.err.println("Socket error");
        } catch (ClassNotFoundException ex) {
           System.err.println(ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                System.err.println("Socket error");
            }
        }

    }

}
