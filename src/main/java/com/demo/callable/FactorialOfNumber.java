package com.demo.callable;

import java.util.concurrent.Callable;


//callable can return a value 
//also it can throw exception 


public class FactorialOfNumber implements Callable<Integer> {

	private Integer input ;
	
	public FactorialOfNumber(Integer input) {
		this.input = input;
	}
	
	
	//instead of call this one has a call method 
	// calll for callable 
	// run for runnable
	@Override
	public Integer call() throws InvalidParamaterException {
		int fact =1;
		if(input < 0) {
			throw new InvalidParamaterException("Number should be positive");
		}
		else {
			for(int i=input ; i>1 ; i--) {
				fact = fact * i;
			}
		}
		return fact;
	}
	
	private class InvalidParamaterException extends Exception {
        /**
		 * 
		 */
		private static final long serialVersionUID = -1525019762632560576L;

		public InvalidParamaterException(String message) {
            super(message);
        }
    }

}
