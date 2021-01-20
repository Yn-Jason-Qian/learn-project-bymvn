package thinkinjava.concurrent.practice;

public class Practice1 {

	public static void main(String[] args) {
		for(int i = 0; i < 10; i++) {
			new Thread(new Task()).start();
		}
	}
	
	private static class Task implements Runnable {
		private static int taskCount = 0;
		private final int id = ++taskCount;
		
		public Task() {
			System.out.println(String.format("task(%s) start!", id));
		}

		@Override
		public void run() {
			for(int i = 0; i < 3; i++) {
				System.out.println(String.format("task(%s) invoke", id));
			}
			System.out.println(String.format("task(%s) end!", id));
		}
		
	}

}
