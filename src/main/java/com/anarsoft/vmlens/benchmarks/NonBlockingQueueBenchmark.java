package com.anarsoft.vmlens.benchmarks;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
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
import java.util.concurrent.ConcurrentLinkedQueue;
import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.EventBus;
import com.vmlens.executorService.EventSink;
import com.vmlens.executorService.VMLensExecutors;
import com.vmlens.executorService.internal.EventBusImpl;

import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.*;
import akka.dispatch.AbstractNodeQueue;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 5)
@Threads(4)
//@OutputTimeUnit(TimeUnit.MILLISECONDS )
public class NonBlockingQueueBenchmark {

	
	private class NonBlockingAkkaQueue extends AbstractNodeQueue 
	{
		
	}
	
	
	
	
	Consumer consumer = (new EventBusImpl(1000)).newConsumer();
	ConcurrentLinkedQueue jdkQueue = new ConcurrentLinkedQueue();
	NonBlockingAkkaQueue akkaQueue = new NonBlockingAkkaQueue();
	
//	 @TearDown(Level.Invocation)
//	 public void tearDown()
//	 {
//		 jdkQueue  = new ConcurrentLinkedQueue();
//		 akkaQueue = new NonBlockingAkkaQueue();
//		 consumer = (new EventBusImpl()).newConsumer();
//		System.gc();
//		System.gc();
//		System.gc();
//				 
//	 }
	
	@Benchmark
	public void jdkQueue()
	{
		jdkQueue.offer("event");
	}
	
	
	@Benchmark 
	public void akkaQueue()
	{
		akkaQueue.add("event");
	}
	
	
	@Benchmark
	public void vmlens()
	{
		consumer.acceptWithoutBackPressure("event");
	}
	
	

	
}
