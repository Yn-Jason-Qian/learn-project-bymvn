package net.stealthcat.test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

import org.apache.zookeeper.KeeperException;

import com.google.common.collect.Lists;

import net.stealthcat.rpc.registry.DistributedLock;

public class DistributedLockTest {

	public static void main(String[] args) {
        concurrentTest();
	}

	private static void sequenceTest() {
		DistributedLock lock = null;
		try {
			lock = new DistributedLock("test");
			lock.lock();
			Thread.sleep(10000);
			count++;
		} catch (IOException | KeeperException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		
	}
	
	static int count = 0;
	private static void concurrentTest() {
		final int nodeCount = 3, taskCount = 300;
		final List<DistributedLock> locks = Lists.newArrayList();
		for(int i = 0; i < nodeCount; i++) {
			try {
				locks.add(new DistributedLock("test"));
			} catch (IOException | KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		ExecutorService executor = Executors.newFixedThreadPool(nodeCount * 3);
		final CountDownLatch latch = new CountDownLatch(taskCount);
		for(int i = 0; i < taskCount; i++) {
			final int j = i;
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					Lock lock = locks.get(j % nodeCount);
					try {
						lock.lock();
						count++;
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
						latch.countDown();
					}
				}
			});
		}
		long start = System.currentTimeMillis();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(count);
		System.out.println("time : " + (System.currentTimeMillis() - start));
		executor.shutdown();
		
	}
}
