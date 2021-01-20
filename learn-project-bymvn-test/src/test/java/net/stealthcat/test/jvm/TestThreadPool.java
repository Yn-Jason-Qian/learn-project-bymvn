package net.stealthcat.test.jvm;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThreadPool {

	public static void main(String[] args) {
		testSynQueue();
		
	}

	static BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10);
	static ExecutorService executor = new ThreadPoolExecutor(10, 600, 3, TimeUnit.SECONDS, queue);
	
	static int count = 1000;
	static CountDownLatch latch = new CountDownLatch(count);
	static CyclicBarrier barrier = new CyclicBarrier(count);
	static AtomicInteger executedCount = new AtomicInteger();
	static AtomicInteger rejectedCount = new AtomicInteger();
	
	public static void testSynQueue() {
		long start = System.currentTimeMillis();
		for(int i=0;i<count;i++) {
			new Thread(new TaskStarter()).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("execute time : %s nm%n" ,System.currentTimeMillis() - start);
		System.out.printf("executed count : %s, rejected count : %s", executedCount.get(), rejectedCount.get());
		executor.shutdown();
	}
	
	static class TaskStarter implements Runnable {
		
		@Override
		public void run() {
			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			
			try {
				executor.execute(new Task());
			} catch (Exception e) {
//				e.printStackTrace();
				latch.countDown();
				rejectedCount.incrementAndGet();
			}
		}
		
	}
	
	static class Task implements Runnable {

		@Override
		public void run() {
			sleep();
			latch.countDown();
			int count = executedCount.incrementAndGet();
			System.out.println("executed count : " + count);
		}
		
	}
	
	static void sleep() {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
