package com.anarsoft.vmlens.benchmarks;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;


@State(
		 Scope.Group)
public class QueueBenchmark {

	
	private ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
	
	
	
	@Benchmark
	@Group("g")
    @GroupThreads(3)
	public void writing()
	{
		concurrentLinkedQueue.offer("test");
	}
	
	@Benchmark
	@Group("g")
    @GroupThreads(1)
	public void reading()
	{
		concurrentLinkedQueue.poll();
	}
	
	
}
