package thinkinjava.concurrent.practice;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Practice11 {
	
	private static class Holder {
		private int field1 = 0;
		
		private int field2 = 0;
		
		public synchronized void increaseField() {
			field1++;
			field1++;
			field2++;
			field2++;
		}
		
		public synchronized void readField() {
			System.out.println("field1 : " + field1);
			System.out.println("field2 : " + field2);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final Holder holder = new Holder();
		List<Thread> threads = Lists.newArrayList();
		for(int i = 0; i < 5; i++) {
			Thread operThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(!Thread.currentThread().isInterrupted()) {
						holder.increaseField();
					}
					
				}
			});
			threads.add(operThread);
			operThread.start();
		}
		
		for(int i = 0; i < 5; i++) {
			Thread readThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(!Thread.currentThread().isInterrupted()) {
						holder.readField();
					}
				}
			});
			threads.add(readThread);
			readThread.start();
		}
		TimeUnit.SECONDS.sleep(1);
		for(Thread thread : threads) {
			thread.interrupt();
		}
	}

}
