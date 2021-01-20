package net.stealthcat.test;

public class TestNotify {
	
	private static Object lock = new Object();

	public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<5;i++) {
			new Thread(new Task(i)).start();
		}
		Thread.sleep(50);
		synchronized (lock) {
			lock.notifyAll();
		}
	}
	
	static class Task implements Runnable {

		private int num;
		
		public Task(int num) {
			this.num = num;
		}

		@Override
		public void run() {
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(int i=0;i<3;i++) {
					System.out.println(num);
				}
			}
		}
		
	}

}
