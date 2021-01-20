package net.stealthcat.test.concurr;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qianyang on 17-12-12.
 */
public class ConditionLock {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition trueNow = lock.newCondition();
    private static Condition falseNow = lock.newCondition();
    private static boolean flag;

    private static class MakeTrue implements Runnable {

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                lock.lock();
                try {
                    if (flag) {
                        trueNow.await();
                    }
                    flag = true;
                    System.out.println("make true");
                    falseNow.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                } finally {
                    lock.unlock();
                }
            }
            System.out.println("MakeTrue over.");
        }
    }

    private static class MakeFalse implements Runnable {

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                lock.lock();
                try {
                    if (!flag) {
                        falseNow.await();
                    }
                    flag = false;
                    System.out.println("make false");
                    trueNow.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                } finally {
                    lock.unlock();
                }
            }
            System.out.println("Makefalse over.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new MakeTrue());
        Thread thread2 = new Thread(new MakeFalse());
        thread1.start();
        thread2.start();
        Thread.sleep(1000);
        thread1.interrupt();
        thread2.interrupt();
    }

}
