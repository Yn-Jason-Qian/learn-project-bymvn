package thinkinjava.concurrent.practice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Practice21 {
	
	static class Task1 implements Runnable{

		@Override
		public void run() {
			synchronized(this) {
				try {
					System.out.println("Task1 wait notified!");
					this.wait();
					System.out.println("Task1 was notified!");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	static class Task2 implements Runnable {
		private Task1 task1;
		
		public Task2(Task1 task1) {
			this.task1 = task1;
		}

		@Override
		public void run() {
			synchronized (task1) {
				try {
					task1.wait(100);
					System.out.println("notify all Task on Task1");
					task1.notifyAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		Task1 task1 = new Task1();
		Task2 task2 = new Task2(task1);
		service.execute(task1);
		service.execute(task2);
		service.shutdown();
	}

}
