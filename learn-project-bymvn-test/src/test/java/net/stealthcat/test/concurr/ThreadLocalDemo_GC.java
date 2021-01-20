package net.stealthcat.test.concurr;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qianyang on 17-12-14.
 */
public class ThreadLocalDemo_GC {
    private static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + " is gc");
        }
    };
    private static List<SimpleDateFormat> formats = Lists.newArrayList();

    private static CountDownLatch latch;
    private static class ParseDate implements Runnable {

        @Override
        public void run() {
            try {
                if (tl.get() == null) {
                    tl.set(new SimpleDateFormat("yyyy-MM-dd") {
                        @Override
                        protected void finalize() throws Throwable {
                            System.out.println(this.toString() + "is gc");
                        }
                    });
                    formats.add(tl.get());
                    System.out.println(Thread.currentThread().getId() + " create simpleDateformat:" + tl.get());
                }
                tl.get().format(new Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        latch = new CountDownLatch(1000);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new ParseDate());
        }
        latch.await();
        System.out.println(String.format("format equals : %s", formats.get(0) == formats.get(1)));
        tl = null;
        System.gc();
        tl = new ThreadLocal<>();
        latch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new ParseDate());
        }
        latch.await();
        Thread.sleep(10000);
//        executorService.shutdown();
        System.gc();
    }
}
