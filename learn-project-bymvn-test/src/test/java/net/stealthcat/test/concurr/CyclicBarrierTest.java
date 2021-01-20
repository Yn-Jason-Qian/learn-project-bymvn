package net.stealthcat.test.concurr;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierTest {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger count = new AtomicInteger();
        CyclicBarrier barrier = new CyclicBarrier(10, () -> {
            System.out.println("Barrier count=" + count.getAndIncrement());
        });
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(100);
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
        Thread.sleep(10000);
    }
}
