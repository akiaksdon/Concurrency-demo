package com.demo.runnable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.RandomUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnableVsThreadLiveTest {

	private static Logger log = 
	  LoggerFactory.getLogger(RunnableVsThreadLiveTest.class);
	
	private static ExecutorService executorService;
	
	@BeforeClass
	public static void setup() {
		
		// creates threads as required and then can reuse already existing threads
		//available in Executors class
		executorService = Executors.newCachedThreadPool(); 
	}

	@Test
	public void givenARunnable_whenRunIt_thenResult() throws InterruptedException {
		//create a new thread and pass runnable as a parameter to the thread
		Thread thread = new Thread(new SimpleRunnable("hello runnable"));
		//start the logic in run method
		thread.start();
		// let thread complete its own task and only then allow others to execute
		thread.join();
	}
	
	@Test
	public void givenAThread_whenRunIt_thenResult() throws InterruptedException {
		//create a new thread and pass runnable as a parameter to the thread
		Thread thread = new SimpleThread("hello runnable");
		//start the logic in run method
		thread.start();
		// let thread complete its own task and only then allow others to execute
		thread.join();
	}
	
	@Test
	public void givenARunnable_whenSubmitToExecutorService_thenResult() throws InterruptedException {
		
		//so here i got to let go creation of thread logic and stuff 
		//it is now managed by thread executor service 
		//runnable return s a future
		executorService.submit(new SimpleRunnable("exector service submit example"));
	}
	
	@Test
	public void givenAThread_whenSubmitToExecutorService_thenResult() throws InterruptedException {
		
		//so here i got to let go creation of thread logic and stuff 
		//it is now managed by thread executor service 
		//runnable return s a future
		executorService.submit(new SimpleThread("Thread service submit ecample"));
	}
	
	@Test
	public void givenACallableAsLambda_whenSubmitToExecutorService_thenResult() throws InterruptedException, ExecutionException {
		
		//so here i got to let go creation of thread logic and stuff 
		//it is now managed by thread executor service 
		//runnable return s a future
		Future<? extends Integer> submit = executorService.submit(()->RandomUtils.nextInt(0,100));
		Integer integer = submit.get();
		log.info("Result is {}",integer);
		
		
	}
	
	@Test
	public void givenACallable_whenSubmitToExecutorService_thenResult() throws InterruptedException, ExecutionException {
		
		//so here i got to let go creation of thread logic and stuff 
		//it is now managed by thread executor service 
		//runnable return s a future
		Future<Integer> submit = executorService.submit(new SimpleCallable());
		Integer object = submit.get();
		log.info("Result from callble object is {}",object);
		
		
	}

}

 class SimpleRunnable implements Runnable {

	 private static Logger log = 
			  LoggerFactory.getLogger(SimpleRunnable.class);
	 
	 private String message;
	 
	 SimpleRunnable(String message) {
			this.message = message;
		}
	 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		log.info(this.message);
	}

}
 
 class SimpleCallable implements Callable<Integer> {
	 
	 	//thrws exception
	 	//return a value
	 	//introduced in 1.5 java (1.8)
	 @Override
		public Integer call() throws Exception {
			return RandomUtils.nextInt(0, 100);
		}

	}
 
 class SimpleThread extends Thread {

	 private static Logger log = 
			  LoggerFactory.getLogger(SimpleRunnable.class);
	 
	 private String message;
	 
	 SimpleThread(String message) {
			this.message = message;
		}
	 
	public void run() {
		// TODO Auto-generated method stub
		log.info(this.message);
	}

}