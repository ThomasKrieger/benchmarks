package com.anarsoft.vmlens.benchmarks;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import com.anarsoft.vmlens.concurrent.example.AtomicPositiveValue;
import com.anarsoft.vmlens.concurrent.example.AtomicPositiveValueUsingAtomicInteger;

@State(Scope.Benchmark)
public class SynchronizedVsAtomicInteger {

	private AtomicPositiveValue valueSynchronized = new AtomicPositiveValue(0);
	private AtomicPositiveValueUsingAtomicInteger valueUsingAtomicInteger = new AtomicPositiveValueUsingAtomicInteger(0);
	
	@Benchmark
	@BenchmarkMode({   Mode.AverageTime})
	  @OutputTimeUnit
	    (TimeUnit.NANOSECONDS)
	public void updateSynchronized() throws Exception
	{
		valueSynchronized.update(1);
	}
	@Benchmark
	@BenchmarkMode({   Mode.AverageTime})
	  @OutputTimeUnit
	    (TimeUnit.NANOSECONDS)
	public void updateAtomicInteger() throws Exception
	{
		valueUsingAtomicInteger.update(1);
	}
	@Benchmark
	@BenchmarkMode({   Mode.AverageTime})
	  @OutputTimeUnit
	    (TimeUnit.NANOSECONDS)
	public void getSynchronized() throws Exception
	{
		valueSynchronized.get();
	}
	@Benchmark
	@BenchmarkMode({   Mode.AverageTime})
	  @OutputTimeUnit
	    (TimeUnit.NANOSECONDS)
	public void getAtomicInteger() throws Exception
	{
		valueUsingAtomicInteger.get();
	}
	
	
}
