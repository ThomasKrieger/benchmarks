package com.anarsoft.vmlens.benchmarks;

import java.util.concurrent.Callable;

public class TestTask implements Runnable , Callable<Long> {

	
	 private long t = System.nanoTime();
	 public int count = 1;
	 public static volatile long consumedCPU;
	 
	@Override
	public void run() {
		 for (long i = count; i > 0; i--) {


	           t += (t * 0x5DEECE66DL + 0xBL + i) & (0xFFFFFFFFFFFFL);


	       }
	       
	       if (t == 42) {

	    	   

	    	               consumedCPU += t;

	    	   

	    	           }
		
	}

	@Override
	public Long call() throws Exception {
		for (long i = count; i > 0; i--) {


	           t += (t * 0x5DEECE66DL + 0xBL + i) & (0xFFFFFFFFFFFFL);


	       }
	       
	       if (t == 42) {

	    	   

	    	               consumedCPU += t;

	    	   

	    	           }
	       
	       
	       return t;
	}

}
