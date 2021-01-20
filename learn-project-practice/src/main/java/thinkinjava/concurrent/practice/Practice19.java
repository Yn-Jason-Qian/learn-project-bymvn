package thinkinjava.concurrent.practice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Practice19 {

	private static void sleepMethod() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			System.out.println("sleep method interrupted!");
		}
	}
	
	static class InterruptingTask implements Runnable {

		@Override
		public void run() {
			sleepMethod();
			System.out.println("InterruptingTask over");
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newCachedThreadPool();
		Future<?> future = service.submit(new InterruptingTask());
		Thread.sleep(100);
		future.cancel(true);
		service.shutdown();
	}

}
