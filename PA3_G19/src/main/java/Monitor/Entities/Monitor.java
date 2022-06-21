/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Monitor.Entities;

import Utils.RequestMessage;
import Utils.ServerInfoMessage;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author leand
 */
public class Monitor {

    private HashMap<Integer, List<ServerInfoMessage>> serversInfo;
    private List<RequestMessage> requests;
    private boolean hasPrimaryLB;
    
    public Monitor() {
        
    }
    
    public boolean hasPrimaryLB() {
        return hasPrimaryLB;
    }

}
