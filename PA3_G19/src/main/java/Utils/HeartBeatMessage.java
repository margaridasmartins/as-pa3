/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author guids
 */
public class HeartBeatMessage extends Message{
    
    
    int ID;
    
    public HeartBeatMessage(int ID) {
        super(CodeMessages.HEARTBEAT);
        this.ID = ID;
    }
    
    public int ID() {
        return ID;
    }
}
