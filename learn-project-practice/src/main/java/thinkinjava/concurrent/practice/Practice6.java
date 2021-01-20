package thinkinjava.concurrent.practice;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Practice6 {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for(int i = 0; i < 5; i++) {
			executor.execute(new Task());
		}
		executor.shutdown();
	}
	
	private static class Task implements Runnable {
		private final static Random random = new Random();
		
		@Override
		public void run() {
			int sleepTime = random.nextInt(10) + 1;
			try {
				TimeUnit.SECONDS.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.printf("sleep time : %s%n", sleepTime);
		}
		
	}

}
