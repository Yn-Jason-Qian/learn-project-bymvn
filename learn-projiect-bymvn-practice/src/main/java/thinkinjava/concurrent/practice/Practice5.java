package thinkinjava.concurrent.practice;

import com.google.common.collect.Lists;
import thinkinjava.generics.Fibonacci;

import java.util.List;
import java.util.concurrent.*;

public class Practice5 {

	public static void main(String[] args) {
		List<Future<Integer>> futures = Lists.newArrayList();
		ExecutorService executor = Executors.newCachedThreadPool();
		for(int i = 0; i < 5; i++) {
			futures.add(executor.submit(new TaskWithResult(20)));
		}
		executor.shutdown();
		for(Future<Integer> future : futures) {
			try {
				System.out.println(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	private static class TaskWithResult implements Callable<Integer> {
		private final int n;
		private final Fibonacci fibonacci = new Fibonacci();
		public TaskWithResult(int n) {
			this.n = n;
		}
		
		@Override
		public Integer call() throws Exception {
			int sum = 0;
			for(int i = 0; i < n; i++) {
				sum += fibonacci.next();
			}
			return sum;
		}
		
	}
}
