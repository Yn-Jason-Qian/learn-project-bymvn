package net.stealthcat.test.jvm;

import java.util.Random;

public class ConsumeSystemCpu {
	
	private static int count = 100;

	private static Object[] locks = new Object[100];
	
	private static Random r = new Random();
	
	public static void main(String[] args) {
		for(int i=0;i<count;i++) {
			locks[i] = new Object();
		}
		
		for(int i=0;i<count;i++) {
			new Thread(new WaitThread(i)).start();
			new Thread(new NotifyThread(i)).start();
		}
	}

	static class WaitThread implements Runnable {

		private Object lock;
		
		WaitThread(int index) {
			lock = locks[index];
		}
		
		@Override
		public void run() {
			while(true) {
				synchronized (lock) {
					try {
						lock.wait(r.nextInt(10));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	static class NotifyThread implements Runnable {

		private Object lock;
		
		NotifyThread(int index) {
			lock = locks[index];
		}
		
		@Override
		public void run() {
			while(true) {
				synchronized (lock) {
					lock.notifyAll();
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
