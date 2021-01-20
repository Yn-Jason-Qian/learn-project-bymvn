package net.stealthcat.test.concurr;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.concurrent.*;

/**
 * Created by qianyang on 17-12-15.
 */
public class ConsumerAndProducer {

    private static class ConsumerByDisruptor implements WorkHandler<PCData> {

        private int id;

        ConsumerByDisruptor(int id) {
            this.id = id;
        }

        @Override
        public void onEvent(PCData event) throws Exception {
            System.out.println(event.id);
            latch.countDown();
        }
    }

    private static class PCDataFactory implements EventFactory<PCData> {

        @Override
        public PCData newInstance() {
            return new PCData();
        }
    }

    private static class Producer {
        private RingBuffer<PCData> ringBuffer;
        public Producer(RingBuffer<PCData> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        public void pushData(ByteBuffer byteBuffer) {
            long sequence = ringBuffer.next();
            try {
                PCData event = ringBuffer.get(sequence);
                event.id = byteBuffer.getInt(0);
            } finally {
                ringBuffer.publish(sequence);
            }
        }
    }

    private static CountDownLatch latch;

    private static void testDisruptor() throws InterruptedException {
        int bufferSize = 8;
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Disruptor<PCData> disruptor = new Disruptor<PCData>(new PCDataFactory(),
                bufferSize, executor, ProducerType.MULTI, new SleepingWaitStrategy());
        disruptor.handleEventsWithWorkerPool(new ConsumerByDisruptor(0),
                new ConsumerByDisruptor(1),
                new ConsumerByDisruptor(2),
                new ConsumerByDisruptor(3)
        );
        disruptor.start();

        RingBuffer<PCData> ringBuffer = disruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        int count = 10000;
        latch = new CountDownLatch(count);
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            byteBuffer.putInt(0, i);
            producer.pushData(byteBuffer);
//            Thread.sleep(100);
//            System.out.println("add data " + i);
        }
        latch.await();
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void main(String[] args) throws InterruptedException {
//        testDisruptor();
        testBlockingQueue();
    }


    private static class PCData {
        int id;
    }

    private static void testBlockingQueue() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            service.execute(new ConsumerByBlockingQueue());
        }
        int count = 10000;
        latch = new CountDownLatch(count);
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            PCData da = new PCData();
            da.id = i;
            blockingQueue.put(da);
        }
        latch.await();
        System.out.println(System.currentTimeMillis() - start);
    }

    private static BlockingQueue<PCData> blockingQueue = new LinkedBlockingQueue<>();
    private static class ConsumerByBlockingQueue implements Runnable{

        @Override
        public void run() {
            while (true) {
                try {
                    PCData data = blockingQueue.take();
                    System.out.println(data.id);
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
