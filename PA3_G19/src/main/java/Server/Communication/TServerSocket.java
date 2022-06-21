/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Communication;

import Communication.ClientSocket;
import Server.Entities.Server;

import Server.GUI.GUI;
import java.io.IOException;
import java.net.ServerSocket;
import Server.Handlers.TServerHandler;

/**
 *
 * @author leand
 */
public class TServerSocket extends Thread {


    private final ServerSocket serverSocket;
    private final Server server;

    public TServerSocket(int portNumber, Server server) throws IOException {
        this.serverSocket = new ServerSocket(portNumber);
        this.server = server;
    }

    @Override
    public void run() {

        while (true) {
            ClientSocket clientSocket;
            try {
                clientSocket = new ClientSocket(serverSocket.accept());

                TServerHandler socket = new TServerHandler(clientSocket, server);
                socket.start();

            } catch (IOException ex) {
                System.err.println("Socket error");
            }
        }
    }
    

}
