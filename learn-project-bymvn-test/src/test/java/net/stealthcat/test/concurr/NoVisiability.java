package net.stealthcat.test.concurr;

/**
 * Created by qianyang on 17-12-11.
 */
public class NoVisiability {
    //volatile
    private static boolean ready;
    private static int count;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready);
            System.out.println(count);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(1000);
        count = 57;
        ready = true;
        Thread.sleep(10000);
    }
}
