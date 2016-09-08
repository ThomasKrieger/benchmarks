package com.anarsoft.vmlens.benchmarks;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;

import com.vmlens.executorService.VMLensExecutors;


@State(Scope.Benchmark)
public class ExecutorBenchmark {
	
	
	 ExecutorService vmlensExecutorService = null;
	 ExecutorService jdkExecutorService = null;
	 
	 
	 private long t = System.nanoTime();
	 public int count = 20;
	 public static volatile long consumedCPU;
	
	 public int iterationCount = 1;
	 
	 
	@TearDown(Level.Iteration) 
    public void shutdownExecutorServiceJDK() throws Exception 
	{

		
		shutdownService ( jdkExecutorService );

    }
	
	
	
	private void shutdownService(ExecutorService executorService) throws Exception
	{
		executorService.shutdown();
		
		boolean stopped = executorService.awaitTermination( 60 , TimeUnit.SECONDS);
		
		while( ! stopped )
		{
			stopped = executorService.awaitTermination( 60 , TimeUnit.SECONDS);
		}
		
	}
	
	@TearDown(Level.Iteration) 
    public void shutdownExecutorServiceVMLens() throws Exception 
	{

		shutdownService ( vmlensExecutorService );
		

    }
	
	@Setup(Level.Iteration)
	public void startupExecutorService()  {
		
		 vmlensExecutorService = VMLensExecutors.newHighThroughputExecutorService(5);
		 jdkExecutorService = Executors.newFixedThreadPool(5);
		 
	
	}
	
	
	
	public Long roundTrip(ExecutorService theService) throws Exception
	{
		long r = 0L;
		
		for(int x = 0 ; x < iterationCount ; x++)
		{
			
		Future<Long> result = theService.submit( (Callable<Long>) new TestTask());
		
		
		r = r +  result.get();
		}
		
		return r;
	}
	
	
	public void fireAndForget(ExecutorService theService)
	{
		for(int x = 0 ; x < iterationCount ; x++)
		{
			
		
		 for (long i = count; i > 0; i--) {


	           t += (t * 0x5DEECE66DL + 0xBL + i) & (0xFFFFFFFFFFFFL);


	       }
	       
	       if (t == 42) {

	    	   

	    	               consumedCPU += t;

	    	   

	    	           }
		
		
		theService.execute(new TestTask());

		}
		}
	
	
	@Benchmark
	@BenchmarkMode({   Mode.AverageTime})
	  @OutputTimeUnit
	    (TimeUnit.NANOSECONDS)
	public Long roundTripJDK() throws Exception
	{
		return roundTrip(jdkExecutorService);
	}
	
	@Benchmark
	@BenchmarkMode({   Mode.AverageTime})
	  @OutputTimeUnit
	    (TimeUnit.NANOSECONDS)
	public Long roundTripVMLens() throws Exception
	{
		
		
		return roundTrip(vmlensExecutorService);
	}
	
	
	
	
	@Benchmark
	@BenchmarkMode({   Mode.Throughput})
	  @OutputTimeUnit
	    (TimeUnit.MILLISECONDS)
	public void fireAndForgetJDK()
	{
		fireAndForget ( jdkExecutorService );
	}
	
	
	
	@Benchmark
	@BenchmarkMode({   Mode.Throughput})
	  @OutputTimeUnit
	    (TimeUnit.MILLISECONDS)
	public void fireAndForgetVMLens()
	{
		fireAndForget (  vmlensExecutorService );
	}
	

	public static void main(String[] args) throws RunnerException
	{
	
		

		for( int i = 1 ; i < 5 ; i++)
		{
			runBenchmark(i,"jdk8" );
		}
	  
		  
	}
	
	
	
	public static void runBenchmark(int threadCount,String jdk) throws RunnerException
	{
		 Options baseOpts = new OptionsBuilder()

	                .include(ExecutorBenchmark.class.getName())

	               

	                .warmupIterations(5)
	                .measurementIterations(10)
	                .forks(1)
	                .threads(threadCount)
	                .verbosity(VerboseMode.SILENT)
	                .build();
		  
		  
		  
		  Collection<RunResult> runnerResultCollection = new Runner(baseOpts).run();

       
		  
		  for( RunResult result : runnerResultCollection )
		  {
			  System.out.printf("%s %s %d %4.4f %4.4f \n" ,result.getPrimaryResult().getLabel() ,jdk , threadCount ,  result.getPrimaryResult().getScore() , result.getPrimaryResult().getScoreError()  );
			  

			  
		  }
		  
		  
	}
	

}
