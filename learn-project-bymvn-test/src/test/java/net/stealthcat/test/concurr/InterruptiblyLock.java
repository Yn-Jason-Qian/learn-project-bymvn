package net.stealthcat.test.concurr;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qianyang on 17-12-11.
 */
public class InterruptiblyLock implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();
    private boolean locked;

    @Override
    public void run() {
        try {
            lock.lockInterruptibly();
            locked = true;
            Thread.sleep(6000);
            System.out.println(Thread.currentThread().getName() + " run over!");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interrupted!");
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread())
                lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        InterruptiblyLock lock1 = new InterruptiblyLock();
        InterruptiblyLock lock2 = new InterruptiblyLock();
        Thread thread1 = new Thread(lock1, "lock1");
        Thread thread2 = new Thread(lock2, "lock2");
        thread1.start();
        thread2.start();
        Thread.sleep(5000);
        if (!lock1.locked) {
            thread1.interrupt();
        }
        if (!lock2.locked) {
            thread2.interrupt();
        }
    }
}
