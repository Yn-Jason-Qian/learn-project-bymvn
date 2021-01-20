package net.stealthcat.test.jvm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;


public class TestMapPerform {

	public static void main(String[] args) throws InterruptedException {
		countTimeSpend(0);
		countTimeSpend(1);
		countTimeSpend(2);
		countTimeSpend(3);
		executor.shutdown();
	}
	
	static void put(Map<String, Object> map) {
		for(int i=0;i<count;i++) {
			map.put(i+"", "");
		}
	}
	
	static void warmVm() {
		int c = count;
		count = 20000;
		put(new HashMap<String, Object>());
		count = c;
	}
	
	static int count = 10000;
	static void testHashMap() {
		put(new HashMap<String, Object>());
	}

	static void testConcurrentHashMap() {
		put(new ConcurrentHashMap<String, Object>());
	}
	
	static int threadCount = 2;
	static ExecutorService executor = Executors.newFixedThreadPool(threadCount);
	static void testHashMapInConcur() throws InterruptedException {
		final Map<String, Object> map = new HashMap<String, Object>();
		final CountDownLatch latch = new CountDownLatch(threadCount);
		final CyclicBarrier barrier = new CyclicBarrier(threadCount);
		for(int i=0;i<threadCount;i++) {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						barrier.await();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for(int i=0;i<count;i++) {
						synchronized (map) {
							map.put(i+"", "");
						}
					}
					latch.countDown();
				}
			});
		}
		latch.await();
	}
	
	static void testConcurrentHashMapInConcur() throws InterruptedException {
		final Map<String, Object> map = new ConcurrentHashMap<String, Object>();
		final CountDownLatch latch = new CountDownLatch(threadCount);
		final CyclicBarrier barrier = new CyclicBarrier(threadCount);
		for(int i=0;i<threadCount;i++) {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						barrier.await();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for(int i=0;i<count;i++) {
						map.put(i+"", "");
					}
					latch.countDown();
				}
			});
		}
		latch.await();
	}
	
	static void countTimeSpend(int offset) throws InterruptedException {
		long start = System.nanoTime();
		switch (offset) {
		case 0:
			testHashMap();
			break;
		case 1:
			testConcurrentHashMap();
			break;
		case 2:
			testHashMapInConcur();
			break;
		case 3:
			testConcurrentHashMapInConcur();
			break;
		default:
			break;
		}
		long time = System.nanoTime() - start;
		int factor = offset < 2? 1 : threadCount;
		System.out.println("spend avg time" + time/(count * factor) + "nano seconds.");
	}
}
