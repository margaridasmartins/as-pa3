/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Monitor.Handlers;

import Communication.ClientSocket;
import Monitor.Entities.Monitor;
import Monitor.GUI.GUI;
import static Utils.CodeMessages.*;
import Utils.HelloMessage;
import Utils.Message;
import Utils.RequestMessage;
import Utils.ServerStatusMessage;
import java.util.List;

/**
 *
 * @author leand
 */
public class TServerHandler extends Thread {

    private final ClientSocket socket;
    private final Monitor monitor;

    public TServerHandler(ClientSocket socket, Monitor monitor) {
        this.socket = socket;
        this.monitor = monitor;
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
                    case HELLO:
                        if (m.type().equals("LB")) {
                            /* Load Balancer HELLO */
                            if (monitor.hasPrimaryLB()) {
                                socket.sendMessage(new HelloMessage(0, null, "S"));
                            }
                            monitor.setLBUp();
                            socket.sendMessage(new HelloMessage(0, null, "P"));
                        } else {
                            
                            HelloMessage hm = (HelloMessage) msg;
                            
                            /* Server HELLO */
                            monitor.addServer(hm.ID());
                        }
                        break;
                    case STATUS:
                        List<ServerStatusMessage> serversStatus = monitor.getServersStatus();
                        Message res = new Message(STATUS)
                                .serversStatus(serversStatus);
                        socket.sendMessage(res);
                        break;
                    case REQUEST:
                        monitor.forwardingRequest((RequestMessage)msg);
                        break;
                    case REPLY:
                        monitor.replyingRequest((RequestMessage)msg);
                        break;
                    case HEARTBEAT:
                        monitor.addHeartBeat(m.port());
                        break;
                }
            }

        }
    }
}
