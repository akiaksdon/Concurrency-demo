package com.demo.completablefuture;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletableFutureLongRunningUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(CompletableFutureLongRunningUnitTest.class);
    

    @Test
    public void whenRunningCompletableFurtureAsync_thenGetMethodWaitsForResult() throws InterruptedException, ExecutionException {
	   
    	Future<String> calculateAsync = calculateAsync();
    	//this blocks 
    	String reString = calculateAsync.get();
    	assertEquals("Hello", reString);
    	
   }

	private Future<String> calculateAsync() {
		// TODO Auto-generated method stub
		CompletableFuture<String> completableFuture = new CompletableFuture<>();
		
		Executors.newCachedThreadPool().submit(() -> {
			try {
				Thread.sleep(300);
				completableFuture.complete("Hello");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	   ); 
		
		return completableFuture;
		
	}
	
	//COMPLETED FUTURE RETURN IMMEDIATELY 
	
	@Test
    public void whenRunningCompletableFutureWithResult_thenGetMethodReturnsImmediately() throws InterruptedException, ExecutionException {
        Future<String> completableFuture = CompletableFuture.completedFuture("Hello");

        String result = completableFuture.get();
        assertEquals("Hello", result);
    }
	
	private Future<String> calculateAsyncWithCancellation() {
		// TODO Auto-generated method stub
		CompletableFuture<String> completableFuture = new CompletableFuture<>();
		
		Executors.newCachedThreadPool().submit(() -> {
			try {
				Thread.sleep(300);
				completableFuture.cancel(false);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	   ); 
		
		return completableFuture;
		
	}
	
	
	@Test(expected = CancellationException.class)
    public void whenRunningCompletableFurtureAsync_thenCancellationThrowsCancellationException() throws InterruptedException, ExecutionException {
	   
    	Future<String> calculateAsync = calculateAsyncWithCancellation();
    	//this blocks 
    	calculateAsync.get();
    	
   }
	
	@Test
    public void whenRunningCompletableFurtureSupplyAsync_thenReturnFutureValue() throws InterruptedException, ExecutionException {
	   
    	CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
    	assertEquals("Hello", future.get());
    	
   }
	
	@Test
    public void whenRunningCompletableFurtureSupplyAsyncRunnable_thenReturnFutureValue() throws InterruptedException, ExecutionException {
	   
    	CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
    		try {
    			System.out.println("waiting 5 ws ");
    			System.out.println("ahere trying to explain some operation that takes time ");
				Thread.sleep(5000);
				System.out.println("completed waiting 5 ws ");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return "Hello";
    	},
    			Executors.newSingleThreadExecutor());
    	
    	System.out.println("gonna block now");
    	assertEquals("Hello", future.get());
    	System.out.println(" block finally left now ....");
    	
   }
	
	

}