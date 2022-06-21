/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Monitor.Communication;

import Communication.ClientSocket;
import Monitor.Entities.Monitor;
import Monitor.GUI.GUI;
import Monitor.Handlers.THeartBeatHandler;
import java.io.IOException;
import java.net.ServerSocket;
import Monitor.Handlers.TServerHandler;

/**
 *
 * @author leand
 */
public class TServerSocket extends Thread {

    private final int portNumber;
    private final ServerSocket serverSocket;
    private final Monitor monitor;

    public TServerSocket(int portNumber, Monitor monitor) throws IOException {
        this.portNumber = portNumber;
        this.serverSocket = new ServerSocket(portNumber);
        this.monitor = monitor;
    }

    @Override
    public void run() {

        while (true) {
            ClientSocket clientSocket;
            try {
                clientSocket = new ClientSocket(serverSocket.accept());

                TServerHandler socket = new TServerHandler(clientSocket, monitor);
                socket.start();
               

            } catch (IOException ex) {
                System.err.println("Socket error");
            }
        }
    }
    

}
