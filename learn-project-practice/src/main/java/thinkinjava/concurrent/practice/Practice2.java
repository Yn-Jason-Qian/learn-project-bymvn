package thinkinjava.concurrent.practice;

import thinkinjava.generics.Fibonacci;

public class Practice2 {

	public static void main(String[] args) {
		for(int i = 0; i < 5; i++) {
			new Thread(new Task(20)).start();
		}
	}
	
	private static class Task implements Runnable {
		private final int n;
		private final Fibonacci fibonacci;
		private static int taskCount = 0;
		private final int id = ++taskCount;
		public Task(int n) {
			this.n = n;
			fibonacci = new Fibonacci();
		}

		@Override
		public void run() {
			for(int i = 0; i < n; i++) {
				System.out.println(String.format("task(%s), fibonacci#%s", id, fibonacci.next()));
			}
		}
		
	}

}
