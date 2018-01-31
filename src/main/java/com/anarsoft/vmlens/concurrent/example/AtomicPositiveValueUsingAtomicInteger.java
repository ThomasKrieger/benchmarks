package com.anarsoft.vmlens.concurrent.example;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicPositiveValueUsingAtomicInteger {
	private final AtomicInteger value;
	public AtomicPositiveValueUsingAtomicInteger(int start)
	{
		value = new AtomicInteger(start);
	}
	  public  int get()
	   {
		return value.get();
	   }
	   public  int update(int delta) throws Exception
	   {
		   int current = value.get();
		   int update = current + delta;
		   if( update < 0 ) 
		   {
			   throw new Exception("value is negative");
		   }
		   while( ! value.compareAndSet(current, update))
		   {
			   current = value.get();
			   update = current + delta;
			   if( update < 0 ) 
			   {
				   throw new Exception("value negative");
			   }
		   }
		   return update;   
	   }
}
