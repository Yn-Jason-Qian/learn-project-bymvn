package net.stealthcat.test.concurr;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by qianyang on 17-12-12.
 */
public class LockSupportDemo implements Runnable{
    private static Object lock = new Object();

    @Override
    public void run() {
        System.out.println("Sleep over, ready to park.");
        synchronized (lock) {
            LockSupport.park();
        }
        if (Thread.interrupted()) {
            System.out.println(String.format("%s is interrupted.", Thread.currentThread().getName()));
        }
        System.out.println(Thread.currentThread().getName() + " parkDown");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread parkThread = new Thread(new LockSupportDemo());
        parkThread.start();
//        parkThread.interrupt();
        Thread.sleep(1000);
        synchronized (lock) {
            LockSupport.unpark(parkThread);
            System.out.println(String.format("Unpark thread[%s]", parkThread.getName()));
        }
    }

}
