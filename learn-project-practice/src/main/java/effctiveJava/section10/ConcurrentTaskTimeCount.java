package effctiveJava.section10;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConcurrentTaskTimeCount {
	
	public static void time(Executor executor, int concurrency, final Runnable task) {
		final CyclicBarrier barrier = new CyclicBarrier(concurrency, new Runnable() {
			private long lastTime = System.nanoTime();
			
			@Override
			public void run() {
				System.out.printf("lastime: %s, spendTime: %s, currentTime: %s%n", lastTime, System.nanoTime() - lastTime, (lastTime = System.nanoTime()));
			}
		});
		for(int i = 0; i < concurrency; i++) {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						barrier.await();
						task.run();
						barrier.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public static void main(String[] args) {
		time(Executors.newCachedThreadPool(), 5, new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
			}
		});
	}

}
