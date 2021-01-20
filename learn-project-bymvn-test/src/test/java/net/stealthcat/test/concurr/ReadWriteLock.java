package net.stealthcat.test.concurr;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by qianyang on 17-12-12.
 */
public class ReadWriteLock {
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private static ReentrantLock lock = new ReentrantLock();

    private static int value;
    private static Random random = new Random(47);
    private static int threadCount = 20;
    private static int readThreadCount = 18;
    private static CountDownLatch latch = new CountDownLatch(threadCount);

    private static class Reader extends Thread {
        private static int count;
        public Reader() {
            super("Reader" + count++);
        }

        @Override
        public void run() {
            for (int i = 0; i < 1; i++) {
//                readLock.lock();
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + value);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
//                    readLock.unlock();
                    lock.unlock();
                }

            }
            latch.countDown();
        }
    }

    private static class Writer extends Thread {
        private static int count;
        public Writer() {
            super("Writer" + count++);
        }

        @Override
        public void run() {
            for (int i = 0; i < 1; i++) {
//                writeLock.lock();
                lock.lock();
                try {
                    value = random.nextInt();
                    System.out.println(Thread.currentThread().getName() + ":" + value);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
//                    writeLock.unlock();
                    lock.unlock();
                }
            }
            latch.countDown();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < readThreadCount; i++) {
            new Reader().start();
        }
        for (int i = 0; i < threadCount - readThreadCount; i++) {
            new Writer().start();
        }
        latch.await();
        System.out.println(System.currentTimeMillis() - start);
    }

}
