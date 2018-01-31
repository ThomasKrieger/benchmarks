package com.anarsoft.vmlens.benchmarks;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import com.vmlens.executorService.Consumer;
import com.vmlens.executorService.EventSink;
import com.vmlens.executorService.internal.EventBusImpl;
import com.vmlens.executorService.internal.ProzessAllListsRunnable;
import com.vmlens.executorService.internal.ProzessOneList;

import gnu.trove.map.hash.TLongObjectHashMap;

@State(Scope.Group)
public class BlockingQueueBenchmark {
	
	private static final int WRITING_THREAD_COUNT = 5;
	private static final int VMLENS_QUEUE_LENGTH = 1000;
	private static final int JDK_QUEUE_LENGTH    = 4000;
	
	EventBusImpl eventBus;
	Consumer consumer;
	ProzessAllListsRunnable prozess;
	TLongObjectHashMap<ProzessOneList> threadId2ProzessOneRing;
	LinkedBlockingQueue jdkQueue;
	
	private long jdkCount = 0;
	private long vmlensCount = 0;
	
	@Setup()
	public void setup() {
		eventBus = new EventBusImpl(VMLENS_QUEUE_LENGTH);
		consumer = eventBus.newConsumer();
		prozess = new ProzessAllListsRunnable(new EventSink() {

			@Override
			public void execute(Object event) {
				vmlensCount++;
			}

			@Override
			public void close() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onWait() {
				// TODO Auto-generated method stub

			}

		}, eventBus);

		threadId2ProzessOneRing = new TLongObjectHashMap<ProzessOneList>();
		jdkQueue = new LinkedBlockingQueue(JDK_QUEUE_LENGTH);
	}

	@Benchmark
	@Group("vmlens")
	@GroupThreads(WRITING_THREAD_COUNT)
	public void offerVMLens() {
		consumer.accept("event");
	}

	@Benchmark
	@Group("vmlens")
	@GroupThreads(1)
	public void takeVMLens() {
		prozess.oneIteration(threadId2ProzessOneRing);
	}

	@Benchmark
	@Group("jdk")
	@GroupThreads(WRITING_THREAD_COUNT)
	public void offerJDK() {
		try {
			jdkQueue.put("event");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Benchmark
	@Group("jdk")
	@GroupThreads(1)
	public void takeJDK() {

		try {
			jdkQueue.poll(100, TimeUnit.SECONDS);
			jdkCount++;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	 @TearDown(Level.Trial)
	 public void printCounts() {
        System.out.println("jdkCount " + jdkCount);
        System.out.println("vmlensCount " + vmlensCount);
	  }

}
