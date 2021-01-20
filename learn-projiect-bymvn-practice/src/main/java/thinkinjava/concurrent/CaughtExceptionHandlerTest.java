package thinkinjava.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CaughtExceptionHandlerTest {
	
	private static class ExceptionTask implements Runnable {

		@Override
		public void run() {
			throw new RuntimeException();
		}
		
	}
	
	private static class ExceptionCaughtHandler implements Thread.UncaughtExceptionHandler {

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			System.out.printf("catch Exception, %s%n", t);
		}
		
	}
	
	private static class ExceptionCaughtThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setUncaughtExceptionHandler(new ExceptionCaughtHandler());
			return t;
		}
		
	}

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool(new ExceptionCaughtThreadFactory());
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionCaughtHandler());
		for(int i = 0; i < 5; i++) {
			executor.execute(new ExceptionTask());
		}
		executor.shutdown();
	}

}
