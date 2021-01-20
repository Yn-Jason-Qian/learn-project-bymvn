package thinkinjava.concurrent.practice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Practice22 {
	
	private static boolean flag = false;
	
	
	static class AlterFlagTask implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			flag = true;
			System.out.println("AlterFlagTask: set flag true!");
		}
		
	}
	
	static class BusyWaiting implements Runnable {
		
		private int count = 0;
		
		@Override
		public void run() {
			while(flag == false) {
				try {
					Thread.sleep(5);
					count++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			flag = false;
			System.out.println("BusyWaiting: set flag false, do while count: " + count);
		}
		
	}
	
	static class FlagObj {
		private volatile boolean flag = false;
		public void setTrue() {
			flag = true;
		}
		public void setFalse() {
			flag = false;
		}
		public boolean isTrue() {
			return flag;
		}
	}
	
	static FlagObj flagObj = new FlagObj();
	
	static class AlterFlagTaskAndNotify implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (flagObj) {
				flagObj.setTrue();
				flagObj.notifyAll();
			}
			System.out.println("AlterFlagTaskAndNotify: set flag true!");
		}
		
	}
	
	static class WaitingNotifiedTask implements Runnable {
		
		private int count;

		@Override
		public void run() {
			synchronized (flagObj) {
				while(!flagObj.isTrue()) {
					try {
						flagObj.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					count++;
					flagObj.setFalse();
				}
			}
			System.out.println("WaitingNotifiedTask: set flag false, do while count: " + count);
		}
		
	}
 
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		AlterFlagTask task1 = new AlterFlagTask();
		BusyWaiting task2 = new BusyWaiting();
		service.execute(task1);
		service.execute(task2);
		AlterFlagTaskAndNotify task3 = new AlterFlagTaskAndNotify();
		WaitingNotifiedTask task4 = new WaitingNotifiedTask();
		service.execute(task3);
		service.execute(task4);
		service.shutdown();
	}
	
}
