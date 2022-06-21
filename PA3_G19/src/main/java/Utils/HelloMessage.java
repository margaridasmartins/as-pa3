package Utils;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author leand
 */
public class HelloMessage extends Message {
    /* Server ID. */
    int ID;


    
    public HelloMessage(int ID, String type) {
        super(CodeMessages.HELLO);
        this.ID = ID;
        this.type = type;
    }
    
    public int ID() {
        return ID;
    }
    
    
}
