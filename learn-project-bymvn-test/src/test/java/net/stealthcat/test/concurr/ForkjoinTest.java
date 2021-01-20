package net.stealthcat.test.concurr;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.StopWatch;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by qianyang on 17-12-26.
 */
public class ForkjoinTest {

    private static class TaskA implements Callable<Integer> {

        @Override
        public Integer call() {
            return compute();
        }
    }

    private static class TaskB extends RecursiveTask<Integer> {

        @Override
        protected Integer compute() {
            return ForkjoinTest.compute();
        }
    }

    private static Random random = new Random(47);
    private static int compute() {
        try {
            TimeUnit.NANOSECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return random.nextInt(1 << 16) / 57;
    }

    static int taskCount = 100000;
    static int threadCount = Runtime.getRuntime().availableProcessors();
    private static void useThreadExecutorPool() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        List<Future<Integer>> futures = Lists.newArrayList();
        StopWatch stopWatch = StopWatch.createStarted();
        for (int i = 0; i < taskCount; i++) {
            futures.add(service.submit(new TaskA()));
        }
        for (Future<Integer> future : futures) {
            future.get();
        }
        stopWatch.stop();
        System.out.println(stopWatch.toString());
        service.shutdown();
    }

    private static void useForkJoinPool() throws ExecutionException, InterruptedException {
        ForkJoinPool service = new ForkJoinPool(threadCount);
        List<Future<Integer>> futures = Lists.newArrayList();
        StopWatch stopWatch = StopWatch.createStarted();
        for (int i = 0; i < taskCount; i++) {
            futures.add(service.submit(new TaskB()));
        }
        for (Future<Integer> future : futures) {
            future.get();
        }
        stopWatch.stop();
        System.out.println(stopWatch.toString());
        service.shutdown();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        useThreadExecutorPool();
        useForkJoinPool();
//        StopWatch stopWatch = StopWatch.createStarted();
//        for (int i = 0; i < taskCount; i++) {
//            compute();
//        }
//        stopWatch.stop();
//        System.out.println(stopWatch.toString());
    }

}
