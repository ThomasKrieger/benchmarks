package com.anarsoft.vmlens.benchmarks;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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




@State(Scope.Benchmark)
public class FileWritingBenchmark {
	
	private DataOutputStream stream;
	
	
	@TearDown(Level.Trial)
	public void close() throws IOException
	{
		stream.close();
		
	}
	
	
	
	@Setup(Level.Trial)
	public void setup()  throws Exception
	{
		
		 stream = new DataOutputStream(new  BufferedOutputStream(new FileOutputStream("test")));
		 
	
	}
	
	
	@Benchmark
	@BenchmarkMode({   Mode.Throughput})
	  @OutputTimeUnit
	    (TimeUnit.MILLISECONDS)
	public void write2File() throws Exception
	{
		
		stream.write(50);
		stream.write(50);
		
		
		
		
	}
	
	

}
