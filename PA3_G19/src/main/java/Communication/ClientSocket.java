package Communication;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import Utils.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author leand
 */
public class ClientSocket {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientSocket(int portNumber, String hostName) {
        try {
            this.socket = new Socket(hostName, portNumber);
            createSocket();
        } catch (IOException e) {
            System.err.println("Could not create client socket");
        }
    }

    public ClientSocket(Socket socket) {
        this.socket = socket;
        try {
            createSocket();
        } catch (IOException e) {
            System.err.println("Could not create client socket");
        }
    }

    private void createSocket() throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }
    
    public int getPort(){
        return socket.getPort();
    }
    
    /**
     * Receive an object message.
     * @return message received
     */
    public Object getMessage(){
        Object reply = null;
        try{
            reply = in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Error receiving socket message");
        }
        return reply;
    }
    
    /**
     * Send an object message.
     * @param obj   the message object 
     * @return      true if successfully sent, false otherwise
     */
    public boolean sendMessage(Object obj){
        try{
            out.reset();
            out.writeObject(obj);
        } catch (IOException e) {
            System.err.println("Error sending message");
            return false;
        }
        return true;
    }

    public void closeSocket() {
        try {
            this.socket.close();
        } catch (IOException e) {
            System.err.println("Error closing socket");
        }
    }
}
