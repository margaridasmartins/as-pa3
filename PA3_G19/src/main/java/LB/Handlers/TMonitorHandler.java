/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LB.Handlers;
import LB.Communication.MonitorSocket;
import Utils.CodeMessages;

/**
 *
 * @author guids
 */
public class TMonitorHandler extends Thread{
    
    private final MonitorSocket socket;
    
    public TMonitorHandler(MonitorSocket socket){
        this.socket = socket; 
    }
    
    @Override
    public void run(){
    
        String inputLine;
        
        while (true) {
            // keep listening to incoming messages
            if ((inputLine = socket.getMessage()) != null) {
                
                String[] clientMessage = inputLine.split("|");
                
                switch(CodeMessages.valueOf(clientMessage[0])){
                    
                    // HB message -> HB|LBID
                    case HB:
                        {
                            String hbMessage = CodeMessages.HB.name() + "|" + clientMessage[1];
                            socket.sendMessage(hbMessage);
                        }
                        break;  
                }
            }
    
        }
        
    }
    
}
