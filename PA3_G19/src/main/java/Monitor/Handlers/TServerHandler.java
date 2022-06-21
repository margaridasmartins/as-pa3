/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Monitor.Handlers;

import Communication.ClientSocket;
import Monitor.Entities.Monitor;
import Monitor.GUI.GUI;
import static Utils.CodeMessages.*;
import Utils.Message;
import Utils.RequestMessage;

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

        Message msg;
        while (true) {
            // keep listening to incoming messages
            if ((msg = socket.getMessage()) != null) {

                switch (msg.code()) {
                    case HELLO: {
                        if (monitor.hasPrimaryLB()) {
                            socket.sendMessage(new Message(HELLO).type("S"));
                        }
                        socket.sendMessage(new Message(HELLO).type("P"));
                        
                    }
                    case REPLY: {
                        monitor.forwardingRequest((RequestMessage)msg);
                    }
                    case FORWARD: {
                        // TODO Handle Request
                    }
                    break;
                }
            }

        }
    }
}
