package net.stealthcat.test.concurr;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by qianyang on 17-12-12.
 */
public class CyclicBarrierDemo extends Thread{

    private static CyclicBarrier cyclicBarrier;

    private static int count;

    public CyclicBarrierDemo() {
        super("CyclicThread" + count++);
    }

    @Override
    public void run() {
        try {
            cyclicBarrier.await();
            doWork();
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        
    }

    private void doWork() {
        try {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + ", Work done.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class BarrierRun implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ", Barrier down.");
        }
    }

    public static void main(String[] args) {
        cyclicBarrier = new CyclicBarrier(10, new BarrierRun());
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new CyclicBarrierDemo();
            threads[i].start();
            if (i == 5) {
//                threads[0].interrupt();
            }
        }
    }
}
