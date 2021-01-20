package net.stealthcat.test.concurr;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by qianyang on 17-12-13.
 */
public class ConcurrentMapTest {

    private static ConcurrentMap<Integer, Integer> concurrentMap = new ConcurrentSkipListMap<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            testConcurrentPut();
        }
        for (int i = 0; i < 10; i++) {
            testConcurrentGet();
        }
        System.out.println(String.format("ConcurrentMap size = %d, Change concurrentMap to ConcurrentHashMap.", concurrentMap.size()));
        concurrentMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++) {
            testConcurrentPut();
        }
        for (int i = 0; i < 10; i++) {
            testConcurrentGet();
        }
        System.out.println(String.format("concurrentMap size = %d", concurrentMap.size()));
    }



    private static void testConcurrentPut() throws InterruptedException {
        concurrentMap.clear();
        CountDownLatch latch = new CountDownLatch(10);
        final Random random = new Random(47);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    int key = j;
                    concurrentMap.put(key, key);
                }
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println("Put spend time : " + (System.currentTimeMillis() - start));
    }

    private static void testConcurrentGet() throws InterruptedException {
//        for (int i = 0; i < 1000000; i++) {
//            concurrentMap.put(i, i);
//        }
        CountDownLatch latch = new CountDownLatch(10);
        final Random random = new Random(47);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    concurrentMap.get(j);
                }
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println("Get spend time : " + (System.currentTimeMillis() - start));
    }
}
