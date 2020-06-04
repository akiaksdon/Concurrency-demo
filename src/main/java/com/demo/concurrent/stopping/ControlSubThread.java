package com.demo.concurrent.stopping;

import java.util.concurrent.atomic.AtomicBoolean;

public class ControlSubThread implements Runnable {
	
	private Thread worker;
    private int interval = 100;
    private AtomicBoolean running = new AtomicBoolean(false);
    private AtomicBoolean stopped = new AtomicBoolean(true);
	
    
    public ControlSubThread(int sleepInterval) {
        interval = sleepInterval;
    }
    
    public void start() {
    	//make a thread of this runnable class
    	//and start
    	worker = new Thread(this);
    	worker.start();
    }
    
    
    public void stop() {
        running.set(false);
    }
    
    //on interrupt 
    public void interrupt() {
        running.set(false);
        
        //throws exception if thread is in wait or sleep
        worker.interrupt();
    }
    
    boolean isRunning() {
        return running.get();
    }

    boolean isStopped() {
        return stopped.get();
    }
    
	@Override
	public void run() {
		
		//here we are using Atomic flag to start or stop thread
		running.set(true);
		stopped.set(false);
		
		while(running.get()) {
			//showing that some lengthy operation is being performed 
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Thread inturrupted");
			}
			//more work here 
		}
		
		//stop once work done 
		stopped.set(true);
	}

}
