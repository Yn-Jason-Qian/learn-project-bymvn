package net.stealthcat.test.concurr;

/**
 * Created by qianyang on 17-12-12.
 */
public class SuspendDemo implements Runnable {
    private static Object lockObj = new Object();

    @Override
    public void run() {
        synchronized (lockObj) {
            Thread.currentThread().suspend();
            System.out.println(Thread.currentThread().getName() + " run over.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new SuspendDemo());
        Thread thread2 = new Thread(new SuspendDemo());
        thread1.start();
        Thread.sleep(1000);
        thread2.start();
        thread2.resume();
        thread1.resume();
    }
}
