package net.stealthcat.test.concurr;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by qianyang on 17-12-20.
 */
public class PSearch {

    private static int threadNum = Runtime.getRuntime().availableProcessors() * 2;

    private static ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

    public static <T> int find(List<T> list, T value) throws ExecutionException, InterruptedException {
//        int moduleSize = list.size() / threadNum;
        int moduleSize = 100000;
        List<Future<Integer>> futures = Lists.newArrayList();
        final AtomicInteger indexHolder = new AtomicInteger(-1);
        for (int i = 0; i < list.size(); i+= moduleSize) {
            int end = i + moduleSize - 1;
            if (end > list.size() - 1) {
                end = list.size() - 1;
            }
            int finalI = i;
            int finalEnd = end;
            futures.add(executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    find(list, value, finalI, finalEnd, indexHolder);
                    return indexHolder.get();
                }
            }));
        }
        for (Future<Integer> future : futures) {
            Integer result = future.get();
            if (result > 0) {
                return result;
            }
        }
        return -1;
    }

    private static <T> void find(List<T> list, T value, int begin, int end, AtomicInteger indexHolder) {
        for (int i = begin; i < end; i++) {
            if (indexHolder.get() > 0) {
                return;
            }
            if (list.get(i).equals(value)) {
                indexHolder.compareAndSet(-1, i);
                return;
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < 1000000; i++) {
            list.add(Integer.toString(i));
        }
        Collections.sort(list);
        long start = System.currentTimeMillis();
        System.out.println(find(list, "249999"));
        System.out.println("time: " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
//        int i = 0;
//        for (String s : list) {
//            if (s.equals("125000")) {
//                System.out.println(i);
//                break;
//            }
//            i++;
//        }
        System.out.println(Collections.binarySearch(list, "249999"));
        System.out.println("time: " + (System.currentTimeMillis() - start));
        System.out.println(list.get(27783));
        System.out.println(list.get(27782));
    }
}
