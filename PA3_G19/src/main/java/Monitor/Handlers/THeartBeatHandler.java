/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Monitor.Handlers;

import Communication.ClientSocket;
import Monitor.Entities.Monitor;
import Utils.HeartBeatMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guids
 */
public class THeartBeatHandler extends Thread{
    
    
    private Monitor monitor;
    private ClientSocket socket;
    private int ID;
    
    public THeartBeatHandler(ClientSocket socket, Monitor monitor, int ID){
        this.monitor = monitor;
        this.socket = socket;
        this.ID = ID;
    }
    
    @Override
    public void run(){
        
        try {
            while(true){
                socket.sendMessage(new HeartBeatMessage(0));
                Thread.sleep(monitor.getHeartbeatThreashold());
                if(monitor.hasHeartBeat(ID)){
                    monitor.removeHeartBeat(ID);
                }
                else{
                    System.out.println("SET DOWN " + ID);
                    monitor.setDown(ID);
                    break;
                }
            }
            
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
    
}
