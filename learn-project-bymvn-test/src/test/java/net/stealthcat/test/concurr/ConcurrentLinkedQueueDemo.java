package net.stealthcat.test.concurr;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by qianyang on 17-12-13.
 */
public class ConcurrentLinkedQueueDemo {
//    private static Queue<String> queue = new ConcurrentLinkedQueue<>();
    private static Queue<String> queue = new LinkedBlockingQueue<>();
    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            queue.add(Integer.toString(i));
        }
        CountDownLatch latch = new CountDownLatch(10);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                long start1 = System.currentTimeMillis();
                while (queue.poll() != null) {

                }
                System.out.println(System.currentTimeMillis() - start1);
                latch.countDown();
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
    }


}
