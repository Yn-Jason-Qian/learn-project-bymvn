package net.stealthcat.test.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by qianyang on 18-4-3.
 */
public class ParkTest {

    public static void main(String[] args) {
        Thread parkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LockSupport.park();
                    System.out.println(Thread.currentThread().getName() + " has been unpark, interrupted = " + Thread.currentThread().isInterrupted());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        parkThread.start();
        parkThread.interrupt();
    }


}
