package net.stealthcat.test.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class DisruptorTest {
    private static final int BUFFER_SIZE = 256;
    private static final int WORKER_SIZE = 10;
    private RingBuffer<Event> ringBuffer;

    @Data
    private static class Event {
        private String type;

        private String msg;
    }



    private static void preHandle (Event event, long sequence, boolean endOfBatch) {
        System.out.println("PRE_HANDLE: msgType: " + event.getType() + ", msg: " + event.getMsg() + ", sequence: " + sequence);
    }

    private static void afterHandle (Event event, long sequence, boolean endOfBatch) {
        System.out.println("AFTER_HANDLE: msgType: " + event.getType() + ", msg: " + event.getMsg() + ", sequence: " + sequence);
    }

    private static class Worker implements WorkHandler<Event> {

        @Override
        public void onEvent(Event event) throws Exception {
            System.out.println("WORK: msgType: " + event.getType() + ", msg: " + event.getMsg());
        }
    }

    /**
     * 模拟canal线程优化，了解barrier的工作机制
     */
//    @Before
    public void init() {
        ringBuffer = RingBuffer.createSingleProducer(Event::new, BUFFER_SIZE, new BlockingWaitStrategy());

        // pre handle
        SequenceBarrier preBarrier = ringBuffer.newBarrier();

        BatchEventProcessor<Event> preHandle = new BatchEventProcessor<>(ringBuffer, preBarrier, DisruptorTest::preHandle);
        ringBuffer.addGatingSequences(preHandle.getSequence());

        // true work
        WorkHandler<Event>[] workHandlers = new Worker[WORKER_SIZE];
        for (int i = 0; i < workHandlers.length; i++) {
            workHandlers[i] = new Worker();
        }
        SequenceBarrier workBarrier = ringBuffer.newBarrier(preHandle.getSequence());
        WorkerPool<Event> workerPool = new WorkerPool<>(ringBuffer, workBarrier, new FatalExceptionHandler(), workHandlers);
        Sequence[] workerSequences = workerPool.getWorkerSequences();
        ringBuffer.addGatingSequences(workerSequences);

        // after handle
        SequenceBarrier afterBarrier = ringBuffer.newBarrier(workerSequences);
        BatchEventProcessor<Event> afterHandle = new BatchEventProcessor<>(ringBuffer, afterBarrier, DisruptorTest::afterHandle);
        ringBuffer.addGatingSequences(afterHandle.getSequence());

        // submit
        ExecutorService aspectExecutor = Executors.newFixedThreadPool(2);
        aspectExecutor.submit(preHandle);
        aspectExecutor.submit(afterHandle);

        ExecutorService workExecutor = Executors.newFixedThreadPool(WORKER_SIZE);
        workerPool.start(workExecutor);
    }

    @Before
    public void init0 () {
        Disruptor<Event> disruptor = new Disruptor<>(Event::new, BUFFER_SIZE, (ThreadFactory) Thread::new, ProducerType.SINGLE, new BlockingWaitStrategy());
        this.ringBuffer = disruptor.getRingBuffer();

        WorkHandler<Event>[] workHandlers = new Worker[WORKER_SIZE];
        for (int i = 0; i < workHandlers.length; i++) {
            workHandlers[i] = new Worker();
        }
        disruptor.handleEventsWith(DisruptorTest::preHandle).thenHandleEventsWithWorkerPool(workHandlers).then(DisruptorTest::afterHandle);
        disruptor.start();
    }

    @Test
    public void test () {
        String[] msgTypes = new String[]{"click", "fade", "dbClick"};
        for (int i = 0; i < 1000; i++) {
            String type = msgTypes[i % msgTypes.length];
            ringBuffer.publishEvent(DisruptorTest::translate, i + "", type);
        }
    }

    private static void translate (Event event, long sequence, String msg, String type) {
        event.setMsg(msg);
        event.setType(type);
    }

}
