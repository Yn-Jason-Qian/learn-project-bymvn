package thinkinjava.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Interrupting {
	
	static class BlockMutex {
		private Lock lock = new ReentrantLock();
		public BlockMutex() {
			lock.lock();
		}
		public void f() {
			try {
				lock.lockInterruptibly();
				System.out.println("f method invoke");
			} catch (InterruptedException e) {
				System.out.println("interrupted by Exception");
			}
			System.out.println("f method end");
		}
	}
	
	static class Block implements Runnable {
		BlockMutex mutex = new BlockMutex();
		
		@Override
		public void run() {
			System.out.println("prepare to invoking BlockMutex.f() method");
			mutex.f();
			System.out.println("invoke over");
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<?> future = executor.submit(new Block());
		Thread.sleep(3000);
		future.cancel(true);
		executor.shutdown();
	}

}
