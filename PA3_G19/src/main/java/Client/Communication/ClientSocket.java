/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.Communication;
import Utils.CodeMessages;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author guids
 */
public class ClientSocket {
    
    private final int  clientId;
    private int requestId = 1;
    private int portNumber;
    private String hostName;
    private Socket socket;
    private PrintWriter out; 
    private BufferedReader in;
    
    public ClientSocket(int portNumber, String hostName, int clientId){
        this.portNumber = portNumber;
        this.hostName = hostName;
        this.clientId = clientId;
    }
    
    public boolean creatSocket(){
        try{ 
            this.socket = new Socket(this.hostName, this.portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in  = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            return true;
        }
        catch(Exception e){
           System.err.println("Could not create client socket");
           return false;
        }
    }
    
    public String getMessage(){
        try{
            return in.readLine();
        }
        catch(IOException e){
            System.err.println("Error getting socket message");
            return null;
        }
        
    }
    
    public int sendRequest(int ni, int dline){
        out.println(String.format("%s|%d|%d|00|01|%d|00|%d|",
                CodeMessages.REQUEST.name(), clientId, requestId, ni, dline));
        return requestId++;
    }
    
    public void closeSocket(){
        try{
            this.socket.close();
        }
        catch(IOException e){
            System.err.println("Error closing socket");
        }
        
    }
    
    
}