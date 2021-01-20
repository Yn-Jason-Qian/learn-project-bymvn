package net.stealthcat.test.concurr;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Long> {
    private static final long THRESHOLD = 10000;
    private long start;
    private long end;

    public CountTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long size = end - start + 1;
        long sum = 0;
        if (size <= THRESHOLD) {
            for (long i = start; i <= end; i++) {
                sum+= i;
            }
        } else {
            List<CountTask> tasks = Lists.newArrayList();
            long step = size / 100;
            long firstOne = start;
            for (int i = 0; i <= 100; i++) {
                long lastOne = firstOne + step - 1;
                if (lastOne > end) {
                    lastOne = end;
                }
                CountTask task = new CountTask(firstOne, lastOne);
                tasks.add(task);
                task.fork();
                firstOne = lastOne + 1;
            }

            for (CountTask task : tasks) {
                sum += task.join();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        CountTask task = new CountTask(0, 20000001L);
        ForkJoinTask<Long> result = pool.submit(task);
        try {
            System.out.println(result.get());
            System.out.println(task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        long sum = 0;
        for (long i = 0; i <= 20000001L; i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - start);
    }
}
