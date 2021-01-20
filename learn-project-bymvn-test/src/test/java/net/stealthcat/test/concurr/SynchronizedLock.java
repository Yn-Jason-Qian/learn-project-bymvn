package net.stealthcat.test.concurr;

/**
 * Created by qianyang on 17-12-12.
 */
public class SynchronizedLock implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 10000000; i++) {
            synchronized (SynchronizedLock.class) {
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread1 = new Thread(new SynchronizedLock());
        Thread thread2 = new Thread(new SynchronizedLock());
        thread1.start();thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(System.currentTimeMillis() - start);
    }
}
