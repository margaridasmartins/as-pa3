/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.Entities;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author guids
 */
public class TWorker extends Thread{
    
    public static final double PI = 3.1415926589793;
    
    private final int ID;
    private final Server server;
    private final ReentrantLock rl;
    private final Condition cWork;
    
    private boolean hasWork=false;
    
    private int ni;
    
    
    public TWorker(int ID, Server server){
        this.ID = ID;
        this.server = server;
        rl = new ReentrantLock();
        cWork = rl.newCondition();

    }
    
    @Override
    public void run() {
        while(true){
        
            try {
                rl.lock();
                while(!hasWork)
                    cWork.await();
                
                // Has work, will calculate pi 
                Thread.sleep(ni*5000);
                
                double pi = Math.round(PI* Math.pow(10,ni))/Math.pow(10, ni);
                
                this.hasWork = false;
                
                server.result(ID, pi);
                
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
            finally{
                rl.unlock();
            }
        }
       
    }
    
    public void newWork(int ni) throws InterruptedException{
        try{
            rl.lock();
            this.ni = ni;
            this.hasWork = true;
            cWork.signal();
        }
        finally{
            rl.unlock();
            
        }
        
    }
}
