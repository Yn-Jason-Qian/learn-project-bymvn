package thinkinjava.concurrent;

public class DeadLock {

	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	
	//互斥死锁
	static class MutexTask1 implements Runnable{

		@Override
		public void run() {
			while(!Thread.interrupted()) {
				synchronized (lock1) {
					System.out.println("task1 get lock1");
					synchronized (lock2) {
						System.out.println("task1 get lock2");
					}
				}
			}
		}
		
	}
	
	static class MutexTask2 implements Runnable{

		@Override
		public void run() {
			while(!Thread.interrupted()) {
				synchronized (lock2) {
					System.out.println("task2 get lock2");
					synchronized (lock1) {
						System.out.println("task2 get lock1");
					}
				}
			}
		}
		
	}
	
	//条件改变与判断不在同步块类，导致死锁
	static boolean waiting = false;
	static class WaitTask1 implements Runnable {

		@Override
		public void run() {
			try {
				while(!Thread.interrupted()) {
					while(!waiting) {
						waiting = true;
						synchronized(lock1) {
							lock1.wait();
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	static class WaitTask2 implements Runnable {

		@Override
		public void run() {
			while(!Thread.interrupted()) {
				while(waiting) {
					synchronized(lock1) {
						lock1.notifyAll();
					}
					waiting = false;
				}
			}
		}
		
	}

	public static void main(String[] args) {
//		new Thread(new MutexTask1()).start();
//		new Thread(new MutexTask2()).start();
		new Thread(new WaitTask1()).start();
		new Thread(new WaitTask2()).start();
	}

}
