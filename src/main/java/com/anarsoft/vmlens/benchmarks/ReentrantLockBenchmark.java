package com.anarsoft.vmlens.benchmarks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class ReentrantLockBenchmark {

	 private long t = System.nanoTime();
	 public int count = 20;
	 public static volatile long consumedCPU;
	
	private long execute()
	{

		 for (long i = count; i > 0; i--) {


	           t += (t * 0x5DEECE66DL + 0xBL + i) & (0xFFFFFFFFFFFFL);


	       }
	       
	       if (t == 42) {

	    	   

	    	               consumedCPU += t;

	    	   

	    	           }
	       
	       return t;
	}
	
	
	@State(Scope.Benchmark)
	public static class Contended
	{
		private Object monitor = new Object();
		private ReentrantLock unfairLock = new ReentrantLock();
		private ReentrantLock fairLock = new ReentrantLock(true);
	}
	
	@State(Scope.Thread)
	public static class UnContended
	{
		private Object monitor = new Object();
		private ReentrantLock unfairLock = new ReentrantLock();
		private ReentrantLock fairLock = new ReentrantLock(true);
	}
	
	
	
	
	@Benchmark
	@BenchmarkMode({   Mode.Throughput})
	  @OutputTimeUnit
	    (TimeUnit.MILLISECONDS)
	public long testSynchronizedThreadContended(Contended contended )
	{
		synchronized(contended.monitor)
		{
			return execute();
		}
	}
	
	
//	@Benchmark
//	@BenchmarkMode({   Mode.Throughput})
//	  @OutputTimeUnit
//	    (TimeUnit.MILLISECONDS)
//	public long testSynchronizedThreadUnContended(UnContended uncontended )
//	{
//		synchronized(uncontended.monitor)
//		{
//			return execute();
//		}
//	}
	
	
	@Benchmark
	@BenchmarkMode({   Mode.Throughput})
	  @OutputTimeUnit
	    (TimeUnit.MILLISECONDS)
	public long testUnfairLockContended(Contended contended )
	{
		contended.unfairLock.lock();
		try{
			return execute();
		}
		finally
		{
			contended.unfairLock.unlock();
		}
		
		
		
	}
	
	
//	@Benchmark
//	@BenchmarkMode({   Mode.Throughput})
//	  @OutputTimeUnit
//	    (TimeUnit.MILLISECONDS)
//	public long testUnfairLockUnContended(UnContended uncontended )
//	{
//		uncontended.unfairLock.lock();
//		try{
//			return execute();
//		}
//		finally
//		{
//			uncontended.unfairLock.unlock();
//		}
//		
//		
//		
//	}
	
	
	
	
	
	
	
	@Benchmark
	@BenchmarkMode({   Mode.Throughput})
	  @OutputTimeUnit
	    (TimeUnit.MILLISECONDS)
	public long testFairLockContended(Contended contended)
	{
		contended.fairLock.lock();
		try{
			return execute();
		}
		finally
		{
			contended.fairLock.unlock();
		}
		
		
		
	}
	
	
	
//	@Benchmark
//	@BenchmarkMode({   Mode.Throughput})
//	  @OutputTimeUnit
//	    (TimeUnit.MILLISECONDS)
//	public long testFairLockUnContended(UnContended uncontended)
//	{
//		uncontended.fairLock.lock();
//		try{
//			return execute();
//		}
//		finally
//		{
//			uncontended.fairLock.unlock();
//		}
//		
//		
//		
//	}
	
	
	
	
}
