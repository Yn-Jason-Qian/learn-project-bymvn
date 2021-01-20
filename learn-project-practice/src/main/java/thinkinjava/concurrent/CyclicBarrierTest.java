package thinkinjava.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
	
	static CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {
		
		@Override
		public void run() {
			System.out.println("barrier action!");
		}
	});
	
	static class Sleeper implements Runnable {

		@Override
		public void run() {
			while(!Thread.interrupted()) {
				System.out.println("Sleeper start！");
				try {
					Thread.sleep(100);
					System.out.println("Sleeper end!");
					barrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	static class Joineder implements Runnable {

		@Override
		public void run() {
			while(!Thread.interrupted()) {
				System.out.println("Joineder start!");
				try {
					barrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					System.out.println("barrier Exception");
				}
				System.out.println("Joineder end!");
			}
		}
	}

	public static void main(String[] args) {
		new Thread(new Sleeper()).start();
		new Thread(new Joineder()).start();
	}

}
