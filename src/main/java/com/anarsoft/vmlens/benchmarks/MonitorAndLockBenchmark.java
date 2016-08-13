/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.anarsoft.vmlens.benchmarks;


import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;


public class MonitorAndLockBenchmark {
	
	

	
	
	@State(Scope.Benchmark)
	public static class Contended
	{
		final LockedClass lockedClass = new LockedClass();
		
	}
	
	
	@State(Scope.Thread)
	public static class UnContended
	{
		final LockedClass lockedClass = new LockedClass();
		
	}
	
	public static void main(String[] args) throws RunnerException
	{
		runBenchmark(1, args[0]);
		runBenchmark(2, args[0]);
		

		for( int i = 16 ; i < 30 ; i += 2)
		{
			runBenchmark(i,args[0] );
		}
	  
		  
	}
	
	
	
	public static void runBenchmark(int threadCount,String jdk) throws RunnerException
	{
		 Options baseOpts = new OptionsBuilder()

	                .include(MonitorAndLockBenchmark.class.getName())

	               

	                .warmupIterations(30)
	                .measurementIterations(50)
	                .forks(3)
	                .threads(threadCount)
	                .verbosity(VerboseMode.SILENT)
	                .build();
		  
		  
		  
		  Collection<RunResult> runnerResultCollection = new Runner(baseOpts).run();

       
		  
		  for( RunResult result : runnerResultCollection )
		  {
			  System.out.printf("%s %s %d %4.4f %4.4f \n" ,result.getPrimaryResult().getLabel() ,jdk , threadCount ,  result.getPrimaryResult().getScore() , result.getPrimaryResult().getScoreError()  );
			  

			  
		  }
		  
		  
	}
	
	
	

	
	@Benchmark
	@BenchmarkMode({   Mode.SampleTime})
	  @OutputTimeUnit
	    (TimeUnit.NANOSECONDS)
	public long contended(Contended state) {

		return state.lockedClass.methodWithSynchronisation( state.lockedClass.loop_count);

	
	}
	
	
	@Benchmark
	@BenchmarkMode({ Mode.SampleTime})
	  @OutputTimeUnit
	    (TimeUnit.NANOSECONDS)
	public long contendedLock(Contended state) {

		return state.lockedClass.methodWithLock( state.lockedClass.loop_count);

	
	}
	
	@Benchmark
	@BenchmarkMode({ Mode.SampleTime })
	  @OutputTimeUnit
	    (TimeUnit.NANOSECONDS)
	public long unContended(UnContended state) {

		return state.lockedClass.methodWithSynchronisation( state.lockedClass.loop_count);


	}

	@Benchmark
	@BenchmarkMode({Mode.SampleTime })
    @OutputTimeUnit
    (TimeUnit.NANOSECONDS)
	public long baseLineUnContended(UnContended state) {

		return state.lockedClass.methodWithoutSynchronization( state.lockedClass.loop_count);


	}
	
	

	

}
