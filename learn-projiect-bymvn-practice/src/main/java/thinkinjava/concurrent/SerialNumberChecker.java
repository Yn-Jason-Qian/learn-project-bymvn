package thinkinjava.concurrent;

import java.util.concurrent.TimeUnit;

public class SerialNumberChecker implements Runnable{
	
	private static class SerialNumberGenerator {
		private static volatile int number;
		public synchronized static int next() {
			return ++number;
		}
	}
	
	private static class CircularSet {
		private int[] cache;
		private int index;
		private int size;
		
		public CircularSet(int size) {
			this.size = size;
			cache = new int[size];
		}
		
		public synchronized void add(int i) {
			cache[index] = i;
			index = (index + 1) % size; 
		}
		
		public synchronized boolean contains(int value) {
			for(int i = index; i > 0; i--) {
				if(cache[index] == value) {
					return true;
				}
			}
			return false;
		}
	}
	
	private CircularSet set = new CircularSet(1000);
	
	@Override
	public void run() {
		while(true) {
			int number = SerialNumberGenerator.next();
			if(set.contains(number)) {
				System.out.println(number);
				System.exit(0);
			}
			set.add(number);
		}
	}

	private static final int SIZE = 10;
	public static void main(String[] args) throws InterruptedException {
		for(int i = 0; i < SIZE; i++) {
			new Thread(new SerialNumberChecker()).start();
		}
		TimeUnit.SECONDS.sleep(10);
		System.out.println("exit normal");
		System.exit(0);
	}
	
}
