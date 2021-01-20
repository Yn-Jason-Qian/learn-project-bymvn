package thinkinjava.concurrent.practice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Practice24 {

	static class Store {
		private int count;
		private final int capacity;
		private final int center;
		public Store(int capacity) {
			this.capacity = capacity;
			center = capacity / 2;
		}
		public synchronized void add(int i) throws InterruptedException {
			int temp = count + i;
			if(temp > capacity) {
				wait();
				return;
			}
			if(temp >= center) {
				notifyAll();
			}
			count = temp;
			System.out.printf("%s add, count: %s%n", Thread.currentThread().getName(), count);
		}
		public synchronized void remove(int i) throws InterruptedException {
			int temp = count - i;
			if(temp < 0) {
				wait();
				return;
			}
			if(temp <= center) {
				notifyAll();
			}
			count = temp;
			System.out.printf("%s remove, count: %s%n", Thread.currentThread().getName(), count);
		}
	}
	
	static class Productor implements Runnable {
		private Store store;
		
		public Productor(Store store) {
			this.store = store;
		}

		@Override
		public void run() {
			try {
				while(!Thread.interrupted()) {
					store.add(1);
				}
				System.out.println(Thread.currentThread().getName() + "interrupted!");
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + "InterruptedException！");
			}
		}
	} 
	
	static class Consumer implements Runnable {
		private Store store;

		public Consumer(Store store) {
			this.store = store;
		}

		@Override
		public void run() {
			try {
				while(!Thread.interrupted()) {
					store.remove(1);
				}
				System.out.println(Thread.currentThread().getName() + "interrupted!");
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + "InterruptedException！");
			}
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		Store store = new Store(100);
		ExecutorService service = Executors.newCachedThreadPool();
		for(int i = 0; i < 10; i++) {
			service.execute(new Productor(store));
			service.execute(new Consumer(store));
		}
		Thread.sleep(3000);
		service.shutdownNow();
	}
	
}
