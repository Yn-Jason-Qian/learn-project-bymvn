package net.stealthcat.test.concurr;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by qianyang on 17-12-13.
 */
public class CompareQueue {

    private static Queue<Integer> queue = new ConcurrentLinkedQueue<>();
//    private static Queue<Integer> queue = new LinkedBlockingQueue<>();
    private static AtomicReference<Integer> max = new AtomicReference<>();
    private static class Taker implements Runnable {

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                Integer str = queue.poll();
                if (str != null) {
                    Integer old = max.get();
                    if (old != null) {
                        if (old.compareTo(str) < 0)
                            max.compareAndSet(old, str);
                    } else {
                        max.compareAndSet(null, str);
                    }
                } else {
                    if (offerOver.get() == 5) {
                        break;
                    }
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        break;
//                    }
                }
            }
            latch.countDown();
        }
    }

    private static CountDownLatch latch;
    private static AtomicInteger count = new AtomicInteger();
    private static volatile AtomicInteger offerOver = new AtomicInteger(0);
    private static class Offer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                queue.add(count.getAndIncrement());
            }
            latch.countDown();
            offerOver.incrementAndGet();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int x = 0; x < 10; x++) {
            latch = new CountDownLatch(15);
            long start = System.currentTimeMillis();
            for (int i = 0; i < 5; i++) {
                new Thread(new Offer()).start();
            }
            ExecutorService executor = Executors.newFixedThreadPool(10);
            for (int i = 0; i < 10; i++) {
                executor.execute(new Taker());
            }
            executor.shutdown();
            latch.await();

            System.out.println("count : " + count.get());
            System.out.println("max : " + max);
            System.out.println("spend time : " + (System.currentTimeMillis() - start));

            count.set(0);
            max.set(null);
            offerOver.set(0);
            System.out.println();
        }
    }

}
