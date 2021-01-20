package net.stealthcat.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadLocal {
	
	private static class ThreadLocalHolder {
		private static ThreadLocal<String> local = new ThreadLocal<String>();
	}
	
	private static class InheriatableThreadLocalHolder {
		private static InheritableThreadLocal<String> local = new InheritableThreadLocal<String>();
	}

	private static ExecutorService executor = Executors.newFixedThreadPool(1);
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		InheriatableThreadLocalHolder.local.set("abc");
		
		Callable<String> task = new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				return InheriatableThreadLocalHolder.local.get();
			}
		};
		System.out.println(executor.submit(task).get());
		
		InheriatableThreadLocalHolder.local.set("bac");
		
		Callable<String> task2 = new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				return InheriatableThreadLocalHolder.local.get();
			}
		};
		System.out.println(executor.submit(task2).get());
		executor.shutdown();
	}
	
}
