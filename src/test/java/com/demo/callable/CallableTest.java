package com.demo.callable;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallableTest {

	private static Logger log = 
	  LoggerFactory.getLogger(CallableTest.class);
	
	private static ExecutorService executorService;
	
	@BeforeClass
	public static void setup() {
		
		// creates threads as required and then can reuse already existing threads
		//available in Executors class
		executorService = Executors.newCachedThreadPool(); 
	}
	

	//here callable return a future then we get future and then value from it
	@Test(expected = ExecutionException.class)
	public void givenACallable_whenTaskSubmitted_thenThrowExecutionException() throws InterruptedException, ExecutionException {
		FactorialOfNumber factorialOfNumber = new FactorialOfNumber(new Integer(-5));
		Future<Integer> submit = executorService.submit(factorialOfNumber);
		//exception is thrown only if get is called or future is accesed
		submit.get().intValue();
		assertEquals(true, submit.isDone());
	}
	
	@Test()
	public void givenACallable_whenTaskSubmitted_thenFutureResult() throws InterruptedException, ExecutionException {
		FactorialOfNumber factorialOfNumber = new FactorialOfNumber(new Integer(4));
		Future<Integer> submit = executorService.submit(factorialOfNumber);
		assertEquals(24, submit.get().intValue());
		assertEquals(true, submit.isDone());
	}
	
	@After
	public void cleanup(){
		executorService.shutdown();
	}


}

 