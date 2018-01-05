package net.stealthcat.test.redis;

import com.google.common.base.Stopwatch;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qianyang on 18-1-4.
 */
public class RedisCASPerformanceTest {
    private static JedisPool jedisPool = new JedisPool("localhost");
    private static String key = "jedis:cas:test";
    private static CountDownLatch latch;

    private static class CasTask implements Runnable{

        @Override
        public void run() {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                atomicIncr(jedis, key);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            latch.countDown();
        }
    }

    public static void atomicIncr(Jedis jedis, String key) {
        if (jedis.get(key) == null) {
            throw new IllegalStateException(String.format("Element of key[%s] has not init now.", key));
        }
        while (true) {
            Transaction transaction = null;
            try {
                jedis.watch(key);
                transaction = jedis.multi();
                transaction.incr(key);
                if (transaction.exec().size() > 0) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.discard();
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e1) {
                    break;
                }
            }
        }
    }

    public static boolean compareAndSet(Jedis jedis, String key, Object origin, Object expected, String2ObjConverter converter) {
        if (jedis.get(key) == null) {
            throw new IllegalStateException(String.format("Element of key[%s] has not init now.", key));
        }
        Transaction transaction = null;
        try {
            jedis.watch(key);
            if (!converter.convert(jedis.get(key)).equals(origin)) {
                return false;
            }
            transaction = jedis.multi();
            transaction.set(key, expected.toString());
            return transaction.exec().size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.discard();
            }
            return false;
        }
    }

    public interface String2ObjConverter {
        Object convert(String strValue);
    }

    public static class ConsumerCabinTask implements Runnable {
        private String key;

        public ConsumerCabinTask(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            Jedis jedis = jedisPool.getResource();
            while (true) {
                int cabin = Integer.parseInt(jedis.get(key));
                if (cabin > 0) {
                    if (compareAndSet(jedis, key, cabin, cabin - 1, Integer::parseInt)) {
                        break;
                    }
                } else {
                    System.out.println("Cabin has been over.");
                    break;
                }
            }
            jedis.close();
            latch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        testCompareAndSet();
    }

    private static void testCompareAndSet() throws InterruptedException {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, Integer.toString(500));
        int count = 500;
        latch = new CountDownLatch(count);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Stopwatch watch = Stopwatch.createStarted();
        for (int i = 0; i < count; i++) {
            executor.execute(new ConsumerCabinTask(key));
        }
        latch.await();
        System.out.println(watch.stop().toString());
        System.out.println(jedis.get(key));
        jedis.close();
        executor.shutdown();
    }

    private static void test () throws InterruptedException {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, Integer.toString(0));
        int count = 500;
        latch = new CountDownLatch(count);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Stopwatch watch = Stopwatch.createStarted();
        for (int i = 0; i < count; i++) {
            executor.execute(new CasTask());
        }
        latch.await();
        System.out.println(watch.stop().toString());
        System.out.println(jedis.get(key));
        jedis.close();
        executor.shutdown();
    }

}
