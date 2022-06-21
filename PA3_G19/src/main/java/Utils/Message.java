/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.Serializable;
import java.util.List;

/*
 *
 * @author leand
 */
public class Message implements Serializable {

    /* Message code. */
    CodeMessages code;
    
    /* Requests being processed by a server. */
    List<ServerStatusMessage> serversStatus;

    /* Generic type for identification */
    String type;
    /* Sender port number. */
    int port;

    public Message(CodeMessages code) {
        this.code = code;
    }
    
    public Message code(CodeMessages code) {
        this.code = code;
        return this;
    }

    public CodeMessages code() {
        return code;
    }
    
    public Message serversStatus(List<ServerStatusMessage> serversStatus) {
        this.serversStatus = serversStatus;
        return this;
    }
    
    public List<ServerStatusMessage> serversStatus() {
        return serversStatus;
    }
    
    public Message type(String type) {
        this.type = type;
        return this;
    }
    
    public String type() {
        return type;
    }
    
    public Message port(int port) {
        this.port = port;
        return this;
    }
    
    public int port() {
        return port;
    }
}
