package com.anarsoft.vmlens.benchmarks;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import com.vmlens.trace.agent.bootstrap.callback.ArrayAccessCallback;
import com.vmlens.trace.agent.bootstrap.callback.CallbackState;
import com.vmlens.trace.agent.bootstrap.callback.CallbackStatePerThread;
import com.vmlens.trace.agent.bootstrap.callback.FieldAccessCallback;
import com.vmlens.trace.agent.bootstrap.callback.MethodCallback;
import com.vmlens.trace.agent.bootstrap.callback.SynchronizedStatementCallback;


/**
 * 
 * Ohne trace:
 * Benchmark                              Mode  Samples       Score  Score error   Units
c.a.v.b.AgentBenchmark.fieldAccess    thrpt      200  199095.525      841.530  ops/ms

Benchmark                              Mode  Samples      Score  Score error   Units
c.a.v.b.AgentBenchmark.fieldAccess    thrpt      200  24030.192      119.523  ops/ms

Nur Methode:
Benchmark                              Mode  Samples        Score  Score error   Units
c.a.v.b.AgentBenchmark.fieldAccess    thrpt      200  3345866.581     9566.472  ops/ms


Benchmark                              Mode  Samples        Score  Score error   Units
c.a.v.b.AgentBenchmark.fieldAccess    thrpt      200  3340875.272    10804.191  ops/ms


Benchmark                              Mode  Samples       Score  Score error   Units
c.a.v.b.AgentBenchmark.fieldAccess    thrpt      200  374672.341      857.409  ops/ms

Benchmark                              Mode  Samples       Score  Score error   Units
c.a.v.b.AgentBenchmark.fieldAccess    thrpt      200  157154.345      520.740  ops/ms



MethodCallback.methodEnter	     						 157154.345
nur methoden aufruf 									3345866.581 
callback State											 374672.341
Beinhaltet thread local aber keine Feld zugriffe       	 199095.525
tracen für jeweils neues object							  24030.192

MethodCallback.methodEnter	 avgt                               80  6.463        0.046  ns/op
MethodCallback.methodEnter  ohne objekt konsatruktor avgt       80  5.583        0.009  ns/op


Nur Benchmark code   								 avgt       80  0.298        0.001  ns/op
FieldAccessCallback                                  avgt       80  41.288        0.291  ns/op

Thread.currentThread().getId(); 					 avgt       80  0.299        0.001  ns/op (kann sein das das weg optimiert wird?)


FieldAccessCallback CallbackState.slidingWindow= 0   avgt       80  5.055        0.031  ns/op

ArrayAccessCallback.newArray                         avgt       80  160948531.381  168132805.956  ns/op


ArrayAccessCallback.get                              avgt       80  952.695      368.514  ns/op


SynchronizedStatementCallback.monitorEnter           avgt       80  33966127.688  82699058.012  ns/op
 * 
 * 
 * 
 * @author thomas
 *
 */

@State(Scope.Benchmark)
public class AgentBenchmark {
	
	
	@Setup
	public void startTracing()
	{
		CallbackState.slidingWindow= 1;
	}
	
	@Benchmark
	@BenchmarkMode({   Mode.AverageTime})
	  @OutputTimeUnit
	    (TimeUnit.NANOSECONDS)
	public void fieldAccess() throws Exception
	{
		
	 //	Thread.currentThread().getId();
		
	// 	CallbackStatePerThread callbackStatePerThread = CallbackState.callbackStatePerThread.get();
	// 	FieldAccessCallback.field_access(  "hallo" , 1 , 1, 1, -1);
		
	//	MethodCallback.methodEnter(4);
		
		
	//	int[] array = new int[6];
		
	//	ArrayAccessCallback.get(array , 4 , 3 , -1 , 1);
		
		SynchronizedStatementCallback.monitorEnter(new Object(), 1, 1, -1);
		
		
	}
	
	

}
