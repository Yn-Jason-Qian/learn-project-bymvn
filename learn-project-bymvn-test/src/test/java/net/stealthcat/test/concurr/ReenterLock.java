package net.stealthcat.test.concurr;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qianyang on 17-12-12.
 */
public class ReenterLock implements Runnable{
    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        for (int i = 0; i < 10000000; i++) {
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread1 = new Thread(new ReenterLock());
        Thread thread2 = new Thread(new ReenterLock());
        thread1.start();thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(System.currentTimeMillis() - start);
    }
}
