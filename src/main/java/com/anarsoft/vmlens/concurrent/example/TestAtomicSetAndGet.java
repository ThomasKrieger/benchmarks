package com.anarsoft.vmlens.concurrent.example;

public class TestAtomicSetAndGet {
	
	
	public void test()
	{
		AtomicPositiveValue atomicPositiveValue= new AtomicPositiveValue(0);
		
		int threadA = atomicPositiveValue.get();
		int threadB = atomicPositiveValue.get();
		atomicPositiveValue.set(threadA + 5);
		atomicPositiveValue.set(threadB + 5);
		
	}
	

}
