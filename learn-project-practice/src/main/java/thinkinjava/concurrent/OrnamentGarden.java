package thinkinjava.concurrent;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class OrnamentGarden {
	
	static class Count {
		private int number;
		public synchronized void increment() {
			number++;
		}
		
		public synchronized int getValue() {
			return number;
		}
	}
	
	static class Entrance implements Runnable {
		private static List<Entrance> entrances = Lists.newArrayList();
		private final int id;
		private int number;
		private static Count count = new Count();
		private static volatile boolean canceled = false;
		private CountDownLatch latch;
		public Entrance(int id, CountDownLatch latch) {
			this.id = id;
			entrances.add(this);
			this.latch = latch;
		}

		@Override
		public void run() {
			while(!canceled) {
				number++;
				count.increment();
				System.out.println(this + ", totalCount: " + count.getValue());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(this + " stoping!");
			latch.countDown();
		}
		
		public static void cancel() {canceled = true;}
		
		public static int sumCount() {
			int totalCount = 0;
			for(Entrance entrance : entrances) {
				totalCount += entrance.number;
			}
			return totalCount;
		}
		
		public static int getTotalCount() {
			return count.getValue();
		}

		@Override
		public String toString() {
			return String.format("Entrance [id=%s, number=%s]", id, number);
		}
		
	}

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(5);
		for(int i = 0; i < 5; i++) {
			new Thread(new Entrance(i, latch)).start();
		}
		try {
			Thread.sleep(3000);
			Entrance.cancel();
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("totalCount: " + Entrance.getTotalCount());
		System.out.println("sumCount: " + Entrance.sumCount());
	}

}
