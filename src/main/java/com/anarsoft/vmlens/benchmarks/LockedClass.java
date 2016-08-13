package com.anarsoft.vmlens.benchmarks;

import java.util.concurrent.locks.ReentrantLock;

public class LockedClass {


	
	
	
	public int loop_count = 5;


    private long t = System.nanoTime();

    private final ReentrantLock lock = new ReentrantLock();


   public static  long consumedCPU;

   public  long methodWithoutSynchronization(int count)
   {
	 

       for (long i = count; i > 0; i--) {


           t += (t * 0x5DEECE66DL + 0xBL + i) & (0xFFFFFFFFFFFFL);


       }
       
       if (t == 42) {

    	   

    	               consumedCPU += t;

    	   

    	           }
	   
	   return t;
	   
	   
   }



	public  synchronized long methodWithSynchronisation(int count)
	{
		return methodWithoutSynchronization(count);
		
		
	}
	
	public long methodWithLock(int count)
	{
		lock.lock();
		try{
			return methodWithoutSynchronization(count);
		}
		finally
		{
			lock.unlock();
		}
		
		
	}
	
	
	
	
	
}
