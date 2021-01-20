package net.stealthcat.test.concurr;

import java.util.concurrent.*;

/**
 * Created by qianyang on 17-12-28.
 */
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {
    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(new WrappedRunnable(new Exception(String.format("Thread[%s]", Thread.currentThread().getName())), command));
    }

    private static class WrappedRunnable implements Runnable {
        private Exception stack;
        private Runnable runnable;
        public WrappedRunnable(Exception stack, Runnable runnable) {
            this.stack = stack;
            this.runnable = runnable;
        }

        @Override
        public void run() throws WrappedException {
            try {
                runnable.run();
            } catch (Exception e) {
                throw new WrappedException(e, stack);
            }
        }
    }

    private static class WrappedException extends RuntimeException {
        private Exception submitStack;

        public WrappedException(Exception submitStack) {
            this.submitStack = submitStack;
        }

        public WrappedException(String message, Exception submitStack) {
            super(message);
            this.submitStack = submitStack;
        }

        public WrappedException(String message, Throwable cause, Exception submitStack) {
            super(message, cause);
            this.submitStack = submitStack;
        }

        public WrappedException(Throwable cause, Exception submitStack) {
            super(cause);
            this.submitStack = submitStack;
        }

        public WrappedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Exception submitStack) {
            super(message, cause, enableSuppression, writableStackTrace);
            this.submitStack = submitStack;
        }

        public Exception getSubmitStack() {
            return submitStack;
        }

        public void setSubmitStack(Exception submitStack) {
            this.submitStack = submitStack;
        }
    }

    private static class WrappedThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler((t, e) -> {
                if (e instanceof WrappedException) {
                    ((WrappedException) e).getSubmitStack().printStackTrace();
                    e.printStackTrace();
                }
            });
            return thread;
        }
    }

    public static void main(String[] args) {
        TraceThreadPoolExecutor executor = new TraceThreadPoolExecutor(
                10, 10, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new WrappedThreadFactory());
        executor.execute(() -> {
            int i = 1 / 0;
        });
    }
}
