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
    
    private final ReentrantLock rl;
    private final Condition cWork;
    private final Condition cfinishWork;
    
    private boolean hasWork=false;
    private boolean finishWork=false;
    
    private int ni;
    
    private double pi;
    
    public TWorker(){
        rl = new ReentrantLock();
        cWork = rl.newCondition();
        cfinishWork = rl.newCondition();
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
                
                pi = Math.round(PI* Math.pow(10,ni))/Math.pow(10, ni);
                
                this.finishWork=true;
                cfinishWork.signal();
                
                this.hasWork = false;
                
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
            finally{
                rl.unlock();
            }
        }
       
    }
    
    public double newWork(int ni) throws InterruptedException{
        try{
            rl.lock();
            this.ni = ni;
            this.hasWork = true;
            this.finishWork = false;
            cWork.signal();
            
            while(!finishWork)
                cfinishWork.await();
            
            return pi;
        }
        finally{
            rl.unlock();
            
        }
        
    }
}
