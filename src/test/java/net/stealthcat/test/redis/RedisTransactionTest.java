package net.stealthcat.test.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qianyang on 18-1-4.
 */
public class RedisTransactionTest {

    private static JedisPool jedisPool = new JedisPool("localhost", 6379);

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        String key = "transaction:test";
        Condition getCondition = lock.newCondition();
        Condition updatedCondition = lock.newCondition();
        Thread updateThread = new Thread(new UpdateTask(getCondition, updatedCondition, key));
        updateThread.start();

        Thread transactionThread = new Thread(new TransactionTask(getCondition,updatedCondition, key));
        transactionThread.start();
        updateThread.join();transactionThread.join();
        Jedis jedis = jedisPool.getResource();
        System.out.println(String.format("Last value = %s.", jedis.get(key)));
        jedis.close();
    }

    private static class UpdateTask implements Runnable{
        private Condition getCondition;
        private Condition updatedCondition;
        private String key;

        public UpdateTask(Condition getCondition, Condition updatedCondition, String key) {
            this.getCondition = getCondition;
            this.updatedCondition = updatedCondition;
            this.key = key;
        }

        @Override
        public void run() {
            lock.lock();
            Jedis jedis = null;
            try {
                getCondition.await();
                System.out.println("Update task was notified!");

                jedis = jedisPool.getResource();
                jedis.set(key, Integer.toString(1));

                System.out.println("Update task over, signal Transaction task.");
                updatedCondition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    private static class TransactionTask implements Runnable{
        private Condition getCondition;
        private Condition updatedCondition;
        private String key;

        public TransactionTask(Condition getCondition, Condition updatedCondition, String key) {
            this.getCondition = getCondition;
            this.updatedCondition = updatedCondition;
            this.key = key;
        }

        @Override
        public void run() {
            lock.lock();
            Jedis jedis = null;
            Transaction transaction = null;
            try {
                jedis = jedisPool.getResource();
                jedis.watch(key);
                String origin = jedis.get(key);
                transaction = jedis.multi();
                System.out.println(String.format("Transaction task has get origin value[%s] and start transaction.", origin));

                getCondition.signal();
                updatedCondition.await();
                System.out.println("Transaction task try to set value.");
                jedis.incr(key);
                transaction.exec();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.discard();
                }
                e.printStackTrace();
            }finally {
                lock.unlock();
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

}
